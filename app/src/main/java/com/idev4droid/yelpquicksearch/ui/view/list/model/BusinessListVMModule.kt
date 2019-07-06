package com.idev4droid.yelpquicksearch.ui.view.list.model

import com.idev4droid.yelpquicksearch.core.data.BusinessService
import dagger.Module
import dagger.Provides

@Module
class BusinessListVMModule {

    @Provides
    fun provideViewModel(service: BusinessService) = BusinessListViewModel(service)
}