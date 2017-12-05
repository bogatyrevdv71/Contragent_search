package ru.fintech.db.contragentsearch_17

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.view.MenuInflater
import android.view.MenuItem
import android.content.Intent




class MainActivity : AppCompatActivity() {

    val cache = ServiceCache.instance
    lateinit var acListAdapter : AutoCompleteAdapter
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.favs -> {
                val intent = Intent(this, FavsPage::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceCache.instance.init(applicationContext)
        setContentView(R.layout.activity_main)
        acListAdapter = AutoCompleteAdapter(applicationContext,
              ServiceCache.instance.onlineInterface)
        val actv = findViewById<AutoCompleteTextView>(R.id.autocomplete)
        actv.setAdapter(acListAdapter)
        actv.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               Log.i("12", acListAdapter.getItem(position).name)
                //open screen #3
                ServiceCache.instance.cache(position)
                val intent = Intent(this@MainActivity, ContragentDetails::class.java)
                intent.putExtra(Intent.EXTRA_TEXT, acListAdapter.getItem(position).hid)
                startActivity(intent)
            }

        }

    }
}
