package au.commbank.codingchallenge.screens.utils

import au.commbank.codingchallenge.config.NetworkConfig
import au.commbank.codingchallenge.screens.account.ui.data.DateDiff
import au.commbank.codingchallenge.screens.account.ui.data.Spending
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

private const val AMOUNT_FORMAT = "$%.2f"
private val serverDateFormat = SimpleDateFormat(NetworkConfig.dateFormat)
private val uiDateFormat = SimpleDateFormat("dd MMM yyyy")

fun formatAmount(amount: Float): String =
        if (amount < 0) {
            "-".plus(AMOUNT_FORMAT.format(abs(amount)))
        } else {
            AMOUNT_FORMAT.format(amount)
        }

fun getTimeInMillisFromDate(date: String, defaultReturn: Long): Long =
        try {
            serverDateFormat.parse(date)?.time ?: defaultReturn
        } catch (exception: Exception) {
            defaultReturn
        }

fun getTimeDiff(date: String): DateDiff {
    try {
        val currDate = Calendar.getInstance().time
        val prevDate = serverDateFormat.parse(date)

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

fun getDisplayDate(date: String): String {
    return try {
        val givenDate = serverDateFormat.parse(date)
        uiDateFormat.format(givenDate)
    } catch (exception: Exception) {
        date
    }
}

fun getBiWeeklySpendingProjection(sortedSpendingByDate: List<Spending>): Float {
    return 0f
}