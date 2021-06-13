package b.learn.rvtest

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface InstantWebToolsService {

    @GET("passenger")
    suspend fun getAllPassengers(@Query("page")  page:String,@Query("size")  size:String): Response<Passengers>
}

