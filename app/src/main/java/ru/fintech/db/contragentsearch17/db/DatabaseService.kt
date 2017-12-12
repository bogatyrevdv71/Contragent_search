package ru.fintech.db.contragentsearch17.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import ru.fintech.db.contragentsearch17.AppModule
import ru.fintech.db.contragentsearch17.dataModel.DaData
import ru.fintech.db.contragentsearch17.dataModel.Organization
import ru.fintech.db.contragentsearch17.utils.Converters
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by DB on 07.12.2017.
 *
 */
@Singleton
class DatabaseService @Inject constructor() : CacheInterface {

    private var suggested : Organization? = null
    override fun suggested() = suggested

    private var dao: OrganizationsDao
    private var dadao: DaDataDao

    @Inject lateinit var ctx: Context

    init{
        AppModule.injector.inject(this)
        val db = Room.databaseBuilder(ctx, AppDatabase::class.java, "db-kurs")
                .fallbackToDestructiveMigration().build()
        dao = db.orgDao()
        dadao = db.dataDao()
    }

    override fun delete(hid: String) {
        val o = dao.getByHid(hid) ?: return
        dao.delete(o)
        val q = dadao.getByHid(o.hid) ?: return
        dadao.delete(q)
    }

    override fun list() = dao.getAll()

    override fun getDetails(hid: String) = dadao.getByHid(hid)

    override fun updateCache(o: Organization) {
        o.last_access = Date()
        o.upd()
        suggested = o
        val old = dao.getByHid(o.hid)
        val dat = o.data
        if (old==null) {
            dao.insertAll(o)
            if (dat != null)
                dadao.insert(dat)
        } else {
            dao.update(o)
            if (dat != null)
                dadao.update(dat)
        }
    }

    override fun setFav(hid: String, isFaved: Boolean) = dao.setFave(hid, isFaved)

    @Database(entities = arrayOf(Organization::class, DaData::class), version = 1)
    @TypeConverters(Converters::class)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun orgDao(): OrganizationsDao
        abstract fun dataDao(): DaDataDao
    }

}