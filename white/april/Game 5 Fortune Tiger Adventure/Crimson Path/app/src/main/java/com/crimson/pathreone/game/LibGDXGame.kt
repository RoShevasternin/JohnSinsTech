package com.crimson.pathreone.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.crimson.pathreone.MainActivity
import com.crimson.pathreone.game.manager.MusicManager
import com.crimson.pathreone.game.manager.NavigationManager
import com.crimson.pathreone.game.manager.SoundManager
import com.crimson.pathreone.game.manager.SpriteManager
import com.crimson.pathreone.game.manager.util.MusicUtil
import com.crimson.pathreone.game.manager.util.SoundUtil
import com.crimson.pathreone.game.manager.util.SpriteUtil
import com.crimson.pathreone.game.screens.IncasLoadingScreen
import com.crimson.pathreone.game.utils.advanced.AdvancedGame
import com.crimson.pathreone.game.utils.disposeAll
import com.crimson.pathreone.util.log

class LibGDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val musicUtil     by lazy { MusicUtil()    }
    val soundUtil     by lazy { SoundUtil()    }
    val loadingAssets by lazy { SpriteUtil.SplashAssets() }
    val allAssets     by lazy { SpriteUtil.GameAssets() }

    var backgroundColor = Color.BLACK
    val disposableSet   = mutableSetOf<Disposable>()

    override fun create() {
        navigationManager = NavigationManager(this)
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)
        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(IncasLoadingScreen::class.java.name)
    }

    override fun render() {
        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            disposableSet.disposeAll()
            disposeAll(musicUtil, assetManager)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

}