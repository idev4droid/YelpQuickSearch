package com.idev4droid.yelpquicksearch.di

import com.idev4droid.yelpquicksearch.ui.view.details.BusinessDetailsFragment
import com.idev4droid.yelpquicksearch.ui.view.list.BusinessListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * FragmentModule is a dagger 2 module used to allow injects into fragments.
 */
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun bindBusinessListFragment(): BusinessListFragment

    @ContributesAndroidInjector
    abstract fun bindBusinessDetailsFragment(): BusinessDetailsFragment
}