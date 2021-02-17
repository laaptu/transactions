package au.commbank.codingchallenge.screens.account.ui.data

import au.commbank.codingchallenge.config.NetworkConfig

data class Spending(
    val date: String,
    val amount: Float,
    val dateFormat: String = NetworkConfig.dateFormat
)