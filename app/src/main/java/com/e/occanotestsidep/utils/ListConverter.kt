package com.e.occanotestsidep.utils

import androidx.room.TypeConverter
import com.e.occanotestsidep.ui.models.Gauge
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class ListConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Gauge?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType =
            object : TypeToken<List<Gauge?>?>() {}.type
        return gson.fromJson<List<Gauge?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Gauge?>?): String? {
        return gson.toJson(someObjects)
    }
}
