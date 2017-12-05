package ru.fintech.db.contragentsearch_17

import android.arch.persistence.room.TypeConverter
import java.util.*



/**
 * Created by DB on 02.12.2017.
 *
 */

class DatetimeConverter {
        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }

        @TypeConverter
        fun fromBrT(v: DaDataBranchType) : Int {
            return v.ordinal
        }
        @TypeConverter
        fun fromSt(v: DaDataStatus) : Int {
            return v.ordinal
        }
        @TypeConverter
        fun fromT(v: DaDataType) : Int {
            return v.ordinal
        }

        @TypeConverter
        fun toBrT(v: Int): DaDataBranchType{
            return DaDataBranchType.values()[v]
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
