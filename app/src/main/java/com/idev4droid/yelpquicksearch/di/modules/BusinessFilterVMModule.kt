package com.idev4droid.yelpquicksearch.di.modules

import com.idev4droid.yelpquicksearch.ui.view.list.filter.viewmodel.BusinessFilterViewModel
import dagger.Module
import dagger.Provides

/**
 * BusinessFilterVMModule is a dagger 2 module used to provide BusinessFilterViewModel
 */
@Module
class BusinessFilterVMModule {
    @Provides
    fun provideViewModel() = BusinessFilterViewModel()
}