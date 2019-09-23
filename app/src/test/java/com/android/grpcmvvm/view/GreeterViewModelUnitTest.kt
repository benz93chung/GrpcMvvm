package com.android.grpcmvvm.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.android.grpcmvvm.data.GreeterRemoteDataSource
import com.android.grpcmvvm.data.GreeterRepository
import com.android.grpcmvvm.grpc.GrpcService
import com.android.grpcmvvm.testrules.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.grpc.ManagedChannel
import io.grpc.examples.helloworld.GreeterGrpc
import io.grpc.examples.helloworld.HelloReply
import io.grpc.examples.helloworld.HelloRequest
import io.grpc.inprocess.InProcessChannelBuilder
import io.grpc.inprocess.InProcessServerBuilder
import io.grpc.stub.StreamObserver
import io.grpc.testing.GrpcCleanupRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.AdditionalAnswers.delegatesTo
import org.mockito.internal.matchers.Any
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class GreeterViewModelUnitTest {
    // region Test rules
    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val grpcCleanupRule = GrpcCleanupRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    // endregion

    // region Mocks
    // Kotlin classes are final. Won't play nice with Mockito, so use MockK instead
    @MockK
    private lateinit var mockGrpcService: GrpcService

    private val mockServiceImpl = Mockito.mock(GreeterGrpc.GreeterImplBase::class.java, delegatesTo<Any>(
        object: GreeterGrpc.GreeterImplBase() {

            override fun sayHello(request: HelloRequest, responseObserver: StreamObserver<HelloReply>) {
                val reply = HelloReply.newBuilder().setMessage("Hello " + request.name).build()
                responseObserver.onNext(reply)
                responseObserver.onCompleted()
            }}))
    // endregion

    // region Private properties
    private lateinit var managedChannel: ManagedChannel
    private lateinit var greeterViewModel: GreeterViewModel
    private lateinit var greeterViewModelFactory: GreeterViewModelFactory
    // endregion

    // region Unit tests
    @Before
    @Throws(Exception::class)
    fun setUp() {
       // MockitoAnnotations.initMocks(this)
        MockKAnnotations.init(this)

        val serverName = InProcessServerBuilder.generateName()

        grpcCleanupRule.register(InProcessServerBuilder
            .forName(serverName)
            .directExecutor()
            .addService(mockServiceImpl)
            .build()
            .start())

        managedChannel = grpcCleanupRule.register(InProcessChannelBuilder
            .forName(serverName)
            .directExecutor()
            .build())

        every { mockGrpcService.createManagedChannel() } returns managedChannel

        greeterViewModelFactory = GreeterViewModelFactory(GreeterRepository(GreeterRemoteDataSource(mockGrpcService)))
        greeterViewModel = greeterViewModelFactory.create(GreeterViewModel::class.java)
    }

    @Test
    fun getGreeting_ReturnsHelloWorld() {
        // Given that I expect Hello World as a getThatGreeting if I get it right away
        val expectedGreeting = "Hello world!"

        // When I get getThatGreeting
        val actualGreeting = greeterViewModel.greeting.getTestValue()

        // Then the getThatGreeting is Hello world!
        assertThat(actualGreeting).matches(expectedGreeting)
    }

    @Test
    fun sayHello_Mars_ReturnsHelloMars() {
        // Given that I want to say hello to Mars
        val nameToGreetTo = "Mars"
        val expectedGreeting = "Hello Mars"

        greeterViewModel.sayHello(nameToGreetTo)

        val actualGreeting = greeterViewModel.greeting.getTestValue()

        // Then the getThatGreeting is Hello Mars
        assertThat(actualGreeting).matches(expectedGreeting)
    }
    // endregion

    // region Extensions
    @Throws(InterruptedException::class)
    fun <T> LiveData<T>.getTestValue(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val observer = Observer<T> {
            value = it
            latch.countDown()
        }
        latch.await(2, TimeUnit.SECONDS)
        observeForever(observer)
        removeObserver(observer)
        return value
    }
    // endregion
}

