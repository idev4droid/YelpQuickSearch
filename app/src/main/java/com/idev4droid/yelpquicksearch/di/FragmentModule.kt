package com.idev4droid.yelpquicksearch.di

import com.idev4droid.yelpquicksearch.ui.view.list.BusinessListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun bindBusinessListFragment(): BusinessListFragment
}