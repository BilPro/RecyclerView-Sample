package b.learn.rvtest

import retrofit2.Call
import retrofit2.http.GET


interface QuatesService {
    @GET("quotes")
    fun getProgrammingQuotes(): Call<List<Quote>>?
}

