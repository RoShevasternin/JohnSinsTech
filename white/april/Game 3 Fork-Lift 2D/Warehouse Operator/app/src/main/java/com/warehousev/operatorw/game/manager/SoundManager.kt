package com.warehousev.operatorw.game.manager

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
        BOX  (SoundData("sound/box.mp3")),
        CLICK(SoundData("sound/click.mp3")),
        TRACTOR_START(SoundData("sound/tractor_start.mp3")),
        TRACTOR_GO(SoundData("sound/tractor_go.mp3")),
        FORK(SoundData("sound/fork.mp3")),
    }

    data class SoundData(
        val path: String,
    ) {
        lateinit var sound: Sound
    }

}