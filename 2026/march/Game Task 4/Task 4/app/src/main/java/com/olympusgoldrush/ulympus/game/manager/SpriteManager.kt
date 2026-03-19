package com.olympusgoldrush.ulympus.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList    = mutableListOf<AtlasData>()
    var loadableTexturesList = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    // Texture
    fun loadTexture() {
        loadableTexturesList.onEach { assetManager.load(it.path, Texture::class.java) }
    }

    fun initTexture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }

    fun initAtlasAndTexture() {
        initAtlas()
        initTexture()
    }


    enum class EnumAtlas(val data: AtlasData) {}

    enum class EnumTexture(val data: TextureData) {
        LOADER(TextureData("textures/loader/loader.png")),

        _1              (TextureData("textures/all/1.png")),
        _2              (TextureData("textures/all/2.png")),
        _3              (TextureData("textures/all/3.png")),
        _4              (TextureData("textures/all/4.png")),
        _5              (TextureData("textures/all/5.png")),
        _6              (TextureData("textures/all/6.png")),
        _7              (TextureData("textures/all/7.png")),
        _8              (TextureData("textures/all/8.png")),
        _9              (TextureData("textures/all/9.png")),
        _10             (TextureData("textures/all/10.png")),
        _11             (TextureData("textures/all/11.png")),
        background_def  (TextureData("textures/all/background_def.png")),
        background_game (TextureData("textures/all/background_game.png")),
        background_lose (TextureData("textures/all/background_lose.png")),
        background_win  (TextureData("textures/all/background_win.png")),
        game_pan        (TextureData("textures/all/game_pan.png")),
        item_1          (TextureData("textures/all/item_1.png")),
        item_2          (TextureData("textures/all/item_2.png")),
        item_3          (TextureData("textures/all/item_3.png")),
        item_4          (TextureData("textures/all/item_4.png")),
        item_5          (TextureData("textures/all/item_5.png")),
        item_6          (TextureData("textures/all/item_6.png")),
        item_7          (TextureData("textures/all/item_7.png")),
        item_8          (TextureData("textures/all/item_8.png")),
        lose_pan        (TextureData("textures/all/lose_pan.png")),
        menu_pan        (TextureData("textures/all/menu_pan.png")),
        rules_pan       (TextureData("textures/all/rules_pan.png")),
        sett_def        (TextureData("textures/all/sett_def.png")),
        sett_press      (TextureData("textures/all/sett_press.png")),
        settings_pan    (TextureData("textures/all/settings_pan.png")),
        win_pan         (TextureData("textures/all/win_pan.png")),
        x_def           (TextureData("textures/all/x_def.png")),
        x_press         (TextureData("textures/all/x_press.png")),

    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}