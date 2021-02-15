package au.commbank.codingchallenge.config

object NetworkConfig {
    const val apiBaseUrl = Urls.base
    const val connectionTimeOutInSec = 30L
    const val readTimeOutInSec = 30L
    const val writeTimeOutInSec = 30L
}

object Urls {
    const val base = "https://www.dropbox.com/s/"
    const val accountDetails = "tewg9b71x0wrou9/data.json?dl=1"
}