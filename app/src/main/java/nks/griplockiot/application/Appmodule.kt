package nks.griplockiot.application

import androidx.room.Room
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.repository.CourseRepository
import nks.griplockiot.repository.PlayerRepository
import nks.griplockiot.viewmodel.CourseListViewModel
import nks.griplockiot.viewmodel.PlayerListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

object Appmodule {

    val viewModelModule = module(override = true) {
        viewModel { CourseListViewModel(get()) }
        viewModel { PlayerListViewModel(get()) }
    }

    val repositoryModule = module(override = true) {
        single { CourseRepository(get()) }
        single { PlayerRepository(get()) }
    }

    val databaseModule = module(override = true) {
        single {
            Room.databaseBuilder(
                    get(),
                    AppDatabase::class.java,
                    "course")
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    val daoModule = module(override = true) {
        single { get<AppDatabase>().getCourseDAO() }
        single { get<AppDatabase>().getPlayerDAO() }
    }
}

