import org.openrndr.*
import org.openrndr.color.*
import org.openrndr.draw.*
import org.openrndr.events.*
import org.openrndr.math.*
import org.openrndr.shape.*
import kotlin.math.*

suspend fun main() = applicationAsync {
    configure {
        title = "Your Title"
    }
    program {

        val urlParamMap = getUrlParamMap(js("window.location.search"))

        extend {
            val a = rgb("#ff0000")
            drawer.clear(a)
            drawer.fill = ColorRGBa.WHITE
            drawer.circle(width / 2.0, height / 2.0, 100.0 + cos(seconds) * 40.0)
        }
    }
}