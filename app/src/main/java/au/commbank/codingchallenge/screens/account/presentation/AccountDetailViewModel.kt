package au.commbank.codingchallenge.screens.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountDetailViewModel @Inject constructor(private val accountDetailRepo: AccountDetailRepo) :
        ViewModel() {

    fun fetchAccountDetail() {
        viewModelScope.launch {
            val accountDetails = accountDetailRepo.getAccountDetails()
            println("Account = ${accountDetails.account}")
            accountDetails.transactions.forEach {
                println("Transaciont = $it")
            }
            accountDetails.pendingTransactions.forEach {
                println("Pending transaction = $it")
            }
            accountDetails.atmLocations.forEach {
                println("Atmlocation = $it")
            }
        }
    }
}