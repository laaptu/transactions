package au.commbank.codingchallenge.screens.account.presentation.data

import au.commbank.codingchallenge.screens.account.data.AtmLocation

data class AccountUIData(val listItems: List<ListItem>, val atmLocMap: Map<String, AtmLocation>,
                         val twoWeekSpendingProjection: Float = 0f)