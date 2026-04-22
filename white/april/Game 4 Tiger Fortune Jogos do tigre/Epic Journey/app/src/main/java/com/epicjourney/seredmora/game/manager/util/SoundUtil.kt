package com.epicjourney.seredmora.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.epicjourney.seredmora.game.manager.AudioManager
import com.epicjourney.seredmora.game.manager.SoundManager
import com.epicjourney.seredmora.game.utils.runGDX

class SoundUtil {

    // Common
    val BTN_CLICK   = SoundManager.EnumSound.BTN_CLICK.data.sound
    val LOSE        = SoundManager.EnumSound.LOSE.data.sound
    val THING_TOUCH = SoundManager.EnumSound.THING_TOUCH.data.sound
    val VICTORY     = SoundManager.EnumSound.VICTORY.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel / 100f) }
}