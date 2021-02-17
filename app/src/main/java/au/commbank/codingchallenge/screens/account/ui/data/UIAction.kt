package au.commbank.codingchallenge.screens.account.ui.data

import au.commbank.codingchallenge.screens.account.data.Location

sealed class UIAction
class DisplayMsg(val msgResId: Int) : UIAction()
class Navigate(val location: Location) : UIAction()
class DisplayList(val items: List<ListItem>) : UIAction()
class ShowProgress(val show: Boolean) : UIAction()