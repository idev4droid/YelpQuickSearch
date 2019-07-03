package com.idev4droid.yelpquicksearch.core.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : ViewModel> : DaggerAppCompatActivity() {
    private var viewModel: T? = null

    abstract fun getViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        viewModel = getViewModel()
    }
}