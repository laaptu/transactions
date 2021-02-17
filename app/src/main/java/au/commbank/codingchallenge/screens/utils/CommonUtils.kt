@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package au.commbank.codingchallenge.screens.utils

import au.commbank.codingchallenge.config.NetworkConfig
import au.commbank.codingchallenge.screens.account.ui.data.DateDiff
import au.commbank.codingchallenge.screens.account.ui.data.DisplayAmount
import au.commbank.codingchallenge.screens.account.ui.data.Spending
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

private const val AMOUNT_FORMAT = "$%.2f"
private val serverDateFormat = SimpleDateFormat(NetworkConfig.dateFormat, Locale.US)
private val uiDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)

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

//TODO: need to account for leap year and accurate days in a month
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
        uiDateFormat.format(givenDate).toUpperCase(Locale.ROOT)
    } catch (exception: Exception) {
        date
    }
}

fun getDisplayAmount(amount: Float): DisplayAmount {
    val mul = if (amount < 0) -1 else 1
    val absAmount = abs(amount)
    return when {
        absAmount < 1e6f -> DisplayAmount.Thousands(amount)
        absAmount < 1e9f -> DisplayAmount.Millions((absAmount / 1e6f) * mul)
        absAmount < 1e12f -> DisplayAmount.Billions((absAmount / 1e9f) * mul)
        else -> DisplayAmount.Trillions((absAmount / 1e12f) * mul)
    }
}

//this is just a rough algorithm, the date difference needs to be more accurate
fun getBiWeeklySpendingProjection(sortedSpendingByDate: List<Spending>): Float {
    var totalSpending = 0f
    val validSpending = sortedSpendingByDate.filter {
        totalSpending += it.amount
        it.amount > 0
    }
    if (validSpending.size == 1) {
        return totalSpending / 2f
    }
    val endTimeInMillis = getTimeInMillis(
        validSpending.first().date,
        validSpending.first().dateFormat
    )
    val startTimeInMillis = getTimeInMillis(
        validSpending.last().date,
        validSpending.last().dateFormat
    )

    val diffInMillis = abs(endTimeInMillis - startTimeInMillis)
    val days: Long = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)
    if (days <= 30)
        return totalSpending / 2f
    val mul = days / 15
    return (totalSpending / mul)
}

private fun getTimeInMillis(dateVal: String, format: String): Long {
    return try {
        val dateFormat = SimpleDateFormat(format, Locale.US)
        dateFormat.parse(dateVal).time
    } catch (exception: Exception) {
        0
    }
}