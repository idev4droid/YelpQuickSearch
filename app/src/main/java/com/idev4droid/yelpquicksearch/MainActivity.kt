package com.idev4droid.yelpquicksearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*

/**
 * MainActivity is the entry point of the app. It will init all third parties and start the Navigation Architecture
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initThirdParties()
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp() =
        findNavController(nav_host_fragment).navigateUp()

    /**
     * Init all third parties SDKs
     */
    private fun initThirdParties() {
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
    }
}
