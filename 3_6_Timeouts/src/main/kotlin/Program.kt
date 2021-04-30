import kotlinx.coroutines.experimental.*


fun main(args: Array<String>) = runBlocking() {

    // 1. withTimeout throws an exception
    //withTimeout()

    // 2. withTimeout throws an exception even if the exception is handled inside the block
    // `withTimeout throws an exception`()

    // 3. now the exception is handled
    // `now the exception is handled`()

    // 4. with timeout or null
    `with timeout or null`()

}

private suspend fun withTimeout() {
    val job = withTimeout(100) {

        repeat(1000) {
            yield()

            print(".")
            Thread.sleep(1)
        }
    }

    delay(100)
}

private suspend fun `withTimeout throws an exception`() {

    val job = withTimeout(100) {

        try {
            repeat(1000) {
                yield()

                print(".")
                Thread.sleep(1)
            }
        } catch (ex: TimeoutCancellationException) {
            println("Exception")
        }
    }

    delay(100)
}

private suspend fun `now the exception is handled`() {
    try {
        val job = withTimeout(100) {

            repeat(1000) {
                yield()

                print(".")
                Thread.sleep(1)
            }
        }
    } catch (ex: TimeoutCancellationException) {
        println()
        println("Exception")
    }

    delay(100)
}

private suspend fun `with timeout or null`() {
    val job = withTimeoutOrNull(100) {

        repeat(1000) {
            yield()

            print(".")
            Thread.sleep(1)
        }

    }

    delay(100)

    println()
    if (job != null) {
        println("job completed")
    } else {
        println("timeout")
    }
}











