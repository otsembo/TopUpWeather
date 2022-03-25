package com.topupmama.weather.common

data class AppState<T>(
    var isLoading:Boolean = false,
    var data:T? = null,
    var message:String? = null
)
