package com.tigerrealm.minimalizm.game.manager

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
        LOADING(AtlasData("atlas/loading.atlas")),
        ALL    (AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Loading
        YellowLoading(TextureData("textures/YellowLoading.png")),
        YellowMasakaj(TextureData("textures/start_masaka.png")),

        // All
        YellowDefeat (TextureData("textures/YellowDefeat.png")),
        YellowMain   (TextureData("textures/YellowMain.png")),
        YcupleMenu   (TextureData("textures/y_cuple_menu.png")),
        YcupleStars  (TextureData("textures/y_cuple_stars.png")),
        YellowVictory(TextureData("textures/YellowVictory.png")),
        YcupleVolume (TextureData("textures/y_cuple_volume.png")),
        YellowLevel  (TextureData("textures/YellowLevel.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}