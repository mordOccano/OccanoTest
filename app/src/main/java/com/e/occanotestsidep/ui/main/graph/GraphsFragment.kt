package com.e.occanotestsidep.ui.main.graph


//import com.e.occanotestsidep.persistence.Graph.repoGraph.CombPresRepository
//import com.e.occanotestsidep.persistence.Graph.repoGraph.FuelRepository
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.main.DashboardStateEvent
import com.e.occanotestsidep.ui.main.DataStateListener
import com.e.occanotestsidep.ui.main.MainViewModel
import com.e.occanotestsidep.ui.models.GraphDots
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


/**
 * A simple [Fragment] subclass.
 */
class GraphsFragment : Fragment() ,View.OnClickListener{

    //mvi example
    /**
    1. properties
    lateinit var viewModel : ViewModel

    --ViewModel

    2.
    lateinit var dataStateListener: DataStateListener

    3.in onViewCreated
    viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
                 }?:throw Exception("Invalid activity")


     4.
    private fun subscribeObservers() {
    viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
    println("Debug: DataState: $dataState")

    dataStateListener.onDataStateChange(dataState)

    dataState.data?.let {
    it.getContentIfNotHandled()?.graphDots?.let {
    viewModel.setGraphData(it)
    }
    }

    viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
    viewState.graphDots.let{
    println( "Debug: setting graph to view")
    it?.let{
    prepareTheDataForGraph(it)
    }
    }
    })
    })
    }

     */

    /**
     * recyclerview example
     *
     * lateinit var RvAdapter: RvAdapter
     */

    private val TAG: String = "GraphFragment"

    lateinit var viewModel : MainViewModel
    lateinit var dataStateListener: DataStateListener



    //TODO(update the fragment for the last update from api - dots[][] for every cylinder)
    var btnGraphToDash :ImageButton? = null

    private var engin: Float?=0.0f

    var engineGraph: GraphView? = null

    var graphDotsList: ArrayList<GraphDots> = ArrayList()
    var seriesList: ArrayList<LineGraphSeries<DataPoint>> = ArrayList()
    var series: LineGraphSeries<DataPoint> = LineGraphSeries()

    private var graph2LastXValue: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graphs, container, false)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG,"onAttach")
        try {
            dataStateListener = context as DataStateListener
        }catch (e: ClassCastException){
            println("DEBUG: $context must implement DataStateListener ")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
                 }?:throw Exception("Invalid activity")

        setPointer(view)
//        initGraphs()
        setListeners()
        subscribeObservers()
        triggerEvent()

    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("Debug: DataState: $dataState")

            dataStateListener.onDataStateChange(dataState)

