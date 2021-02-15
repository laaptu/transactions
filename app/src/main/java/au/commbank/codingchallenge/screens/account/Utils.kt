package au.commbank.codingchallenge.screens.account

import au.commbank.codingchallenge.screens.account.presentation.data.DateDiff
import au.commbank.codingchallenge.screens.account.presentation.data.Spending
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object Utils {
    private const val AMOUNT_FORMAT = "$%.2f"


    fun formatAmount(amount: Float): String =
            if (amount < 0) {
                "-".plus(AMOUNT_FORMAT.format(abs(amount)))
            } else {
                AMOUNT_FORMAT.format(amount)
            }

    fun getTimeInMillisFromDate(date: String, format: String, defaultReturn: Long): Long =
            try {
                val formatter = SimpleDateFormat(format, Locale.US)
                formatter.parse(date)?.time ?: defaultReturn
            } catch (exception: Exception) {
                defaultReturn
            }

    fun getTimeDiff(date: String, format: String): DateDiff {
        try {
            val sdf = SimpleDateFormat(format, Locale.US)
            val currDate = Calendar.getInstance().time
            val prevDate = sdf.parse(date)

            val diffInMills = abs(currDate.time - prevDate.time)
            val days: Long = TimeUnit.DAYS.convert(diffInMills, TimeUnit.MILLISECONDS)
            if (days < 30)
                return DateDiff.Day(days)

            val months = days / 30
            if (months < 12)
                return DateDiff.Month(months)

            val years = months / 12
            return DateDiff.Year(years)
        } catch (exception: Exception) {
            return DateDiff.Invalid
        }
    }

    fun getBiWeeklySpendingProjection(sortedSpendingByDate: List<Spending>): Float {
        return 0f
    }
}