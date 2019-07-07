package com.idev4droid.yelpquicksearch.ui.view.list.viewmodel

import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class BusinessListVMModule {

    @Provides
    fun provideViewModel(service: BusinessService, schedulerProvider: SchedulerProvider) =
        BusinessListViewModel(service, schedulerProvider)
}