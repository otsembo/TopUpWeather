package com.topupmama.weather.data.workers

import com.topupmama.weather.data.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class WorkerDependencies
    @Inject constructor(ioScope: CoroutineScope, repository: WeatherRepository)
{

        val scope = ioScope
        val repo = repository

}