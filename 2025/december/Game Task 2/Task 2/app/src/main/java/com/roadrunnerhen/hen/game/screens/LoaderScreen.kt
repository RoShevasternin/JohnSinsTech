package com.roadrunnerhen.hen.game.screens

import com.roadrunnerhen.hen.game.GDX_GLOBAL_isGame
import com.roadrunnerhen.hen.game.GDX_GLOBAL_isLoadAssets
import com.roadrunnerhen.hen.game.actors.main.AMainLoader
import com.roadrunnerhen.hen.game.manager.MusicManager
import com.roadrunnerhen.hen.game.manager.SoundManager
import com.roadrunnerhen.hen.game.manager.SpriteManager
import com.roadrunnerhen.hen.game.utils.*
import com.roadrunnerhen.hen.game.utils.actor.animHide
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedScreen
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedStage
import com.roadrunnerhen.hen.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoaderScreen : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false
    private var isFinishProgress = false

    private val aMain by lazy { AMainLoader(this) }

    override fun show() {
        loadSplashAssets()
        super.show()
        //setBackBackground(gdxGame.assetsLoader.BACKGROUND_LOADER.region)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun hideScreen(block: Block) {
        aMain.animHide(TIME_ANIM_SCREEN) {
            block.invoke()
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(gdxGame.spriteManager) {
            loadableAtlasList = mutableListOf(SpriteManager.EnumAtlas.LOADER.data)
            loadAtlas()
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.MASK.data,
            )
            loadTexture()
        }
        gdxGame.assetManager.finishLoading()
        gdxGame.spriteManager.initAtlasAndTexture()
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
                    //runGDX { aMain.updatePercent(progress) }
                    if (progress % 50 == 0) log("progress = $progress%")
                    if (progress == 100) isFinishProgress = true

                    //delay((15..30).shuffled().first().toLong())
                    //aMain.updatePercent(progress)
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

        gdxGame.musicUtil.apply { currentMusic = Ruta.apply {
            isLooping = true
            coff      = 0.23f
        } }

        hideScreen {
            gdxGame.navigationManager.navigate(MenuScreen::class.java.name)
        }
    }


}