package ru.fintech.db.contragentsearch17.presenters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.fintech.db.contragentsearch17.AppModule
import ru.fintech.db.contragentsearch17.R
import ru.fintech.db.contragentsearch17.dataModel.Organization
import ru.fintech.db.contragentsearch17.db.CacheInterface
import ru.fintech.db.contragentsearch17.inet.DaDataApi
import ru.fintech.db.contragentsearch17.utils.UnitTask
import ru.fintech.db.contragentsearch17.utils.Utils
import javax.inject.Inject

/**
 * Created by DB on 07.12.2017.
 *
 */
class DetailsPresenter  (val ctx: Context,
                         frag: Int,
                         val name: Int,
                         val value: Int) : ArrayAdapter<Map.Entry<Int, String>>(ctx, frag, name)
{
    @Inject lateinit var svc : CacheInterface
    @Inject lateinit var www : DaDataApi
    init {
        AppModule.injector.inject(this)
    }
    var org = Organization()
    lateinit var fav: MenuItem
    lateinit var map: MenuItem
    fun showFave() {
        fav.icon = ContextCompat.getDrawable(ctx,
        if (org.faved)
            android.R.drawable.star_big_on
        else
            android.R.drawable.star_big_off
        )
    }
    fun setMap() {
        val locationExists = org.data?.address?.data?.geo_lat != null && org.data?.address?.data?.geo_lon != null
        if (::map.isInitialized) {
            map.isEnabled = locationExists
            map.isVisible = locationExists
        }

    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val v = super.getView(position, convertView, parent)
        v.findViewById<TextView>(R.id.item_name).text = context.getString(getItem(position).key)
        v.findViewById<TextView>(R.id.item_value).text = getItem(position).value
        return v
    }
    fun fave() {
        org.faved = !org.faved
        UnitTask({svc.updateCache(org)}).execute()
        showFave()
    }

    fun remove() {
        UnitTask({svc.delete(org.hid)}).execute()
    }

    fun locate(): String {
        return (org.data?.address?.data?.geo_lat?:"") + "_" + (org.data?.address?.data?.geo_lon?:"")
    }
    fun getName(): String {
        return (org.name)
    }

    private fun refresh() {
        val f = Utils.mapByAnnotations(org.data, HashMap())
        clear()
        addAll(f.entries)
        notifyDataSetChanged()
        setMap()
    }

    fun fetch(hid: String) {
        val old = svc.suggested()
        if (old?.hid == hid) {
            org = old
            refresh()
        }
        UnitTask({
            val dat = svc.getDetails(hid)
            org.data = dat
        }, {refresh()
            www.suggestOrganizationsAsync(org.name, { list: List<Organization>?,
                                                      i: Int, throwable: Throwable? ->
                if (throwable != null) {
                    //make toast
                    Log.e("DetailsPresenter", "inet access error", throwable)
                    return@suggestOrganizationsAsync
                }
                if (list == null) {
                    Log.e("DetailsPresenter", "inet access error: error code #"+i.toString())
                    return@suggestOrganizationsAsync
                }
                for (o in list) {
                    if (o.hid == org.hid){
                        org = o
                        break
                    }
                }
                refresh()
            })
        }).execute()


    }


}