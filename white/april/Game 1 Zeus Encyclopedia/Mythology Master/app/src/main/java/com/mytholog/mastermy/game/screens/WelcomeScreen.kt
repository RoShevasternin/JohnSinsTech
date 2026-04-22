package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.utils.*
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.animShowScreen
import com.mytholog.mastermy.game.utils.actor.setOnClickListener
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import com.mytholog.mastermy.game.utils.font.FontParameter
import kotlinx.coroutines.launch

class WelcomeScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private var isFirst = true

        var COIN = 0
    }

    private val fontParameter = FontParameter()
    private val font          = fontGenerator_Noah_Head_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(148))

    private val ls148 = Label.LabelStyle(font, GColor.brown)

    private val btnDeck = AButton(this, AButton.Static.Type.DeckOverview)
    private val imgMenu = Image(game.all.MENU)
    private val imgCoin = Image(game.all.COIN)
    private val lblCoin = Label(COIN.toString(), ls148)


    override fun show() {
        if (isFirst) {
            isFirst = false
            game.musicUtil.apply { music = Orpheus.apply {
                isLooping = true
                volumeLevelFlow.value = 25f
            } }
        }

        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.splash.BACKGROUND_WELCOME.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                addImgMenu()
                addImgCoin()
                addLblCoin()
                addBtnDeckOverview()
            }
        }
    }

    private fun AdvancedStage.addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(216f,212f,648f,1092f)

        val aPlay     = Actor()
        val aRules    = Actor()
        val aSettings = Actor()
        val aExit     = Actor()
        addActors(aPlay, aRules, aSettings, aExit)
        aPlay.apply {
            setBounds(216f,999f,647f,304f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(SettingGameScreen::class.java.name, WelcomeScreen::class.java.name)
                }
            }
        }
        aRules.apply {
            setBounds(267f,709f,545f,256f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(RulesScreen::class.java.name, WelcomeScreen::class.java.name)
                }
            }
        }
        aSettings.apply {
            setBounds(267f,419f,545f,256f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(SettingsScreen::class.java.name, WelcomeScreen::class.java.name)
                }
            }
        }
        aExit.apply {
            setBounds(388f,212f,321f,151f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.exit()
                }
            }
        }
    }


    private fun AdvancedStage.addImgCoin() {
        addActor(imgCoin)
        imgCoin.setBounds(311f,1476f,219f,213f)
    }

    private fun AdvancedStage.addLblCoin() {
        addActor(lblCoin)
        lblCoin.setBounds(553f,1490f,234f,185f)
    }

    private fun AdvancedStage.addBtnDeckOverview() {
        addActor(btnDeck)
        btnDeck.setBounds(36f,1712f,364f,172f)
        btnDeck.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(DeckOverviewScreen::class.java.name, WelcomeScreen::class.java.name)
            }
        }
    }

}