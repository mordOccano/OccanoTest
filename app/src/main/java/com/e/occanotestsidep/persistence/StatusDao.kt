package com.e.occanotestsidep.persistence

import androidx.lifecycle.LiveData
import androidx.room.*

import com.e.occanotestsidep.ui.models.Status
import retrofit2.http.GET

@Dao
interface StatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStatus(status: Status): Long

    @Query("SELECT * FROM statuses")
    fun getAllStatuses():List<Status>

    @Delete
    fun deleteStatus(status: Status)

    @Query("""
        UPDATE statuses SET kindOfAcknowledge= :kindOfAcknowledge
        WHERE statusId= :statusId""")
    fun updateStatusAcknowledge(kindOfAcknowledge: Boolean, statusId: String)

}