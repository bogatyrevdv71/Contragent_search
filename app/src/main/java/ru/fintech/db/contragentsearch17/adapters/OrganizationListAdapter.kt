package ru.fintech.db.contragentsearch17.adapters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.fintech.db.contragentsearch17.AppModule
import ru.fintech.db.contragentsearch17.R
import ru.fintech.db.contragentsearch17.dataModel.Organization
import ru.fintech.db.contragentsearch17.db.CacheInterface
import ru.fintech.db.contragentsearch17.utils.UnitTask
import javax.inject.Inject

/**
 * Created by DB on 07.12.2017.
 *
 */

class OrganizationListAdapter (context: Context) : OrganizationAdapter(context) {
    @Inject lateinit var svc: CacheInterface

    init {
        AppModule.injector.inject(this)
    }

    lateinit var liveResults : LiveData<List<Organization>>

    fun fetch(a: AppCompatActivity) {
        UnitTask({
            liveResults = svc.list()
            liveResults.observe(a, Observer { l: List<Organization>? ->
                if (l != null) {
                    mResults = l
                    notifyDataSetChanged()
                } else
                    notifyDataSetInvalidated()
            })
        }).execute()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cv = super.getView(position, convertView, parent)
        val btn = cv.findViewById<ImageView>(R.id.isfav)
        btn.visibility = if (getItem(position).faved) View.VISIBLE else View.GONE
        return cv
    }

}