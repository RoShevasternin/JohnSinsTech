package com.tigerrealm.minimalizm.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.tigerrealm.minimalizm.MainActivity
import com.tigerrealm.minimalizm.game.manager.MusicManager
import com.tigerrealm.minimalizm.game.manager.NavigationManager
import com.tigerrealm.minimalizm.game.manager.SoundManager
import com.tigerrealm.minimalizm.game.manager.SpriteManager
import com.tigerrealm.minimalizm.game.manager.util.MusicUtil
import com.tigerrealm.minimalizm.game.manager.util.SoundUtil
import com.tigerrealm.minimalizm.game.manager.util.SpriteUtil
import com.tigerrealm.minimalizm.game.screens.YellowLoadingScreen
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedGame
import com.tigerrealm.minimalizm.game.utils.disposeAll
import com.tigerrealm.minimalizm.util.log

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

        navigationManager.navigate(YellowLoadingScreen::class.java.name)
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