package com.e.occanotestsidep.ui.auth.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.e.occanosidetest.utils.StaticAddress
import com.e.occanosidetest.utils.TopSpacingItemDecoration
import com.e.occanotestsidep.R
import com.e.occanotestsidep.ui.models.Ship
import com.e.occanotestsidep.utils.Single
import kotlinx.android.synthetic.main.fragment_calibration_fragment_z.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class LoginFragment : Fragment(),LoginAdapter.Interaction, View.OnClickListener{

    var ip:String? = null
    var selectedShip = Ship()

    private var requestQueue: RequestQueue? = null
    var shipList =  ArrayList<Ship>()
    lateinit var loginAdapter:LoginAdapter

    var clickCounter:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAddress()
        setListeners(view)
        initRv()
        initList()

        this.loginAdapter.submitList(shipList)
    }

    private fun setListeners(view: View) {
        view.findViewById<ImageButton>(R.id.buttonLogin).setOnClickListener(this)
        view.findViewById<ImageButton>(R.id.btn_add_vessel).setOnClickListener(this)
        view.findViewById<CardView>(R.id.btn_background_add_vessel).setOnClickListener(this)

    }

    private fun initRv() {
        rv_login.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(10)
            addItemDecoration(topSpacingItemDecoration)
            loginAdapter = LoginAdapter(this@LoginFragment)
            adapter = loginAdapter
        }

    }

    private fun initList() {
        shipList.add(Ship(0, "ANAFI WARRIOR",	"MITSUI MAN B&W 6S60 MC-C","4071.5",6,"s","60","c",	"c","9370848",	"Tanker","2009","243.8","42","107593","60205",false))
        shipList.add(Ship(1, "GREEN WARRIOR",	"MITSUI MAN B&W 6S60 MC-C","4071.5",6,	"s",	"60"	,"c"	,"c"	,"9514169"	,"Tanker"	,"2011"	,"229"	,"42.04"	,"104626"	,"56326", false))
        shipList.add(Ship(2, "PATMOS WARRIOR","D.U. SULZER 6RTA58T","3830",6,"4.17","58","","","9337418",	"Tanker",	"2007","239","42.03","105572","56172",false))
        shipList.add(Ship(3, "SYROS WARRIOR","MITSUI MAN B&W 6S60 MC-C","4071.5",6,"s",	"60",	"c"	,"c"	,"9370850"	,"Tanker"	,"2009"	,"243.8",	"42","107687",	"60205",false))
        shipList.add(Ship(4, "DILIGENT WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9750050",	"Tanker",	"2016",	"274.22","48",  "149992",	"81287", false))
        shipList.add(Ship(5, "FAITHFUL WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9750062",	"Tanker",	"2016",	"274.22","48",  "149992",	"81287", false))
        shipList.add(Ship(6, "PRUDENT WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9753545",	"Tanker",	"2017",	"274",	   "48","149992",	"81287", false))
        shipList.add(Ship(7, "RELIABLE WARRIOR",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	6,	"g",	"70",	"e",	"c",	"9753557",	"Tanker",	"2017",	"274.22","48",  "149992",	"81287", false))
        //FAKE ABA SHEL THE FAKE
        shipList.add(Ship(8, "TARAGGONA ZIM",	"HYUNDAI MAN B&W 6G70 ME-C9.5",	"7518.3",	8,	"g",	"70",	"e",	"c",	"9753567",	"Tanker",	"2017",	"274.22","48",  "149992",	"81287", false))
    }

    override fun onItemSelected(position: Int, item: Ship) {

        for (stamShip: Ship in shipList){
            stamShip.isSelected = false
        }
        item.isSelected = true
        selectedShip = item

        loginAdapter.notifyDataSetChanged()
    }


    private fun submit(data: String) {
        val URL = "http://"+ip+":5000/register"
        requestQueue = Volley.newRequestQueue(
            Objects.requireNonNull(requireContext())
        )
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            URL,
            Response.Listener { response ->
                try {
                    val objres = JSONObject(response)
                } catch (e: JSONException) {
                }
            },
            Response.ErrorListener { error ->
                if (error != null && error.message != null) {
                } else if (error is TimeoutError || error is NoConnectionError) {
                }
            }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return try {
                    data.toByteArray(charset("utf-8"))
                } catch (uee: UnsupportedEncodingException) { //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    uee.message!!.toByteArray()
                }
            }
        }
        requestQueue!!.add(stringRequest)
    }

    override fun onClick(v: View?) {

//        TODO("save in share preferences how much cylinders are, and affect on count of gauges in ui")

        when(v!!.id){
            R.id.buttonLogin -> {
              try {
                  val data =

                  "{" +
                          "\"msgtype\":" + "\"login\"" + ","  +
                          "\"vessel\":" +  "\""+ selectedShip.vessel.toString() +"\""+ "," +
                          "\"m_e\":" +"\""+ selectedShip.m_e.toString() +"\""+ ","  +
                          "\"displacement_engine\":" + selectedShip.displacement_engine!!.toFloat() + "," +
                          "\"Number_of_cylinders\":" + selectedShip.Number_of_cylinders!!.toInt()  + ","  +
                          "\"Stroke_bore_ratio\":" +"\""+ selectedShip.Stroke_bore_ratio.toString() +"\""+ ","  +
                          "\"Diameter_of_piston_in_cm\":" + selectedShip.Diameter_of_piston_in_cm!!.toFloat() + "," +
                          "\"Concept\":" +"\""+ selectedShip.Concept!!.toString() +"\""+ ","  +
                          "\"Design\":" +"\""+ selectedShip.Design.toString() +"\""+ ","  +
                          "\"IMO\":" +"\""+ selectedShip.IMO.toString() +"\""+ ","  +
                          "\"AIS_Vessel_Type\":" +"\""+ selectedShip.AIS_Vessel_Type.toString() +"\""+ ","  +
                          "\"Year_Built\":" + selectedShip.Year_Built!!.toInt() + ","  +
                          "\"Length_Overall\":" + selectedShip.Length_Overall.toString().toFloat() + ","  +
                          "\"Gross_Tonnage\":" + selectedShip.Gross_Tonnage.toString().toFloat() + ","  +
                          "\"Breadth_Extreme\":" +"\""+ selectedShip.Breadth_Extreme.toString() +"\""+ ","  +
                          "\"Deadweight\":" + selectedShip.Deadweight.toString().toFloat() + ","  +
                          "\"IMO\":" + selectedShip.Gross_Tonnage.toString().toFloat() +","  +
                          "\"posix\":" + System.currentTimeMillis()/1000 +
                          "}"

                  Log.d("msg login",data)
                  submit(data)
                  saveShipProperties()
              }  catch (e:Error){
                  e.printStackTrace()
              }

                if (findNavController().currentDestination?.id == R.id.loginFragment) {
                    findNavController().navigate(R.id.action_loginFragment_to_loadingFragment)}
//                Navigation.findNavController(v).navigate(R.id.action_loadFragment_to_dashboardMainFragment)
            }

            R.id.btn_background_add_vessel -> {

                login_title.text = "Add Vessel"
                btn_background_add_vessel.visibility = View.GONE
                buttonLogin.visibility = View.GONE

                    //rv gone
                    rv_login.visibility = View.GONE
                    //edit texts visible

                    sv_et_forms.visibility = View.VISIBLE

                add_breadth_extreme.error = "Enter valid value, ex. 48"
                add_dead_weight.error = "Enter valid value, ex. 149992 (t)"
                add_diameter.error = "Enter valid value, ex. 70 (cm)"
                add_displacement.error = "Enter valid value, ex. 7518.3"
                add_gross_tonnage.error = "Enter valid value, ex. 81287"
                add_imo.error = "Enter valid value, ex. 9753567"
                add_length.error = "Enter valid value, ex. 274.22"
                add_name_of_ship.error = "Enter valid value, ex. ZIM"
                add_num_of_cyl.error = "Enter valid value, ex. 6"
                add_year_built.error = "Enter valid value, ex. 2017"

//                btn_add_vessel.isClickable = !((add_breadth_extreme.text.isEmpty() )
//                        || (add_dead_weight.text.isEmpty())
//                        || (add_diameter.text.isEmpty())
//                        || (add_displacement.text.isEmpty())
//                        || (add_gross_tonnage.text.isEmpty())
//                        || (add_imo.text.isEmpty())
//                        || (add_length.text.isEmpty())
//                        || (add_name_of_ship.text.isEmpty())
//                        || (add_num_of_cyl.text.isEmpty())
//                        || (add_year_built.text.isEmpty()))
//
                btn_add_vessel.visibility = View.VISIBLE
                }

                R.id.btn_add_vessel -> {

                    if (add_breadth_extreme.text.isNullOrEmpty() ) add_breadth_extreme.setText("48")
                    if (add_dead_weight.text.isNullOrEmpty())  add_dead_weight.setText("149992")
                    if (add_diameter.text.isNullOrEmpty())add_diameter.setText("70")
                    if (add_displacement.text.isNullOrEmpty())add_displacement.setText("7518.3")
                    if (add_gross_tonnage.text.isNullOrEmpty())add_gross_tonnage.setText("81287")
                    if (add_imo.text.isNullOrEmpty())add_imo.setText("9753567")
                    if (add_length.text.isNullOrEmpty())add_length.setText("274.22")
                    if (add_name_of_ship.text.isNullOrEmpty())add_name_of_ship.setText("name")
                    if (add_num_of_cyl.text.isNullOrEmpty())add_num_of_cyl.setText("6")
                    if (add_year_built.text.isNullOrEmpty())add_year_built.setText("2017")



                    login_title.text = "ALL Vessel"
                    var ship = Ship(9,add_name_of_ship.text.toString(),"0",add_displacement.text.toString(),
                        add_num_of_cyl.text.toString().toInt(),"",add_diameter.text.toString(),"",
                        "",add_imo.text.toString(),"",add_year_built.text.toString(),add_length.text.toString(),
                        add_breadth_extreme.text.toString(),add_dead_weight.text.toString(),add_gross_tonnage.text.toString(),true)
//                    selectedShip = ship
                    shipList.add(ship)
                    loginAdapter.notifyDataSetChanged()

                    sv_et_forms.visibility = View.GONE
//                    //edit texts visible
//                    add_breadth_extreme.visibility = View.GONE
//                    add_dead_weight.visibility = View.GONE
//                    add_diameter.visibility = View.GONE
//                    add_displacement.visibility = View.GONE
//                    add_gross_tonnage.visibility = View.GONE
//                    add_imo.visibility = View.GONE
//                    add_length.visibility = View.GONE
//                    add_name_of_ship.visibility = View.GONE
//                    add_num_of_cyl.visibility = View.GONE
//                    add_year_built.visibility = View.GONE

                    rv_login.visibility = View.VISIBLE

                    btn_background_add_vessel.visibility = View.VISIBLE
                    buttonLogin.visibility = View.VISIBLE
                    btn_add_vessel.visibility = View.GONE

                }
            }

        }


    fun getAddress(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(R.string.default_ip)
        ip = sharedPref.getString("ip", defaultValue)
    }

    fun saveShipProperties(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("nameOfCurrentShip", selectedShip.vessel)
            putString("nameOfCurrentEngine", selectedShip.m_e)
            commit()
        }
    }
}