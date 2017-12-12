package ru.fintech.db.contragentsearch17.dataModel

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.fintech.db.contragentsearch17.R


@Entity
data class DaData(
        @Traverse @Embedded(prefix = "addr_")
        var address: DaDataAddr?,
        @Displayed(name = R.string.branch_count)
        var branch_count: Int?,
        @Displayed(name = R.string.branch_type)
        var branch_type: DaDataBranchType?,
        @Displayed(name = R.string.inn)
        var inn: String,
        @Displayed(name = R.string.kpp)
        var kpp: String,
        @Displayed(name = R.string.ogrn)
        var ogrn: String?,
        @Displayed(name = R.string.ogrn_date)
        var ogrn_date: Long?,
        @PrimaryKey
        var hid: String,
        @Traverse @Embedded(prefix = "mgmt_")
        var management: DaDataMgmt?,
        @Traverse @Embedded(prefix = "name_")
        var name: DaDataName,
        @Displayed(name = R.string.okved)
        var okved: String?,
        @Traverse @Embedded(prefix = "opf_")
        var opf: DaDataOpf?,
        @Traverse @Embedded(prefix = "state_")
        var state: DaDataState,
        @Displayed(name = R.string.type)
        var type: DaDataType
) {
    constructor() : this(DaDataAddr(), 0, DaDataBranchType.MAIN,
            "", "", "", 0, "", DaDataMgmt(), DaDataName(), "", DaDataOpf(), DaDataState(),
            DaDataType.LEGAL)

    val fullname: String
        get() = name.full

    data class DaDataState(
            @Displayed(name = R.string.actuality_date)
            var actuality_date: Long? = 0,
            @Displayed(name = R.string.registration_date)
            var registration_date: Long? = 0,
            @Displayed(name = R.string.liquidation_date)
            var liquidation_date: Long? = 0,
            @Displayed(name = R.string.status)
            var status: DaDataStatus = DaDataStatus.ACTIVE)


    enum class DaDataBranchType {
        MAIN, BRANCH
    }

    enum class DaDataStatus {
        ACTIVE, LIQUIDATING, LIQUIDATED
    }

    enum class DaDataType {
        LEGAL, INDIVIDUAL
    }

    data class DaDataMgmt(
            @Displayed(name =  R.string.name)
            var name: String = "",
            @Displayed(name =  R.string.post)
            var post: String = "")

    data class DaDataName(
            @Displayed(name =  R.string.full_with_opf)
            var full_with_opf: String? = "",
            @Displayed(name =  R.string.short_with_opf)
            var short_with_opf: String? = "",
            @Displayed(name =  R.string.latin)
            var latin: String? = "",
            @Displayed(name =  R.string.full_name)
            var full: String = "",
            @Displayed(name =  R.string.short_name)
            var short: String = "")

    data class DaDataOpf(
            @Displayed(name =  R.string.code)
            var code: String? = "",
            @Displayed(name =  R.string.full_opf)
            var full: String? = "",
            @Displayed(name =  R.string.short_opf)
            var short: String? = "")

}