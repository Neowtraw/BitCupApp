package com.codingub.bitcupapp.data.utils

import com.codingub.bitcupapp.R
import com.codingub.bitcupapp.utils.Resource
import java.io.IOException

class NetworkUnavailableException(message: String = Resource.string(R.string.network_error)) : IOException(message)
