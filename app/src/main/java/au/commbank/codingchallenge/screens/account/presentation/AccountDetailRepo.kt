package au.commbank.codingchallenge.screens.account.presentation

import au.commbank.codingchallenge.screens.account.domain.AccountApiService
import au.commbank.codingchallenge.screens.account.domain.data.AccountDetails
import javax.inject.Inject

class AccountDetailRepo @Inject constructor(private val accountApiService: AccountApiService) {
    suspend fun getAccountDetails(): AccountDetails = accountApiService.getAccountDetails()
}