package au.commbank.codingchallenge.common.ui.listadapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Taken from below with some modification
 * https://github.com/sockeqwe/AdapterDelegates
 */
abstract class AdapterDelegate<T> {
    abstract fun isForViewType(items: List<T>, position: Int): Boolean
    abstract fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun onBindViewHolder(items: List<T>, position: Int,
                                  holder: RecyclerView.ViewHolder)
}