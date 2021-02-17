package au.commbank.codingchallenge.screens.account.ui

import androidx.lifecycle.Observer
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.BaseTest
import au.commbank.codingchallenge.common.Logger
import au.commbank.codingchallenge.common.data.Error
import au.commbank.codingchallenge.common.data.Success
import au.commbank.codingchallenge.common.getOrAwaitValue
import au.commbank.codingchallenge.common.ui.events.Event
import au.commbank.codingchallenge.screens.account.data.AccountDetailRepo
import au.commbank.codingchallenge.screens.account.data.AccountDetails
import au.commbank.codingchallenge.screens.account.data.AtmLocation
import au.commbank.codingchallenge.screens.account.ui.data.*
import au.commbank.codingchallenge.screens.account.ui.data.mappers.AccountDataMapper
import com.nhaarman.mockitokotlin2.mock
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class AccountDetailViewModelTest : BaseTest() {

    @Mock
    lateinit var accountDetailRepo: AccountDetailRepo

    @Mock
    lateinit var accountDataMapper: AccountDataMapper

    @Mock
    lateinit var vmStateProvider: VMStateProvider

    @Mock
    lateinit var logger: Logger
    lateinit var viewModel: AccountDetailViewModel

    @BeforeEach
    override fun init() {
        super.init()
        `when`(vmStateProvider.getInitialViewState()).thenReturn(Initialized)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = AccountDetailViewModel(
            accountDetailRepo,
            accountDataMapper,
            vmStateProvider,
            logger
        )
    }

    @DisplayName(
        """
        GIVEN ViewModelState is Initialised,
        WHEN fetchAccountDetail() is called,
        THEN accountDetailRepo's getAccountDetail is invoked
    """
    )
    @Test
    fun initializedViewModelCallsAccountDetailRepo() {
        runBlockingTest {
            viewModel.fetchAccountDetail()
            verify(accountDetailRepo, times(1)).getAccountDetails()
        }
    }

    @DisplayName(
        """
        GIVEN ViewModelState is Not Initialised,
        WHEN fetchAccountDetail() is called,
        THEN accountDetailRepo's getAccountDetail is NOT invoked
    """
    )
    @Test
    fun viewModelNotInInitializedStateWontCallAccountDetailRepo() {
        runBlockingTest {
            `when`(vmStateProvider.getInitialViewState()).thenReturn(Empty)
            initViewModel()

            viewModel.fetchAccountDetail()

            verify(accountDetailRepo, never()).getAccountDetails()
        }
    }

    @DisplayName(
        """
        GIVEN ViewModelState is Not Initialised,
        WHEN fetchAccountDetail() is called,
        THEN View must be notified not to show progress
    """
    )
    @Test
    fun viewModelNotInInitializedStateProgressShouldNotShowForView() {
        runBlockingTest {
            `when`(vmStateProvider.getInitialViewState()).thenReturn(Empty)
            initViewModel()

            viewModel.fetchAccountDetail()

            val uiAction = viewModel.uiAction.getOrAwaitValue().peek()
            assertTrue("UIAction must be ShowProgress", uiAction is ShowProgress)
            assertEquals(
                "ShowProgress must be false", false,
                (uiAction as ShowProgress).show
            )
        }
    }

    @DisplayName(
        """
        GIVEN ViewModelState is DataFetched,
        WHEN fetchAccountDetail() is called,
        THEN View must be given the valid listItems of DateFetched
    """
    )
    @Test
    fun viewModelInDataFetchedStateShouldLoadValidList() {
        runBlockingTest {
            val dataFetched: DataFetched = mock()
            val accountUIData: AccountUIData = mock()
            val listItems: List<ListItem> = emptyList()
            `when`(vmStateProvider.getInitialViewState()).thenReturn(dataFetched)
            `when`(dataFetched.accountUIData).thenReturn(accountUIData)
            `when`(accountUIData.listItems).thenReturn(listItems)
            initViewModel()

            viewModel.fetchAccountDetail()

            val uiAction = viewModel.uiAction.getOrAwaitValue().peek()
            assertTrue("UIAction must be DisplayList", uiAction is DisplayList)
            assertEquals(listItems, (uiAction as DisplayList).items)
        }
    }

    @DisplayName(
        """
        GIVEN AccountDetailsRepo returns Success with valid data,
        WHEN fetchAccountDetail() is called,
        THEN View must be given the valid listItems of DateFetched
    """
    )
    @Test
    fun viewModelReturnsValidListWhenAccountDetailRespondsSuccess() {
        runBlockingTest {
            val accountDetails: AccountDetails = mock()
            val success: Success<AccountDetails> = mock()
            `when`(success.data).thenReturn(accountDetails)
            `when`(accountDetailRepo.getAccountDetails()).thenReturn(success)

            val accountUIData: AccountUIData = mock()
            `when`(accountDataMapper.mapToAccountUIData(accountDetails)).thenReturn(accountUIData)
            val listItems: List<ListItem> = emptyList()
            `when`(accountUIData.listItems).thenReturn(listItems)

            viewModel.fetchAccountDetail()

            val uiAction = viewModel.uiAction.getOrAwaitValue().peek()
            assertTrue("UIAction must be DisplayList", uiAction is DisplayList)
            assertEquals(listItems, (uiAction as DisplayList).items)
        }
    }

    @DisplayName(
        """
        GIVEN AccountDetailsRepo returns Success with Invalid data,
        WHEN fetchAccountDetail() is called,
        THEN View must be notified by ErrorMsgId
    """
    )
    @Test
    fun viewModelNotifyWithErrorMsgIdWhenSuccessDataIsInvalid() {
        runBlockingTest {
            val success: Success<AccountDetails> = mock()
            `when`(success.data).thenReturn(null)
            `when`(accountDetailRepo.getAccountDetails()).thenReturn(success)

            viewModel.fetchAccountDetail()

            val uiAction = viewModel.uiAction.getOrAwaitValue().peek()
            assertTrue("UIAction must be DisplayList", uiAction is DisplayMsg)
            assertEquals(R.string.error_fetching_account, (uiAction as DisplayMsg).msgResId)
        }
    }

    @DisplayName(
        """
        GIVEN AccountDetailsRepo returns Error,
        WHEN fetchAccountDetail() is called,
        THEN View must be notified by ErrorMsgId
    """
    )
    @Test
    fun viewModelNotifyWithErrorMsgIdWhenRepoReturnsError() {
        runBlockingTest {
            val failure: Error<AccountDetails> = mock()
            `when`(failure.errorType).thenReturn(0)
            `when`(accountDetailRepo.getAccountDetails()).thenReturn(failure)

            viewModel.fetchAccountDetail()

            val uiAction = viewModel.uiAction.getOrAwaitValue().peek()
            assertTrue("UIAction must be DisplayList", uiAction is DisplayMsg)
            assertEquals(R.string.error_fetching_account, (uiAction as DisplayMsg).msgResId)
        }
    }

    @DisplayName(
        """
        GIVEN ViewModel is not at DataFetchedState,
        WHEN onAtmLocationId() is called,
        THEN View won't be notified of anything
    """
    )
    @Test
    fun viewNotNotifiedForOnAtmLocationIdClickWhenViewModelIsInIncorrectState() {
        `when`(vmStateProvider.getInitialViewState()).thenReturn(Empty)
        initViewModel()

        viewModel.onAtmLocationClick("1234")

        val observer: Observer<Event<UIAction>> = mock()
        viewModel.uiAction.observeForever(observer)

        verifyNoMoreInteractions(observer)

        viewModel.uiAction.removeObserver(observer)
    }


    @DisplayName(
        """
        GIVEN invalid atmLocationId,
        WHEN onAtmLocationId() is called,
        THEN View won't be notified of anything
    """
    )
    @Test
    fun viewNotNotifiedForInvalidAtmLocationId() {
        val dataFetched: DataFetched = mock()
        val accountUIData: AccountUIData = mock()
        val atmLocationMap: Map<String, AtmLocation> = mock()
        `when`(vmStateProvider.getInitialViewState()).thenReturn(dataFetched)
        `when`(dataFetched.accountUIData).thenReturn(accountUIData)
        `when`(accountUIData.atmLocMap).thenReturn(atmLocationMap)
        `when`(atmLocationMap[any()]).thenReturn(null)

        viewModel.onAtmLocationClick("1234")

        val observer: Observer<Event<UIAction>> = mock()
        viewModel.uiAction.observeForever(observer)

        verifyNoMoreInteractions(observer)

        viewModel.uiAction.removeObserver(observer)
    }
}