package com.example.consumowebservices

import retrofit2.http.GET

interface QuotesAPI {
    @GET("api/quotes")
    suspend fun getQuote():Quote
}