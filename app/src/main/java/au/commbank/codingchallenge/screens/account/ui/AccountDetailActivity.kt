package au.commbank.codingchallenge.screens.account.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.ui.ViewModelActivity

class AccountDetailActivity : ViewModelActivity<AccountDetailViewModel>() {
    override val viewModel: AccountDetailViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initActionBar(
                toolBar = toolbar,
                titleText = getString(R.string.account_details),
        )
        viewModel.fetchAccountDetail()

    }
}