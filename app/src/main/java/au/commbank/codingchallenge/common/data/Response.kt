package au.commbank.codingchallenge.common.data

//https://developer.android.com/jetpack/guide
sealed class Response<T>(val data: T?, val message: String = "")
class Success<T>(data: T) : Response<T>(data)
class Error<T>(message: String, val errorType: Int) : Response<T>(null, message)