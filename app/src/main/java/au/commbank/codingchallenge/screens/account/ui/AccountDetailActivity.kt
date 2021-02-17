package au.commbank.codingchallenge.screens.account.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.ui.ViewModelActivity
import au.commbank.codingchallenge.common.ui.events.EventObserver
import au.commbank.codingchallenge.databinding.ActivityAccountDetailBinding
import au.commbank.codingchallenge.screens.account.ui.adapters.AccountAdapter
import au.commbank.codingchallenge.screens.account.ui.data.DataFetched
import au.commbank.codingchallenge.screens.account.ui.data.DisplayMsg
import au.commbank.codingchallenge.screens.account.ui.data.InProgress
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
        listenToViewModelEvents()
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

    private fun listenToViewModelEvents() {
        viewModel.viewState.observe(this, Observer {
            if (it is DataFetched) {
                accountAdapter.setItems(it.accountUIData.listItems)
            } else if (it is InProgress) {
                showProgress(it.inProgress, binding.refreshLayout)
            }
        })

        viewModel.uiAction.observe(this, EventObserver {
            if (it is DisplayMsg) {
                showInfoMsg(it.msgResId)
            }
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