package au.commbank.codingchallenge.screens.account.ui.data

import javax.inject.Inject

sealed class VMState
object Initialized : VMState()
object Empty : VMState()
class DataFetched(val accountUIData: AccountUIData) : VMState()

class VMStateProvider @Inject constructor() {
    fun getInitialViewState(): VMState = Initialized
}