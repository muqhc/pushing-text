import org.openrndr.*
import org.openrndr.color.*
import org.openrndr.draw.*
import org.openrndr.events.*
import org.openrndr.math.*
import org.openrndr.shape.*
import kotlin.math.*

suspend fun main() = applicationAsync {
    configure {
        title = "Pushing Text"
    }
    program {

        var mousePosition: Vector2 = Vector2(0.0,0.0)

        val urlParamMap = getUrlParamMap(js("window.location.search"))

        val rawPrimaryText = urlParamMap["primary"] ?: "here|welcome|scroll|down"
        val scalePreset: Double = urlParamMap["scale"]?.toDoubleOrNull() ?: 1.0
        
        val primaryTexts = rawPrimaryText.split('|')

        mouse.moved.listen {
            mousePosition = it.position
        }

        mouse.dragged.listen {
            mousePosition = it.position
        }

        extend {
            val myBackgroundColor = ColorRGBa.PINK
            val myPrimaryColor = ColorRGBa.GRAY

            val middle = Vector2(0.0,height/2.0)

            val jTransition = (mousePosition.y - (height/2.0)) / min(0.1,mousePosition.x)

            println(jTransition)

            
            drawer.clear(myBackgroundColor)

            alphabet15dotWriter(drawer) {
                val a15dStyle = A15DWriteStyle().apply {
                    color = myPrimaryColor
                    charGap = 0.6
                    scale = defaultStyle.scale * scalePreset
                    weight = defaultStyle.weight * scalePreset
                    linearTransition = Matrix22(
                        1.0,         0.0,
                        jTransition, 1.0
                    )
                }

                newWriting(a15dStyle) {
                    val startCursorY = middle.y - (textHeight * primaryTexts.count() / 2.0)
                    move(20.0*scalePreset,startCursorY)
                    primaryTexts.forEach {
                        writeLine(it)
                    }
                }
            }

        }
    }
}