//            dataState.data?.let { event ->
//                event.getContentIfNotHandled().let {
//                    viewModel.setMainData(it!!)
//                }
//            }

            viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
                viewState.graphDots.let{
                    println( "Debug: setting graph to view ${it.toString()}")
                    it?.let{
                        prepareTheDataForGraph(it)
                    }
                }
            })
        })
    }

    private fun prepareTheDataForGraph(it: List<GraphDots>) {
//        graphDotsList?.addAll(it)

        /**
         * 1. get the data
         * 2. convert the data to datapoints
         * 3. insert the data points to the graph
         */



        it.run {
//            val tempSeries: LineGraphSeries<DataPoint> = LineGraphSeries(
//                arrayOf<DataPoint>(
//                    DataPoint(0.0, 0.0)
//                )
//            )
            var dots: ArrayList<DataPoint> = ArrayList(360)
            for (i in it){
                for (j in 0..359){

                   dots.add(j,(DataPoint(j.toDouble(), i.yList[j])))
//                    tempSeries.appendData((DataPoint(j.toDouble(), i.yList[j])),true,360)
//                    series?.appendData(i.yList?.get(j)?.let { it1 -> DataPoint(j.toDouble(), it1) }, true, 360)
                }


                val seriesT: LineGraphSeries<DataPoint> =
                    LineGraphSeries(
                        arrayOf(
                            DataPoint(dots[0].x, dots[0].y),
                            DataPoint(dots[1].x, dots[1].y),
                            DataPoint(dots[2].x, dots[2].y),
                            DataPoint(dots[3].x, dots[3].y),
                            DataPoint(dots[4].x, dots[4].y),
                            DataPoint(dots[5].x, dots[5].y),
                            DataPoint(dots[6].x, dots[6].y),
                            DataPoint(dots[7].x, dots[7].y),
                            DataPoint(dots[8].x, dots[8].y),
                            DataPoint(dots[9].x, dots[9].y),
                            DataPoint(dots[10].x, dots[10].y),
                            DataPoint(dots[11].x, dots[11].y),
                            DataPoint(dots[12].x, dots[12].y),
                            DataPoint(dots[13].x, dots[13].y),
                            DataPoint(dots[14].x, dots[14].y),
                            DataPoint(dots[15].x, dots[15].y),
                            DataPoint(dots[16].x, dots[16].y),
                            DataPoint(dots[17].x, dots[17].y),
                            DataPoint(dots[18].x, dots[18].y),
                            DataPoint(dots[19].x, dots[19].y),
                            DataPoint(dots[20].x, dots[20].y),
                            DataPoint(dots[21].x, dots[21].y),
                            DataPoint(dots[22].x, dots[22].y),
                            DataPoint(dots[23].x, dots[23].y),
                            DataPoint(dots[24].x, dots[24].y),
                            DataPoint(dots[25].x, dots[25].y),
                            DataPoint(dots[26].x, dots[26].y),
                            DataPoint(dots[27].x, dots[27].y),
                            DataPoint(dots[28].x, dots[28].y),
                            DataPoint(dots[29].x, dots[29].y),
                            DataPoint(dots[30].x, dots[30].y),
                            DataPoint(dots[31].x, dots[31].y),
                            DataPoint(dots[32].x, dots[32].y),
                            DataPoint(dots[33].x, dots[33].y),
                            DataPoint(dots[34].x, dots[34].y),
                            DataPoint(dots[35].x, dots[35].y),
                            DataPoint(dots[36].x, dots[36].y),
                            DataPoint(dots[37].x, dots[37].y),
                            DataPoint(dots[38].x, dots[38].y),
                            DataPoint(dots[39].x, dots[39].y),
                            DataPoint(dots[40].x, dots[40].y),
                            DataPoint(dots[41].x, dots[41].y),
                            DataPoint(dots[42].x, dots[42].y),
                            DataPoint(dots[43].x, dots[43].y),
                            DataPoint(dots[44].x, dots[44].y),
                            DataPoint(dots[45].x, dots[45].y),
                            DataPoint(dots[46].x, dots[46].y),
                            DataPoint(dots[47].x, dots[47].y),
                            DataPoint(dots[48].x, dots[48].y),
                            DataPoint(dots[49].x, dots[49].y),
                            DataPoint(dots[50].x, dots[50].y),
                            DataPoint(dots[51].x, dots[51].y),
                            DataPoint(dots[52].x, dots[52].y),
                            DataPoint(dots[53].x, dots[53].y),
                            DataPoint(dots[54].x, dots[54].y),
                            DataPoint(dots[55].x, dots[55].y),
                            DataPoint(dots[56].x, dots[56].y),
                            DataPoint(dots[57].x, dots[57].y),
                            DataPoint(dots[58].x, dots[58].y),
                            DataPoint(dots[59].x, dots[59].y),
                            DataPoint(dots[60].x, dots[60].y),
                            DataPoint(dots[61].x, dots[61].y),
                            DataPoint(dots[62].x, dots[62].y),
                            DataPoint(dots[63].x, dots[63].y),
                            DataPoint(dots[64].x, dots[64].y),
                            DataPoint(dots[65].x, dots[65].y),
                            DataPoint(dots[66].x, dots[66].y),
                            DataPoint(dots[67].x, dots[67].y),
                            DataPoint(dots[68].x, dots[68].y),
                            DataPoint(dots[69].x, dots[69].y),
                            DataPoint(dots[70].x, dots[70].y),
                            DataPoint(dots[71].x, dots[71].y),
                            DataPoint(dots[72].x, dots[72].y),
                            DataPoint(dots[73].x, dots[73].y),
                            DataPoint(dots[74].x, dots[74].y),
                            DataPoint(dots[75].x, dots[75].y),
                            DataPoint(dots[76].x, dots[76].y),
                            DataPoint(dots[77].x, dots[77].y),
                            DataPoint(dots[78].x, dots[78].y),
                            DataPoint(dots[79].x, dots[79].y),
                            DataPoint(dots[80].x, dots[80].y),
                            DataPoint(dots[81].x, dots[81].y),
                            DataPoint(dots[82].x, dots[82].y),
                            DataPoint(dots[83].x, dots[83].y),
                            DataPoint(dots[84].x, dots[84].y),
                            DataPoint(dots[85].x, dots[85].y),
                            DataPoint(dots[86].x, dots[86].y),
                            DataPoint(dots[87].x, dots[87].y),
                            DataPoint(dots[88].x, dots[88].y),
                            DataPoint(dots[89].x, dots[89].y),
                            DataPoint(dots[90].x, dots[90].y),
                            DataPoint(dots[91].x, dots[91].y),
                            DataPoint(dots[92].x, dots[92].y),
                            DataPoint(dots[93].x, dots[93].y),
                            DataPoint(dots[94].x, dots[94].y),
                            DataPoint(dots[95].x, dots[95].y),
                            DataPoint(dots[96].x, dots[96].y),
                            DataPoint(dots[97].x, dots[97].y),
                            DataPoint(dots[98].x, dots[98].y),
                            DataPoint(dots[99].x, dots[99].y),
                            DataPoint(dots[100].x, dots[100].y),
                            DataPoint(dots[101].x, dots[101].y),
                            DataPoint(dots[102].x, dots[102].y),
                            DataPoint(dots[103].x, dots[103].y),
                            DataPoint(dots[104].x, dots[104].y),
                            DataPoint(dots[105].x, dots[105].y),
                            DataPoint(dots[106].x, dots[106].y),
                            DataPoint(dots[107].x, dots[107].y),
                            DataPoint(dots[108].x, dots[108].y),
                            DataPoint(dots[109].x, dots[109].y),
                            DataPoint(dots[110].x, dots[110].y),
                            DataPoint(dots[111].x, dots[111].y),
                            DataPoint(dots[112].x, dots[112].y),
                            DataPoint(dots[113].x, dots[113].y),
                            DataPoint(dots[114].x, dots[114].y),
                            DataPoint(dots[115].x, dots[115].y),
                            DataPoint(dots[116].x, dots[116].y),
                            DataPoint(dots[117].x, dots[117].y),
                            DataPoint(dots[118].x, dots[118].y),
                            DataPoint(dots[119].x, dots[119].y),
                            DataPoint(dots[120].x, dots[120].y),
                            DataPoint(dots[121].x, dots[121].y),
                            DataPoint(dots[122].x, dots[122].y),
                            DataPoint(dots[123].x, dots[123].y),
                            DataPoint(dots[124].x, dots[124].y),
                            DataPoint(dots[125].x, dots[125].y),
                            DataPoint(dots[126].x, dots[126].y),
                            DataPoint(dots[127].x, dots[127].y),
                            DataPoint(dots[128].x, dots[128].y),
                            DataPoint(dots[129].x, dots[129].y),
                            DataPoint(dots[130].x, dots[130].y),
                            DataPoint(dots[131].x, dots[131].y),
                            DataPoint(dots[132].x, dots[132].y),
                            DataPoint(dots[133].x, dots[133].y),
                            DataPoint(dots[134].x, dots[134].y),
                            DataPoint(dots[135].x, dots[135].y),
                            DataPoint(dots[136].x, dots[136].y),
                            DataPoint(dots[137].x, dots[137].y),
                            DataPoint(dots[138].x, dots[138].y),
                            DataPoint(dots[139].x, dots[139].y),
                            DataPoint(dots[140].x, dots[140].y),
                            DataPoint(dots[141].x, dots[141].y),
                            DataPoint(dots[142].x, dots[142].y),
                            DataPoint(dots[143].x, dots[143].y),
                            DataPoint(dots[144].x, dots[144].y),
                            DataPoint(dots[145].x, dots[145].y),
                            DataPoint(dots[146].x, dots[146].y),
                            DataPoint(dots[147].x, dots[147].y),
                            DataPoint(dots[148].x, dots[148].y),
                            DataPoint(dots[149].x, dots[149].y),
                            DataPoint(dots[150].x, dots[150].y),
                            DataPoint(dots[151].x, dots[151].y),
                            DataPoint(dots[152].x, dots[152].y),
                            DataPoint(dots[153].x, dots[153].y),
                            DataPoint(dots[154].x, dots[154].y),
                            DataPoint(dots[155].x, dots[155].y),
                            DataPoint(dots[156].x, dots[156].y),
                            DataPoint(dots[157].x, dots[157].y),
                            DataPoint(dots[158].x, dots[158].y),
                            DataPoint(dots[159].x, dots[159].y),
                            DataPoint(dots[160].x, dots[160].y),
                            DataPoint(dots[161].x, dots[161].y),
                            DataPoint(dots[162].x, dots[162].y),
                            DataPoint(dots[163].x, dots[163].y),
                            DataPoint(dots[164].x, dots[164].y),
                            DataPoint(dots[165].x, dots[165].y),
                            DataPoint(dots[166].x, dots[166].y),
                            DataPoint(dots[167].x, dots[167].y),
                            DataPoint(dots[168].x, dots[168].y),
                            DataPoint(dots[169].x, dots[169].y),
                            DataPoint(dots[170].x, dots[170].y),
                            DataPoint(dots[171].x, dots[171].y),
                            DataPoint(dots[172].x, dots[172].y),
                            DataPoint(dots[173].x, dots[173].y),
                            DataPoint(dots[174].x, dots[174].y),
                            DataPoint(dots[175].x, dots[175].y),
                            DataPoint(dots[176].x, dots[176].y),
                            DataPoint(dots[177].x, dots[177].y),
                            DataPoint(dots[178].x, dots[178].y),
                            DataPoint(dots[179].x, dots[179].y),
                            DataPoint(dots[180].x, dots[180].y),
                            DataPoint(dots[181].x, dots[181].y),
                            DataPoint(dots[182].x, dots[182].y),
                            DataPoint(dots[183].x, dots[183].y),
                            DataPoint(dots[184].x, dots[184].y),
                            DataPoint(dots[185].x, dots[185].y),
                            DataPoint(dots[186].x, dots[186].y),
                            DataPoint(dots[187].x, dots[187].y),
                            DataPoint(dots[188].x, dots[188].y),
                            DataPoint(dots[189].x, dots[189].y),
                            DataPoint(dots[190].x, dots[190].y),
                            DataPoint(dots[191].x, dots[191].y),
                            DataPoint(dots[192].x, dots[192].y),
                            DataPoint(dots[193].x, dots[193].y),
                            DataPoint(dots[194].x, dots[194].y),
                            DataPoint(dots[195].x, dots[195].y),
                            DataPoint(dots[196].x, dots[196].y),
                            DataPoint(dots[197].x, dots[197].y),
                            DataPoint(dots[198].x, dots[198].y),
                            DataPoint(dots[199].x, dots[199].y),
                            DataPoint(dots[200].x, dots[200].y),
                            DataPoint(dots[201].x, dots[201].y),
                            DataPoint(dots[202].x, dots[202].y),
                            DataPoint(dots[203].x, dots[203].y),
                            DataPoint(dots[204].x, dots[204].y),
                            DataPoint(dots[205].x, dots[205].y),
                            DataPoint(dots[206].x, dots[206].y),
                            DataPoint(dots[207].x, dots[207].y),
                            DataPoint(dots[208].x, dots[208].y),
                            DataPoint(dots[209].x, dots[209].y),
                            DataPoint(dots[210].x, dots[210].y),
                            DataPoint(dots[211].x, dots[211].y),
                            DataPoint(dots[212].x, dots[212].y),
                            DataPoint(dots[213].x, dots[213].y),
                            DataPoint(dots[214].x, dots[214].y),
                            DataPoint(dots[215].x, dots[215].y),
                            DataPoint(dots[216].x, dots[216].y),
                            DataPoint(dots[217].x, dots[217].y),
                            DataPoint(dots[218].x, dots[218].y),
                            DataPoint(dots[219].x, dots[219].y),
                            DataPoint(dots[220].x, dots[220].y),
                            DataPoint(dots[221].x, dots[221].y),
                            DataPoint(dots[222].x, dots[222].y),
                            DataPoint(dots[223].x, dots[223].y),
                            DataPoint(dots[224].x, dots[224].y),
                            DataPoint(dots[225].x, dots[225].y),
                            DataPoint(dots[226].x, dots[226].y),
                            DataPoint(dots[227].x, dots[227].y),
                            DataPoint(dots[228].x, dots[228].y),
                            DataPoint(dots[229].x, dots[229].y),
                            DataPoint(dots[230].x, dots[230].y),
                            DataPoint(dots[231].x, dots[231].y),
                            DataPoint(dots[232].x, dots[232].y),
                            DataPoint(dots[233].x, dots[233].y),
                            DataPoint(dots[234].x, dots[234].y),
                            DataPoint(dots[235].x, dots[235].y),
                            DataPoint(dots[236].x, dots[236].y),
                            DataPoint(dots[237].x, dots[237].y),
                            DataPoint(dots[238].x, dots[238].y),
                            DataPoint(dots[239].x, dots[239].y),
                            DataPoint(dots[240].x, dots[240].y),
                            DataPoint(dots[241].x, dots[241].y),
                            DataPoint(dots[242].x, dots[242].y),
                            DataPoint(dots[243].x, dots[243].y),
                            DataPoint(dots[244].x, dots[244].y),
                            DataPoint(dots[245].x, dots[245].y),
                            DataPoint(dots[246].x, dots[246].y),
                            DataPoint(dots[247].x, dots[247].y),
                            DataPoint(dots[248].x, dots[248].y),
                            DataPoint(dots[249].x, dots[249].y),
                            DataPoint(dots[250].x, dots[250].y),
                            DataPoint(dots[251].x, dots[251].y),
                            DataPoint(dots[252].x, dots[252].y),
                            DataPoint(dots[253].x, dots[253].y),
                            DataPoint(dots[254].x, dots[254].y),
                            DataPoint(dots[255].x, dots[255].y),
                            DataPoint(dots[256].x, dots[256].y),
                            DataPoint(dots[257].x, dots[257].y),
                            DataPoint(dots[258].x, dots[258].y),
                            DataPoint(dots[259].x, dots[259].y),
                            DataPoint(dots[260].x, dots[260].y),
                            DataPoint(dots[261].x, dots[261].y),
                            DataPoint(dots[262].x, dots[262].y),
                            DataPoint(dots[263].x, dots[263].y),
                            DataPoint(dots[264].x, dots[264].y),
                            DataPoint(dots[265].x, dots[265].y),
                            DataPoint(dots[266].x, dots[266].y),
                            DataPoint(dots[267].x, dots[267].y),
                            DataPoint(dots[268].x, dots[268].y),
                            DataPoint(dots[269].x, dots[269].y),
                            DataPoint(dots[270].x, dots[270].y),
                            DataPoint(dots[271].x, dots[271].y),
                            DataPoint(dots[272].x, dots[272].y),
                            DataPoint(dots[273].x, dots[273].y),
                            DataPoint(dots[274].x, dots[274].y),
                            DataPoint(dots[275].x, dots[275].y),
                            DataPoint(dots[276].x, dots[276].y),
                            DataPoint(dots[277].x, dots[277].y),
                            DataPoint(dots[278].x, dots[278].y),
                            DataPoint(dots[279].x, dots[279].y),
                            DataPoint(dots[280].x, dots[280].y),
                            DataPoint(dots[281].x, dots[281].y),
                            DataPoint(dots[282].x, dots[282].y),
                            DataPoint(dots[283].x, dots[283].y),
                            DataPoint(dots[284].x, dots[284].y),
                            DataPoint(dots[285].x, dots[285].y),
                            DataPoint(dots[286].x, dots[286].y),
                            DataPoint(dots[287].x, dots[287].y),
                            DataPoint(dots[288].x, dots[288].y),
                            DataPoint(dots[289].x, dots[289].y),
                            DataPoint(dots[290].x, dots[290].y),
                            DataPoint(dots[291].x, dots[291].y),
                            DataPoint(dots[292].x, dots[292].y),
                            DataPoint(dots[293].x, dots[293].y),
                            DataPoint(dots[294].x, dots[294].y),
                            DataPoint(dots[295].x, dots[295].y),
                            DataPoint(dots[296].x, dots[296].y),
                            DataPoint(dots[297].x, dots[297].y),
                            DataPoint(dots[298].x, dots[298].y),
                            DataPoint(dots[299].x, dots[299].y),
                            DataPoint(dots[300].x, dots[300].y),
                            DataPoint(dots[301].x, dots[301].y),
                            DataPoint(dots[302].x, dots[302].y),
                            DataPoint(dots[303].x, dots[303].y),
                            DataPoint(dots[304].x, dots[304].y),
                            DataPoint(dots[305].x, dots[305].y),
                            DataPoint(dots[306].x, dots[306].y),
                            DataPoint(dots[307].x, dots[307].y),
                            DataPoint(dots[308].x, dots[308].y),
                            DataPoint(dots[309].x, dots[309].y),
                            DataPoint(dots[310].x, dots[310].y),
                            DataPoint(dots[311].x, dots[311].y),
                            DataPoint(dots[312].x, dots[312].y),
                            DataPoint(dots[313].x, dots[313].y),
                            DataPoint(dots[314].x, dots[314].y),
                            DataPoint(dots[315].x, dots[315].y),
                            DataPoint(dots[316].x, dots[316].y),
                            DataPoint(dots[317].x, dots[317].y),
                            DataPoint(dots[318].x, dots[318].y),
                            DataPoint(dots[319].x, dots[319].y),
                            DataPoint(dots[320].x, dots[320].y),
                            DataPoint(dots[321].x, dots[321].y),
                            DataPoint(dots[322].x, dots[322].y),
                            DataPoint(dots[323].x, dots[323].y),
                            DataPoint(dots[324].x, dots[324].y),
                            DataPoint(dots[325].x, dots[325].y),
                            DataPoint(dots[326].x, dots[326].y),
                            DataPoint(dots[327].x, dots[327].y),
                            DataPoint(dots[328].x, dots[328].y),
                            DataPoint(dots[329].x, dots[329].y),
                            DataPoint(dots[330].x, dots[330].y),
                            DataPoint(dots[331].x, dots[331].y),
                            DataPoint(dots[332].x, dots[332].y),
                            DataPoint(dots[333].x, dots[333].y),
                            DataPoint(dots[334].x, dots[334].y),
                            DataPoint(dots[335].x, dots[335].y),
                            DataPoint(dots[336].x, dots[336].y),
                            DataPoint(dots[337].x, dots[337].y),
                            DataPoint(dots[338].x, dots[338].y),
                            DataPoint(dots[339].x, dots[339].y),
                            DataPoint(dots[340].x, dots[340].y),
                            DataPoint(dots[341].x, dots[341].y),
                            DataPoint(dots[342].x, dots[342].y),
                            DataPoint(dots[343].x, dots[343].y),
                            DataPoint(dots[344].x, dots[344].y),
                            DataPoint(dots[345].x, dots[345].y),
                            DataPoint(dots[346].x, dots[346].y),
                            DataPoint(dots[347].x, dots[347].y),
                            DataPoint(dots[348].x, dots[348].y),
                            DataPoint(dots[349].x, dots[349].y),
                            DataPoint(dots[350].x, dots[350].y),
                            DataPoint(dots[351].x, dots[351].y),
                            DataPoint(dots[352].x, dots[352].y),
                            DataPoint(dots[353].x, dots[353].y),
                            DataPoint(dots[354].x, dots[354].y),
                            DataPoint(dots[355].x, dots[355].y),
                            DataPoint(dots[356].x, dots[356].y),
                            DataPoint(dots[357].x, dots[357].y),
                            DataPoint(dots[358].x, dots[358].y),
                            DataPoint(dots[359].x, dots[359].y)
                        )
                    )

                seriesList.add(seriesT)
            }
        }


        with(engineGraph) {
            for (i in seriesList.iterator()) {
                this!!.addSeries(i)
                with(viewport) {
                    isXAxisBoundsManual = true
                    isScrollable = true
                    setMinX(0.0)
                    setMaxX(370.0)
                }
            }
        }

        Log.e("prepareTheDataForGraph", seriesList.toString())

    }

    private fun setListeners() {
        engineGraph!!.setOnClickListener{}
        btnGraphToDash!!.setOnClickListener(this)
    }

    private fun initGraphs() {
        val series =
            LineGraphSeries(
                arrayOf<DataPoint>(
                    DataPoint(0.0, -2.0),
                    DataPoint(1.0, 5.0),
                    DataPoint(2.0, 3.0),
                    DataPoint(3.0, 2.0),
                    DataPoint(4.0, 6.0)
                )
            )

        val series2 =
            LineGraphSeries(
                arrayOf<DataPoint>(
                    DataPoint(0.0, 3.0),
                    DataPoint(1.0, 3.0),
                    DataPoint(2.0, 6.0),
                    DataPoint(3.0, 2.0),
                    DataPoint(4.0, 5.0)
                )
            )

        with(engineGraph) {
            this!!.addSeries(series)
            this!!.addSeries(series2)
            with(viewport){
                isXAxisBoundsManual = true
                isScrollable = true
                setMinX(0.0)
                setMaxX(360.0)
            }
        }
    }

    private fun triggerEvent() {
//        viewModel.setStateEvent(DashboardStateEvent.GetCylinders())
        viewModel.setStateEvent(DashboardStateEvent.GetReport())
    }


    //TODO("understand how to present bunch of gauges on one graph yalla bye")


    private fun setPointer(v: View) {

        //graph
        engineGraph = v.findViewById(R.id.engine_graph) as GraphView





        btnGraphToDash = v.findViewById(R.id.btn_graph_to_dash)

    }

//    private fun setGraph(cylinder: Cylinder) {
//        graph2LastXValue++
//        torqueSeries!!.appendData(
//            DataPoint(graph2LastXValue, cylinder.Torque_engine_value.toDouble()),
//            true,
//            40
//        )
//
//        Toast.makeText(context,torque_engine.toString() + bmep.toString() + scavenging_pressure.toString(),Toast.LENGTH_SHORT).show()
//    }


    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.btn_graph_to_dash ->{

            }
      }
    }

}