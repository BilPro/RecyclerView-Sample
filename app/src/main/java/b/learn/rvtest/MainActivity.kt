package b.learn.rvtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import b.learn.rvtest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //https://programming-quotes-api.herokuapp.com/quotes
    //https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28
    //https://medium.com/exploring-android/android-networking-with-coroutines-and-retrofit-a2f20dd40a83
    //https://android.jlelse.eu/kotlin-coroutines-and-retrofit-e0702d0b8e8f
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: Array<String>
    private lateinit var userData: ArrayList<User>

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        myDataset = Array<String>(130) { "$it" }
        userData = ArrayList()
        for (data in myDataset) {
            var user = User(id = data, title = "User " + data, desc = "Desc " + data)
            userData.add(user)
        }

        /*RetrofitClient.getService().getProgrammingQuotes()?.enqueue(object: Callback<List<Quote>>{
            override fun onResponse(call: Call<List<Quote>>, response: Response<List<Quote>>) {
                if(response.isSuccessful) {
                    viewManager = LinearLayoutManager(this@MainActivity)

                    viewAdapter = MyAdapter(response.body() as ArrayList<Quote>) { position: Int ->
                        //Toast.makeText(MainActivity::class.java,"Clicked $position",Toast.LENGTH_SHORT).show()
                    }

                    binding.myRecyclerView.apply {
                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        setHasFixedSize(true)

                        // use a linear layout manager
                        layoutManager = viewManager

                        // specify an viewAdapter (see also next example)
                        adapter = viewAdapter
                    }
                }
            }
            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                //t.printStackTrace()
            }
        })

*/
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.getService().getProgrammingQuotes()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.
                        viewManager = LinearLayoutManager(this@MainActivity)

                        viewAdapter =
                            MyAdapter(response.body() as ArrayList<Quote>) { position: Int ->
                                //Toast.makeText(MainActivity::class.java,"Clicked $position",Toast.LENGTH_SHORT).show()
                            }

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
                        //toast("Error: ${response.code()}")
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
