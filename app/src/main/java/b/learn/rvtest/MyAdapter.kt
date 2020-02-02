package b.learn.rvtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.my_text_view.view.*

class MyAdapter(private val myDataset: Array<String>, val clickListener: (view: View,position : Int) -> Unit) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var tvNumber: AppCompatTextView = v.tvNumber
        var tvTitle = v.tvTitle
        var tvDescribtion = v.tvDescription
        var clRow = v.clRow
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyAdapter.MyViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_text_view, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.apply {
            tvNumber.text = myDataset[position]
            tvTitle.text = "Title " + myDataset[position]
            tvDescribtion.text = "Describtion " + myDataset[position]
            clRow.setOnClickListener {
                clickListener(v,position)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}