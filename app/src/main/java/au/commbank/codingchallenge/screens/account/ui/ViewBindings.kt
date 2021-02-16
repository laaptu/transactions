package au.commbank.codingchallenge.screens.account.ui

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.screens.account.ui.data.DateDiff
import au.commbank.codingchallenge.screens.account.ui.data.DisplayAmount
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
    @BindingAdapter(value = ["description", "isPendingTransaction"], requireAll = true)
    fun displayTransactionDescription(
        textView: AppCompatTextView,
        description: String,
        isPendingTransaction: Boolean
    ) {
        if (isPendingTransaction) {
            val pendingStr = textView.context.getString(R.string.pending)
            val len = pendingStr.length
            val spannableString = SpannableString(pendingStr.plus(" ").plus(description))
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                0, len + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannableString
        } else {
            textView.text = description
        }
    }

    @JvmStatic
    @BindingAdapter("show")
    fun showView(view: View, show: Boolean) {
        if (show)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

}