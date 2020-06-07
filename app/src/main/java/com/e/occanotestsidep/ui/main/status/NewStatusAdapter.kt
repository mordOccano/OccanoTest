package com.e.occanotestsidep.ui.main.status

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occanotestsidep.ui.models.Status
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Alert
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_details
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_subtitle
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_timestamp
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_title
import kotlinx.android.synthetic.main.status_rv_item_blue.view.*
import java.util.ArrayList

class NewStatusAdapter(private val interaction: Interaction?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    var llist: MutableList<Status> =
//        ArrayList()
//    private val llist: ArrayList<Status> = ArrayList()
//    var interaction: Interaction


    private val TAG: String = "NewStatusAdapter"

    override fun getItemViewType(position: Int): Int {
//        return llist.get(position).statusKindOfDanger
//        return differ.currentList[position].saverity
//        right now we've just alert
        return 2
    }


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Alert>() {

        override fun areItemsTheSame(oldItem: Alert, newItem: Alert): Boolean {
            return oldItem.alert_id == newItem.alert_id
        }

        override fun areContentsTheSame(oldItem: Alert, newItem: Alert): Boolean {
            return  oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            0 -> {
                return NewStatusViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_blue,
                        parent,
                        false
                    ),
                    interaction
                )
            }
            1->{
                return NewStatusViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_yellow,
                        parent,
                        false
                    ),
                    interaction
                )
            }
            2 ->{
                return NewStatusViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_red,
                        parent,
                        false
                    ),
                    interaction
                )
            }
            else -> {
                return NewStatusViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_blue,
                        parent,
                        false
                    ),
                    interaction
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (!differ.currentList[position].kindOfAcknowledge){
            when (holder) {
                is NewStatusViewHolder -> {
                    holder.bind(differ.currentList[position])
                }
            }
//        }else{
//            holder.itemView.visibility = View.GONE
//        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
//        return llist.size
    }

    fun submitList(list: List<Alert>) {
       differ.submitList(list)
    }

    class NewStatusViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Alert) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.tv_item_title.text = item.description.toString()
//            itemView.tv_item_timestamp.text = item.timeStampOfstatus
            itemView.tv_item_subtitle.text = item.labels.toString()
//            itemView.tv_item_details.text = item.statusMoreContent

            if (item.saverity>=1 && item.saverity<3){
                itemView.item_saverity_img.setImageResource(R.drawable.ic_report_black_24dp)
            } else if (item.saverity>=3){
                itemView.item_saverity_img.setImageResource(R.drawable.ic_report_problem_black_24dp)
            }else{
                itemView.item_saverity_img.setImageResource(R.drawable.ic_trending_down_black_24dp)
            }
//            itemView.btn_item_rv_status_more.setOnClickListener {
//                tv_item_subtitle.visibility = View.GONE
//                tv_item_details.visibility = View.VISIBLE
//                tv_item_details.text = item.getStatusMoreContent()
//                btn_item_rv_status_more.visibility = View.GONE
//                btn_item_rv_status_less.visibility = View.VISIBLE
//            }
//
//            itemView.btn_item_rv_status_less.setOnClickListener {
//                tv_item_details.visibility = View.GONE
//                btn_item_rv_status_less.visibility = View.GONE
//                tv_item_subtitle.visibility = View.VISIBLE
//                btn_item_rv_status_more.visibility = View.VISIBLE
//            }

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Alert)
    }
}
