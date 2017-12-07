package ru.fintech.db.contragentsearch17.db

import ru.fintech.db.contragentsearch17.dataModel.DaData
import ru.fintech.db.contragentsearch17.dataModel.Organization

/**
 * Created by DB on 07.12.2017.
 *
 */
interface CacheInterface {
    fun delete(hid: String)
    fun list(): List<Organization>
    fun getDetails(hid: String): DaData?
    fun updateCache(o: Organization)
    fun setFav(hid: String, isFaved: Boolean)
    fun suggested(): Organization?
}