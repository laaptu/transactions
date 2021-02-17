package au.commbank.codingchallenge.screens.account.ui.data.mappers

import au.commbank.codingchallenge.common.Logger
import au.commbank.codingchallenge.screens.account.data.Transaction
import au.commbank.codingchallenge.screens.account.ui.data.TransactionItem
import au.commbank.codingchallenge.screens.utils.getTimeInMillisFromDate
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionMapper @Inject constructor(private val logger: Logger) : Comparator<String> {

    companion object {
        private val TAG = TransactionMapper::class.java.simpleName
        private const val INVALID_TIME = -1L
    }

    fun merge(
        transactions: List<Transaction>,
        pendingTransactions: List<Transaction>
    ): TreeMap<String, MutableList<TransactionItem>> {
        val map: TreeMap<String, MutableList<TransactionItem>> = TreeMap(this)

        addTransactionsToMap(transactions, map)
        addTransactionsToMap(pendingTransactions, map, true)

        return map
    }

    private fun addTransactionsToMap(
        transactions: List<Transaction>,
        map: TreeMap<String, MutableList<TransactionItem>>,
        isPendingTransaction: Boolean = false
    ) {
        transactions.forEach { transaction ->
            val transactionItem = mapTransactionToTransactionItem(transaction, isPendingTransaction)
            val key = transactionItem.date
            if (map[key] == null)
                map[key] = mutableListOf()
            map[key]?.add(transactionItem)
        }
    }

    private fun mapTransactionToTransactionItem(
        transaction: Transaction,
        isPending: Boolean = false
    ): TransactionItem =
        with(transaction) {
            TransactionItem(
                id = id,
                date = effectiveDate,
                description = description,
                amount = amount,
                isPending = isPending,
                atmLocationId = atmId
            )
        }

    override fun compare(date1: String, date2: String): Int {
        val timeInMillis1 = getTimeInMillisFromDate(date1, INVALID_TIME)
        val timeInMillis2 = getTimeInMillisFromDate(date2, INVALID_TIME)
        if (timeInMillis1 == INVALID_TIME || timeInMillis2 == INVALID_TIME) {
            logger.error(
                TAG,
                """
                There is issue with date format for either $date1 or $date2,
                will create issues in ordering
                """.trimIndent()
            )
            return date2.compareTo(date1)
        }
        return timeInMillis2.compareTo(timeInMillis1)
    }
}