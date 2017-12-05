package ru.fintech.db.contragentsearch_17

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_favs_page.*

class FavsPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favs_page)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val acListAdapter = AutoCompleteAdapter(applicationContext,
                ServiceCache.instance, true)
        val actv = findViewById<ListView>(R.id.favs_view)
        actv.adapter = acListAdapter
        acListAdapter.fetch()
        actv.onItemClickListener =  object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.i("34", acListAdapter.getItem(position).name)
                //open screen #3
                ServiceCache.instance.access(position)
                val intent = Intent(applicationContext, ContragentDetails::class.java)
                intent.putExtra(Intent.EXTRA_TEXT, acListAdapter.getItem(position).hid)
                intent.type="text/plain"
                startActivity(intent)
            }
        }

        }

}
