package com.mytholog.mastermy.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound

class SoundManager(var assetManager: AssetManager) {

    var loadableSoundList = mutableListOf<SoundData>()

    fun load() {
        loadableSoundList.onEach { assetManager.load(it.path, Sound::class.java) }
    }

    fun init() {
        loadableSoundList.onEach { it.sound = assetManager[it.path, Sound::class.java] }
        loadableSoundList.clear()
    }

    enum class EnumSound(val data: SoundData) {
        bonus   (SoundData("sound/bonus.mp3")),
        card    (SoundData("sound/card.mp3")),
        click   (SoundData("sound/click.mp3")),
        coins_1 (SoundData("sound/coins_1.mp3")),
        coins_2 (SoundData("sound/coins_2.mp3")),
        coins_3 (SoundData("sound/coins_3.mp3")),
        coins_4 (SoundData("sound/coins_4.mp3")),
        win     (SoundData("sound/win.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}