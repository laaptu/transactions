package au.commbank.codingchallenge.screens.account.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.Logger
import au.commbank.codingchallenge.common.data.Error
import au.commbank.codingchallenge.common.data.ErrorType.InvalidResult
import au.commbank.codingchallenge.common.data.Response
import au.commbank.codingchallenge.common.data.Success
import au.commbank.codingchallenge.screens.account.data.AccountDetailRepo
import au.commbank.codingchallenge.screens.account.data.AccountDetails
import au.commbank.codingchallenge.screens.account.ui.data.mappers.AccountDataMapper
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountDetailViewModel @Inject constructor(private val accountDetailRepo: AccountDetailRepo,
                                                 private val accountDataMapper: AccountDataMapper,
                                                 private val logger: Logger) :
        ViewModel() {

    companion object {
        private val TAG = AccountDetailViewModel::class.java.simpleName
    }

    fun fetchAccountDetail() {
        viewModelScope.launch {
            val response: Response<AccountDetails> = accountDetailRepo.getAccountDetails()
            when (response) {
                is Success -> {
                    if (response.data != null) {
                        handleSuccess(response.data)
                    } else {
                        notifyError("Response contains invalid data",
                                InvalidResult,
                                R.string.error_fetching_transactions)
                    }
                }
                is Error -> notifyError(response.message,
                        response.errorType,
                        R.string.error_fetching_transactions)

            }
        }
    }

    private fun handleSuccess(accountDetails: AccountDetails) {
        val accountUIData = accountDataMapper mapToAccountUIData accountDetails
    }

    private fun notifyError(errorMsg: String, errorType: Int, errorMsgId: Int) {
        logger.error(TAG, "Error message = $errorMsg and type = $errorType")
    }
}