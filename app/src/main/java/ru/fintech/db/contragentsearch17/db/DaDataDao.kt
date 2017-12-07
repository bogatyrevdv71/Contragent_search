package ru.fintech.db.contragentsearch17.db

import android.arch.persistence.room.*
import ru.fintech.db.contragentsearch17.dataModel.DaData

@Dao
interface DaDataDao{
    @Query("SELECT * from dadata WHERE hid=:hid")
    fun getByHid(hid: String): DaData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(da : DaData)

    @Delete
    fun delete(vararg das: DaData)

    @Update
    fun update(vararg das: DaData)
}