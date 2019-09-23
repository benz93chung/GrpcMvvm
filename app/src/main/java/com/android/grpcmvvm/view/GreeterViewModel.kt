package com.android.grpcmvvm.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.grpcmvvm.data.GreeterRepository
import kotlinx.coroutines.launch

class GreeterViewModel constructor(private val greeterRepository: GreeterRepository) : ViewModel() {

    val greeting: LiveData<String>
        get() = greeterRepository.greeting

    fun sayHello(message: String) {
        viewModelScope.launch {
            greeterRepository.sayHello(message)
        }
    }
}