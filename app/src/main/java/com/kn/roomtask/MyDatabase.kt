package com.kn.roomtask

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kn.roomtask.dao.PersonDao
import com.kn.roomtask.entity.Company
import com.kn.roomtask.entity.Person

@Database(entities = [Person::class, Company::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao?

    companion object {
        private val LOG_TAG = MyDatabase::class.java.simpleName
        private val LOCK = Any()
        private const val DATABASE_NAME = "roomDatabase.db"
        private var sInstance: MyDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): MyDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Log.d(LOG_TAG, "Creating new database instance")
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            Log.d(LOG_TAG, "Getting the database instance")
            return sInstance
        }
    }
}