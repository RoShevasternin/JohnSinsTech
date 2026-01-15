package com.roadrunnerhen.hen.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.roadrunnerhen.hen.game.manager.AudioManager
import com.roadrunnerhen.hen.game.utils.runGDX
import com.roadrunnerhen.hen.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.5f)
    val click_2   = AdvancedSound(SoundManager.EnumSound.click_2.data.sound, 0.7f)
    val fail  = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.4f)
    val lose = AdvancedSound(SoundManager.EnumSound.lose.data.sound, 0.5f)
    val victory = AdvancedSound(SoundManager.EnumSound.victory.data.sound, 0.4f)
    val win = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.5f)

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(advancedSound: AdvancedSound) {
        if (isPause.not()) {
            advancedSound.apply {
                sound.play((volumeLevel / 100f) * coff)
            }
        }
    }

    data class AdvancedSound(val sound: Sound, val coff: Float)
}