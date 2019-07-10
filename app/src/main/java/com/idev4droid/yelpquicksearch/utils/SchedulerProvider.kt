package com.idev4droid.yelpquicksearch.utils

import io.reactivex.Scheduler

/**
 * Data class SchedulerProvider is a holder for the schedulers used when subscribing to Observable.
 * @param backgroundScheduler is a Scheduler used for background tasks
 * @param foregroundScheduler is a Scheduler used for foreground tasks
 */
data class SchedulerProvider(val backgroundScheduler: Scheduler, val foregroundScheduler: Scheduler)