package com.goalblast.onewingame.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.goalblast.onewingame.game.screens.LoaderScreen
import com.goalblast.onewingame.game.utils.Acts
import com.goalblast.onewingame.game.utils.AlignH
import com.goalblast.onewingame.game.utils.AlignV
import com.goalblast.onewingame.game.utils.actor.addActorAligned
import com.goalblast.onewingame.game.utils.advanced.AdvancedGroup
import com.goalblast.onewingame.game.utils.gdxGame

class ALoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLoading  = Image(gdxGame.assetsLoader.loader)

    override fun addActorsOnGroup() {
        addImgLoading()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoading() {
        imgLoading.setSize(212f, 208f)
        addActorAligned(imgLoading, AlignH.CENTER, AlignV.CENTER)
        imgLoading.setOrigin(Align.center)

        // Rotate
        imgLoading.addAction(Acts.forever(Acts.rotateBy(-360f, 0.8f)))
        // Scale
        imgLoading.addAction(
            Acts.forever(
                Acts.sequence(
            Acts.scaleTo(0.97f, 0.97f, 0.25f),
            Acts.scaleTo(1f, 1f, 0.25f),
        )))
    }

}