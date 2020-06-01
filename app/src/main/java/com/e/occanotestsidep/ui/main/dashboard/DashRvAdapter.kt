package com.e.occanotestsidep.ui.main.dashboard

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Cylinder
import kotlinx.android.synthetic.main.dash_frag_rv_item.view.*

class DashRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cylinder>() {

        override fun areItemsTheSame(oldItem: Cylinder, newItem: Cylinder): Boolean {
           return oldItem.bmep.value==newItem.bmep.value
        }

        override fun areContentsTheSame(oldItem: Cylinder, newItem: Cylinder): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return DashViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.dash_frag_rv_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DashViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Cylinder>) {
        differ.submitList(list)
    }

    class DashViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Cylinder) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.cylinder_dash_num.text = "Cylinder Num: ${item.numOfCylInEngine.toString()}"
            itemView.bmep_gauge.speedTo(item.bmep.value)
            itemView.exhaust_gauge.speedTo(item.exhaust_temperature.value)
            itemView.fuel_gauge.speedTo(item.fuel_flow_rate.value)
            itemView.imep_gauge.speedTo(item.imep.value)
            itemView.break_power_gauge.speedTo(item.break_power.value)
            itemView.comp_pres_gauge.speedTo(item.compression_pressure.value)
            itemView.engine_speed_gauge.speedTo(item.rpm.value)
            itemView.firing_pres_gauge.speedTo(item.firing_pressure.value)
            itemView.load_gauge.speedTo(item.load.value)
            itemView.injec_gauge.speedTo(item.injection_timing.value)
            itemView.scave_gauge.speedTo(item.scavenging_pressure.value)
            itemView.torque_gauge.speedTo(item.torque_engine.value)
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Cylinder)
    }
}
