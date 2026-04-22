package com.tigerrealm.minimalizm.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedGroup
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedScreen
import com.tigerrealm.minimalizm.game.utils.font.FontParameter
import com.tigerrealm.minimalizm.game.utils.runGDX

class ATimerGroup(override val screen: AdvancedScreen, font: BitmapFont): AdvancedGroup() {

    private val timeLbl = Label("0:00", Label.LabelStyle(font, Color.WHITE))

    var isPause = false

//    val getTime: String get() = timeLbl.text.toString()

    override fun addActorsOnGroup() {
        addAndFillActor(timeLbl)
        timeLbl.setAlignment(Align.center)
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

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
                        timeLbl.setText("$minute:${if (second < 10) "0$second" else second}")
                    }
                }
            }

            if (timer <= 0) runGDX { timeOutBlock() }
        }
    }

}