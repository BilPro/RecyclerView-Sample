package b.learn.rvtest

import android.R
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import b.learn.rvtest.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException


class MainActivity : AppCompatActivity() {

    //https://programming-quotes-api.herokuapp.com/quotes
    //https://codeburst.io/android-swipe-menu-with-recyclerview-8f28a235ff28
    //https://medium.com/exploring-android/android-networking-with-coroutines-and-retrofit-a2f20dd40a83
    //https://android.jlelse.eu/kotlin-coroutines-and-retrofit-e0702d0b8e8f


    //https://stackoverflow.com/questions/44965278/recyclerview-itemtouchhelper-buttons-on-swipe
    //https://stackoverflow.com/questions/32227482/recyclerview-swipe-with-a-view-below-it
    //https://stackoverflow.com/questions/20797099/swipe-listview-item-from-right-to-left-show-delete-button

    //Half swipe libraries
    //https://github.com/ntnhon/RecyclerViewRowOptionsDemo
    //https://github.com/jinksw/SwipeToShow
    //https://github.com/chthai64/SwipeRevealLayout (Try this)
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var myDataset: Array<String>
    private lateinit var userData: ArrayList<User>
    private lateinit var quotes :ArrayList<Quote>

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*myDataset = Array<String>(130) { "$it" }
        userData = ArrayList()
        for (data in myDataset) {
            var user = User(id = data, title = "User " + data, desc = "Desc " + data)
            userData.add(user)
        }*/

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

                        quotes = response.body() as ArrayList<Quote>
                        viewAdapter =
                            MyAdapter(quotes,{ position: Int ->
                                //Toast.makeText(MainActivity::class.java,"Clicked $position",Toast.LENGTH_SHORT).show()
                                var addQuote = quotes[quotes.size-1]
                                var newQuote = Quote((addQuote.id.toInt()+1).toString(),addQuote.en,addQuote.author)
                                quotes.add(0,newQuote)
                                viewAdapter.notifyItemInserted(0)
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
                        ///val itemTouchHelper = ItemTouchHelper(simpleCallback)
                        //itemTouchHelper.attachToRecyclerView(binding.myRecyclerView)
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


    /*var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: ViewHolder,
            target: ViewHolder
        ): Boolean {
            Toast.makeText(this@MainActivity, "on Move", Toast.LENGTH_SHORT).show()
            return false
        }

        override fun onSwiped(viewHolder: ViewHolder, swipeDir: Int) {
            Toast.makeText(this@MainActivity, "on Swiped ", Toast.LENGTH_SHORT).show()
            //Remove swiped item from list and notify the RecyclerView
           // val position = viewHolder.adapterPosition
           // quotes.removeAt(position)
            //viewAdapter.notifyDataSetChanged()
        }

        //https://stackoverflow.com/questions/41015101/itemtouchhelper-limit-swipe-width-of-itemtouchhelper-simplecallback-on-recycle
        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX/4, dY/4, actionState, isCurrentlyActive)
        }
    }*/

}
