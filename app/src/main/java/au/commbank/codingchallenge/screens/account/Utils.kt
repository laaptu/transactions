package au.commbank.codingchallenge.screens.account

import java.text.SimpleDateFormat
import java.util.*
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
}