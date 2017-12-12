package ru.fintech.db.contragentsearch17.dataModel

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Organization(
        var value: String = "",
        var unrestricted_value: String = "",
        var last_access: Date? = null,
        var faved: Boolean = false) {
    override fun toString() = value
    @Ignore
    var data: DaData? = null
    val name: String
        get() = value
    //TODO: fix getters/setters/etc
    @PrimaryKey
    var hid: String = data?.hid ?: ""
        get() {
            field = data?.hid ?: field
            return field
        }

    fun upd() {
        hid = data?.hid ?: hid
        inn = data?.inn ?: inn
        address = data?.address?.value ?: address
//        data?.address?.upd()
    }

    var inn: String = data?.inn ?: ""
        get() {
            field = data?.inn ?: field
            return field
        }
    var address: String = data?.address?.value ?: ""
        get() {
            field = data?.address?.value ?: field
            return field
        }
}