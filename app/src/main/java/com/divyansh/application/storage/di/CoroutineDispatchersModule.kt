package com.divyansh.application.storage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object CoroutineDispatchersModule {

    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}