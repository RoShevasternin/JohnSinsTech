package com.chekrun.roadrunicen.game.actors

import android.annotation.SuppressLint
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.chekrun.roadrunicen.game.utils.GameColor
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedGroup
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedScreen
import com.chekrun.roadrunicen.game.utils.font.FontParameter
import com.chekrun.roadrunicen.game.utils.gdxGame
import kotlin.math.max

class ATimer(override val screen: AdvancedScreen): AdvancedGroup() {

    private val parameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + ":").setSize(70)
    private val font70    = screen.fontGenerator_Regular.generateFont(parameter)

    private val imgTimer = Image(gdxGame.assetsAll.timer_pan)
    private val lblTimer = Label("01:00", Label.LabelStyle(font70, GameColor.ffffff))

    // 🔹 змінні для таймера
    private var totalTime   = 60 // 60 секунд = 1 хвилина
    private var accumulator = 0f
    private var isRunning   = false

    var timeOut: () -> Unit = {}

    override fun addActorsOnGroup() {
        addAndFillActor(imgTimer)

        addAndFillActor(lblTimer)
        //lblTimer.setBounds(48f, 83f, 158f, 58f)
        lblTimer.setAlignment(Align.center)
    }

    override fun act(delta: Float) {
        super.act(delta)

        if (isRunning && totalTime > 0) {
            accumulator += delta
            if (accumulator >= 1f) {
                accumulator -= 1f
                totalTime = max(0, totalTime - 1)
                updateLabel()

                if (totalTime == 0) {
                    isRunning = false
                    timeOut.invoke()
                }
            }
        }
    }

    // 🔹 запускаємо таймер
    fun start() {
        totalTime   = 60
        accumulator = 0f
        isRunning   = true
        updateLabel()
    }

    // 🔹 оновлення тексту у форматі MM:SS
    @SuppressLint("DefaultLocale")
    private fun updateLabel() {
        val minutes = totalTime / 60
        val seconds = totalTime % 60
        lblTimer.setText(String.format("%02d:%02d", minutes, seconds))
    }

    // 🔹 стоп таймера (якщо треба)
    fun stop() {
        isRunning = false
    }

}