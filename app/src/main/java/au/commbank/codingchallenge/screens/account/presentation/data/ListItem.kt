package au.commbank.codingchallenge.screens.account.presentation.data

sealed class ListItem(val id: String)
class AccountInfoItem(
        id: String,
        val accountName: String,
        val accountNumber: String,
        val availableFunds: Float,
        val accountBalance: Float
) : ListItem(id)

class DateItem(
        id: String,
        val date: String,
        val dateDiff: String
) : ListItem(id)

class TransactionItem(
        id: String,
        val date: String,
        val description: String,
        val amount: Float,
        val isPending: Boolean = false,
        val atmLocationId: String? = null
) : ListItem(id)