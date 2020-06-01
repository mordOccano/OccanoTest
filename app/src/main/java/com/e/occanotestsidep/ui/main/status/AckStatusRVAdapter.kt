package com.e.occanotestsidep.ui.main.status

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Status
import kotlinx.android.synthetic.main.status_rv_item.view.*
import java.util.*

class AckStatusRVAdapter (list: ArrayList<Status>, onStatusListener: OnStatusListener):
RecyclerView.Adapter<AckStatusRVAdapter.ViewHolder>()
{
    private val TAG :String = "AckStatusRVAdapter"
    private var llist: ArrayList<Status> = ArrayList()
    var mOnStatusListener: OnStatusListener? = null

   init{
       llist = list
        mOnStatusListener = onStatusListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (llist[position].kindOfAcknowledge){
            try {
                holder.tvItemTitle?.text = llist[position].statusMainTitle
                holder.tvItemTimeStamp?.text = llist[position].timeStampOfstatus
                holder.tvItemSubTitle?.text = llist[position].statusSubTitle
                holder.tvItemDetails?.text = llist[position].statusMoreContent

            } catch (e: NullPointerException) {
                Log.e(
                   TAG,
                    "onBindViewHolder: NullPointerException " + e.message
                )
            }

        }
    }
    override fun getItemCount(): Int {
        return llist.size
    }

    class ViewHolder(itemView: View, onStatusListener: OnStatusListener?) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvItemTitle: TextView? = null
        var tvItemTimeStamp: TextView? = null
        var tvItemSubTitle: TextView? = null
        var tvItemDetails: TextView? = null
        var mOnStatusListener: OnStatusListener? = null
        override fun onClick(v: View) {
        mOnStatusListener!!.onStatusClick(adapterPosition)
        }

        init {
            tvItemTitle = itemView.tv_item_title
            tvItemSubTitle = itemView.tv_item_subtitle
            tvItemTimeStamp = itemView.tv_item_timestamp
            tvItemDetails = itemView.tv_item_details
//
            mOnStatusListener = onStatusListener
            itemView.setOnClickListener(this)
        }
    }

    interface OnStatusListener {
        fun onStatusClick(position: Int)
    }


}