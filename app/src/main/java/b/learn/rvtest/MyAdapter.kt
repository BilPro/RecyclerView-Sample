package b.learn.rvtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import b.learn.rvtest.databinding.MyTextViewBinding
import b.learn.rvtest.databinding.RowTypeTwoBinding


//https://www.androidhive.info/android-databinding-in-recyclerview-profile-screen/
//https://stackoverflow.com/a/59030115/3529309

class MyAdapter(private val quotes:ArrayList<Quote>, val clickListener: (position : Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_1 : Int =0
    private val VIEW_TYPE_2 : Int =1

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

    class TypeTwoViewHolder(val binding: RowTypeTwoBinding) : RecyclerView.ViewHolder(binding.root) {
        var clRow = binding.clRow

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
    ): RecyclerView.ViewHolder {
        // create a new view
        /*val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_text_view, parent, false)*/
            layoutInflater = LayoutInflater.from(parent.context)
        return if(viewType==VIEW_TYPE_1) {
            val listItemBinding = MyTextViewBinding.inflate(layoutInflater, parent, false)
            // val binding: MyTextViewBinding = DataBindingUtil.inflate(layoutInflater,R.layout.my_text_view, parent, false)
            // set the view's size, margins, paddings and layout parameters
            MyViewHolder(listItemBinding)
        }else {
            val rowTypeTwoBinding = RowTypeTwoBinding.inflate(layoutInflater, parent, false)
            TypeTwoViewHolder(rowTypeTwoBinding)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = quotes.size
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder.apply {
            //tvNumber.text = users[position].id
            //tvTitle.text = users?.get(position).title
            //tvDescribtion.text = users?.get(position).desc
            /*clRow.setOnClickListener {
                clickListener(position)
            }*/
            //bind(users[position],clickListener)
            //user=users[position]
        //}
        if(holder.itemViewType==VIEW_TYPE_1){
            var myViewHolder = holder as MyViewHolder
            myViewHolder.bind(quotes[position],clickListener)
        }else if(holder.itemViewType==VIEW_TYPE_2){
            var myViewHolder = holder as TypeTwoViewHolder
            myViewHolder.bind(quotes[position],clickListener)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(position % 2==0){
            VIEW_TYPE_1
        }else{
            VIEW_TYPE_2
        }
    }
}