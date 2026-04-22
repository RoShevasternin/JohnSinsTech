package com.epicjourney.seredmora.game.manager

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
        BTN_CLICK  (SoundData("sound/btn_click.mp3")),
        LOSE       (SoundData("sound/lose.mp3")),
        THING_TOUCH(SoundData("sound/thing_touch.mp3")),
        VICTORY    (SoundData("sound/victory.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}