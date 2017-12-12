package ru.fintech.db.contragentsearch17.adapters

import android.content.Context
import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import ru.fintech.db.contragentsearch17.AppModule
import ru.fintech.db.contragentsearch17.dataModel.Organization
import ru.fintech.db.contragentsearch17.inet.DaDataApi
import ru.fintech.db.contragentsearch17.inet.InetException
import javax.inject.Inject

/**
 * Created by DB on 07.12.2017.
 *
 */
class AutoCompleteAdapter (context: Context) : OrganizationAdapter(context),
            Filterable{
    init {
        AppModule.injector.inject(this)
    }
    @Inject
    lateinit var svc: DaDataApi //TODO: mb split it to 'OrgProvider' & 'AddrProvider'?
    override fun getFilter(): Filter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val res = FilterResults()
            constraint?.let{
                try {
                    val ret = svc.suggestOrganizations(constraint.toString())
                    res.values = ret
                    res.count = ret?.size ?: 0
                }
                catch (e: InetException) {
                    Log.e("Suggests", "Error getting suggests", e)
                }
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