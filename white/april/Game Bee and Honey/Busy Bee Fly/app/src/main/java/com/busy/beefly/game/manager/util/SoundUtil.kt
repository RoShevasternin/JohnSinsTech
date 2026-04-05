package com.busy.beefly.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.busy.beefly.game.manager.AudioManager
import com.busy.beefly.game.manager.SoundManager
import com.busy.beefly.game.utils.runGDX
class SoundUtil {

    val DOWN  = SoundManager.EnumSound.DOWN.data.sound
    val MED   = SoundManager.EnumSound.MED.data.sound
    val SLASH = SoundManager.EnumSound.SLASH.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

