package b.learn.rvtest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    fun getService(): QuatesService{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/BilPro/RecyclerView-Sample/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        var service: QuatesService=retrofit.create(QuatesService::class.java)
        return service
    }

    fun getInstantWebTools(): InstantWebToolsService{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        var retrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://api.instantwebtools.net/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        var service: InstantWebToolsService=retrofit.create(InstantWebToolsService::class.java)
        return service
    }

}