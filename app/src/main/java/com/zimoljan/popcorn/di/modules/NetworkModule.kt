package com.zimoljan.popcorn.di.modules

import com.squareup.moshi.Moshi
import com.zimoljan.popcorn.data.remote.ApiInterface
import com.zimoljan.popcorn.utils.calladapter.flow.FlowResourceCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRavenApi(moshi: Moshi): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/Zimi14/d01e243a50264504df21f9e041b71257/raw/df2aac3ddabe120af2030a7c02c1f2c9f394ba3d/")
            .addCallAdapterFactory(FlowResourceCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiInterface::class.java)
    }

}