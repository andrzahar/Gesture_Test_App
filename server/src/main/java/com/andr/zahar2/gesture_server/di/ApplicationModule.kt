package com.andr.zahar2.gesture_server.di

import android.content.Context
import androidx.room.Room
import com.andr.zahar2.api.model.GestureEvent
import com.andr.zahar2.gesture_server.data.db.LogDao
import com.andr.zahar2.gesture_server.data.db.LogDatabase
import com.andr.zahar2.gesture_server.domain.GestureDomain
import com.andr.zahar2.gesture_server.data.server.ClientsConnectionsManager
import com.andr.zahar2.gesture_server.data.server.ServerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideClientsConnectionsManager(): ClientsConnectionsManager = ClientsConnectionsManager()

    @Provides
    @Singleton
    fun provideServerManager(
        @ApplicationContext appContext: Context,
        clientsConnectionsManager: ClientsConnectionsManager
    ): ServerManager = ServerManager(appContext, clientsConnectionsManager)

    @Provides
    @Singleton
    fun provideLogDatabase(@ApplicationContext appContext: Context): LogDatabase =
        Room.databaseBuilder(
            appContext,
            LogDatabase::class.java,
            "log"
        ).build()

    @Provides
    fun provideLogDao(database: LogDatabase): LogDao = database.logDao()

    @Provides
    @Singleton
    fun provideGestureDomain(
        serverManager: ServerManager,
        clientsConnectionsManager: ClientsConnectionsManager,
        logDao: LogDao
    ): GestureDomain = GestureDomain(serverManager, clientsConnectionsManager, logDao)
}