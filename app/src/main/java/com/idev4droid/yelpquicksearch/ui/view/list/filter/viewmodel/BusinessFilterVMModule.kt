package com.idev4droid.yelpquicksearch.ui.view.list.filter.viewmodel

import com.idev4droid.yelpquicksearch.core.data.BusinessService
import dagger.Module
import dagger.Provides

@Module
class BusinessFilterVMModule {
    @Provides
    fun provideViewModel(service: BusinessService) = BusinessFilterViewModel(service)
}