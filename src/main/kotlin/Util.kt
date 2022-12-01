import java.io.File
import kotlin.system.measureNanoTime

fun readInput(day: Int) = File(ClassLoader.getSystemResource("input/$day").file).readText()

    fun <T> verifyAndTime(taskName: String, expectedResult: T, functionUnderTest: () -> T) {
        println("\n-------------$taskName-------------")

        // Verify result
        functionUnderTest().let { result ->
            if(result == expectedResult)
                println("SUCCESS! The correct answer of $expectedResult was returned")
            else
                throw Exception("BOOOO! You really biffed this one mate. Expected $expectedResult but you got $result")
        }

        // warm up for benchmarking
        repeat(5) {
            ((1..1000).map {
                measureNanoTime {
                    functionUnderTest()
                }
            }.average() / 1000000)
        }
        // benchmarking
        ((1..100).map {
            measureNanoTime {
                functionUnderTest()
            }
        }.average() / 1000000).let { averageDuration ->
            println("Average duration of the method was ${averageDuration}ms")
        }
    }