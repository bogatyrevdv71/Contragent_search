package ru.fintech.db.contragentsearch17.utils

import android.arch.persistence.room.TypeConverter
import ru.fintech.db.contragentsearch17.dataModel.DaData
import ru.fintech.db.contragentsearch17.dataModel.DaData.DaDataStatus
import ru.fintech.db.contragentsearch17.dataModel.DaData.DaDataType
import java.util.*


/**
 * Created by DB on 02.12.2017.
 *
 */

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromBrT(v: DaData.DaDataBranchType) : Int {
        return v.ordinal
    }
    @TypeConverter
    fun fromSt(v: DaDataStatus) : Int {
        return v.ordinal
    }
    @TypeConverter
    fun fromT(v: DaData.DaDataType) : Int {
        return v.ordinal
    }

    @TypeConverter
    fun toBrT(v: Int): DaData.DaDataBranchType {
        return DaData.DaDataBranchType.values()[v]
    }
    @TypeConverter
    fun toSt(v: Int):DaDataStatus {
        return DaDataStatus.values()[v]
    }
    @TypeConverter
    fun toT(v: Int): DaDataType {
        return DaDataType.values()[v]
    }
}
