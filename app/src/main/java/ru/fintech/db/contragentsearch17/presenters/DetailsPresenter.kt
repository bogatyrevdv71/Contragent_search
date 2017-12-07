package ru.fintech.db.contragentsearch17.presenters

import android.content.Context
import android.content.res.Resources
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.fintech.db.contragentsearch17.App
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
class DetailsPresenter  (ctx: Context,
                         frag: Int,
                         val name: Int,
                         val value: Int) : ArrayAdapter<Map.Entry<String, String>>(ctx, frag, name)
{
    @Inject lateinit var svc : CacheInterface
    @Inject lateinit var www : DaDataApi
    init {
        App.injector.inject(this)
    }
    var org = Organization()
    val resources : Resources = ctx.resources
    lateinit var fav: MenuItem
    lateinit var map: MenuItem
    @Suppress("DEPRECATION")
    fun showFave() {
        fav.icon = resources.getDrawable(
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
        v.findViewById<TextView>(R.id.item_name).text = getItem(position).key
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
            UnitTask({
                val q = www.suggestOrganizations(org.name)
                if (q != null)
                    for (o in q) {
                        if (o.hid==org.hid) {
                            org = o
                            break
                        }
                    }
            }, {refresh()}).execute()
        }).execute()


    }


}