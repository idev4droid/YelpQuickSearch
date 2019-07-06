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

class YelpQuickSearchApp : Application(), Observer<Boolean>, HasActivityInjector, HasSupportFragmentInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var connectivityLiveData: ConnectivityLiveData
    var hasInternet = false

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
        initConnectivityLiveData()
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

fun Fragment.getApp(): YelpQuickSearchApp? {
    return (activity?.application as? YelpQuickSearchApp)
}

fun Fragment.observeConnectivityChange(observer: Observer<Boolean>) {
    getApp()?.connectivityLiveData?.observe(this, observer)
}