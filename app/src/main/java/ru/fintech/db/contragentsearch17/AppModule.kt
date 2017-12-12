package ru.fintech.db.contragentsearch17

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import ru.fintech.db.contragentsearch17.utils.DaggerInjector
import ru.fintech.db.contragentsearch17.utils.Injector
import javax.inject.Singleton

/**
 * Created by DB on 07.12.2017.
 *
 */
@Module
class AppModule (val app: Application){

    @Provides @Singleton fun provideContext() : Context = app.applicationContext
    companion object {
        lateinit var injector : Injector
        fun init(a: Application) {
            injector = DaggerInjector.builder().appModule(AppModule(a)).build()
        }
    }
}