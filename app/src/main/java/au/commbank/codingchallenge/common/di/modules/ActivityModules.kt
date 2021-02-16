package au.commbank.codingchallenge.common.di.modules

import au.commbank.codingchallenge.screens.account.ui.AccountDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModules {
    @ContributesAndroidInjector
    abstract fun bindsAccountDetailActivity(): AccountDetailActivity
}