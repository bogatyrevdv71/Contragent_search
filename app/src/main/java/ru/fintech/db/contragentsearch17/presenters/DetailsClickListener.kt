package ru.fintech.db.contragentsearch17.presenters

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import ru.fintech.db.contragentsearch17.AppModule
import ru.fintech.db.contragentsearch17.BuildConfig
import ru.fintech.db.contragentsearch17.ui.DetailsActivity
import ru.fintech.db.contragentsearch17.ui.FavsActivity
import ru.fintech.db.contragentsearch17.adapters.OrganizationListAdapter
import ru.fintech.db.contragentsearch17.db.CacheInterface
import ru.fintech.db.contragentsearch17.utils.UnitTask
import javax.inject.Inject

/**
 * Created by DB on 07.12.2017.
 *
 */
class DetailsClickListener(val adapter: OrganizationListAdapter,
                           val ac : FavsActivity) : AdapterView.OnItemClickListener {

    init {
        AppModule.injector.inject(this)
    }
    @Inject lateinit var svc: CacheInterface
    var ctx = ac.applicationContext

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val org = adapter.getItem(position)
        if (BuildConfig.DEBUG) {
            Log.i("Recent clicked: ", org.name)
        }
        //open screen #3
        UnitTask({svc.updateCache(org)}).execute()
        val intent = Intent(ctx, DetailsActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, adapter.getItem(position).hid)
        intent.type="text/plain"
        ac.startActivity(intent)
    }
}