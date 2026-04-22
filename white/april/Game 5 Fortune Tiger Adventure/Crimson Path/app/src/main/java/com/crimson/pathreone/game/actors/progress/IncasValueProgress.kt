package com.crimson.pathreone.game.actors.progress

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.crimson.pathreone.game.actors.masks.Mask
import com.crimson.pathreone.game.utils.advanced.AdvancedGroup
import com.crimson.pathreone.game.utils.advanced.AdvancedScreen
import com.crimson.pathreone.game.utils.runGDX

class IncasValueProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val LENGTH = 323f

    private val progressImg = Image(screen.game.allAssets.scr_progress)
    private val scrollerImg = Image(screen.game.allAssets.scroller)
    private val mask        = Mask(screen, screen.game.allAssets.shoto_progress, alphaWidth = 1053)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        addMask()
        addScroller()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    scrollerImg.x = percent * onePercentX
                    progressImg.x = scrollerImg.x - LENGTH
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addMask() {
        addActor(mask)
        mask.setBounds(0f, 11f, 369f, 33f)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addActor(progressImg)
        progressImg.setBounds(-369f, 0f, 369f, 33f)
    }

    private fun AdvancedGroup.addScroller() {
        addActor(scrollerImg)
        scrollerImg.setBounds(0f, 0f, 52f, 52f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun inputListener() = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            progressPercentFlow.value = when {
                x <= 0 -> 0f
                x >= LENGTH -> 100f
                else -> x / onePercentX
            }
        }
    }

    fun setProgressPercent(percent: Float) {
        progressPercentFlow.value = percent
    }


}