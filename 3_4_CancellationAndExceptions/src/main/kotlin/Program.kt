import kotlinx.coroutines.experimental.*


fun main(args: Array<String>) = runBlocking {

    // 1. Use a standard try..finally
    finallyStandard()

    // 2. Show noncancellable
    nonCancellable()

    // 3. Custom Exception
    customException()

    // 4. otherException
    otherException()
}

private suspend fun finallyStandard() {

    println("finallyStandard()")

    val job = launch {
        try {
            repeat(1000) {
                yield()

                print(".")
                Thread.sleep(1)
            }
        } catch (ex: CancellationException) {
            println()
            println("Cancelled")
        } finally {
            println()
            println("In finally")
        }
    }

    delay(100)
    job.cancelAndJoin()
}

private suspend fun nonCancellable() {

    println("nonCancellable()")

    val job = launch {
        try {
            repeat(1000) {
                yield()

                print(".")
                Thread.sleep(1)
            }
        } finally {
            run(NonCancellable) {
                delay(1000)
                println()
                println("In finally")
            }
        }
    }

    delay(100)
    job.cancelAndJoin()
}

private suspend fun customException() {

    println("customException()")

    val job = launch {
        try {
            repeat(1000) {
                yield()

                print(".")
                Thread.sleep(1)
            }
        } catch (ex: CancellationException) {
            println()
            println("Cancelled: ${ex.message}")
        } finally {
            run(NonCancellable) {
                delay(1000)
                println()
                println("In finally")
            }
        }
    }

    delay(100)
    job.cancel(CancellationException("Too many jobs"))
    job.join()
}

private suspend fun otherException() {

    println("otherException()")

    val job = launch {
        try {
            repeat(1000) {
                yield()

                print(".")
                Thread.sleep(1)
            }
        } catch (ex: CancellationException) {
            println()
            println("Cancelled: ${ex.message}")
        } finally {
            run(NonCancellable) {
                delay(1000)
                println()
                println("In finally")
            }
        }
    }

    delay(100)
    job.cancel(Exception())
    job.join()
}


