package au.commbank.codingchallenge.common.ui.listadapters

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Taken from below with some modification
 * https://github.com/sockeqwe/AdapterDelegates
 */

open class DelegatesRVAdapter<T>(
    listDelegates: List<AdapterDelegate<T>>,
    diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val delegates: SparseArrayCompat<AdapterDelegate<T>> = SparseArrayCompat()
    private val differ: AsyncListDiffer<T> = AsyncListDiffer(this, diffCallback)


    init {
        listDelegates.forEachIndexed { index, adapterDelegate ->
            delegates.put(index, adapterDelegate)
        }
    }

    private fun getDelegateForViewType(viewType: Int): AdapterDelegate<T>? = delegates[viewType]

    private fun getItemViewType(items: List<T>, position: Int): Int {
        for (i in 0 until delegates.size()) {
            val delegate = delegates.valueAt(i)
            if (delegate.isForViewType(items[position])) {
                return delegates.keyAt(i)
            }
        }
        val errorMsg = "No AdapterDelegate added for item at position=$position. items=$items"
        throw NullPointerException(errorMsg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate: AdapterDelegate<T> = getDelegateForViewType(viewType)
            ?: throw NullPointerException("No AdapterDelegate added for ViewType $viewType")

        return delegate.onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegate = getDelegateForViewType(holder.itemViewType)
            ?: throw NullPointerException("No delegate found for item at position = $position for viewType = ${holder.itemViewType}")
        delegate.onBindViewHolder(differ.currentList, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewType(differ.currentList, position)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun getItems(): List<T>? = differ.currentList
    fun setItems(items: List<T>?) = differ.submitList(items)

}




