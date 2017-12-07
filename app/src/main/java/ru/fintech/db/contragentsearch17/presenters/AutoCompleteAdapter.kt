package ru.fintech.db.contragentsearch17.presenters

import android.content.Context
import android.widget.Filter
import android.widget.Filterable
import ru.fintech.db.contragentsearch17.App
import ru.fintech.db.contragentsearch17.dataModel.Organization
import ru.fintech.db.contragentsearch17.inet.DaDataApi
import javax.inject.Inject

/**
 * Created by DB on 07.12.2017.
 *
 */
class AutoCompleteAdapter (context: Context) : OrganizationAdapter(context),
            Filterable{
    init {
        App.injector.inject(this)
    }
    @Inject
    lateinit var svc: DaDataApi //TODO: mb split it to 'OrgProvider' & 'AddrProvider'?
    override fun getFilter(): Filter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val res = FilterResults()
            constraint?.let{
                val ret = svc.suggestOrganizations(constraint.toString())
                res.values = ret
                res.count = ret?.size?:0
            }
            return res
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) =
                if (results != null && results.count > 0) {
                    @Suppress("UNCHECKED_CAST")
                    mResults = results.values as List<Organization>
                    notifyDataSetChanged()
                }
                else
                    notifyDataSetInvalidated()
    }
}