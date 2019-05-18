package com.idev4droid.yelpquicksearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.idev4droid.yelpquicksearch.view.BusinessListFragment
import io.fabric.sdk.android.Fabric



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initThirdParties()
        setContentView(R.layout.activity_main)

        loadListFragment()
    }

    private fun loadListFragment() {
        val businessListFragment = BusinessListFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, businessListFragment)
        fragmentTransaction.commit()
    }

    private fun initThirdParties() {
        Fabric.with(this, Crashlytics())
    }
}
