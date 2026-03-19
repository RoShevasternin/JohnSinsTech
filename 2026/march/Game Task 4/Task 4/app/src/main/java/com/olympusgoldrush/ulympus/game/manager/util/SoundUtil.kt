package com.olympusgoldrush.ulympus.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.olympusgoldrush.ulympus.game.manager.AudioManager
import com.olympusgoldrush.ulympus.game.utils.runGDX
import com.olympusgoldrush.ulympus.game.manager.SoundManager
import kotlin.compareTo
import kotlin.div
import kotlin.times

class SoundUtil {

    val click   = AdvancedSound(SoundManager.EnumSound.click.data.sound, 1f)
    val bonus   = AdvancedSound(SoundManager.EnumSound.bonus.data.sound, 1f)
    val fail    = AdvancedSound(SoundManager.EnumSound.fail.data.sound, 1f)
    val lose    = AdvancedSound(SoundManager.EnumSound.lose.data.sound, 1f)
    val win     = AdvancedSound(SoundManager.EnumSound.win.data.sound, 1f)

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