package com.mytholog.mastermy.game.manager.util

import com.badlogic.gdx.audio.Sound
import com.mytholog.mastermy.game.manager.AudioManager
import com.mytholog.mastermy.game.utils.runGDX
import com.mytholog.mastermy.game.manager.SoundManager

class SoundUtil {

    val CLICK   = SoundManager.EnumSound.click.data.sound
    val bonus   = SoundManager.EnumSound.bonus.data.sound
    val card    = SoundManager.EnumSound.card.data.sound
    private val coins_1 = SoundManager.EnumSound.coins_1.data.sound
    private val coins_2 = SoundManager.EnumSound.coins_2.data.sound
    private val coins_3 = SoundManager.EnumSound.coins_3.data.sound
    private val coins_4 = SoundManager.EnumSound.coins_4.data.sound
    val win     = SoundManager.EnumSound.win.data.sound

    val listCoin = listOf(
        coins_1,
        coins_2,
        coins_3,
        coins_4
    )

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound, coff: Float = 1f) = runGDX { if (isPause.not()) sound.play((volumeLevel / 100f) * coff) }
}