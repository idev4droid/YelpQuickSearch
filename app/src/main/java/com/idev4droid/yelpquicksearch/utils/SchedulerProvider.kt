package com.idev4droid.yelpquicksearch.utils

import io.reactivex.Scheduler

data class SchedulerProvider(val backgroundScheduler: Scheduler, val foregroundScheduler: Scheduler)