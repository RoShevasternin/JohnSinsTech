package com.crimson.pathreone.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.crimson.pathreone.game.utils.GameColor
import com.crimson.pathreone.game.utils.advanced.AdvancedGroup
import com.crimson.pathreone.game.utils.advanced.AdvancedScreen
import com.crimson.pathreone.game.utils.font.FontParameter
import com.crimson.pathreone.game.utils.runGDX
import com.crimson.pathreone.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ATimer(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS)
    private val font96        = screen.fontGeneratorCodaCaption.generateFont(fontParameter.setSize(96))

    private val timeLbl = Label("60", Label.LabelStyle(font96, GameColor.black))

    var isPause = false

    override fun addActorsOnGroup() {
        addTimer()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addTimer() {
        addAndFillActor(timeLbl)
        timeLbl.setAlignment(Align.center)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun goTime(timeOut: () -> Unit) {
        coroutine?.launch {
            var timer  = 60
            var second: Int

            while (isActive && timer > 0) {
                delay(1000)
                if (isPause.not()) {
                    timer -= 1
                    second = timer % 60

                    runGDX {
                        timeLbl.setText(second)
                        if (timer <= 10) timeLbl.style.fontColor = GameColor.red
                    }
                }
            }

            if (timer <= 0) runGDX { timeOut() }
        }
    }

}