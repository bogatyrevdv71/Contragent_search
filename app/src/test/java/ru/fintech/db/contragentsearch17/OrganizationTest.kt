package ru.fintech.db.contragentsearch17

import org.junit.Test

import org.junit.Assert.*
import ru.fintech.db.contragentsearch17.dataModel.DaData
import ru.fintech.db.contragentsearch17.dataModel.DaDataAddr
import ru.fintech.db.contragentsearch17.dataModel.Organization

/**
 * Created by DB on 05.12.2017.
 *
 */
class OrganizationTest {
    val daDataVal = DaData(DaDataAddr(null, null), null, null, "innTest", "kppTest", null,
            null, "hidTest", null, DaData.DaDataName(null, null, null,
            "fullNameTest", "shortNameTest"), null, null, DaData.DaDataState(null,
            null, null, DaData.DaDataStatus.ACTIVE), DaData.DaDataType.LEGAL)
    var organizationVal = Organization("valueTest", "unValueTest", null, false)
    @Test
    fun getInn() {
        organizationVal.data = daDataVal
        assertEquals("innTest",organizationVal.inn)
    }

    @Test
    fun getHid() {
        organizationVal.data = daDataVal
        assertEquals("hidTest",organizationVal.hid)
    }

}