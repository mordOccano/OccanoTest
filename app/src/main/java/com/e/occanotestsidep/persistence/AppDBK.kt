package com.e.occanotestsidep.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.e.occanotestsidep.ui.models.Cylinder
import com.e.occanotestsidep.ui.models.GaugeForCalibration
import com.e.occanotestsidep.ui.models.Status
import com.e.occanotestsidep.utils.ListConverter

@Database(entities = [Cylinder::class, Status::class,GaugeForCalibration::class], version = 11)
@TypeConverters(ListConverter::class)
abstract class AppDBK : RoomDatabase(){
//    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    }
//
//    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL(
//                "ALTER TABLE users "
//                        + " ADD COLUMN last_update INTEGER"
//            )
//        }
//    }

    companion object {
        @Volatile private var instance: AppDBK? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDBK::class.java, "AppDBK.db")
            .build()


//    val DATABASE_NAME: String = "app_db"


//    open fun getInstance(context: Context): AppDBK? {
//        if (instance == null) {
//            instance = Room.databaseBuilder(
//                context.applicationContext,
//                AppDBK::class.java,
//                DATABASE_NAME
//            )
////                .addMigrations(AppDBK.MIGRATION_1_2, AppDatabase.MIGRATION_2_3)
//                .fallbackToDestructiveMigration()
//                .build()
//        }
//        return instance
//    }
}
    abstract fun getCylDao(): CylindersDao?
    abstract fun getStatusDao(): StatusDao?
}