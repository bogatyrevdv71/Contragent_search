package ru.fintech.db.contragentsearch17.inet

import ru.fintech.db.contragentsearch17.dataModel.DaDataAddr
import ru.fintech.db.contragentsearch17.dataModel.Organization

interface DaDataApi {
    fun suggestOrganizations(query: String): List<Organization>?
    fun suggestAddress(query: String): DaDataAddr?
}