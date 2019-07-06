package com.idev4droid.yelpquicksearch.modelView.component

import com.idev4droid.yelpquicksearch.core.data.BusinessRepository
import com.idev4droid.yelpquicksearch.modelView.BusinessListViewModel
import com.idev4droid.yelpquicksearch.modelView.BusinessListViewModelListener
import dagger.Module
import dagger.Provides

@Module
class BusinessListVMModule {
    @Provides
    fun provideViewModel(listener: BusinessListViewModelListener, repository: BusinessRepository) =
        BusinessListViewModel(listener, repository)
}