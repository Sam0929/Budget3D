package com.example.budget3d

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Budget3DApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Aqui você pode inicializar ferramentas de log como Timber no futuro
    }
}