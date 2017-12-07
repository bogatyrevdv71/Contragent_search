package ru.fintech.db.contragentsearch17

import android.app.Application
import ru.fintech.db.contragentsearch17.utils.DaggerInjector
import ru.fintech.db.contragentsearch17.utils.Dependencies
import ru.fintech.db.contragentsearch17.utils.Injector

/**
 * Created by DB on 07.12.2017.
 *
 */
object App {
     lateinit var injector: Injector
    fun init(a: MainActivity) {
        injector = DaggerInjector.builder().mainActivity(a).build()
    }
}