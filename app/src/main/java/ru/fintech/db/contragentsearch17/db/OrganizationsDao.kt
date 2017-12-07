package ru.fintech.db.contragentsearch17.db

import android.arch.persistence.room.*
import ru.fintech.db.contragentsearch17.dataModel.Organization

@Dao
interface OrganizationsDao {
    @Query("SELECT * from organization WHERE hid=:hid")
    fun getByHid(hid: String) : Organization?

    @Query("SELECT * from organization ORDER BY faved DESC, last_access DESC")
    fun getAll() : List<Organization>

    @Query("UPDATE organization SET faved=:faved WHERE hid=:hid")
    fun setFave(hid: String, faved: Boolean)

    @Update
    fun update(vararg orgs: Organization)

    @Insert
    fun insertAll(vararg organizations: Organization)

    @Delete
    fun delete(vararg orgs: Organization)
}