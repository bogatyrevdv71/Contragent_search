package ru.fintech.db.contragentsearch17

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_contragent_details.*
import ru.fintech.db.contragentsearch17.dataModel.Organization
import ru.fintech.db.contragentsearch17.presenters.DetailsPresenter

class ContragentDetails : AppCompatActivity() {
    lateinit var menu: Menu

    lateinit var presenter : DetailsPresenter

    lateinit var hid : String

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.contragent_details_menu, menu)
        presenter.fav = menu.getItem(0)
        presenter.showFave()

        presenter.map=menu.getItem(2)
        presenter.setMap()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.fav -> {
                presenter.fave()
                return true
            }
            R.id.del -> {
                presenter.remove()
                finish()
                return true
            }
            R.id.map -> {
                val intent = Intent(applicationContext, MapsActivity::class.java)
                intent.putExtra("Location", presenter.locate())
                intent.putExtra("Name", presenter.getName())
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contragent_details)
        hid = intent.getStringExtra(Intent.EXTRA_TEXT)
        presenter = DetailsPresenter(applicationContext, R.layout.fragment_details_item, R.id.item_value, R.id.item_name)
        presenter.fetch(hid)
        val lv  = findViewById<ListView>(R.id.details)
        lv.adapter = presenter
        if (BuildConfig.DEBUG) {
            Log.i("Loaded details: ", hid)
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        // Save the user's current game state
        savedInstanceState!!.putString("hid",hid)


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState)
        hid=savedInstanceState.getString("hid")
    }


}
