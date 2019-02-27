package nks.griplockiot.application

import android.app.Application
import nks.griplockiot.application.Appmodule.daoModule
import nks.griplockiot.application.Appmodule.databaseModule
import nks.griplockiot.application.Appmodule.repositoryModule
import nks.griplockiot.application.Appmodule.viewModelModule
import org.koin.android.ext.android.startKoin

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(viewModelModule,
                daoModule, databaseModule, repositoryModule))
    }
}