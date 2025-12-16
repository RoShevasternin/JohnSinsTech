package com.chekrun.roadrunicen.game.actors.main

import com.chekrun.roadrunicen.game.screens.LoaderScreen
import com.chekrun.roadrunicen.game.utils.Acts
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedGroup
import com.chekrun.roadrunicen.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.chekrun.roadrunicen.game.actors.AProgress
import com.chekrun.roadrunicen.game.utils.actor.disable

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    private val imgLoaderPan = Image(gdxGame.assetsLoader.load_pan)
    private val progress     = AProgress(screen)
    private val imgLoading   = Image(gdxGame.assetsLoader.loading)

    override fun addActorsOnGroup() {
        addImgLoaderPan()
        addProgress()
        addImgLoading()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgLoaderPan() {
        addActor(imgLoaderPan)
        imgLoaderPan.setBounds(175f, 178f, 731f, 137f)

        // Rotate
        //imgLoading.addAction(Acts.forever(Acts.rotateBy(-360f, 1f)))

        // Scale
        //imgLoader.addAction(Acts.forever(Acts.sequence(
        //    Acts.scaleTo(1.134f, 1.134f, 0.36f),
        //    Acts.scaleTo(1f, 1f, 0.36f),
        //)))
    }

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(209f, 214f, 663f, 74f)
    }

    private fun addImgLoading() {
        addActor(imgLoading)
        imgLoading.setBounds(425f, 208f, 230f, 78f)

        imgLoading.disable()
        imgLoading.setOrigin(Align.center or Align.left)

        // Scale
        imgLoading.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.95f, 0.95f, 0.3f),
            Acts.scaleTo(1f, 1f, 0.3f),
        )))
    }

    // Logic --------------------------------------------------------------------------

    fun updatePercent(percent: Int) {
        progress.progressPercentFlow.value = percent.toFloat()
    }

}