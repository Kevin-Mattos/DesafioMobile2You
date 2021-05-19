package com.example.desafiomobile2you

import android.app.Application
import com.example.desafiomobile2you.repository.MovieRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {



    override fun onCreate() {
        super.onCreate()
        startKoin{
//            androidLogger(Level.NONE)
            androidContext(applicationContext)
            modules(appModule)
        }
    }

}


val appModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
    }

    factory { MovieRepository(get()) }
}