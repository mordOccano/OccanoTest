package com.e.occanotestsidep.ui.main.status

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Status
import java.util.*

class NewStatusRVAdapter (list: ArrayList<Status>, onStatusListener: OnStatusListener):
RecyclerView.Adapter<NewStatusRVAdapter.ViewHolder>()
{
    private var llist: ArrayList<Status> = ArrayList()
    var mOnStatusListener: OnStatusListener? = null

   init{
       llist = list
        mOnStatusListener = onStatusListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        when(i){
            0 -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_blue,
                        parent,
                        false
                    ),
                    mOnStatusListener
                )
            }
            1->{
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_yellow,
                        parent,
                        false
                    ),
                    mOnStatusListener
                )
            }
            2 ->{
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_red,
                        parent,
                        false
                    ),
                    mOnStatusListener
                )
            }
            else -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.status_rv_item_blue,
                        parent,
                        false
                    ),
                    mOnStatusListener
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return llist[position].statusKindOfDanger
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

    }

    override fun getItemCount(): Int {
        return llist.size
    }

    class ViewHolder(itemView: View, onNoteListener: OnStatusListener?) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
//        var tvNoteTitle: TextView
//        var tvTimeStamp: TextView
//        var mOnNoteListener: OnNoteListener?
        override fun onClick(v: View) {
//            mOnNoteListener!!.onNoteClick(adapterPosition)
        }

        init {
//            tvNoteTitle = itemView.findViewById(R.id.note_title)
//            tvTimeStamp = itemView.findViewById(R.id.note_time_stamp)
//            mOnNoteListener = onNoteListener
//            itemView.setOnClickListener(this)
        }
    }

    interface OnNoteListener {
        fun onNoteClick(position: Int)
    }

    interface OnStatusListener {
        fun onStatusClick(position: Int)
    }


}