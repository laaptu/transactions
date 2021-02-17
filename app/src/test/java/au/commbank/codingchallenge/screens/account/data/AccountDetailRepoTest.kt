package au.commbank.codingchallenge.screens.account.data

import au.commbank.codingchallenge.common.BaseTest
import au.commbank.codingchallenge.common.Logger
import au.commbank.codingchallenge.common.data.Error
import au.commbank.codingchallenge.common.data.ErrorType
import au.commbank.codingchallenge.common.data.Success
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class AccountDetailRepoTest : BaseTest() {
    @Mock
    lateinit var accountApiService: AccountApiService

    @Mock
    lateinit var logger: Logger
    lateinit var accountDetailRepo: AccountDetailRepo

    @BeforeEach
    override fun init() {
        super.init()
        accountDetailRepo = AccountDetailRepo(accountApiService, logger)
    }

    @DisplayName(
        """
       GIVEN api service with valid AccountDetails,
       WHEN getAccountDetails() is called,
       THEN it must return Success with valid AccountDetails
    """
    )
    @Test
    fun validAccountDetailsReturnsSuccess() {
        runBlockingTest {
            val accountDetails = mock(AccountDetails::class.java)
            `when`(accountApiService.getAccountDetails()).thenReturn(accountDetails)

            val response = accountDetailRepo.getAccountDetails()

            assertTrue("Response must be success", response is Success)
            assertEquals(accountDetails, (response as Success).data)
        }
    }

    @DisplayName(
        """
       GIVEN api service throws Exception,
       WHEN getAccountDetails() is called,
       THEN it must return Error with errorMsg and ErrorType as General
    """
    )
    @Test
    fun exceptionReturnsError() {
        runBlockingTest {
            val errorMsg = "Error getting account details"
            `when`(accountApiService.getAccountDetails()).thenAnswer {
                throw Exception(errorMsg)
            }

            val response = accountDetailRepo.getAccountDetails()
            assertTrue("Response must be Error", response is Error)
            assertTrue(
                "Error response must contain error message",
                (response as Error).message.contains(errorMsg)
            )
            assertEquals(ErrorType.General, response.errorType)
        }
    }

}