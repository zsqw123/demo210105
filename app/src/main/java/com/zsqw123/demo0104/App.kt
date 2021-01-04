package com.zsqw123.demo0104

import android.app.Application

lateinit var app: Application

class App : Application() {
    init {
        app = this
    }
}