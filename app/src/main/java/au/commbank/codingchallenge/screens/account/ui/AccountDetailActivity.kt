package au.commbank.codingchallenge.screens.account.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.ui.ViewModelActivity
import au.commbank.codingchallenge.databinding.ActivityAccountDetailBinding
import au.commbank.codingchallenge.screens.account.ui.adapters.AccountAdapter

class AccountDetailActivity : ViewModelActivity<AccountDetailViewModel>() {
    override val viewModel: AccountDetailViewModel by viewModels {
        viewModelFactory
    }

    private val binding: ActivityAccountDetailBinding by binding(R.layout.activity_account_detail)
    private val accountAdapter = AccountAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            initActionBar(
                toolBar = appBar.toolbar,
                titleText = getString(R.string.account_details)
            )
            initRecyclerView(recyclerView)
        }

        viewModel.accountItems.observe(this, Observer {
            accountAdapter.setItems(it)
        })
        viewModel.fetchAccountDetail()
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
}