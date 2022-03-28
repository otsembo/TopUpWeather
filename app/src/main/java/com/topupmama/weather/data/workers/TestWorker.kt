package com.topupmama.weather.data.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class TestWorker(mCtx: Context, workerParameters: WorkerParameters) : Worker(mCtx,workerParameters){

    override fun doWork(): Result {
        Log.d("TAG", "doWork: Work Here")
        return Result.success()
    }
}