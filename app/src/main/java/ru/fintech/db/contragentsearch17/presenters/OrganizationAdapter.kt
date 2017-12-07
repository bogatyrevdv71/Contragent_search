package ru.fintech.db.contragentsearch17.presenters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import ru.fintech.db.contragentsearch17.R
import ru.fintech.db.contragentsearch17.dataModel.Organization

/**
 * Created by DB on 07.12.2017.
 *
 */
open class OrganizationAdapter (private val context: Context) : BaseAdapter() {
    protected var mResults : List<Organization> = ArrayList<Organization>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cv = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.fragment_item_list, parent, false)
        val org = getItem(position)
        cv.findViewById<TextView>(R.id.name).text = org.name
        cv.findViewById<TextView>(R.id.inn).text = org.inn
        cv.findViewById<TextView>(R.id.address).text = org.address
        return cv
    }

    override fun getItem(position: Int): Organization = mResults[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mResults.size
}