package com.roadrunnerhen.hen.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.roadrunnerhen.hen.game.actors.shader.AMaskGroup
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedGroup
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedScreen
import com.roadrunnerhen.hen.game.utils.font.FontParameter
import com.roadrunnerhen.hen.game.utils.gdxGame
import com.roadrunnerhen.hen.game.utils.runGDX
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AProgress(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "%")

    private val LENGTH = 407f

    private val backgroundImage = Image(screen.drawerUtil.getRegion(Color.WHITE))
    private val progressImage   = Image(gdxGame.assetsAll.prog)
    private val mask            = AMaskGroup(screen, gdxGame.assetsLoader.MASK)
    private val pipImage        = Image(gdxGame.assetsAll.pip)

    private val onePercentX = LENGTH / 100f

    // 0 .. 100 %
    val progressPercentFlow = MutableStateFlow(0f)


    override fun addActorsOnGroup() {
        //addBackground()
        addMask()
        addPip()

        coroutine?.launch {
            progressPercentFlow.collect { percent ->
                runGDX {
                    progressImage.x = (percent * onePercentX) - LENGTH
                    pipImage.x = progressImage.x + LENGTH - 21f
                }
            }
        }

        addListener(inputListener())
    }

    // ---------------------------------------------------
    // Add Actors
    // ---------------------------------------------------

    private fun AdvancedGroup.addBackground() {
        addAndFillActor(backgroundImage)
    }

    private fun AdvancedGroup.addMask() {
        addAndFillActor(mask)
        mask.addProgress()
    }

    private fun AdvancedGroup.addProgress() {
        addAndFillActor(progressImage)
    }

    private fun AdvancedGroup.addPip() {
        addActor(pipImage)
        pipImage.y = -9f
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

            event?.stop()
        }
    }

}