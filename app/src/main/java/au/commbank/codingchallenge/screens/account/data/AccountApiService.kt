package au.commbank.codingchallenge.screens.account.data

import au.commbank.codingchallenge.config.Urls
import retrofit2.http.GET

interface AccountApiService {
    @GET(Urls.accountDetails)
    suspend fun getAccountDetails(): AccountDetails
}