package com.idev4droid.yelpquicksearch.di.modules

import com.idev4droid.yelpquicksearch.ui.view.list.filter.viewmodel.BusinessFilterViewModel
import dagger.Module
import dagger.Provides

@Module
class BusinessFilterVMModule {
    @Provides
    fun provideViewModel() = BusinessFilterViewModel()
}