package au.commbank.codingchallenge.screens.account.presentation.data.mappers

import au.commbank.codingchallenge.common.Logger
import au.commbank.codingchallenge.config.NetworkConfig
import au.commbank.codingchallenge.screens.account.Utils
import au.commbank.codingchallenge.screens.account.Utils.getBiWeeklySpendingProjection
import au.commbank.codingchallenge.screens.account.data.Account
import au.commbank.codingchallenge.screens.account.data.AccountDetails
import au.commbank.codingchallenge.screens.account.data.AtmLocation
import au.commbank.codingchallenge.screens.account.presentation.data.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.absoluteValue

@Singleton
class AccountDataMapper @Inject constructor(private val transactionMapper: TransactionMapper,
                                            private val logger: Logger) {

    companion object {
        private val TAG = AccountDataMapper::class.java.simpleName
    }

    fun mapAccountDetailToAccountUIData(accountDetails: AccountDetails): AccountUIData {
        return with(accountDetails) {
            val listItems = mutableListOf<ListItem>()
            listItems.add(mapAccountToAccountInfoItem(account))

            val transactionItems = transactionMapper.merge(transactions, pendingTransactions)
            val spendings = mutableListOf<Spending>()
            transactionItems.forEach { (date, transactionItems) ->
                listItems.add(mapDateToDateItem(date))
                listItems.addAll(transactionItems)

                spendings.add(Spending(date, getTotalSpending(transactionItems)))
            }

            val twoWeekSpendingProjection = getBiWeeklySpendingProjection(spendings)
            logger.debug(TAG, "Estimated bi weekly spending projection = $twoWeekSpendingProjection")
            AccountUIData(
                    listItems,
                    mapByAtmId(atmLocations),
                    twoWeekSpendingProjection
            )
        }
    }

    private fun mapDateToDateItem(date: String): DateItem =
            DateItem(
                    id = date,
                    date = date,
                    dateDiff = Utils.getTimeDiff(date, NetworkConfig.dateFormat)
            )

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

    private fun getTotalSpending(transactionItems: List<TransactionItem>): Float =
            transactionItems.filter {
                it.amount < 0
            }.sumByDouble {
                it.amount.toDouble()
            }.absoluteValue.toFloat()
}