package b.learn.rvtest

import android.graphics.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import b.learn.rvtest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.concurrent.Executors


class PagingActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecipeAdapter
    lateinit var passengers :PagedList<Passengers>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewManager = LinearLayoutManager(this@PagingActivity)

        viewAdapter =
            RecipeAdapter({ position: Int ->

            },{ position: Int ->
                // viewAdapter.removeItemAt(position)
            })
        binding.myRecyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        var dataSourceFactory  = DataSourceFactory()
        var config = (PagedList.Config.Builder()).setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .setPrefetchDistance(4)
            .build()

        var executor = Executors.newFixedThreadPool(5)

        var passengerPagedList = LivePagedListBuilder<Long, Passenger>(dataSourceFactory, config)
            .setFetchExecutor(executor)
            .build()

        passengerPagedList.observe(this, Observer {
            viewAdapter.submitList(it)
        } )

       /* CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.getInstantWebTools().getAllPassengers( "0","10")
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.

                        viewManager = LinearLayoutManager(this@PagingActivity)

                        passengers = response.body() as Passengers
                        viewAdapter =
                            RecipeAdapter(passengers.data,{ position: Int ->
                                //Toast.makeText(MainActivity::class.java,"Clicked $position",Toast.LENGTH_SHORT).show()
                                *//*var addQuote = recipes.results[recipes.results.size-1]
                                var newQuote = Quote((addQuote.pk.toInt()+1).toString(),addQuote.en,addQuote.author)
                                recipes.results.add(0,newQuote)
                                viewAdapter.notifyItemInserted(0)*//*
                            },{ position: Int ->
                                viewAdapter.removeItemAt(position)
                            })
                        binding.myRecyclerView.apply {
                            // use this setting to improve performance if you know that changes
                            // in content do not change the layout size of the RecyclerView
                            setHasFixedSize(true)

                            // use a linear layout manager
                            layoutManager = viewManager

                            // specify an viewAdapter (see also next example)
                            adapter = viewAdapter
                        }
                    } else {
                        Log.e("","Error: ${response.code()}")
                    }
                } catch (e: HttpException) {
                    //toast("Exception ${e.message}")
                } catch (e: Throwable) {
                    //toast("Ooops: Something else went wrong")
                }
            }
        }*/
    }
}
