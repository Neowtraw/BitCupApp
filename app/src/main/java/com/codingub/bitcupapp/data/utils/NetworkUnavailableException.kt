package com.codingub.bitcupapp.data.utils

import java.io.IOException

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)
