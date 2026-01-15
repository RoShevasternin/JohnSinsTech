package com.roadrunnerhen.hen.game.actors.main

import com.roadrunnerhen.hen.game.screens.LoaderScreen
import com.roadrunnerhen.hen.game.utils.Acts
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedGroup
import com.roadrunnerhen.hen.game.utils.gdxGame
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.roadrunnerhen.hen.game.actors.AProgress
import com.roadrunnerhen.hen.game.utils.actor.disable
import com.roadrunnerhen.hen.game.utils.actor.setOrigin

class AMainLoader(
    override val screen: LoaderScreen,
): AdvancedGroup() {

    //private val imgLoaderPan = Image(gdxGame.assetsLoader.load_pan)
    //private val progress     = AProgress(screen)
    private val imgLoading   = Image(gdxGame.assetsLoader.loader)

    override fun addActorsOnGroup() {
        //addImgLoaderPan()
        //addProgress()
        addImgLoading()
    }

    // Actors ------------------------------------------------------------------------

//    private fun addImgLoaderPan() {
//        addActor(imgLoaderPan)
//        imgLoaderPan.setBounds(175f, 178f, 731f, 137f)
//
//        // Rotate
//        //imgLoading.addAction(Acts.forever(Acts.rotateBy(-360f, 1f)))
//
//        // Scale
//        //imgLoader.addAction(Acts.forever(Acts.sequence(
//        //    Acts.scaleTo(1.134f, 1.134f, 0.36f),
//        //    Acts.scaleTo(1f, 1f, 0.36f),
//        //)))
//    }
//
//    private fun addProgress() {
//        addActor(progress)
//        progress.setBounds(209f, 214f, 663f, 74f)
//    }

    private fun addImgLoading() {
        addActor(imgLoading)
        imgLoading.setBounds(437f, 821f, 206f, 278f)

        imgLoading.disable()
        imgLoading.setOrigin(Align.center)

        // Rotate
        imgLoading.addAction(Acts.forever(Acts.rotateBy(-360f, 0.8f)))
        // Scale
        imgLoading.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.97f, 0.97f, 0.25f),
            Acts.scaleTo(1f, 1f, 0.25f),
        )))
    }

    // Logic --------------------------------------------------------------------------

//    fun updatePercent(percent: Int) {
//        progress.progressPercentFlow.value = percent.toFloat()
//    }

}