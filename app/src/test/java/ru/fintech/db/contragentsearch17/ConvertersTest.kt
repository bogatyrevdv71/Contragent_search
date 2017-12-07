package ru.fintech.db.contragentsearch17

import org.junit.Test

import org.junit.Assert.*
import ru.fintech.db.contragentsearch17.dataModel.DaData.*
import ru.fintech.db.contragentsearch17.utils.Converters
import java.util.*

/**
 * Created by DB on 05.12.2017.
 *
 */
class ConvertersTest {
    @Test
    fun fromTimestamp() {
        val now = Date()
        assertEquals(now.time, Converters().dateToTimestamp(now))
    }

    @Test
    fun dateToTimestamp() {
        val now = Date().time
        assertEquals(Date(now), Converters().fromTimestamp(now))
    }

    @Test
    fun fromBrT() {
        val v=DaDataBranchType.MAIN
        assertEquals(0, Converters().fromBrT(v))
    }

    @Test
    fun fromSt() {
        val v=DaDataStatus.ACTIVE
        assertEquals(0, Converters().fromSt(v))
    }

    @Test
    fun fromT() {
        val v=DaDataType.INDIVIDUAL
        assertEquals(1, Converters().fromT(v))
    }

    @Test
    fun toBrT() {
        assertEquals(DaDataBranchType.MAIN, Converters().toBrT(0))
    }

    @Test
    fun toSt() {
        assertEquals(DaDataStatus.ACTIVE, Converters().toSt(0))
    }

    @Test
    fun toT() {
        assertEquals(DaDataType.LEGAL, Converters().toT(0))
    }

}