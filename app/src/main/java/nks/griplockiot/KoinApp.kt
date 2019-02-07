package nks.griplockiot

import android.app.Application
import nks.griplockiot.Appmodule.myModule
import org.koin.android.ext.android.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(myModule))
    }
}