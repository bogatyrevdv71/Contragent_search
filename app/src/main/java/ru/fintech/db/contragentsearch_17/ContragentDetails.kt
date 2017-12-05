package ru.fintech.db.contragentsearch_17

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_contragent_details.*

class ContragentDetails : AppCompatActivity() {
    lateinit var menu: Menu

    var org = Organization()

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.contragent_details_menu, menu)
        showFave()
        return true
    }

    private fun showFave() {
        val id: Drawable
        if (org.faved)
            id = resources.getDrawable(android.R.drawable.star_big_on)
        else
            id = resources.getDrawable(android.R.drawable.star_big_off)
        menu.getItem(0).setIcon(id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.fav -> {
                // сделать магию, отправить в БД, сменить иконку
                org.faved = ! org.faved
                ServiceCache.instance.upd(org)
                showFave()
                return true
            }
            R.id.del -> {
                ServiceCache.instance.del(org)
                finish()
                return true
            }
            R.id.map -> {
                //magical intent
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    val details = ArrayList<Map.Entry<String,String>>()
    lateinit var adapter : ArrayAdapter<Map.Entry<String,String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contragent_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val hid = intent.getStringExtra(Intent.EXTRA_TEXT)
        val lv  = findViewById<ListView>(R.id.details)
        adapter = object : ArrayAdapter<Map.Entry<String,String>>(applicationContext, R.layout.fragment_details_item, R.id.item_value, details) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val v = super.getView(position, convertView, parent)
                v.findViewById<TextView>(R.id.item_name).text = getItem(position).key
                v.findViewById<TextView>(R.id.item_value).text = getItem(position).value
                return v
            }
        }
        lv.adapter = adapter
        org = ServiceCache.instance.get(hid, object : ListCallback {
            override fun ready(v: List<Organization>) {
                if (v.isEmpty()) return
                val f = OrganizationDisplayer().map(v[0].data,  HashMap<String,String> ())
                details.clear()
                details.addAll(f.entries)
                adapter.notifyDataSetChanged()
                if (BuildConfig.DEBUG) {
                    Log.i("1000", v[0].name)
                }
                org.data = v[0].data
            }
        })
        if (BuildConfig.DEBUG) {
            Log.i("99", hid)
        }
    }

}
