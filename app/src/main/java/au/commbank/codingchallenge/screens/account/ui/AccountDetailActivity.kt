package au.commbank.codingchallenge.screens.account.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.ui.ViewModelActivity
import au.commbank.codingchallenge.common.ui.events.EventBus
import au.commbank.codingchallenge.common.ui.events.EventObserver
import au.commbank.codingchallenge.databinding.ActivityAccountDetailBinding
import au.commbank.codingchallenge.screens.account.ui.adapters.AccountAdapter
import au.commbank.codingchallenge.screens.account.ui.data.*
import au.commbank.codingchallenge.screens.location.AtmLocationActivity
import com.google.android.material.snackbar.Snackbar

class AccountDetailActivity : ViewModelActivity<AccountDetailViewModel>() {
    override val viewModel: AccountDetailViewModel by viewModels {
        viewModelFactory
    }

    private val binding: ActivityAccountDetailBinding by binding(R.layout.activity_account_detail)
    private val accountAdapter = AccountAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        listenToViewEvents()
        fetchAccountDetails()
    }

    private fun initViews() {
        binding.apply {
            initActionBar(
                toolBar = appBar.toolbar,
                titleText = getString(R.string.account_details)
            )
            initRecyclerView(recyclerView)
            refreshLayout.setOnRefreshListener {
                refreshAccountDetails()
            }
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = accountAdapter
    }

    private fun listenToViewEvents() {

        viewModel.uiAction.observe(this, EventObserver { uiAction ->
            when (uiAction) {
                is DisplayMsg -> showInfoMsg(uiAction.msgResId)
                is DisplayList -> accountAdapter.setItems(uiAction.items)
                is ShowProgress -> showProgress(uiAction.show, binding.refreshLayout)
                is Navigate -> startActivity(
                    AtmLocationActivity.getLaunchIntent(
                        this,
                        uiAction.location
                    )
                )
            }
        })

        EventBus.register(this, EventObserver {
            if (it is AtmLocationClick)
                viewModel.onAtmLocationClick(it.atmLocationId)
        })
    }

    private fun showProgress(showProgress: Boolean, refreshLayout: SwipeRefreshLayout) {
        if (showProgress && !refreshLayout.isRefreshing)
            refreshLayout.isRefreshing = true
        else if (refreshLayout.isRefreshing)
            refreshLayout.isRefreshing = false
    }

    private fun showInfoMsg(msgResId: Int) {
        Snackbar.make(binding.refreshLayout, getString(msgResId), Snackbar.LENGTH_SHORT).show()
    }

    private fun fetchAccountDetails() = viewModel.fetchAccountDetail()
    private fun refreshAccountDetails() = viewModel.refreshAccountDetails()


}