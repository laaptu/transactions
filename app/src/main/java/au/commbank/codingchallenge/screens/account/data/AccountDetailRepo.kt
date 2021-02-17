package au.commbank.codingchallenge.screens.account.data

import au.commbank.codingchallenge.common.Logger
import au.commbank.codingchallenge.common.data.Error
import au.commbank.codingchallenge.common.data.ErrorType
import au.commbank.codingchallenge.common.data.Response
import au.commbank.codingchallenge.common.data.Success
import javax.inject.Inject

class AccountDetailRepo @Inject constructor(
    private val accountApiService: AccountApiService,
    private val logger: Logger
) {
    companion object {
        private val TAG = AccountDetailRepo::class.java.simpleName
    }

    suspend fun getAccountDetails(): Response<AccountDetails> {
        return try {
            Success(accountApiService.getAccountDetails())
        } catch (exception: Exception) {
            val errorMsg =
                "Error fetching account details due to: $exception & \n${exception.message}"
            logger.error(TAG, errorMsg)
            Error(errorMsg, ErrorType.General)
        }
    }
}