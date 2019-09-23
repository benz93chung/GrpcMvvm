package com.android.grpcmvvm.di

import com.android.grpcmvvm.data.GreeterRemoteDataSource
import com.android.grpcmvvm.data.GreeterRepository
import com.android.grpcmvvm.grpc.GrpcService
import com.android.grpcmvvm.view.GreeterViewModelFactory

object Injector {
    // Provide GrpcService
    private fun provideGrpcService() = GrpcService(
        "192.168.1.107",
        50051
    )

    // Provide DataSource
    private fun provideGreeterRemoteDataSource(grpcService: GrpcService) = GreeterRemoteDataSource(grpcService)

    // Provide Repository
    private fun provideGreeterRepository(greeterRemoteDataSource: GreeterRemoteDataSource) = GreeterRepository(greeterRemoteDataSource)

    // Provide GreeterViewModel factory
    fun provideGreeterViewModelFactory() = GreeterViewModelFactory(
        provideGreeterRepository(
            provideGreeterRemoteDataSource(
                provideGrpcService()
            )
        )
    )
}