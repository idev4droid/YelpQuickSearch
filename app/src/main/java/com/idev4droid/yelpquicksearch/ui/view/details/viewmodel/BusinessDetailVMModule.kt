package com.idev4droid.yelpquicksearch.ui.view.details.viewmodel

import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class BusinessDetailVMModule {
    @Provides
    fun provideViewModel(service: BusinessService, schedulerProvider: SchedulerProvider) =
        BusinessDetailViewModel(service, schedulerProvider)
}