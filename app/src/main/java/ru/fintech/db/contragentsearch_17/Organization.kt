package ru.fintech.db.contragentsearch_17

import android.arch.persistence.room.*
import java.util.*
import kotlin.collections.HashMap

data class DaDataAddr (
        @Displayed(name="Адрес")
        var value: String? = "",
        @Displayed(name="Полный адрес")
                  var unrestricted_value: String? = "")


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.BINARY)
annotation class Displayed(val name: String)

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

data class DaDataState (
        @Displayed(name="Дата актуальности сведений")
        var actuality_date: Long?=0,
        @Displayed(name="Дата регистрации")
                   var registration_date: Long?=0,
        @Displayed(name="Дата ликвидации")
                   var liquidation_date: Long?=0,
        @Displayed(name="Статус организации")
                   var status: DaDataStatus=DaDataStatus.ACTIVE)

@Entity
data class DaData (
        @Embedded(prefix="addr_")
            var address: DaDataAddr,
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
        @Embedded(prefix="mgmt_")
             var management: DaDataMgmt?,
        @Embedded(prefix="name_")
            var name: DaDataName,
        @Displayed(name="Код ОКВЭД")
            var okved: String?,
        @Embedded(prefix="opf_")
            var opf: DaDataOpf?,
        @Embedded(prefix="state_")
            var state: DaDataState,
        @Displayed(name="Тип организации")
            var type: DaDataType
              ) {
    constructor(): this(DaDataAddr(),0,DaDataBranchType.MAIN,
            "","","",0,"", DaDataMgmt(), DaDataName(),"", DaDataOpf(), DaDataState(),
            DaDataType.LEGAL)
    val fullname: String
            get()=name.full
}

@Entity
data class Organization (
                    var value: String ="",
                    var unrestricted_value: String ="",
                    var last_access: Date? = null,
                    var faved: Boolean = false){
    override fun toString() = value
    @Ignore
    var data: DaData? = null
    val name : String
        get() = value
    @PrimaryKey
    var hid : String = data?.hid?:""
        get() {field = data?.hid?:field
                return field}
    fun upd() {
        hid = data?.hid?:""
        inn = data?.inn?:""
        address = data?.address?.value?:""
    }
    var inn: String = data?.inn?:""
        get() {field = data?.inn?:field
                return field}
    var address: String = data?.address?.value?:""
        get() {field = data?.address?.value?:field
                return field}
}

class OrganizationDisplayer {
    fun map(o: Any?, m1: HashMap<String, String>) : HashMap<String,String> {
        if (o == null) return m1
        var m = m1
        val fields = o.javaClass.declaredFields
        for(field in fields) {
            field.isAccessible=true
            val ann = field.getAnnotation(Displayed::class.java)
            if (ann is Displayed) {
                val v = field.get(o)
                if (v != null) {
                    if (v is Long)
                        m[ann.name] = DatetimeConverter().fromTimestamp(v).toString()
                    else
                        m[ann.name] = v.toString()
                }
            }
            val emb = field.getAnnotation(Embedded::class.java)
            if (emb is Embedded) {
                m = map(field.get(o), m)
            }
        }
        return m
    }
}