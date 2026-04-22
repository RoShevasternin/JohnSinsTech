package com.crimson.pathreone.game.utils

import com.badlogic.gdx.math.Vector2

object Layout {

    object Splash {
        val incasLoadingText       = LayoutData(293f,1436f,453f,129f)
        val incasLoadingBackground = LayoutData(66f,1269f,947f,143f)
        val incasLoadingTiger      = LayoutData(177f,123f,768f,929f)
        val incasLoadingProgress   = LayoutData(98f,1296f,886f,87f)
    }

    data class LayoutData(
        var x: Float = 0f,
        var y: Float = 0f,
        var w: Float = 0f,
        var h: Float = 0f,
        // horizontal space
        val hs: Float = 0f,
        // vertical space
        val vs: Float = 0f,
    ) {

        fun position() = Vector2(x, y)
        fun size() = Vector2(w, h)

    }

}












