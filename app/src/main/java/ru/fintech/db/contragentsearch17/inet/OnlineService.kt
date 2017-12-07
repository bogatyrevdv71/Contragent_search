package ru.fintech.db.contragentsearch17.inet

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.fintech.db.contragentsearch17.dataModel.DaDataAddr
import ru.fintech.db.contragentsearch17.dataModel.Organization
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineService @Inject constructor() : DaDataApi {

    //TODO: вытащить адрес/токен куда-нибудь (шоб не хардкодить)
    private interface RetrofitApi {
        @Headers(
                "Content-Type: application/json",
                "Accept: application/json",
                "Authorization: Token 9c780075e250e5090a9ee99a48073b619c3f6d70"
        )
        @POST("suggest/party")
        fun party(@Body body: ApiRequest) : Call<OrganizationList>
        @POST("suggest/address")
        fun address(@Body body: ApiRequest) : Call<AddressList>
    }
    private data class ApiRequest (val query: String, val count: Int)
    private class OrganizationList(val suggestions: Array<Organization>)
    private class AddressList(val suggestions: Array<DaDataAddr>)

    private val addr = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/"
    private val api : RetrofitApi = Retrofit.Builder()
            .baseUrl(addr)
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().create()))
            .build().create(RetrofitApi::class.java)
    override fun suggestOrganizations (query: String): List<Organization>? {
        val c = api.party(ApiRequest(query, 5))
        return c.execute().body()?.suggestions?.toList()
    }
    override fun suggestAddress(query: String): DaDataAddr? {
        val c = api.address(ApiRequest(query, 1))
        return c.execute().body()?.suggestions?.get(0)
    }
}