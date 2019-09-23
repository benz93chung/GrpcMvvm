package com.android.grpcmvvm.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.grpcmvvm.data.GreeterRepository

class GreeterViewModelFactory(private val greeterRepository: GreeterRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GreeterViewModel(greeterRepository) as T
    }
}