package au.commbank.codingchallenge.screens.account.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import au.commbank.codingchallenge.common.ui.listadapters.DelegatesRVAdapter
import au.commbank.codingchallenge.screens.account.ui.adapters.delegates.AccountInfoItemDelegate
import au.commbank.codingchallenge.screens.account.ui.adapters.delegates.DateItemDelegate
import au.commbank.codingchallenge.screens.account.ui.adapters.delegates.TransactionItemDelegate
import au.commbank.codingchallenge.screens.account.ui.data.ListItem

class AccountAdapter : DelegatesRVAdapter<ListItem>(
    listOf(
        AccountInfoItemDelegate(),
        DateItemDelegate(),
        TransactionItemDelegate()
    ), DiffCallback
)

object DiffCallback : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
        oldItem matchesContentWith newItem
}