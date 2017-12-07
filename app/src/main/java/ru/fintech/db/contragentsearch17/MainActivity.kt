package ru.fintech.db.contragentsearch17

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.AutoCompleteTextView
import android.view.MenuItem
import android.content.Intent
import dagger.Module
import dagger.Provides
import ru.fintech.db.contragentsearch17.presenters.AutoCompleteAdapter
import ru.fintech.db.contragentsearch17.presenters.SuggestClickListener
import javax.inject.Singleton

@Module
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
                val intent = Intent(this, FavsPage::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.init(this)
        setContentView(R.layout.activity_main)
        acListAdapter = AutoCompleteAdapter(applicationContext)
        val actv = findViewById<AutoCompleteTextView>(R.id.autocomplete)
        actv.setAdapter(acListAdapter)
        actv.onItemClickListener = SuggestClickListener(acListAdapter, this)
    }
    @Provides @Singleton fun provideContext() = applicationContext
}
