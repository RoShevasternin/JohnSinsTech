package com.epicjourney.seredmora.game.manager

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
        valLoader(AtlasData("atlas/valLoader.atlas")),
        valAll   (AtlasData("atlas/valAll.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Val Loader
        ValBackground(TextureData("textures/valLoader/ValBackground.png")),
        vmaskav      (TextureData("textures/valLoader/vmaskav.png")),
        // Val All
        VAL_LEVEL   (TextureData("textures/valAll/val_level.png")),
        VAL_MENU    (TextureData("textures/valAll/val_menu.png")),
        VAL_PAN1    (TextureData("textures/valAll/val_pan1.png")),
        VAL_PAN2    (TextureData("textures/valAll/val_pan2.png")),
        VAL_PAN3    (TextureData("textures/valAll/val_pan3.png")),
        VAL_PAN4    (TextureData("textures/valAll/val_pan4.png")),
        VAL_PANEL   (TextureData("textures/valAll/val_panel.png")),
        VAL_RULES   (TextureData("textures/valAll/val_rules.png")),
        VAL_SETTINGS(TextureData("textures/valAll/val_settings.png")),
        BG2(TextureData("textures/valAll/BG2.png")),
        BG3(TextureData("textures/valAll/BG3.png")),
        BG4(TextureData("textures/valAll/BG4.png")),
        RESULT(TextureData("textures/valAll/result.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}