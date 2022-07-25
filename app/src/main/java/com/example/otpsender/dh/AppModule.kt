package com.example.otpsender.dh

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.otpsender.app.App
import com.example.otpsender.constant.Constant
import com.example.otpsender.data.database.MessageDatabase
import com.example.otpsender.retrofit.UserApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext applicationContext: Context): App {
        return applicationContext as App
    }

    @Provides
    @Singleton
    fun provideSavedMessageDb(app: Application): MessageDatabase {
        return Room.databaseBuilder(app, MessageDatabase::class.java, "messages")
            .build()
    }


    @Provides
    @Singleton
    fun provideUserApi(): UserApi =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(UserApi::class.java)

}