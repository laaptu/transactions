package au.commbank.codingchallenge.common.ui

import androidx.lifecycle.ViewModel
import au.commbank.codingchallenge.common.di.modules.viewmodel.ViewModelProviderFactory
import javax.inject.Inject

abstract class ViewModelActivity<T : ViewModel> : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    abstract val viewModel: T
}