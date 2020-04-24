package com.redmadrobot.debug_panel.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.redmadrobot.debug_panel.data.storage.dao.DebugServersDao
import com.redmadrobot.debug_panel.data.storage.dao.DebugUserCredentialsDao
import com.redmadrobot.debug_panel.data.storage.entity.DebugServer
import com.redmadrobot.debug_panel.data.storage.entity.DebugUserCredentials
import com.redmadrobot.debug_panel.data.toggles.model.FeatureToggle
import com.redmadrobot.debug_panel.data.toggles.model.FeatureTogglesDao

@Database(
    entities = [
        DebugUserCredentials::class,
        DebugServer::class,
        FeatureToggle::class
    ],
    version = 1
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun getDebugUserCredentialsDao(): DebugUserCredentialsDao

    abstract fun getDebugServersDao(): DebugServersDao

    abstract fun getFeatureTogglesDao(): FeatureTogglesDao

    companion object {
        private const val DATABASE_NAME = "local_storage"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(AppDatabase::class) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
