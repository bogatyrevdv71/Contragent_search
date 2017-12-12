package ru.fintech.db.contragentsearch17.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_favs.*
import ru.fintech.db.contragentsearch17.R
import ru.fintech.db.contragentsearch17.presenters.DetailsClickListener
import ru.fintech.db.contragentsearch17.adapters.OrganizationListAdapter

class FavsActivity : AppCompatActivity() {

    lateinit var adapter: OrganizationListAdapter

//    override fun onResume() {
//        super.onResume()
//        //adapter.fetch()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favs)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = OrganizationListAdapter(applicationContext)
        val actv = findViewById<ListView>(R.id.favs_view)
        actv.adapter = adapter
        adapter.fetch(this)
        actv.onItemClickListener = DetailsClickListener(adapter, this)

    }

}
