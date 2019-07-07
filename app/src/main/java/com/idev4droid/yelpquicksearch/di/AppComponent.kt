package com.idev4droid.yelpquicksearch.di

import android.app.Application
import com.idev4droid.yelpquicksearch.YelpQuickSearchApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, FragmentModule::class, AppModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: YelpQuickSearchApp)
}