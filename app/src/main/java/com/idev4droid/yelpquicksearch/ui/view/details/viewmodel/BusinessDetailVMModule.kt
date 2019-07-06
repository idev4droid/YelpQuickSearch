package com.idev4droid.yelpquicksearch.ui.view.details.viewmodel

import com.idev4droid.yelpquicksearch.core.data.BusinessService
import dagger.Module
import dagger.Provides

@Module
class BusinessDetailVMModule {
    @Provides
    fun provideViewModel(service: BusinessService) = BusinessDetailViewModel(service)
}