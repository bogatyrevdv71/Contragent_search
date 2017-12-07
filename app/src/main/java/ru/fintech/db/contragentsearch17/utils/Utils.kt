package ru.fintech.db.contragentsearch17.utils

import android.arch.persistence.room.*
import ru.fintech.db.contragentsearch17.dataModel.Displayed
import ru.fintech.db.contragentsearch17.dataModel.Traverse
import kotlin.collections.HashMap

object Utils {
    fun mapByAnnotations(o: Any?, m1: HashMap<String, String>) : HashMap<String,String> {
        if (o == null) return m1
        var m = m1
        val fields = o::class.java.declaredFields //.javaClass.declaredFields
        for(field in fields) {
            field.isAccessible=true
            val ann = field.getAnnotation(Displayed::class.java)
            if (ann is Displayed) {
                val v = field.get(o)
                if (v != null) {
                    if (v is Long) {
                        m[ann.name] = Converters().fromTimestamp(v).toString()
                    } else {
                        if (v.toString() != "")
                            m[ann.name] = v.toString()
                    }
                }
            }
            val emb = field.getAnnotation(Traverse::class.java)
            if (emb is Traverse) {
                    m = mapByAnnotations(field.get(o), m)
            }
        }
        return m
    }
}