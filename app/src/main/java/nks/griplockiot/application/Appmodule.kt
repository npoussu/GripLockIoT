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

    val myModule = module(override = true) {
        single { CourseRepository(get()) }
        viewModel { CourseListViewModel(get()) }
        single {
            Room.databaseBuilder(
                    get(),
                    AppDatabase::class.java,
                    "course")
                    .fallbackToDestructiveMigration()
                    .build()
        }
        single { get<AppDatabase>().getCourseDAO() }

        single { PlayerRepository(get()) }
        viewModel { PlayerListViewModel(get()) }
        single { get<AppDatabase>().getPlayerDAO() }
    }
}

