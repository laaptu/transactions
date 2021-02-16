package au.commbank.codingchallenge.screens.account.ui

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
            is DisplayAmount.Millions -> textView.text = context.getString(R.string.million, displayText)
            is DisplayAmount.Billions -> textView.text = context.getString(R.string.billion, displayText)
            else -> textView.text = context.getString(R.string.trillion, displayText)
        }
    }

    @JvmStatic
    @BindingAdapter("dateDiff")
    fun displayDateDiff(textView: AppCompatTextView, dateDiff: DateDiff) {
        val resources = textView.context.resources
        val diff = dateDiff.diff.toInt()
        when (dateDiff) {
            is DateDiff.Month -> textView.text = resources.getQuantityString(
                    R.plurals.numberOfMonths,
                    diff, diff)
            is DateDiff.Year -> textView.text = resources.getQuantityString(
                    R.plurals.numberOfYears,
                    diff, diff)
            else -> {
                if (diff == 0)
                    textView.text = resources.getString(R.string.today)
                else
                    textView.text = resources.getQuantityString(R.plurals.numberOfDays,
                            diff, diff)
            }
        }
    }
}