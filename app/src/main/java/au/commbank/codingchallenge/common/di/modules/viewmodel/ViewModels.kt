package au.commbank.codingchallenge.common.di.modules.viewmodel

import androidx.lifecycle.ViewModel
import au.commbank.codingchallenge.common.di.modules.viewmodel.ViewModelKey
import au.commbank.codingchallenge.screens.account.AccountDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModels {
    @Binds
    @IntoMap
    @ViewModelKey(AccountDetailViewModel::class)
    abstract fun bindAccountDetailViewModel(accountDetailViewModel: AccountDetailViewModel): ViewModel

}