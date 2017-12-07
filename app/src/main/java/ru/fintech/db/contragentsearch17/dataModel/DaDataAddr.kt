package ru.fintech.db.contragentsearch17.dataModel

import android.arch.persistence.room.Embedded

data class DaDataAddr (
        @Displayed(name="Адрес")
        var value: String? = "",
        @Displayed(name="Полный адрес")
        var unrestricted_value: String? = "",
        @Embedded(prefix="geo_")
        var data: DaDataAddress? = null
        ) {
    data class DaDataAddress (var qc_geo: String= "",
                              var geo_lat: String= "",
                              var geo_lon: String= "")
}