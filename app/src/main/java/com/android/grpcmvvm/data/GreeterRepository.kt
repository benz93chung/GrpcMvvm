package com.android.grpcmvvm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GreeterRepository constructor(private val greeterRemoteDataSource: GreeterRemoteDataSource) {
    private val response = MutableLiveData<String>()

    val greeting: LiveData<String>
        get() {
            if (response.value == null) {
                response.value = "Hello world!"
            }

            return response
        }

    suspend fun sayHello(message: String) {
        response.value = greeterRemoteDataSource.sayHello(message)
    }
}