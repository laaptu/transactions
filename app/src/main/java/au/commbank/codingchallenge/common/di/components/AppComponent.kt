package au.commbank.codingchallenge.common.di.components

import au.commbank.codingchallenge.MainApplication
import au.commbank.codingchallenge.common.di.modules.ActivityModules
import au.commbank.codingchallenge.common.di.modules.ApiModule
import au.commbank.codingchallenge.common.di.modules.NetworkModule
import au.commbank.codingchallenge.common.di.modules.viewmodel.ViewModels
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
        modules = [AndroidInjectionModule::class, ActivityModules::class,
            ViewModels::class, ApiModule::class,
            NetworkModule::class]
)
@Singleton
interface AppComponent {
    fun inject(mainApplication: MainApplication)
}