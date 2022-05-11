package com.example.mercariandroidtemplate.imageloader

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GlideModule {

    @Provides
    fun provideGlideImageLoader(context: Application) : ImageLoader =
        GlideImageLoader(context)

}