package ru.fintech.db.contragentsearch17.presenters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.fintech.db.contragentsearch17.App
import ru.fintech.db.contragentsearch17.R
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
        App.injector.inject(this)
    }

    fun fetch() { UnitTask({
        mResults = svc.list()
    }, {notifyDataSetChanged()}).execute()}

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cv = super.getView(position, convertView, parent)
        val btn = cv.findViewById<ImageView>(R.id.isfav)
        btn.visibility = if (getItem(position).faved) View.VISIBLE else View.GONE
        return cv
    }

}