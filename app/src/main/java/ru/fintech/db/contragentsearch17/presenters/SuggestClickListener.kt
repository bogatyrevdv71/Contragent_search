package ru.fintech.db.contragentsearch17.presenters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import ru.fintech.db.contragentsearch17.App
import ru.fintech.db.contragentsearch17.BuildConfig
import ru.fintech.db.contragentsearch17.ContragentDetails
import ru.fintech.db.contragentsearch17.MainActivity
import ru.fintech.db.contragentsearch17.db.CacheInterface
import ru.fintech.db.contragentsearch17.utils.UnitTask
import javax.inject.Inject

/**
 * Created by DB on 07.12.2017.
 *
 */
class SuggestClickListener (val adapter: AutoCompleteAdapter,
                            val ac : MainActivity) : AdapterView.OnItemClickListener {

    val ctx = ac.applicationContext
    @Inject lateinit var svc: CacheInterface

    init {
        App.injector.inject(this)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val org = adapter.getItem(position)
        if (BuildConfig.DEBUG) {
            Log.i("Clicked suggest: ", org.name)
        }
        //open screen #3
        //ServiceCache.instance.cache(position)

        val intent = Intent(ctx, ContragentDetails::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, org.hid)
        UnitTask({svc.updateCache(org)}).execute()
        ac.startActivity(intent)
    }
}