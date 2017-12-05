package ru.fintech.db.contragentsearch_17

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

/**
 * Created by DB on 30.11.2017.
 *
 */
class AutoCompleteAdapter (private val context: Context,
                           private val svc: ServiceCacheInterface,
                           private val is_faves: Boolean = false) : BaseAdapter(),
        Filterable, ListCallback {

    override fun ready(v: List<Organization>) {
        mResults = v
        notifyDataSetChanged()
    }

    fun fetch() {
        if (!is_faves) return
        svc.listAll(this)
    }

    var mResults : List<Organization> = ArrayList<Organization>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cv = convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.fragment_item_list, parent, false)
        val org = getItem(position)
        cv.findViewById<TextView>(R.id.name).text = org.name
        cv.findViewById<TextView>(R.id.inn).text = org.inn
        cv.findViewById<TextView>(R.id.address).text = org.address
        val btn = cv.findViewById<ImageView>(R.id.isfav)
        if (is_faves && org.faved) {
            btn.visibility = View.VISIBLE
        } else {
            btn.visibility = View.GONE
        }
        return cv
    }

    override fun getItem(position: Int): Organization = mResults[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mResults.size

    override fun getFilter(): Filter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val res = FilterResults()
            constraint?.let{
                val ret = svc.findOrganizations(constraint.toString())
                res.values = ret
                res.count = ret?.size?:0
            }
            return res
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count > 0) {
                notifyDataSetChanged()
                mResults = results.values as List<Organization>
            }
            else
                notifyDataSetInvalidated()
        }
    }
}