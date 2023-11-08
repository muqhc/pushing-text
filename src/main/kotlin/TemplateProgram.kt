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

            val iTransitionY = (mousePosition.y - (height/2.0)) / max(0.1,mousePosition.x)
            val iTransitionX = (mousePosition.x / width) / 2 + 0.75
            
            drawer.clear(myBackgroundColor)

            alphabet15dotWriter(drawer) {
                val a15dStyle = A15DWriteStyle().apply {
                    color = myPrimaryColor
                    charGap = 0.6
                    lineGap = 1.5
                    scale = defaultStyle.scale * scalePreset
                    weight = defaultStyle.weight * scalePreset
                    isCursorXTransiting = true
                    isCursorYTransiting = true
                    linearTransition = Matrix22(
                        iTransitionX, 0.0,
                        iTransitionY, 1.0
                    )
                }

                newWriting(a15dStyle) {
                    val startCursorY = middle.y - ((textHeight / 2.0 + (style.lineGap)) * primaryTexts.count())
                    move(20.0*scalePreset,startCursorY)
                    primaryTexts.forEach {
                        writeLine(it)
                    }
                }
            }

        }
    }
}