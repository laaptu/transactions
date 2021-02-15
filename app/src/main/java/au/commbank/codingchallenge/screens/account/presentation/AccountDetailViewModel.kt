package au.commbank.codingchallenge.screens.account.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.commbank.codingchallenge.screens.account.data.AccountDetailRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountDetailViewModel @Inject constructor(private val accountDetailRepo: AccountDetailRepo) :
        ViewModel() {

    fun fetchAccountDetail() {
        viewModelScope.launch {
            val accountUIData = accountDetailRepo.getAccountDetails()
        }
    }
}