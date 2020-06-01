package com.e.occanotestsidep.ui.main.dashboard

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occanosidetest.utils.StaticAddress.Companion.max_bmep_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_break_power_gauge
import com.e.occanosidetest.utils.StaticAddress.Companion.max_comp_pres_gauge
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.Gauge
import com.e.occanotestsidep.ui.models.MainDashboard
import com.e.occanotestsidep.ui.models.Status
import kotlinx.android.synthetic.main.main_dashboard_rv_item.view.*

class DasboardMainRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainDashboard>() {
        override fun areItemsTheSame(oldItem: MainDashboard, newItem: MainDashboard): Boolean {
            return oldItem.value == newItem.value
        }

        override fun areContentsTheSame(oldItem: MainDashboard, newItem: MainDashboard): Boolean {
            return oldItem == newItem
        }


    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            0 ->{
                return MainDashboardRvViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.main_dashboard_rv_blue,
                        parent,
                        false
                    ),
                    interaction
                )
            }
            1->{
                return MainDashboardRvViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.main_dashboard_rv_item_red,
                            parent,
                            false
                        ),
                interaction
                )
            }
            -1->{
                return MainDashboardRvViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.main_dashboard_rv_item_red,
                        parent,
                        false
                    ),
                    interaction
                )
            }
            else -> {
                return MainDashboardRvViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.main_dashboard_rv_blue,
                        parent,
                        false
                    ),
                    interaction
                )
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return differ.currentList[position].gaugeHealth

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainDashboardRvViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list:List<MainDashboard>) {
        differ.submitList(list)
    }

    class MainDashboardRvViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: MainDashboard) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            itemView.main_dash_rv_item_title.text =  " ${item.name}"

            itemView.main_dash_rv_item_gauge?.let {
                it.setMaxSpeed(item.maxSpeed)
                it.tickNumber = 9
                it.speedTo(item.value)
                it.setUnit(item.unit)
            }

            var s: String? = ""
            item.status?.let {
                for (i in it){
                    s += "${i.statusMainTitle.toString()} "
                }
            }

            itemView.main_dash_rv_item_status.text = s.toString()

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: MainDashboard)
    }
}
