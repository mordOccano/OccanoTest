package com.e.occanotestsidep.ui.main.dashboard

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Gauge
import kotlinx.android.synthetic.main.cylinders_frag_rv_item.view.*

class CylindersRvAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<List<Gauge>>() {

        override fun areItemsTheSame(oldItem: List<Gauge>, newItem: List<Gauge>): Boolean {
            if (oldItem.isNotEmpty()){
                oldItem[0].value == newItem[0].value
            }
            return false
        }

        override fun areContentsTheSame(oldItem: List<Gauge>, newItem: List<Gauge>): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CylindersViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cylinders_frag_rv_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var temPostion = position
            if (position > 10) {
                temPostion = position % 11
            }

            when (holder) {
                is CylindersViewHolder -> {
                    holder.bind(differ.currentList[temPostion])
                }
            }

    }

    override fun getItemCount(): Int{
        try {
           return differ.currentList[0].size

        }catch (e: Exception ){
            e.printStackTrace()
        }
        return 0
    }



    fun submitList(list: List<List<Gauge>>) {
        differ.submitList(list)
    }

    class CylindersViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: List<Gauge>) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            if(item.isNotEmpty()) {

                try {
                    itemView.cylinders_kind.text = "gauge: ${item[0].name}"
                    itemView.cylinders_unit.text = "unit: ${item[0].unit}"
                    itemView.cyl1.setMaxSpeed(item[0].maxValue)
                    itemView.cyl1.tickNumber = 9
                    itemView.cyl1.speedTo(item[0].value)
                    itemView.cyl2.setMaxSpeed(item[1].maxValue)
                    itemView.cyl2.tickNumber = 9
                    itemView.cyl2.speedTo(item[1].value)
                    itemView.cyl3.setMaxSpeed(item[2].maxValue)
                    itemView.cyl3.tickNumber = 9
                    itemView.cyl3.speedTo(item[2].value)
                    itemView.cyl4.setMaxSpeed(item[3].maxValue)
                    itemView.cyl4.tickNumber = 9
                    itemView.cyl4.speedTo(item[3].value)
                    itemView.cyl5.setMaxSpeed(item[4].maxValue)
                    itemView.cyl5.tickNumber = 9
                    itemView.cyl5.speedTo(item[4].value)
                    itemView.cyl6.setMaxSpeed(item[5].maxValue)
                    itemView.cyl6.tickNumber = 9
                    itemView.cyl6.speedTo(item[5].value)
                    itemView.cyl7.setMaxSpeed(item[5].maxValue)
                    itemView.cyl7.tickNumber = 9
                    itemView.cyl7.speedTo(item[5].value)
                    itemView.cyl8.setMaxSpeed(item[5].maxValue)
                    itemView.cyl8.tickNumber = 9
                    itemView.cyl8.speedTo(item[5].value)
                    itemView.cylinders_status_description.text = "All Cylinder are normal"

                    var s:String = ""
                    var avg: Float = 0.0f
                    for (i in item){
                        avg += i.value
                    }
                    avg /= item.size
                    for(i in item){
                        if (i.value> 1.1*avg || i.value<0.9*avg){
                            //when will be a real healthy it will replace that value calculation

                            s += "cylinder ${i.cyl_num} is mot normal\n"
                        }
                    }

                    //consider to put in the cell another recycler view in order to accept all of the situation of engines with different count of cylinders
                    if (s.length>3){
                        itemView.cylinders_status_description.text = s
                    }
                    else{itemView.cylinders_status_description.text = ""
                    }

                }catch (e: Exception){
                    Log.e("CylindersViewHolder", "UI exception")
                }


            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: List<Gauge>)
    }
}
