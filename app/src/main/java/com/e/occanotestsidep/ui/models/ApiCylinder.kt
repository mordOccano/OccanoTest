package com.e.occanotestsidep.ui.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//that class is for the current integration 13.05.20 for dashfragment for each cylinder

@Parcelize
@Entity(tableName = "api_cylinder")
data class ApiCylinder(
    @PrimaryKey
    @ColumnInfo(name = "cylinder_num")
    var numOfCylInEngine: Int = 0,

    @ColumnInfo(name = "cylinder_details")
    var dashboard: Dashboard? = null
) : Parcelable {

}