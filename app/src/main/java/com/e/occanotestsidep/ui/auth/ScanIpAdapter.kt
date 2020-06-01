package com.e.occanotestsidep.ui.auth

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Device
import kotlinx.android.synthetic.main.scan_ip_rv_item_not_selected.view.*
import kotlinx.android.synthetic.main.scan_ip_rv_item_selected.view.*

class ScanIpAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Device>() {

        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem.ip == newItem.ip
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemViewType(position: Int): Int {
        return when(differ.currentList[position].isSelected){
            true -> 1
            false -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(viewType){
            0 -> return ScanIpVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.scan_ip_rv_item_not_selected,
                    parent,
                    false
                ),
                interaction
            )
            1 -> return ScanIpVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.scan_ip_rv_item_selected,
                parent,
                false
            ),
            interaction
        )
            else -> return ScanIpVH(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.scan_ip_rv_item_not_selected,
                    parent,
                    false
                ),
                interaction
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScanIpVH -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Device>) {
        differ.submitList(list)
        Log.e("submitList" , list.toString())
    }

    class ScanIpVH
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Device) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }


            itemView.tv_rv_scan_hostname_sel?.text = item.hostname
            itemView.tv_rv_scan_ip_sel?.text = item.ip
            itemView.tv_rv_scan_hostname_not?.text = item.hostname
            itemView.tv_rv_scan_ip_not?.text = item.ip


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Device)
    }
}
