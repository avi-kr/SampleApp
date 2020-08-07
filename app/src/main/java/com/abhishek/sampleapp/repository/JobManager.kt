package com.abhishek.sampleapp.repository

import android.util.Log
import kotlinx.coroutines.Job

/**
 * Created by Abhishek Kumar on 07/08/20.
 * (c)2020 VMock. All rights reserved.
 */

open class JobManager(
    private val className: String
) {

    private val TAG = "AppDebug"

    private val jobs: HashMap<String, Job> = HashMap()

    fun addJob(methodName: String, job: Job) {
        cancelJob(methodName)
        jobs[methodName] = job
    }

    fun cancelJob(methodName: String) {
        getJob(methodName)?.cancel()
    }

    fun getJob(methodName: String): Job? {
        if (jobs.containsKey(methodName)) {
            jobs[methodName]?.let {
                return it
            }
        }
        return null
    }

    fun cancelActiveJobs() {
        for ((methodName, job) in jobs) {
            if (job.isActive) {
                Log.e(TAG, "$className: cancelling jobs in method: $methodName ")
                job.cancel()
            }
        }
    }
}