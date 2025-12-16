package com.chekrun.roadrunicen.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.chekrun.roadrunicen.game.manager.AudioManager
import com.chekrun.roadrunicen.game.utils.runGDX
import com.chekrun.roadrunicen.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click = AdvancedSound(SoundManager.EnumSound.click.data.sound, 0.5f)
    val win   = AdvancedSound(SoundManager.EnumSound.win.data.sound, 0.7f)
    val fail  = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 0.7f)
    val touch = AdvancedSound(SoundManager.EnumSound.touch.data.sound, 0.3f)

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