package au.commbank.codingchallenge.screens.account.domain

import au.commbank.codingchallenge.config.Urls
import au.commbank.codingchallenge.screens.account.domain.data.AccountDetails
import retrofit2.http.GET

interface AccountApiService {
    @GET(Urls.accountDetails)
    suspend fun getAccountDetails(): AccountDetails
}