package au.commbank.codingchallenge.screens.account.ui.data

sealed class UIAction
class DisplayMsg(val msgResId: Int) : UIAction()
class Navigate<T>(val address: Int, data: T) : UIAction()
class DisplayList(val items: List<ListItem>) : UIAction()
class ShowProgress(val show: Boolean) : UIAction()