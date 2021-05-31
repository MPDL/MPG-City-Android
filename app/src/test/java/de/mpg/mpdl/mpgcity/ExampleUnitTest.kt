package de.mpg.mpdl.mpgcity

import de.mpg.mpdl.mpgcity.mvvm.ui.activity.MainActivity
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println("videoId: "+MainActivity.getVideoIdByUrl("https://www.youtube.com/watch?v=X2yDs5FHDok&feature=youtu.be"))
    }
}