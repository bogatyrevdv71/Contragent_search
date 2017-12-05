package ru.fintech.db.contragentsearch_17

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by DB on 05.12.2017.
 */
class OrganizationTest {
    val daDataVal = DaData(DaDataAddr(null,null),null,null,"innTest","kppTest",null,
            null,"hidTest",null,DaDataName(null,null,null,
            "fullNameTest","shortNameTest"),null,null, DaDataState(null,
            null,null,DaDataStatus.ACTIVE),DaDataType.LEGAL)
    var organizationVal = Organization("valueTest","unValueTest",null,false)
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