package ru.fintech.db.contragentsearch17.presenters

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import ru.fintech.db.contragentsearch17.AppModule
import ru.fintech.db.contragentsearch17.BuildConfig
import ru.fintech.db.contragentsearch17.ui.DetailsActivity
import ru.fintech.db.contragentsearch17.ui.MainActivity
import ru.fintech.db.contragentsearch17.adapters.AutoCompleteAdapter
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
        AppModule.injector.inject(this)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val org = adapter.getItem(position)
        if (BuildConfig.DEBUG) {
            Log.i("Clicked suggest: ", org.name)
        }
        //open screen #3
        //ServiceCache.instance.cache(position)

        val intent = Intent(ctx, DetailsActivity::class.java)
        intent.putExtra(Intent.EXTRA_TEXT, org.hid)
        UnitTask({svc.updateCache(org)}).execute()
        ac.startActivity(intent)
    }
}