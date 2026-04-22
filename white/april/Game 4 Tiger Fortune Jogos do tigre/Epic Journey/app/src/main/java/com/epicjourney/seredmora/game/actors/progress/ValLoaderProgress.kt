package com.epicjourney.seredmora.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.epicjourney.seredmora.game.actors.masks.Mask
import com.epicjourney.seredmora.game.utils.advanced.AdvancedGroup
import com.epicjourney.seredmora.game.utils.advanced.AdvancedScreen
import com.epicjourney.seredmora.game.utils.runGDX

class ValLoaderProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 930f

    private val backgroundImg = Image(screen.game.loadingAssets.background)
    private val progressImg   = Image(screen.game.loadingAssets.progress)
    private val loadingImg    = Image(screen.game.loadingAssets.text)
    private val mask          = Mask(screen, screen.game.loadingAssets.vmaskav, alphaWidth = 700)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    private val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addAndFillActor(backgroundImg)
        addMask()
        addLoading()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX { progressImg.x = percent * onePercentX - LENGTH }
            }
        }

//        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(12f, 11f, 930f, 87f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addActor(progressImg)
        progressImg.setBounds(-LENGTH, 0f, LENGTH, 87f)
    }

    private fun AdvancedGroup.addLoading() {
        addActor(loadingImg)
        loadingImg.setBounds(363f, 33f, 230f, 43f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

//    private fun inputListener() = object : InputListener() {
//        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
//            touchDragged(event, x, y, pointer)
//            return true
//        }
//
//        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
//            progressPercentFlow.value = when {
//                x <= 0 -> 0f
//                x >= LENGTH -> 100f
//                else -> x / onePercentX
//            }
//        }
//    }

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}