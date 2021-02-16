package au.commbank.codingchallenge.screens.account.ui.data

import au.commbank.codingchallenge.screens.utils.matches

sealed class ListItem(val id: String) {
    override fun equals(other: Any?): Boolean {
        if (other is ListItem)
            other.id == id
        return false
    }

    open fun areContentsSame(other: ListItem): Boolean = equals(other)

}

class AccountInfoItem(
        id: String,
        val accountName: String,
        val accountNumber: String,
        val availableFunds: Float,
        val accountBalance: Float
) : ListItem(id) {
    override fun areContentsSame(other: ListItem): Boolean {
        return if (other is AccountInfoItem)
            availableFunds matches other.availableFunds &&
                    accountBalance matches other.accountBalance
        else
            super.areContentsSame(other)
    }
}

class DateItem(
        id: String,
        val displayDate: String,
        val dateDiff: DateDiff
) : ListItem(id)

class TransactionItem(
        id: String,
        val date: String,
        val description: String,
        val amount: Float,
        val isPending: Boolean = false,
        val atmLocationId: String? = null
) : ListItem(id) {
    override fun areContentsSame(other: ListItem): Boolean {
        return if (other is TransactionItem)
            isPending == other.isPending
        else
            super.areContentsSame(other)
    }
}


sealed class DateDiff(val diff: Long) {
    class Day(days: Long) : DateDiff(days)
    class Month(months: Long) : DateDiff(months)
    class Year(years: Long) : DateDiff(years)
    object Invalid : DateDiff(-1)
}

sealed class DisplayAmount(val amount: Float) {
    class Thousands(amount: Float) : DisplayAmount(amount)
    class Millions(amount: Float) : DisplayAmount(amount)
    class Billions(amount: Float) : DisplayAmount(amount)
    class Trillions(amount: Float) : DisplayAmount(amount)
}