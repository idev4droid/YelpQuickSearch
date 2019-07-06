package com.idev4droid.yelpquicksearch.di

import android.app.Application
import com.idev4droid.yelpquicksearch.YelpQuickSearchApp
import com.idev4droid.yelpquicksearch.ui.view.list.model.BusinessListVMModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, FragmentModule::class, AppModule::class, BusinessListVMModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: YelpQuickSearchApp)
}