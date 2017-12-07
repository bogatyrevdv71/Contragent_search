package ru.fintech.db.contragentsearch17.dataModel

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class DaData (
        @Traverse @Embedded(prefix="addr_")
            var address: DaDataAddr?,
        @Displayed(name="Количество филиалов")
            var branch_count: Int?,
        @Displayed(name="Тип подразделения")
            var branch_type: DaDataBranchType?,
        @Displayed(name="ИНН")
            var inn: String,
        @Displayed(name="КПП")
            var kpp: String,
        @Displayed(name="ОГРН")
            var ogrn: String?,
        @Displayed(name="Дата выдачи ОГРН")
            var ogrn_date: Long?,
        @PrimaryKey
            var hid: String,
        @Traverse @Embedded(prefix="mgmt_")
             var management: DaDataMgmt?,
        @Traverse @Embedded(prefix="name_")
            var name: DaDataName,
        @Displayed(name="Код ОКВЭД")
            var okved: String?,
        @Traverse @Embedded(prefix="opf_")
            var opf: DaDataOpf?,
        @Traverse @Embedded(prefix="state_")
            var state: DaDataState,
        @Displayed(name="Тип организации")
            var type: DaDataType
              ) {
    constructor(): this(DaDataAddr(),0,DaDataBranchType.MAIN,
            "","","",0,"", DaDataMgmt(), DaDataName(),"", DaDataOpf(), DaDataState(),
            DaDataType.LEGAL)
    val fullname: String
            get()=name.full

    data class DaDataState (
            @Displayed(name="Дата актуальности сведений")
            var actuality_date: Long?=0,
            @Displayed(name="Дата регистрации")
            var registration_date: Long?=0,
            @Displayed(name="Дата ликвидации")
            var liquidation_date: Long?=0,
            @Displayed(name="Статус организации")
            var status: DaDataStatus=DaDataStatus.ACTIVE)


    enum class DaDataBranchType {
        MAIN, BRANCH
    }

    enum class DaDataStatus {
        ACTIVE, LIQUIDATING, LIQUIDATED
    }

    enum class DaDataType {
        LEGAL, INDIVIDUAL
    }

    data class DaDataMgmt (
            @Displayed(name="ФИО руководителя")
            var name: String = "",
            @Displayed(name="Должность руководителя")
            var post: String = "")

    data class DaDataName (
            @Displayed(name="Полное наименование с ОПФ")
            var full_with_opf: String? = "",
            @Displayed(name="Краткое наименование с ОПФ")
            var short_with_opf: String? = "",
            @Displayed(name="Наименование на латинице")
            var latin: String? = "",
            @Displayed(name="Полное наименование")
            var full: String= "",
            @Displayed(name="Краткое наименование")
            var short: String= "")

    data class DaDataOpf (
            @Displayed(name="Код ОКОПФ")
            var code: String?= "",
            @Displayed(name="Полное название ОПФ")
            var full: String?= "",
            @Displayed(name="Краткое название ОПФ")
            var short: String?= "")

}