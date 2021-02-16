package au.commbank.codingchallenge.screens.account.ui.adapters.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import au.commbank.codingchallenge.common.ui.listadapters.BindingDelegate
import au.commbank.codingchallenge.common.ui.listadapters.BindingViewHolder
import au.commbank.codingchallenge.databinding.DateItemBinding
import au.commbank.codingchallenge.screens.account.ui.data.DateItem
import au.commbank.codingchallenge.screens.account.ui.data.ListItem


class DateItemDelegate :
    BindingDelegate<ListItem, DateItem, DateItemBinding, DateItemDelegate.DateItemViewHolder>() {

    override fun isForViewType(item: ListItem): Boolean = item is DateItem

    override fun createViewHolder(parent: ViewGroup): DateItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DateItemBinding.inflate(layoutInflater, parent, false)
        return DateItemViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(item: DateItem, holder: DateItemViewHolder) {
        holder.bind(item)
    }

    class DateItemViewHolder(binding: DateItemBinding) :
        BindingViewHolder<DateItem, DateItemBinding>(binding) {
        override fun bind(item: DateItem) {
            binding.dateItem = item
            binding.executePendingBindings()
        }
    }
}