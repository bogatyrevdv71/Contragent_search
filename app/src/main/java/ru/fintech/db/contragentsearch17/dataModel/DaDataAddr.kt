package ru.fintech.db.contragentsearch17.dataModel

import android.arch.persistence.room.Embedded
import ru.fintech.db.contragentsearch17.R

data class DaDataAddr(
        @Displayed(name = R.string.adress)
        var value: String? = "",
        @Displayed(name = R.string.full_adress)
        var unrestricted_value: String? = "",
        @Embedded(prefix = "geo_")
        var data: DaDataAddress? = null
) {
    data class DaDataAddress(var qc_geo: String = "",
                             var geo_lat: String = "",
                             var geo_lon: String = "")
}