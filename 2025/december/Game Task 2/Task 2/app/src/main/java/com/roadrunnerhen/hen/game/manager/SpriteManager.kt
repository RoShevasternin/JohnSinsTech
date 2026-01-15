package com.roadrunnerhen.hen.game.manager

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
        MASK(TextureData("textures/loader/mask.png")),

        B_BLUR       (TextureData("textures/all/b_blur.png")),
        B_DEF        (TextureData("textures/all/b_def.png")),
        B_GAME1      (TextureData("textures/all/b_game1.png")),
        B_GAME1_LOSE (TextureData("textures/all/b_game1_lose.png")),
        B_GAME1_WIN  (TextureData("textures/all/b_game1_win.png")),
        B_GAME2      (TextureData("textures/all/b_game2.png")),
        B_GAME2_LOSE (TextureData("textures/all/b_game2_lose.png")),
        B_GAME2_WIN  (TextureData("textures/all/b_game2_win.png")),
        GAME         (TextureData("textures/all/game.png")),
        RES          (TextureData("textures/all/res.png")),
        SCH          (TextureData("textures/all/sch.png")),
        SETT         (TextureData("textures/all/sett.png")),

        _1(TextureData("textures/items/1.png")),
        _2(TextureData("textures/items/2.png")),
        _3(TextureData("textures/items/3.png")),
        _4(TextureData("textures/items/4.png")),
        _5(TextureData("textures/items/5.png")),
        _6(TextureData("textures/items/6.png")),
        _7(TextureData("textures/items/7.png")),
        _8(TextureData("textures/items/8.png")),

    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}