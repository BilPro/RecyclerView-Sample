package b.learn.rvtest

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import b.learn.rvtest.databinding.MyTextViewBinding


//https://www.androidhive.info/android-databinding-in-recyclerview-profile-screen/

class MyAdapter(private val users:ArrayList<Quote>, val clickListener: (position : Int) -> Unit) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    lateinit var layoutInflater :LayoutInflater
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val binding: MyTextViewBinding) : RecyclerView.ViewHolder(binding.root) {
        //var tvNumber: AppCompatTextView = binding.tvNumber
        //var tvTitle = binding.tvTitle
        //var tvDescribtion = binding.tvDescription
        var clRow = binding.clRow
        var quote : Quote? = binding.quote

        fun bind (quote : Quote, clickListener:(position : Int) -> Unit){
            binding.quote=quote
            clRow.setOnClickListener {
                clickListener(adapterPosition)
            }
            binding.executePendingBindings()
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAdapter.MyViewHolder {
        // create a new view
        /*val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_text_view, parent, false)*/
            layoutInflater = LayoutInflater.from(parent.context)
        val listItemBinding = MyTextViewBinding.inflate(layoutInflater, parent, false)

       // val binding: MyTextViewBinding = DataBindingUtil.inflate(layoutInflater,R.layout.my_text_view, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(listItemBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.apply {
            //tvNumber.text = users[position].id
            //tvTitle.text = users?.get(position).title
            //tvDescribtion.text = users?.get(position).desc
            /*clRow.setOnClickListener {
                clickListener(position)
            }*/
            bind(users[position],clickListener)
            //user=users[position]
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = users.size
}