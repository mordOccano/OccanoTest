package com.e.occanotestsidep.ui.main.alert

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Alert
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_subtitle
import kotlinx.android.synthetic.main.status_rv_item.view.tv_item_title
import kotlinx.android.synthetic.main.status_rv_item_blue.view.*
import kotlin.math.roundToInt

class AlertAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Alert>() {

        override fun areItemsTheSame(oldItem: Alert, newItem: Alert): Boolean {
            return oldItem.alert_id == newItem.alert_id
        }


        override fun areContentsTheSame(oldItem: Alert, newItem: Alert): Boolean {
            return oldItem == newItem
         }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return AlertViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.status_rv_item_blue,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlertViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Alert>) {
        differ.submitList(list)
    }

    class AlertViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Alert) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.tv_item_title.text = item.description.toString()
            itemView.tv_item_subtitle.text = item.labels.toString()

            if (item.saverity.toInt()>5){
                itemView.item_saverity_img.setImageResource(R.drawable.ic_report_black_24dp )
            }
            when(item.saverity.roundToInt()){
                0 ->{ itemView.item_saverity_img.setImageResource(R.drawable.ic_trending_down_black_24dp) }
                1 ->{ itemView.item_saverity_img.setImageResource(R.drawable.ic_report_problem_black_24dp) }
                2 ->{ itemView.item_saverity_img.setImageResource(R.drawable.ic_report_problem_black_24dp) }
                3 ->{ itemView.item_saverity_img.setImageResource(R.drawable.ic_report_problem_black_24dp) }
                4 ->{ itemView.item_saverity_img.setImageResource(R.drawable.ic_report_black_24dp) }
                5 ->{ itemView.item_saverity_img.setImageResource(R.drawable.ic_report_black_24dp) }

            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Alert)
    }
}
