import org.openrndr.*
import org.openrndr.color.*
import org.openrndr.draw.*
import org.openrndr.events.*
import org.openrndr.math.*
import org.openrndr.shape.*
import kotlin.math.*


class Matrix22(
    val c0r0: Double = 0.0,
    val c1r0: Double = 0.0,
    val c0r1: Double = 0.0,
    val c1r1: Double = 0.0
) : LinearType<Matrix22> {

    companion object {
        val IDENTITY = Matrix22(1.0,0.0,0.0,1.0)
    }

    val determinant: Double get() = (c0r0*c1r1)-(c1r0*c0r1)

    override operator fun plus(right: Matrix22) = Matrix22(
        c0r0 + right.c0r0, c1r0 + right.c1r0,
        c0r1 + right.c0r1, c1r1 + right.c1r1
    )

    override operator fun minus(right: Matrix22) = Matrix22(
        c0r0 - right.c0r0, c1r0 - right.c1r0,
        c0r1 - right.c0r1, c1r1 - right.c1r1
    )


    operator fun times(v: Vector2) = Vector2(
        v.x * c0r0 + v.y * c1r0,
        v.x * c0r1 + v.y * c1r1
    )

    override operator fun times(scale: Double) = Matrix22(
        c0r0 * scale, c1r0 * scale,
        c0r1 * scale, c1r1 * scale
    )

    override operator fun div(scale: Double) = Matrix22(
        c0r0 / scale, c1r0 / scale,
        c0r1 / scale, c1r1 / scale
    )

    operator fun times(mat: Matrix22) = Matrix22(
        this.c0r0 * mat.c0r0 + this.c1r0 * mat.c0r1,
        this.c0r0 * mat.c1r0 + this.c1r0 * mat.c1r1,

        this.c0r1 * mat.c0r0 + this.c1r1 * mat.c0r1,
        this.c0r1 * mat.c1r0 + this.c1r1 * mat.c1r1
    )
}


fun separateOneDigit(num: Int): Pair<Int,Int> = (num / 10) to (num % 10)

fun separateDigit(num: Int): List<Int> = sequence {
    var stk = num
    while (stk > 0) {
        val (nstk,y) = separateOneDigit(stk)
        yield(y)
        stk = nstk
    }
}.toList().reversed()

fun getUrlParamMap(url: String) =
    url.split('?').getOrNull(1)?.split('&')?.associate {
        val parts = it.split('=')
        val name = parts[0]
        val value = parts[1]
        name to value
    } ?: mapOf()





