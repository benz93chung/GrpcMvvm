# GrpcMvvm

# Reactive gRPC Android App
#### An explorative Android project of gRPC + MVVM + LiveData
![Alt Text](https://media.giphy.com/media/ju0GeQ0vBiGZfayiR1/giphy.gif)

### Setup and usage
- Clone this project
- Open project with Android Studio and build it
    - Based on helloworld.proto...
    - ...protoc will generate gRPC code to Android project in build folder 
- Follow this guide to set up and run server (please take note of IP and port it is running on)
    <br/>
    https://grpc.io/docs/quickstart/java/
- Once server is running with said IP and port, change accordingly at Injector.kt
    ```kotlin
    // Provide GrpcService
    private fun provideGrpcService() = GrpcService(
        "192.168.1.107", // <- insert your IP here
        50051 // <- insert your port here
    )
    ```
- Build project, and run at emulator

### Bonus
#### Unit test included!
##### Find out more at .../app/src/test
```kotlin
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
```

### Additional notes on unit test strategy
For the written unit test, it is written as such to explore on how gRPC stuff (protoc generated code) can be mocked and tested along with Android stuff (ViewModel).
<br/>
<br/>
If you have seen it, a channel is set up based on a fake server implementation and gets injected to the creation of the ViewModel like as if the said channel provides a passage to the "server".
<br/>
<br/>
You would not need to write such a test down to that level of mocking gRPC stuff or so, but just write tests to test against just the Android stuff instead (in this case, you would mock what the repository returns to the ViewModel), especially if your backend team already wrote unit tests that tests on client interaction of gRPC code.

### Credits
This project is inspired by prongbang's Android + gRPC repository
<br/>
https://github.com/prongbang/android-grpc
<br/>
<br/>
If you are going to star this repository, please also leave a star to prongbang's. :)
