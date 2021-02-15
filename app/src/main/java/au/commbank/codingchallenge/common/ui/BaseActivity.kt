package au.commbank.codingchallenge.common.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import au.commbank.codingchallenge.R
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    protected fun <T : ViewDataBinding> binding(@LayoutRes layoutId: Int): Lazy<T> =
        lazy {
            DataBindingUtil.setContentView<T>(this, layoutId)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
    }


    fun initActionBar(
        toolBar: Toolbar,
        iconResId: Int = R.drawable.ic_app_icon,
        titleText: String = getString(R.string.app_name),
        homeAsUpEnabled: Boolean = false
    ) {
        setSupportActionBar(toolBar)
        supportActionBar?.apply {
            setIcon(iconResId)
            title = " ".plus(titleText)
            if (homeAsUpEnabled)
                setDisplayHomeAsUpEnabled(true)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }


}