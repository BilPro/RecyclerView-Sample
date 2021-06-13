Paging 2 - Showing data from web service
-----------------------------------------
Step 0: Add dependencies
Step 1. First of all add the DataSource and extend from DataSource.Factory<Long, Passenger>()
Now, override the loadInitial and loadAfter
In loadInitial add the code the get data from network that will be called first.
loadAfter
This will be called after first time
You will call callback.onResult(passengers.data, null, 2.toLong()) method first time in loadInitial when you get the successful response.
And pass the key of next page in last param.

In next method loadAfter add the
callback.onResult(passengers.data,  params.key + 1)
method and pass next page key in last param.

In our case we will start page from one and keep add on it to get the next page.

Step 2. Add DataSourceFactory factory and extend it with DataSource.Factory<Long, Passenger>()

Step 3. Now build the configuration to get the page

var config = (PagedList.Config.Builder()).setEnablePlaceholders(true)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .setPrefetchDistance(4)
            .build()

Step 4. Now get the LiveData
var dataSourceFactory  = DataSourceFactory()
var executor = Executors.newFixedThreadPool(5)
var passengerPagedList = LivePagedListBuilder<Long, Passenger>(dataSourceFactory, config)
            .setFetchExecutor(executor)
            .build()

Step 5. Extend Adapter from PagedListAdapter<Passenger, RecyclerView.ViewHolder>(MyAdapter.CALLBACK)

Add the below code in adapter
companion object {
        val CALLBACK: DiffUtil.ItemCallback<Passenger?> =
            object : DiffUtil.ItemCallback<Passenger?>() {

                override fun areContentsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
                    return true
                }

                override fun areItemsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
                    return oldItem._id === newItem._id
                }
            }
    }

Now you will get the item from this method getItem(position) in adapter

Step 6. Now observe the live data and call the submitList method to set the list in adapter
passengerPagedList.observe(this, Observer {
            viewAdapter.submitList(it)
        } )
