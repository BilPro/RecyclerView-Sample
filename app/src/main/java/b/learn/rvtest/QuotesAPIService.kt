package b.learn.rvtest

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface QuatesService {
    @GET("quotes")
    suspend fun getProgrammingQuotes(): Response<List<Quote>>
}

