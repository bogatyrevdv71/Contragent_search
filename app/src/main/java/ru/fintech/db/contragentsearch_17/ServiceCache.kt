package ru.fintech.db.contragentsearch_17

import android.arch.persistence.room.*
import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.content.Context
import android.os.AsyncTask
import java.util.*


interface ServiceCacheInterface {
    fun listAll(cb: ListCallback)
    fun findOrganizations(query: String) : List<Organization>?
}

interface ListCallback {
    fun ready(v: List<Organization>)
}

class ServiceCache : ServiceCacheInterface {

    @Database(entities = arrayOf(Organization::class, DaData::class), version = 3)
    @TypeConverters(DatetimeConverter::class)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun orgDao(): OrganizationsDao
        abstract fun dataDao(): DaDataDao
    }

    override fun findOrganizations(query: String): List<Organization>? {
        val local = find(query)
        return local ?: onlineInterface.findOrganizations(query)
    }

    fun find(query:String?) : List<Organization> {
        // Todo: find in cache
        return listOf()
    }

    fun upd(o: Organization) {
        CacheTask(dao!!, dadao).execute(o)
    }

    fun get(hid: String, cb: ListCallback?) : Organization {
        if (suggested?.hid != hid) throw UnknownError()
        if (suggested?.data == null) {
            DetailTask(onlineInterface, cb, dadao, suggested!!).execute()
        } else {
            cb?.ready(listOf(suggested!!))
        }
        return suggested!!
    }

    fun del(o: Organization) {
        DeleteTask(dao!!, dadao).execute(o)
    }

    class DetailTask(val int: ServiceCacheInterface,
                     val cb: ListCallback?, val da: DaDataDao?, val o: Organization) :
            AsyncTask<Void, Void, Organization> (){
        override fun doInBackground(vararg params: Void?): Organization? {
            if (da != null) {
                val dada =  da.getByHid(o.hid)
                o.data = dada[0]
                return o
            }
            val l = int.findOrganizations(o.name)
            if (l?.isEmpty() != false) return null
            for (q in l) {
                if (q.hid == o.hid) return q
            }
            return null
        }

        override fun onPostExecute(result: Organization?) {
            if (result != null) cb?.ready(listOf(result)) else cb?.ready(listOf())
            if (da != null) {
                DetailTask(int, cb, null, o).execute()
            }
        }
    }

    var mResult: ArrayList<Organization> = ArrayList<Organization>()
    var suggested: Organization? = null

    override fun listAll(cb: ListCallback) {
        ListTask(dao!!, cb, mResult).execute()
    }

    val onlineInterface = OnlineService()

    class ListTask(val dao:OrganizationsDao, val cb: ListCallback,
                   val mResult: ArrayList<Organization>) :
            AsyncTask<Void, Void, List<Organization>>() {
        override fun doInBackground(vararg params: Void?): List<Organization> {
            return dao.getAll()
        }

        override fun onPostExecute(result: List<Organization>) {
            cb.ready(result)
            mResult.clear()
            mResult.addAll(result)
        }
    }

    class CacheTask (val dao: OrganizationsDao,
                     val dadao: DaDataDao): AsyncTask<Organization, Void, Void>() {
        override fun doInBackground(vararg params: Organization): Void? {
            for (a in params) {
                if (dao.getByHid(a.hid).isEmpty()) {
                    dao.insertAll(a)
                    dadao.insert(a.data!!)
                }
                else
                    dao.update(a)
            }
            return null
        }

    }

    class DeleteTask (val dao: OrganizationsDao,
                      val dadao: DaDataDao): AsyncTask<Organization, Void, Void>() {
        override fun doInBackground(vararg params: Organization): Void? {
            for (a in params) {
                val q = dadao.getByHid(a.hid)
                for (b in q)
                    dadao.delete(b)
            }
            dao.delete(*params)
            return null
        }
    }

    private var dao : OrganizationsDao? = null
    private lateinit var dadao : DaDataDao

    fun access(o: Organization) {
        o.last_access = Date()
        suggested = o
        CacheTask(dao!!, dadao).execute(o)
    }

    fun access(i: Int) {
        val f = mResult[i]
        access(f)
    }

    fun cache(i: Int) {
        val f = onlineInterface.last!![i]
        f.upd()
        access(f)
    }

    fun init(c: Context) {
        if (dao == null) {
            val db =  Room.databaseBuilder(c, AppDatabase::class.java, "db-kurs")
                    .fallbackToDestructiveMigration().build()
            dao = db.orgDao()
            dadao = db.dataDao()
        }
    }

    companion object {
        val instance = ServiceCache()
    }
}

@Dao
interface OrganizationsDao {
    @Query("SELECT * from organization WHERE hid=:hid")
    fun getByHid(hid: String) : List<Organization>

    @Query("SELECT * from organization ORDER BY faved DESC, last_access DESC")
    fun getAll() : List<Organization>

    @Update
    fun update(vararg orgs: Organization)

    @Insert
    fun insertAll(vararg organizations: Organization)

    @Delete
    fun delete(vararg orgs: Organization)
}

@Dao
interface DaDataDao{
    @Query("SELECT * from dadata WHERE hid=:hid")
    fun getByHid(hid: String): List<DaData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(da : DaData)

    @Delete
    fun delete(vararg das: DaData)
}

class OnlineService : ServiceCacheInterface {
    override fun listAll(cb: ListCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var last : List<Organization>? = null
    private val addr = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/"
    private val api : Api = Retrofit.Builder()
            .baseUrl(addr)
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().create()))
            .build().create(Api::class.java)
    override fun findOrganizations (query: String): List<Organization>? {
        val c = api.party(ApiRequest(query, 5))
        last = c.execute().body()?.suggestions?.toList() ?: null
        return last
    }

}


