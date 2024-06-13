package com.andr.zahar2.gesture_server.di

import android.content.Context
import com.andr.zahar2.api.model.GestureEvent
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
    fun provideGestureDomain(
        serverManager: ServerManager,
        clientsConnectionsManager: ClientsConnectionsManager
    ): GestureDomain = GestureDomain(serverManager, clientsConnectionsManager)
}