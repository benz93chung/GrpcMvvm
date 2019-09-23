package com.android.grpcmvvm.data

import com.android.grpcmvvm.grpc.GrpcService
import io.grpc.ManagedChannel
import io.grpc.StatusRuntimeException
import io.grpc.examples.helloworld.GreeterGrpc
import io.grpc.examples.helloworld.HelloRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GreeterRemoteDataSource constructor(private val grpcService: GrpcService) {
    private lateinit var channel: ManagedChannel

    suspend fun sayHello(message: String): String =
        withContext(Dispatchers.IO) {
            channel = grpcService.createManagedChannel()

            val stub = GreeterGrpc.newBlockingStub(channel)
            val request = HelloRequest.newBuilder().setName(message).build()

            return@withContext try {
                stub.sayHello(request).message
            } catch (e: StatusRuntimeException) {
                e.printStackTrace()
                e.status.code.toString() + "\n" + e.status.cause.toString()
            } finally {
                channel.shutdown()
            }
        }
}