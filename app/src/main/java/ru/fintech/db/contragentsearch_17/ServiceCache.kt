package ru.fintech.db.contragentsearch_17

import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

interface ServiceCacheInterface {
    fun findOrganizations(query: String) : List<Organization>?
}

class ServiceCache : ServiceCacheInterface {
    override fun findOrganizations(query: String): List<Organization>? {
        val c = api.party(ApiRequest(query, 5))
        return c.execute().body()?.suggestions?.toList() ?: null
    }

    private val addr = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/"
    private val api : Api = Retrofit.Builder()
                .baseUrl(addr)
                .addConverterFactory(GsonConverterFactory.create(
                        GsonBuilder().registerTypeAdapter(Organization::class.java,
                                object : JsonDeserializer<Organization> {
                                    override fun deserialize(json: JsonElement?, typeOfT: Type?,
                                                             context: JsonDeserializationContext?):
                                            Organization? {
                                        if (json?.isJsonObject == true) {
                                            val o = json.getAsJsonObject()
                                            val d = o.getAsJsonObject("data")
                                            return Organization(
                                                    o.getAsJsonPrimitive("value").asString,
                                                    d.getAsJsonPrimitive("hid").asString,
                                                    d.getAsJsonPrimitive("inn").asString,
                                                    d.getAsJsonObject("address")
                                                            .getAsJsonPrimitive(
                                                                    "value").asString)
                                        }
                                        return null
                                    }
                                })
                                .create()))
                .build().create(Api::class.java)

    companion object {
        val instance = ServiceCache()
    }
}
