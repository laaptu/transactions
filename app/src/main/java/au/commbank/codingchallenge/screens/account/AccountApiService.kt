package au.commbank.codingchallenge.screens.account

import au.commbank.codingchallenge.config.Urls
import au.commbank.codingchallenge.screens.location.AccountDetails
import retrofit2.http.GET

interface AccountApiService {
    @GET(Urls.accountDetails)
    suspend fun getAccountDetails(): AccountDetails
}