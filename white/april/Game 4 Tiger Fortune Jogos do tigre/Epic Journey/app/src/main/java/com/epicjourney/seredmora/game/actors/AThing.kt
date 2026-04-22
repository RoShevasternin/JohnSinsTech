package com.epicjourney.seredmora.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.epicjourney.seredmora.game.utils.actor.disable
import com.epicjourney.seredmora.game.utils.advanced.AdvancedGroup
import com.epicjourney.seredmora.game.utils.advanced.AdvancedScreen

class AThing(override val screen: AdvancedScreen, region: TextureRegion): AdvancedGroup() {

    private val img = Image(region)

    override fun addActorsOnGroup() {
        addAndFillActor(img)
        img.disable()
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun selected() {
        clearActions()
        val scale = 0.4f
        val time  = 0.4f
        addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-scale, -scale, time, Interpolation.smooth),
            Actions.scaleBy(scale, scale, time, Interpolation.smooth),
        )))
    }

    fun unselected() {
        clearActions()
        addAction(Actions.scaleTo(1f, 1f, 0.3f, Interpolation.smooth2))
    }

}