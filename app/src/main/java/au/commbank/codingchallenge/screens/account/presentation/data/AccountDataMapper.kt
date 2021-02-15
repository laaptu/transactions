package au.commbank.codingchallenge.screens.account.presentation.data

import au.commbank.codingchallenge.screens.account.domain.data.Account
import au.commbank.codingchallenge.screens.account.domain.data.AccountDetails
import au.commbank.codingchallenge.screens.account.domain.data.AtmLocation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountDataMapper @Inject constructor(private val transactionMapper: TransactionMapper) {

    fun mapAccountDetailToAccountUIData(accountDetails: AccountDetails): AccountUIData {
        return with(accountDetails) {
            val listItems = mutableListOf<ListItem>()
            listItems.add(mapAccountToAccountInfoItem(account))
            AccountUIData(
                    listItems,
                    mapByAtmId(atmLocations)
            )
        }
    }


    private fun mapAccountToAccountInfoItem(account: Account): AccountInfoItem =
            with(account) {
                AccountInfoItem(
                        id = accountNumber,
                        accountName = accountName,
                        accountNumber = accountNumber,
                        availableFunds = available,
                        accountBalance = balance
                )
            }

    private fun mapByAtmId(atmLocations: List<AtmLocation>): Map<String, AtmLocation> =
            atmLocations.map { atmLocation ->
                atmLocation.id to atmLocation
            }.toMap()


}