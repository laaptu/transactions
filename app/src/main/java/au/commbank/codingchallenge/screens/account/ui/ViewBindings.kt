package au.commbank.codingchallenge.screens.account.ui

import android.os.Build
import android.text.Html
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.ui.events.EventBus
import au.commbank.codingchallenge.screens.account.ui.data.AtmLocationClick
import au.commbank.codingchallenge.screens.account.ui.data.DateDiff
import au.commbank.codingchallenge.screens.account.ui.data.DisplayAmount
import au.commbank.codingchallenge.screens.account.ui.data.TransactionItem
import au.commbank.codingchallenge.screens.utils.formatAmount
import au.commbank.codingchallenge.screens.utils.getDisplayAmount

object ViewBindings {

    @JvmStatic
    @BindingAdapter("displayAmount")
    fun displayAmount(textView: AppCompatTextView, amount: Float) {
        val displayAmount = getDisplayAmount(amount)
        val displayText = formatAmount(displayAmount.amount)
        val context = textView.context
        when (displayAmount) {
            is DisplayAmount.Thousands -> textView.text = displayText
            is DisplayAmount.Millions -> textView.text =
                context.getString(R.string.million, displayText)
            is DisplayAmount.Billions -> textView.text =
                context.getString(R.string.billion, displayText)
            else -> textView.text = context.getString(R.string.trillion, displayText)
        }
    }

    @JvmStatic
    @BindingAdapter("displayDateDiff")
    fun displayDateDiff(textView: AppCompatTextView, dateDiff: DateDiff) {
        val resources = textView.context.resources
        val diff = dateDiff.diff.toInt()
        when (dateDiff) {
            is DateDiff.Month -> textView.text = resources.getQuantityString(
                R.plurals.numberOfMonths,
                diff, diff
            )
            is DateDiff.Year -> textView.text = resources.getQuantityString(
                R.plurals.numberOfYears,
                diff, diff
            )
            else -> {
                if (diff == 0)
                    textView.text = resources.getString(R.string.today)
                else
                    textView.text = resources.getQuantityString(
                        R.plurals.numberOfDays,
                        diff, diff
                    )
            }
        }
    }

    @JvmStatic
    @BindingAdapter("displayTransactionItem")
    fun displayTransactionItem(
        textView: AppCompatTextView,
        transactionItem: TransactionItem
    ) {
        displayDescription(textView, transactionItem.description, transactionItem.isPending)
        setRightDrawable(textView, transactionItem.atmLocationId != null)
    }

    private fun setRightDrawable(
        textView: AppCompatTextView,
        setRightDrawable: Boolean
    ) {
        val rightDrawable = if (setRightDrawable)
            ContextCompat.getDrawable(textView.context, R.drawable.ic_location)
        else
            null
        textView.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null)
    }

    private fun displayDescription(
        textView: AppCompatTextView,
        description: String,
        isPending: Boolean
    ) {
        val desc = if (isPending)
            textView.context.getString(R.string.pending).plus(" ").plus(description)
        else
            description
        textView.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(desc, Html.FROM_HTML_MODE_LEGACY)
        else
            Html.fromHtml(desc)
    }

    @JvmStatic
    @BindingAdapter("atmLocationClick")
    fun onAtmLocationClick(view: View, atmLocationId: String?) {
        if (atmLocationId != null) {
            view.isEnabled = true
            view.isClickable = true
            view.setOnClickListener {
                EventBus.postEvent(AtmLocationClick(atmLocationId))
            }
        } else {
            view.isEnabled = false
            view.isClickable = false
            view.setOnClickListener(null)
        }
    }
}