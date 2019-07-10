package com.idev4droid.yelpquicksearch.di.modules

import com.idev4droid.yelpquicksearch.core.data.BusinessService
import com.idev4droid.yelpquicksearch.ui.view.details.viewmodel.BusinessDetailViewModel
import com.idev4droid.yelpquicksearch.utils.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * BusinessDetailVMModule is a dagger 2 module used to provide BusinessDetailViewModel
 */
@Module
class BusinessDetailVMModule {
    @Provides
    fun provideViewModel(service: BusinessService, schedulerProvider: SchedulerProvider) =
        BusinessDetailViewModel(service, schedulerProvider)
}