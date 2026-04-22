package com.crimson.pathreone.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureLoader
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTextureList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun loadTexture() {
        loadableTextureList.onEach {
            assetManager.load(it.path, Texture::class.java, TextureLoader.TextureParameter().apply {
                minFilter = Texture.TextureFilter.Linear
                magFilter = Texture.TextureFilter.Linear
                genMipMaps = true
            })
        }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    fun initTexture() {
        loadableTextureList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTextureList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {
        Loading(AtlasData("atlas/Loading.atlas")),
        All    (AtlasData("atlas/All.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Val Loader
        aca_mask        (TextureData("textures/Loading/aca_mask.png")),
        IncasBackground (TextureData("textures/Loading/IncasBackground.png")),
        // Val All
        enh                (TextureData("textures/All/enh.png")),
        IncasGameBackground(TextureData("textures/All/IncasGameBackground.png")),
        IncasPerfectly     (TextureData("textures/All/IncasPerfectly.png")),
        IncasVeryBad       (TextureData("textures/All/IncasVeryBad.png")),
        lrseexit           (TextureData("textures/All/lrseexit.png")),
        mini_tiger         (TextureData("textures/All/mini_tiger.png")),
        music              (TextureData("textures/All/music.png")),
        panel              (TextureData("textures/All/panel.png")),
        rules_text         (TextureData("textures/All/rules_text.png")),
        shoto_progress     (TextureData("textures/All/shoto_progress.png")),
        sound              (TextureData("textures/All/sound.png")),
        sisi               (TextureData("textures/All/sisi.png")),
        mini               (TextureData("textures/All/mini.png")),
        bigi               (TextureData("textures/All/bigi.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}