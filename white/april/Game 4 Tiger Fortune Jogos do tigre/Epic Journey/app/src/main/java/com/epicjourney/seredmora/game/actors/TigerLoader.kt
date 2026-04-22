package com.epicjourney.seredmora.game.actors

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.epicjourney.seredmora.game.utils.advanced.AdvancedGroup
import com.epicjourney.seredmora.game.utils.advanced.AdvancedScreen

class TigerLoader(override val screen: AdvancedScreen): AdvancedGroup() {

    private val assets = screen.game.loadingAssets

    private val tigerImg  = Image(assets.tigrula)
    private val shadowImg = Image(assets.shadow)

    override fun addActorsOnGroup() {
        addTigerImg()
        addShadowImg()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addTigerImg() {
        addActor(tigerImg)
        tigerImg.setBounds(0f, 14f, 890f, 1288f)
    }

    private fun addShadowImg() {
        addActor(shadowImg)
        shadowImg.setBounds(33f, 0f, 778f, 78f)
        shadowImg.setOrigin(Align.center)
    }

    // ---------------------------------------------------
    // Anim
    // ---------------------------------------------------

    fun startAnim() {
        tigerImg.addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, 80f, 0.35f, Interpolation.sine),
            Actions.moveBy(0f, -80f, 0.35f, Interpolation.sine),
        )))

        val scale = 0.4f
        shadowImg.addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-scale, -scale, 0.35f, Interpolation.sine),
            Actions.scaleBy(scale, scale, 0.35f, Interpolation.sine),
        )))
    }

    fun endAnim() {
        listOf(tigerImg, shadowImg).onEach { it.clearActions() }

        tigerImg.addAction(Actions.moveTo(tigerImg.x, 1920f, 0.35f, Interpolation.pow2))
        shadowImg.addAction(Actions.fadeOut(0.35f))
    }

}