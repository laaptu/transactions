package au.commbank.codingchallenge.screens.account.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.Logger
import au.commbank.codingchallenge.common.data.Error
import au.commbank.codingchallenge.common.data.ErrorType.InvalidResult
import au.commbank.codingchallenge.common.data.Response
import au.commbank.codingchallenge.common.data.Success
import au.commbank.codingchallenge.common.ui.events.Event
import au.commbank.codingchallenge.screens.account.data.AccountDetailRepo
import au.commbank.codingchallenge.screens.account.data.AccountDetails
import au.commbank.codingchallenge.screens.account.ui.data.*
import au.commbank.codingchallenge.screens.account.ui.data.mappers.AccountDataMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountDetailViewModel @Inject constructor(
    private val accountDetailRepo: AccountDetailRepo,
    private val accountDataMapper: AccountDataMapper,
    viewStateProvider: ViewStateProvider,
    private val logger: Logger
) :
    ViewModel() {

    companion object {
        private val TAG = AccountDetailViewModel::class.java.simpleName
    }

    private var currViewState: ViewState = viewStateProvider.getInitialViewState()
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(currViewState)
    val viewState: LiveData<ViewState> = _viewState

    private val _uiAction: MutableLiveData<Event<UIAction>> = MutableLiveData()
    val uiAction: LiveData<Event<UIAction>> = _uiAction

    fun fetchAccountDetail() {
        //this is to be done to prevent multiple calls during screen rotation
        if (_viewState.value != Initialized)
            return
        refreshAccountDetails()
    }

    fun refreshAccountDetails() {
        viewModelScope.launch {
            if (_viewState.value is InProgress)
                return@launch
            showProgress(true)
            val response: Response<AccountDetails> = accountDetailRepo.getAccountDetails()
            when (response) {
                is Success -> {
                    if (response.data != null) {
                        handleSuccess(response.data)
                    } else {
                        notifyError(
                            "Response contains invalid data",
                            InvalidResult,
                            R.string.error_fetching_account
                        )
                    }
                }
                is Error -> notifyError(
                    response.message,
                    response.errorType,
                    R.string.error_fetching_account
                )
            }
        }
    }

    private fun handleSuccess(accountDetails: AccountDetails) {
        val accountUIData = accountDataMapper mapToAccountUIData accountDetails
        currViewState = DataFetched(accountUIData)
        showProgress(false)
        _viewState.value = currViewState
    }

    private fun notifyError(errorMsg: String, errorType: Int, errorMsgId: Int) {
        logger.error(TAG, "Error message = $errorMsg and type = $errorType")
        showProgress(false)
        _uiAction.value = Event(DisplayMsg(errorMsgId))
        _viewState.value = currViewState
    }

    private fun showProgress(showProgress: Boolean) {
        _viewState.value = InProgress(showProgress)
    }
}