package b.learn.rvtest

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class PassengersListDataSource : PageKeyedDataSource<Long, Passenger>() {

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Long, Passenger>
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.getInstantWebTools().getAllPassengers( "0","10")
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                       var passengers = response.body() as Passengers
                        callback.onResult(passengers.data, null, 2.toLong())
                    } else {
                        Log.e("","Error: ${response.code()}")
                    }
                } catch (e: HttpException) {
                    //toast("Exception ${e.message}")
                } catch (e: Throwable) {
                    //toast("Ooops: Something else went wrong")
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Passenger>) {
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Passenger>) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.getInstantWebTools().getAllPassengers( params.key.toString(),"10")
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        var passengers = response.body() as Passengers
                        callback.onResult(passengers.data,  params.key + 1)
                    } else {
                        Log.e("","Error: ${response.code()}")
                    }
                } catch (e: HttpException) {
                    //toast("Exception ${e.message}")
                } catch (e: Throwable) {
                    //toast("Ooops: Something else went wrong")
                }
            }
        }
    }
}