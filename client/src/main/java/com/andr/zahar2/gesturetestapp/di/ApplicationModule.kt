package com.andr.zahar2.gesturetestapp.di

import com.andr.zahar2.gesturetestapp.data.Network
import com.andr.zahar2.gesturetestapp.domain.GesturesDomain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
    }

    @Provides
    @Singleton
    fun provideNetwork(client: HttpClient): Network = Network(client)

    @Provides
    @Singleton
    fun provideGesturesDomain(network: Network): GesturesDomain = GesturesDomain(network)
}