package com.chekrun.roadrunicen.game.manager

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


    enum class EnumAtlas(val data: AtlasData) {
        LOADER(AtlasData("atlas/loader.atlas")),
        ALL(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        BACKGROUND_LOADER(TextureData("textures/loader/background_loader.png")),
        MASK             (TextureData("textures/loader/mask.png")),

        BACKGROUND_FAIL(TextureData("textures/all/background_fail.png")),
        BACKGROUND_MENU(TextureData("textures/all/background_menu.png")),
        BACKGROUND_GAME(TextureData("textures/all/background_game.png")),
        BACKGROUND_WIN (TextureData("textures/all/background_win.png")),
        CHICKEN        (TextureData("textures/all/chicken.png")),
        DONE           (TextureData("textures/all/done.png")),
        GAME_PAN       (TextureData("textures/all/game_pan.png")),
        KOKI           (TextureData("textures/all/koki.png")),
        LOSE           (TextureData("textures/all/lose.png")),
        MENU_PAN       (TextureData("textures/all/menu_pan.png")),
        RULES          (TextureData("textures/all/rules.png")),

        _1(TextureData("textures/items/1.png")),
        _2(TextureData("textures/items/2.png")),
        _3(TextureData("textures/items/3.png")),
        _4(TextureData("textures/items/4.png")),
        _5(TextureData("textures/items/5.png")),
        _6(TextureData("textures/items/6.png")),

    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}