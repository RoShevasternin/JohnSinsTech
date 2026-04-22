package com.epicjourney.seredmora.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.epicjourney.seredmora.game.utils.GameColor
import com.epicjourney.seredmora.game.utils.advanced.AdvancedGroup
import com.epicjourney.seredmora.game.utils.advanced.AdvancedScreen
import com.epicjourney.seredmora.game.utils.font.FontParameter
import com.epicjourney.seredmora.game.utils.runGDX
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ATimerGroup(override val screen: AdvancedScreen): AdvancedGroup() {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars+":")
    private val font64        = screen.fontGeneratorBungeeRegular.generateFont(fontParameter.setSize(64))

    private val panel   = Image(screen.game.allAssets.val_timer_background)
    private val timeLbl = Label("00:00", Label.LabelStyle(font64, GameColor.polublack))

    var isPause = false

    override fun addActorsOnGroup() {
        addAndFillActor(panel)
        addTimer()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addTimer() {
        addActor(timeLbl)
        timeLbl.setBounds(29f, 20f, 209f, 77f)
        timeLbl.setAlignment(Align.center)

    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun startTimer(timeOutBlock: () -> Unit) {
        coroutine?.launch {
            var timer  = 30
            var minute: Int
            var second: Int

            while (isActive && timer > 0) {
                delay(1000)
                if (isPause.not()) {
                    timer -= 1
                    minute = timer / 60
                    second = timer % 60

                    runGDX {
                        timeLbl.setText("${if (minute < 1) "0$minute" else minute}:${if (second < 10) "0$second" else second}")
                    }
                }
            }

            if (timer <= 0) runGDX { timeOutBlock() }
        }
    }

}