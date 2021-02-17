package au.commbank.codingchallenge.screens.account.ui.data

import javax.inject.Inject

sealed class ViewState
object Initialized : ViewState()
class DataFetched(val accountUIData: AccountUIData) : ViewState()
class InProgress(val inProgress: Boolean) : ViewState()


class ViewStateProvider @Inject constructor() {
    fun getInitialViewState(): ViewState = Initialized
}