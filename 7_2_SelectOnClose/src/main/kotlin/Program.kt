import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.selects.select

fun producer1() = produce {
    send("from producer 1")
}

fun producer2() = produce {
    send("from producer 2")
}

suspend fun selector(message1: ReceiveChannel<String>, message2: ReceiveChannel<String>): String =
    select<String> {
        message2.onReceiveOrNull { value ->
            value ?: "Channel 2 is closed"
        }
        message1.onReceiveOrNull { value ->
            value ?: "Channel 1 is closed"
        }
    }


fun main(args: Array<String>) = runBlocking<Unit> {
    val m1 = producer1()
    val m2 = producer2()

    repeat(15) {
        println(selector(m1, m2))
    }
}
