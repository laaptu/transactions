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
    vmStateProvider: VMStateProvider,
    private val logger: Logger
) :
    ViewModel() {

    companion object {
        private val TAG = AccountDetailViewModel::class.java.simpleName
    }

    private var currVMState: VMState = vmStateProvider.getInitialViewState()

    private val _uiAction: MutableLiveData<Event<UIAction>> = MutableLiveData()
    val uiAction: LiveData<Event<UIAction>> = _uiAction

    fun fetchAccountDetail() {
        //this is to be done to prevent multiple calls during screen rotation
        if (currVMState == Initialized) {
            currVMState = Empty
            refreshAccountDetails()
        } else {
            showProgress(false)
            if (currVMState is DataFetched)
                displayList((currVMState as DataFetched).accountUIData.listItems)
        }
    }

    fun refreshAccountDetails() {
        viewModelScope.launch {
            if (isUIShowingProgress())
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

    fun onAtmLocationClick(atmLocationId: String) {
        if (currVMState is DataFetched && !isUIShowingProgress()) {
            (currVMState as DataFetched).accountUIData.atmLocMap[atmLocationId]?.let {
                _uiAction.value = Event(Navigate(it.location))
            }
        }
    }

    private fun handleSuccess(accountDetails: AccountDetails) {
        val accountUIData = accountDataMapper mapToAccountUIData accountDetails
        currVMState = DataFetched(accountUIData)
        showProgress(false)
        displayList(accountUIData.listItems)
    }

    private fun notifyError(errorMsg: String, errorType: Int, errorMsgId: Int) {
        logger.error(TAG, "Error message = $errorMsg and type = $errorType")
        showProgress(false)
        _uiAction.value = Event(DisplayMsg(errorMsgId))
    }

    private fun showProgress(showProgress: Boolean) {
        _uiAction.value = Event(ShowProgress(showProgress))
    }

    private fun isUIShowingProgress(): Boolean = _uiAction.value?.peek().let {
        it is ShowProgress && it.show
    }

    private fun displayList(items: List<ListItem>) {
        _uiAction.value = Event(DisplayList(items))
    }


}