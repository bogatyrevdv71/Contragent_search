package ru.fintech.db.contragentsearch17.presenters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import ru.fintech.db.contragentsearch17.App
import ru.fintech.db.contragentsearch17.BuildConfig
import ru.fintech.db.contragentsearch17.ContragentDetails
import ru.fintech.db.contragentsearch17.FavsPage
import ru.fintech.db.contragentsearch17.db.CacheInterface
import ru.fintech.db.contragentsearch17.utils.UnitTask
import javax.inject.Inject

/**
 * Created by DB on 07.12.2017.
 *
 */
class DetailsClickListener(val adapter: OrganizationListAdapter,
                           val ac : FavsPage) : AdapterView.OnItemClickListener {

    init {
        App.injector.inject(this)
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
        val intent = Intent(ctx, ContragentDetails::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, adapter.getItem(position).hid)
        intent.type="text/plain"
        ac.startActivity(intent)
    }
}