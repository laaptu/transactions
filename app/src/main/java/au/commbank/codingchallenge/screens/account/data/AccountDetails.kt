package au.commbank.codingchallenge.screens.account.data

import com.google.gson.annotations.SerializedName


class AccountDetails(
        val account: Account,
        val transactions: List<Transaction> = emptyList(),
        @SerializedName("pending")
        val pendingTransactions: List<Transaction> = emptyList(),
        @SerializedName("atms")
        val atmLocations: List<AtmLocation> = emptyList()
)

data class Account(
        val accountName: String,
        val accountNumber: String,
        val available: Float,
        val balance: Float
)

data class Transaction(
        val id: String,
        val effectiveDate: String,
        val description: String,
        val amount: Float,
        val atmId: String?
)

data class AtmLocation(
        val id: String,
        val name: String,
        val address: String,
        val location: Location
)

data class Location(
        @SerializedName("lat")
        val latitude: Double,
        @SerializedName("lng")
        val longitude: Double
)