package com.topupmama.weather.common

sealed class AppResource<T> (val data: T? = null, val message:String? = null){
    class AppSuccess<T>(data: T) : AppResource<T>(data)
    class AppError<T>(message: String, data: T? = null) : AppResource<T>(message = message, data = data)
    class AppLoading<T>(data: T? = null) : AppResource<T>(data)
}
