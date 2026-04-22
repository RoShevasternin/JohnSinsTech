package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.manager.MusicManager
import com.mytholog.mastermy.game.manager.SoundManager
import com.mytholog.mastermy.game.manager.SpriteManager
import com.mytholog.mastermy.game.utils.GColor
import com.mytholog.mastermy.game.utils.TIME_ANIM
import com.mytholog.mastermy.game.utils.actor.animHide
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.animShow
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import com.mytholog.mastermy.game.utils.font.FontParameter
import com.mytholog.mastermy.game.utils.region
import com.mytholog.mastermy.game.utils.runGDX
import com.mytholog.mastermy.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val progressFlow     = MutableStateFlow(0f)
    private var isFinishLoading  = false

    private var isFinishProgress = false

    private val fontParameter = FontParameter()
    private val font          = fontGenerator_Noah_Head_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars+"%").setSize(80))

    private val ls80 = Label.LabelStyle(font, GColor.brown)

    private val imgBlock   by lazy { Image(game.splash.BLOCK) }
    private val lblPercent by lazy { Label("0", ls80) }

    override fun show() {
        stageUI.root.animHide()
        loadSplashAssets()
        setBackBackground(game.splash.BACKGROUND_WELCOME.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
        loadAssets()
        collectProgress()
    }

    override fun render(delta: Float) {
        super.render(delta)
        loadingAssets()
        isFinish()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgBlock)
        imgBlock.setBounds(385f,57f,311f,225f)

        addActor(lblPercent)
        lblPercent.apply {
            setAlignment(Align.center)
            setBounds(446f,112f,188f,100f)
        }
    }

    // Logic ------------------------------------------------------------------------

    private fun loadSplashAssets() {
        with(game.spriteManager) {
            loadableTexturesList = mutableListOf(
                SpriteManager.EnumTexture.BACKGROUND_WELCOME.data,
                SpriteManager.EnumTexture.BLOCK.data,
            )
            loadTexture()
        }
        game.assetManager.finishLoading()
        game.spriteManager.initTeture()
    }

    private fun loadAssets() {
        with(game.spriteManager) {
            loadableAtlasList = SpriteManager.EnumAtlas.entries.map { it.data }.toMutableList()
            loadAtlas()
            loadableTexturesList = SpriteManager.EnumTexture.entries.map { it.data }.toMutableList()
            loadTexture()
        }
        with(game.musicManager) {
            loadableMusicList = MusicManager.EnumMusic.entries.map { it.data }.toMutableList()
            load()
        }
        with(game.soundManager) {
            loadableSoundList = SoundManager.EnumSound.entries.map { it.data }.toMutableList()
            load()
        }
    }

    private fun initAssets() {
        game.spriteManager.initAtlas()
        game.spriteManager.initTeture()
        game.musicManager.init()
        game.soundManager.init()
    }

    private fun loadingAssets() {
        if (isFinishLoading.not()) {
            if (game.assetManager.update(16)) {
                isFinishLoading = true
                initAssets()
            }
            progressFlow.value = game.assetManager.progress
        }
    }

    private fun collectProgress() {
        coroutine?.launch {
            var progress = 0
            progressFlow.collect { p ->
                while (progress < (p * 100)) {
                    progress += 1
                    if (progress % 25 == 0) log("progress = $progress%")
                    runGDX { lblPercent.setText("${progress}%") }
                    if (progress == 100) isFinishProgress = true

                    //delay((20..50).shuffled().first().toLong())
                }
            }
        }
    }

    private fun isFinish() {
        if (isFinishProgress) {
            isFinishProgress = false

            toScreen(WelcomeScreen::class.java.name)
        }
    }

    private fun toScreen(screenName: String) {
        stageUI.root.animHideScreen(TIME_ANIM) { game.navigationManager.navigate(screenName) }
    }

}