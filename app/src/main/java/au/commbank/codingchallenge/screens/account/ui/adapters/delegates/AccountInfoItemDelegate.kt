package au.commbank.codingchallenge.screens.account.ui.adapters.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import au.commbank.codingchallenge.common.ui.listadapters.BindingDelegate
import au.commbank.codingchallenge.common.ui.listadapters.BindingViewHolder
import au.commbank.codingchallenge.databinding.AccountInfoItemBinding
import au.commbank.codingchallenge.screens.account.ui.data.AccountInfoItem
import au.commbank.codingchallenge.screens.account.ui.data.ListItem


class AccountInfoItemDelegate :
    BindingDelegate<ListItem, AccountInfoItem, AccountInfoItemBinding, AccountInfoItemDelegate.AccountInfoItemViewHolder>() {

    override fun isForViewType(item: ListItem): Boolean = item is AccountInfoItem

    override fun createViewHolder(parent: ViewGroup): AccountInfoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AccountInfoItemBinding.inflate(layoutInflater, parent, false)
        return AccountInfoItemViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(item: AccountInfoItem, holder: AccountInfoItemViewHolder) {
        holder.bind(item)
    }

    class AccountInfoItemViewHolder(binding: AccountInfoItemBinding) :
        BindingViewHolder<AccountInfoItem, AccountInfoItemBinding>(binding) {
        override fun bind(item: AccountInfoItem) {
            binding.accountInfoItem = item
            binding.executePendingBindings()
        }
    }
}