package com.side.swipe.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.side.swipe.game.manager.AudioManager
import com.side.swipe.game.manager.SoundManager
import com.side.swipe.game.utils.runGDX
class SoundUtil {


    val metal = SoundManager.EnumSound.metal.data.sound

    // 0..100
    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, volume: Float = volumeLevel) = runGDX {
        if (isPause.not()) sound.play(volume / 100f)
    }
}

