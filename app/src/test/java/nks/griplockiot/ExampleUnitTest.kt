package nks.griplockiot

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, addNumbers(6,2))
    }
}

fun addNumbers(number: Int, number2: Int) : Int {
    return number + number2
}