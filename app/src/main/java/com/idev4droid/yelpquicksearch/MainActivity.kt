package com.idev4droid.yelpquicksearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.crashlytics.android.Crashlytics
import com.idev4droid.yelpquicksearch.modelView.BusinessesViewModel
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        var businessesViewModel = BusinessesViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initThirdParties()
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp() =
        findNavController(nav_host_fragment).navigateUp()

    private fun initThirdParties() {
        Fabric.with(this, Crashlytics())
    }
}
