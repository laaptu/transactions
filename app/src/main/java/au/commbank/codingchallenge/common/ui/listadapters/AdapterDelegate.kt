package au.commbank.codingchallenge.common.ui.listadapters

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Taken from below with some modification
 * https://github.com/sockeqwe/AdapterDelegates
 */
abstract class AdapterDelegate<T> {
    abstract fun isForViewType(item: T): Boolean
    abstract fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun onBindViewHolder(
        items: List<T>, position: Int,
        holder: RecyclerView.ViewHolder
    )
}

//TODO: Simplify as this is a bit too much abstraction
@Suppress("UNCHECKED_CAST")
abstract class BindingDelegate<T, I : T, V : ViewDataBinding, BVH : BindingViewHolder<I, V>> :
    AdapterDelegate<T>() {

    override fun onBindViewHolder(items: List<T>, position: Int, holder: RecyclerView.ViewHolder) =
        onBindViewHolder(items[position] as I, holder as BVH)

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        createViewHolder(parent)

    abstract fun onBindViewHolder(item: I, holder: BVH)
    abstract fun createViewHolder(parent: ViewGroup): BVH
}

abstract class BindingViewHolder<I, V : ViewDataBinding>(val binding: V) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: I)
}

