package com.e.occanotestsidep.ui.main.status

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.occanosidetest.utils.TopSpacingItemDecoration

import com.e.occanotestsidep.R
import com.e.occanotestsidep.persistence.AppDBK
import com.e.occanotestsidep.ui.main.DashboardStateEvent
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.Status
import kotlinx.android.synthetic.main.fragment_status.*
import kotlinx.android.synthetic.main.fragment_status_archive.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatusArchiveFragment : Fragment() ,AckStatusAdapter.Interaction, View.OnClickListener,AckStatusRVAdapter.OnStatusListener {

    private val TAG: String = "StatusArchiveFragment"

    lateinit var viewModel: MainViewModel
    lateinit var dataStateListener: DataStateListener
    lateinit var statusAckRVAdapter: AckStatusRVAdapter

//    private var statusesListForRv:MutableList<Status> = ArrayList()
    private var statusesListForRv:ArrayList<Status> = ArrayList()
    private var statusesListFromApi:ArrayList<Status> = ArrayList()
    private lateinit var dbk: AppDBK


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_archive, container, false)
    }

    override fun onResume() {
        super.onResume()
        setUI()
    }

    private fun setUI() {
        dbk = AppDBK(requireContext())
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        }?:throw Exception("Invalid activity")
        triggerGetStatusesEvent()
        setListeners()
        subscribeObservers()
        initAckRv()    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUI()
    }

    private fun triggerGetStatusesEvent() {
        viewModel.setStateEvent(DashboardStateEvent.GetCylinders())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener ")
        }
    }



    private fun subscribeObservers(){
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            println("Debug: DataState: ${dataState} ")

            //handle loading and message
            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let {
                it.getContentIfNotHandled()?.statuses?.let {
                    viewModel.setStatusesData(it)
                }
            }

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->

            viewState.statuses.let {
                println("DEBUG: Setting statuses for rv: ${it}")
                it?.let {
                    prepareStatusList(it)
                }

            }
        })
    }



    private fun prepareStatusList(it: List<Status>) {

        GlobalScope.launch {
            val list = (dbk.getStatusDao()?.getAllStatuses()!!.toMutableList())

//          in order to upddate the api in the future, which of the statuses is a known by crew
//          and the update this mobile app, from the api, because there is an update data.
//            for (i in list) {
//                dbk.getStatusDao()?.insertStatus(i)
//            }

            withContext(Dispatchers.Main){
                statusesListForRv.addAll(list)
//                statusAckRVAdapter.submitList(statusesListForRv)
                statusAckRVAdapter.notifyDataSetChanged()

            }


            Log.e("3 ${TAG}",dbk.getStatusDao()?.getAllStatuses()!!.toMutableList().toString())
            Log.e("3 ${TAG}",statusesListForRv.toString())

            withContext(Dispatchers.Main){
                statusesListForRv.addAll(list)
                prepareSubList()
            }

            for (i in statusesListForRv) {
                dbk.getStatusDao()?.deleteStatus(i)
                dbk.getStatusDao()?.insertStatus(i)
            }

            Log.e("3 ${TAG}",dbk.getStatusDao()?.getAllStatuses()!!.toMutableList().toString())
            Log.e("3 ${TAG}",statusesListForRv.toString())

        }
    }

    private fun prepareSubList(){
        for (i in statusesListForRv){
            if (!i.kindOfAcknowledge){
                for (j in statusesListFromApi){
                    if (i.statusId == j.statusId){
                        statusesListFromApi.remove(j)
                    }
                }
            }
        }
        statusesListForRv.union(statusesListFromApi)
        statusAckRVAdapter.notifyDataSetChanged()
    }

    //        statusAckRVAdapter.submitList(statusesList.toList())


    private fun setListeners(){
       view?.findViewById<ImageButton>(R.id.btn_archive_to_status)!!.setOnClickListener(this)
    }

    private fun initAckRv() {
        status_rv_acknowledge_notification.apply {
            layoutManager = LinearLayoutManager(activity)
            val  topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(status_rv_acknowledge_notification)
            statusAckRVAdapter = AckStatusRVAdapter(statusesListForRv,this@StatusArchiveFragment)
            adapter = statusAckRVAdapter
        }
    }

    override fun onItemSelected(position: Int, item: Status) {
        //remove from db - swipe
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_archive_to_status -> {
                v.findNavController().navigate(R.id.action_statusArchiveFragment_to_statusFragment)
            }
            else ->{

            }
        }

    }

    private val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteStatus(statusesListForRv[viewHolder.adapterPosition])
            }
        }

    private fun deleteStatus(status: Status) {
        statusesListForRv.remove(status)
        //what happend here - is it possible to swipe?
        GlobalScope.launch {
            dbk.getStatusDao()?.deleteStatus(status)
        }
        statusAckRVAdapter.notifyDataSetChanged()
    }

    override fun onStatusClick(position: Int) {
        Toast.makeText(context,"the cell is ${position}",Toast.LENGTH_LONG).show()
    }
}
