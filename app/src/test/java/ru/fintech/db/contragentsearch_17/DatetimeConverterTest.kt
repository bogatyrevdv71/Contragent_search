package ru.fintech.db.contragentsearch_17

import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Created by DB on 05.12.2017.
 */
class DatetimeConverterTest {
    @Test
    fun fromTimestamp() {
        val now = Date()
        assertEquals(now.time, DatetimeConverter().dateToTimestamp(now))
    }

    @Test
    fun dateToTimestamp() {
        val now = Date().time
        assertEquals(Date(now),DatetimeConverter().fromTimestamp(now))
    }

    @Test
    fun fromBrT() {
        val v=DaDataBranchType.MAIN
        assertEquals(0,DatetimeConverter().fromBrT(v))
    }

    @Test
    fun fromSt() {
        val v=DaDataStatus.ACTIVE
        assertEquals(0,DatetimeConverter().fromSt(v))
    }

    @Test
    fun fromT() {
        val v=DaDataType.INDIVIDUAL;
        assertEquals(1,DatetimeConverter().fromT(v))
    }

    @Test
    fun toBrT() {
        assertEquals(DaDataBranchType.MAIN,DatetimeConverter().toBrT(0))
    }

    @Test
    fun toSt() {
        assertEquals(DaDataStatus.ACTIVE,DatetimeConverter().toSt(0))
    }

    @Test
    fun toT() {
        assertEquals(DaDataType.LEGAL,DatetimeConverter().toT(0))
    }

}