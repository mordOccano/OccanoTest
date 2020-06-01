package com.e.occanotestsidep.ui.main.dashboard

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.CalibGauge
import com.e.occanotestsidep.ui.models.Gauge
import kotlinx.android.synthetic.main.calib_rv_item_not.view.*

class CalibrationRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CalibGauge>() {

        override fun areItemsTheSame(oldItem: CalibGauge, newItem: CalibGauge): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: CalibGauge, newItem: CalibGauge): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position].isSelected){
            false -> 0
            true -> 1
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
          0 -> return CalibViewHolder(
              LayoutInflater.from(parent.context).inflate(
                  R.layout.calib_rv_item_not,
                  parent,
                  false
              ),
              interaction
          )
            1-> return CalibViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.calib_rv_item_selected,
                    parent,
                    false
                ),
                interaction
            )
            else -> return CalibViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.calib_rv_item_not,
                        parent,
                        false
                    ),
                    interaction
                )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CalibViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CalibGauge>) {
        differ.submitList(list)
    }

    class CalibViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: CalibGauge) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.rv_calibration_text?.text = item.name
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CalibGauge)
    }
}
