package ru.fintech.db.contragentsearch17.utils

import dagger.Binds
import dagger.Module
import ru.fintech.db.contragentsearch17.AppModule
import ru.fintech.db.contragentsearch17.ui.MainActivity
import ru.fintech.db.contragentsearch17.db.CacheInterface
import ru.fintech.db.contragentsearch17.db.DatabaseService
import ru.fintech.db.contragentsearch17.inet.DaDataApi
import ru.fintech.db.contragentsearch17.inet.OnlineService
import javax.inject.Singleton

/**
 * Created by DB on 07.12.2017.
 *
 */
@Module(includes= arrayOf(AppModule::class))
interface Dependencies {
    @Binds @Singleton fun cacheInterface(db: DatabaseService): CacheInterface
    @Binds @Singleton fun daDataApi(svc: OnlineService): DaDataApi
}