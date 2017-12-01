package ru.fintech.db.contragentsearch_17

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @Headers(
            "Content-Type: application/json",
            "Accept: application/json",
            "Authorization: Token 9c780075e250e5090a9ee99a48073b619c3f6d70"
    )
    @POST("suggest/party")
    fun party(@Body body: ApiRequest) : Call<SuggestList>
}

data class ApiRequest (val query: String, val count: Int) {}

data class SuggestList (val suggestions: Array<Organization>) {}
