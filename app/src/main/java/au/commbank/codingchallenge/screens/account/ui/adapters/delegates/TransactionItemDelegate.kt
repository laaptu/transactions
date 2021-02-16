package au.commbank.codingchallenge.screens.account.ui.adapters.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import au.commbank.codingchallenge.common.ui.listadapters.BindingDelegate
import au.commbank.codingchallenge.common.ui.listadapters.BindingViewHolder
import au.commbank.codingchallenge.databinding.TransactionItemBinding
import au.commbank.codingchallenge.screens.account.ui.data.ListItem
import au.commbank.codingchallenge.screens.account.ui.data.TransactionItem


class TransactionItemDelegate :
    BindingDelegate<ListItem, TransactionItem, TransactionItemBinding, TransactionItemDelegate.TransactionItemViewHolder>() {

    override fun isForViewType(item: ListItem): Boolean = item is TransactionItem

    override fun createViewHolder(parent: ViewGroup): TransactionItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TransactionItemBinding.inflate(layoutInflater, parent, false)
        return TransactionItemViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(item: TransactionItem, holder: TransactionItemViewHolder) {
        holder.bind(item)
    }

    class TransactionItemViewHolder(binding: TransactionItemBinding) :
        BindingViewHolder<TransactionItem, TransactionItemBinding>(binding) {
        override fun bind(item: TransactionItem) {
            binding.transactionItem = item
            binding.executePendingBindings()
        }
    }
}