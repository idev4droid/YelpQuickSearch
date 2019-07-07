package com.idev4droid.yelpquicksearch.ui.view.list.filter.viewmodel

import dagger.Module
import dagger.Provides

@Module
class BusinessFilterVMModule {
    @Provides
    fun provideViewModel() = BusinessFilterViewModel()
}