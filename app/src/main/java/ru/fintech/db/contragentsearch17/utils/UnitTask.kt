package ru.fintech.db.contragentsearch17.utils

import android.os.AsyncTask

/**
 * Created by DB on 07.12.2017.
 *
 */
class UnitTask(private val f: () -> Unit, private val g: ()->Unit={}) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        f()
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        g()
    }
}