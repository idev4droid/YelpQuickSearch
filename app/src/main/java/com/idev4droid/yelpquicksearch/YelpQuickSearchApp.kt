package com.idev4droid.yelpquicksearch

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.idev4droid.connectivity.live.data.ConnectivityLiveData
import com.idev4droid.yelpquicksearch.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * YelpQuickSearchApp starts the DI process and the connectivityLiveData that will live during the app lifecycle.
 */
class YelpQuickSearchApp : Application(), Observer<Boolean>, HasActivityInjector, HasSupportFragmentInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var connectivityLiveData: ConnectivityLiveData
    var hasInternet = false

    override fun onCreate() {
        super.onCreate()
        initDaggerComponents()
        initConnectivityLiveData()
    }

    private fun initDaggerComponents() {
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    private fun initConnectivityLiveData() {
        connectivityLiveData = ConnectivityLiveData(this)
        connectivityLiveData.observeForever(this)
    }

    override fun onChanged(connected: Boolean?) {
        hasInternet = connected ?: false
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector
}