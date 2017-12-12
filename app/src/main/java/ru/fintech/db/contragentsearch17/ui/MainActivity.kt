package ru.fintech.db.contragentsearch17.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.AutoCompleteTextView
import ru.fintech.db.contragentsearch17.AppModule
import ru.fintech.db.contragentsearch17.R
import ru.fintech.db.contragentsearch17.adapters.AutoCompleteAdapter
import ru.fintech.db.contragentsearch17.presenters.SuggestClickListener

class MainActivity : AppCompatActivity() {

    lateinit var acListAdapter : AutoCompleteAdapter
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.favs -> {
                val intent = Intent(this, FavsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppModule.init(this.application)
        setContentView(R.layout.activity_main)
        acListAdapter = AutoCompleteAdapter(applicationContext)
        val actv = findViewById<AutoCompleteTextView>(R.id.autocomplete)
        actv.setAdapter(acListAdapter)
        actv.onItemClickListener = SuggestClickListener(acListAdapter, this)
    }
}
