package com.e.occanotestsidep.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.e.occanotestsidep.ui.models.Cylinder

import com.e.occanotestsidep.ui.models.Status
import retrofit2.http.GET

@Dao
interface CylindersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCylinder(cylinder: Cylinder): Long

    @Query("SELECT * FROM cylinders")
    fun cylinders():LiveData<List<Cylinder>>

    @Delete
    suspend fun deleteCyl(cylinder: Cylinder)

//    @Query("""
//        UPDATE cylinders SET cylinder =  :cylinder
//        WHERE cylinder_num= :cylinderNum""")
//    suspend fun updateStatusAcknowledge(cylinder: Cylinder, cylinderNum:Int)
//

}