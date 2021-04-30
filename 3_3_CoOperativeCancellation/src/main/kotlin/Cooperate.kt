import kotlinx.coroutines.experimental.*

fun main(args: Array<String>) = runBlocking() {

    //throw CancellationException
    //v1()

    //return launch
    //v2()

    //return repeat
    //v3()

    //loop
    v4()
}

private suspend fun v1() {
    val job = launch {
        repeat(1000) {
            if (!isActive) throw CancellationException()
            print(".")
            Thread.sleep(1)
        }
    }

    delay(100)
    job.cancelAndJoin()
    println("done")
}

private suspend fun v2() {
    val job = launch {
        repeat(1000) {
            if (!isActive) return@launch
            print(".")
            Thread.sleep(1)
        }
    }

    delay(100)
    job.cancelAndJoin()
    println("done")
}

private suspend fun v3() {
    val job = launch {
        repeat(1000) {
            if (!isActive) return@repeat
            print(".")
            Thread.sleep(1)
        }
    }

    delay(100)
    job.cancelAndJoin()
    println("done")
}

private suspend fun v4() {
    val job = launch {
        repeat(1000) {
            print(".")
            if (!isActive) return@repeat
            Thread.sleep(1)
        }
    }

    delay(100)
    job.cancelAndJoin()
    println("done")
}

