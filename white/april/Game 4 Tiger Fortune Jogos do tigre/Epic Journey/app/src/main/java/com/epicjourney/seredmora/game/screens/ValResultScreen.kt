package com.epicjourney.seredmora.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.epicjourney.seredmora.game.LibGDXGame
import com.epicjourney.seredmora.game.screens.level.ILevelScreen
import com.epicjourney.seredmora.game.utils.TIME_ANIM
import com.epicjourney.seredmora.game.utils.actor.animHide
import com.epicjourney.seredmora.game.utils.actor.animShow
import com.epicjourney.seredmora.game.utils.actor.setOnClickListener
import com.epicjourney.seredmora.game.utils.advanced.AdvancedScreen
import com.epicjourney.seredmora.game.utils.advanced.AdvancedStage
import com.epicjourney.seredmora.game.utils.region

class ValResultScreen(override val game: LibGDXGame) : AdvancedScreen() {
    companion object {
        var isWin = true
        var levelScreen: ILevelScreen.Static.ELevel = ILevelScreen.Static.ELevel.L1
    }

    private val panelImg = Image(game.allAssets.VAL_PANEL)
    private val menuImg = Image(game.allAssets.RESULT).apply { color.a = 0f }
    private val textImg = Image(if (isWin) game.allAssets.val_victory else game.allAssets.val_lose)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackgrounds(getBackground())
        addAndFillActor(Image(drawerUtil.getRegion(Color.valueOf(if (isWin) "005F2C" else "680303").apply { a = 0.6f })))
        addPanel()
        addMenu()

        if (isWin) game.soundUtil.apply { play(VICTORY) } else game.soundUtil.apply { play(LOSE) }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(90f, 325f, 907f, 1208f)

        addActor(textImg)
        textImg.setBounds(255f, 1386f, 513f, 115f)
    }

    private fun AdvancedStage.addMenu() {
        addActor(menuImg)
        menuImg.setBounds(178f, 612f, 670f, 591f)
        menuImg.animShow(TIME_ANIM)

        val names = listOf(
            ValLevelScreen::class.java.name,
            ValSettingsScreen::class.java.name,
            ValRulesScreen::class.java.name,
        )

        var ny = 1070f

        names.onEach { screenName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(179f, ny, 670f, 133f)
            ny -= 96f + 133f

            btn.setOnClickListener(game.soundUtil) { navToByScreenName(screenName) }
        }

        val xBtn = Actor()
        addActor(xBtn)
        xBtn.apply {
            setBounds(812f, 350f, 171f, 175f)
            setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun getBackground(): TextureRegion = when(levelScreen) {
        ILevelScreen.Static.ELevel.L1 -> game.loadingAssets.ValBackground.region
        ILevelScreen.Static.ELevel.L2 -> game.allAssets.BG2.region
        ILevelScreen.Static.ELevel.L3 -> game.allAssets.BG3.region
        ILevelScreen.Static.ELevel.L4 -> game.allAssets.BG4.region
    }

    private fun navToByScreenName(screenName: String) {
        menuImg.animHide(TIME_ANIM) {
            if (screenName == ValLevelScreen::class.java.name) panelImg.animHide(TIME_ANIM) {
                game.navigationManager.navigate(screenName, ValMenuScreen::class.java.name)
            } else game.navigationManager.navigate(screenName, ValMenuScreen::class.java.name)
        }
    }


}