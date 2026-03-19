package com.olympusgoldrush.ulympus.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.olympusgoldrush.ulympus.game.screens.LoaderScreen
import com.olympusgoldrush.ulympus.game.utils.Acts
import com.olympusgoldrush.ulympus.game.utils.AlignH
import com.olympusgoldrush.ulympus.game.utils.AlignV
import com.olympusgoldrush.ulympus.game.utils.actor.addActorAligned
import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedGroup
import com.olympusgoldrush.ulympus.game.utils.gdxGame

class ALoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLoading  = Image(gdxGame.assetsLoader.LOADER)

    override fun addActorsOnGroup() {
        addImgLoading()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoading() {
        imgLoading.setSize(311f, 301f)
        addActorAligned(imgLoading, AlignH.CENTER, AlignV.CENTER)
        imgLoading.setOrigin(Align.center)

        // Rotate
        imgLoading.addAction(Acts.forever(Acts.rotateBy(-360f, 0.75f)))
        // Scale
        imgLoading.addAction(
            Acts.forever(
                Acts.sequence(
            Acts.scaleTo(0.95f, 0.95f, 0.25f),
            Acts.scaleTo(1f, 1f, 0.25f),
        )))
    }

}