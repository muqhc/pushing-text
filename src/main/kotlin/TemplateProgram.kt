import org.openrndr.*
import org.openrndr.color.*
import org.openrndr.draw.*
import org.openrndr.events.*
import org.openrndr.math.*
import org.openrndr.shape.*
import org.openrndr.extra.noclear.NoClear
import kotlin.math.*

suspend fun main() = applicationAsync {
    configure {
        title = "Pushing Text"
    }
    program {
        var lastInterected = 0.0
        var isInteracted = false

        val urlParamMap = getUrlParamMap(js("window.location.search"))

        val rawPrimaryText = urlParamMap["primary"] ?: "here|welcome|scroll|down"
        val scalePreset: Double = urlParamMap["scale"]?.toDoubleOrNull() ?: 1.0
        
        val primaryTexts = rawPrimaryText.split('|')

        mouse.moved.listen {
            isInteracted = true
        }

        extend(NoClear())

        extend {
            if (isInteracted) {
                lastInterected = seconds
                isInteracted = false
            }

            if ((seconds - lastInterected) > 0.5) return@extend

            val mousePosition = mouse.position

            val myBackgroundColor = ColorRGBa.PINK
            val myPrimaryColor = ColorRGBa.GRAY

            val middle = Vector2(0.0,height/2.0)

            val iTransitionY = tanh((mousePosition.y - (height/2.0)) / max(0.1,mousePosition.x))
            val iTransitionX = (mousePosition.x / width) / 1.5 + 0.66
            
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
