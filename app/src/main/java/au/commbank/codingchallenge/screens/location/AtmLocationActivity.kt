package au.commbank.codingchallenge.screens.location

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import au.commbank.codingchallenge.R
import au.commbank.codingchallenge.common.ui.BaseActivity

class AtmLocationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_detail)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initActionBar(toolBar = toolbar,
                titleText = "Find us",
                homeAsUpEnabled = true)


    }
}