package com.olympusgoldrush.ulympus.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.olympusgoldrush.ulympus.game.GDX_GLOBAL_isGame
import com.olympusgoldrush.ulympus.game.GDX_GLOBAL_isLoadAssets
import com.olympusgoldrush.ulympus.game.actors.ALoader
import com.olympusgoldrush.ulympus.game.manager.MusicManager
import com.olympusgoldrush.ulympus.game.manager.SoundManager
import com.olympusgoldrush.ulympus.game.manager.SpriteManager
import com.olympusgoldrush.ulympus.game.utils.*
import com.olympusgoldrush.ulympus.game.utils.actor.addAndFillActor
import com.olympusgoldrush.ulympus.game.utils.actor.animDelay
import com.olympusgoldrush.ulympus.game.utils.actor.animHide
import com.olympusgoldrush.ulympus.game.utils.actor.animShow
import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedScreen
import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedStage
import com.olympusgoldrush.ulympus.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aLoader by lazy { ALoader(this) }

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------

    override fun show() {
        loadSplashAssets()
        super.show()
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun Group.addActorsOnStageUI() {
        addAndFillActor(aLoader)
    }

    // ------------------------------------------------------------------------
    // Screen Animations
    // ------------------------------------------------------------------------
    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animShowScreen(blockEnd: Block) {}

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableTexturesList = mutableListOf(SpriteManager.EnumTexture.LOADER.data)
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initTexture()
    }

    private fun loadAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(gdxGame.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(gdxGame.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        gdxGame.spriteManager.initAtlasAndTexture()
        gdxGame.musicManager.init()
        gdxGame.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (gdxGame.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = gdxGame.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress && GDX_GLOBAL_isGame) {
            isFinishProgress = false

            toGame()
        }
    }

    private fun toGame() {
        GDX_GLOBAL_isLoadAssets = true
        gdxGame.activity.hideWebView()

       gdxGame.musicUtil.apply { currentMusic = MUSICA.apply {
           isLooping = true
           coff      = 0.3f
       } }

        animHideScreen { gdxGame.navigationManager.navigate(MenuScreen::class.java.name) }
    }


}