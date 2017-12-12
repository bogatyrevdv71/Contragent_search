package ru.fintech.db.contragentsearch17.inet

import android.content.Context
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_item_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.fintech.db.contragentsearch17.BuildConfig
import ru.fintech.db.contragentsearch17.R
import ru.fintech.db.contragentsearch17.dataModel.DaDataAddr
import ru.fintech.db.contragentsearch17.dataModel.Organization
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineService @Inject constructor() : DaDataApi {

    @Inject lateinit var ctx: Context
    //TODO: вытащить адрес/токен куда-нибудь (шоб не хардкодить)
    private interface RetrofitApi {
        @Headers(
                "Content-Type: application/json",
                "Accept: application/json",
                "Authorization: Token " + BuildConfig.TOKEN
        )
        @POST("suggest/party")
        fun party(@Body body: ApiRequest) : Call<OrganizationList>
        @POST("suggest/address")
        fun address(@Body body: ApiRequest) : Call<AddressList>
    }
    private data class ApiRequest (val query: String, val count: Int)
    private class OrganizationList(val suggestions: Array<Organization>)
    private class AddressList(val suggestions: Array<DaDataAddr>)

    private val addr = BuildConfig.URL
    private val api : RetrofitApi = Retrofit.Builder()
            .baseUrl(addr)
            .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().create()))
            .build().create(RetrofitApi::class.java)
    @Throws(IOException::class)
    override fun suggestOrganizations (query: String): List<Organization>? {
        val c = api.party(ApiRequest(query, 5))
        val f : Response<OrganizationList>
        try {
            f = c.execute()
        }
        catch (e: IOException) {
            throw InetException(0, e)
        }
        catch (e: RuntimeException) {
            throw InetException(0, e)
        }
        val bod = f.body() ?: throw InetException(f.code(),
                RuntimeException("Error code " + f.code().toString()))
        return bod.suggestions.toList()
    }
    override fun suggestOrganizationsAsync(query: String, callback: (List<Organization>?, Int,
                                                                     Throwable?) -> Unit) {
        val c = api.party(ApiRequest(query, 5))
        c.enqueue(object: Callback<OrganizationList> {
            override fun onFailure(call: Call<OrganizationList>, t: Throwable?) {
                callback(null, 0, t)
            }

            override fun onResponse(call: Call<OrganizationList>, response: Response<OrganizationList>) {
                callback(response.body()?.suggestions?.toList(), response.code(), null)
            }

        })
    }
    override fun suggestAddress(query: String): DaDataAddr? {
        val c = api.address(ApiRequest(query, 1))
        return c.execute().body()?.suggestions?.get(0)
    }
}