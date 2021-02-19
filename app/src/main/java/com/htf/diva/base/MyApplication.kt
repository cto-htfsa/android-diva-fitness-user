package com.htf.diva.base

import android.app.Application
import android.content.Context



class MyApplication : Application() {
    /*private var sAnalytics: GoogleAnalytics? = null
    private var sTracker: Tracker? = null*/

    override fun onCreate() {
        super.onCreate()
        instance = this
        setAppContext(this)

    }


    companion object {
        private lateinit var instance: MyApplication
        private lateinit var mAppContext: Context

        fun getInstance(): MyApplication {
            return instance
        }

        fun getAppContext(): Context {
            return mAppContext
        }

        fun setAppContext(mAppContext: Context) {
            Companion.mAppContext = mAppContext
        }
    }
}