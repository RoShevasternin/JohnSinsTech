package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.utils.GColor
import com.mytholog.mastermy.game.utils.TIME_ANIM
import com.mytholog.mastermy.game.utils.WIDTH_UI
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.animShowScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import com.mytholog.mastermy.game.utils.font.FontParameter
import com.mytholog.mastermy.game.utils.region

class WinnerScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val fontParameter = FontParameter()
    private val font103       = fontGenerator_Olympus.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + "Player").setSize(103))
    private val font123       = fontGenerator_Noah_Head_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(123))

    private val ls103 = Label.LabelStyle(font103, Color.BLACK)
    private val ls123 = Label.LabelStyle(font123, Color.WHITE)

    private val btnMenu    = AButton(this, AButton.Static.Type.Menu)
    private val btnRestart = AButton(this, AButton.Static.Type.Restart)
    private val imgWinner  = Image(game.all.WINNER)
    private val imgCoin    = Image(game.all.COIN)
    private val lblCoin    = Label(GameScreen.WINNER_COINS.toString(), ls123)
    private val imgIcon    = Image(game.all.listPlayer[GameScreen.WINNER_ICON_INDEX])


    override fun show() {
        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.all.BACKGROUND_WINNER.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM) {
            game.soundUtil.apply { play(win) }
        }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtnBackAndSett()
        addImgWinner()
        addImgCoin()
        addLblCoin()
        addImgIcon()
    }

    private fun AdvancedStage.addBtnBackAndSett() {
        addActors(btnMenu, btnRestart)
        btnMenu.setBounds(278f,254f,206f,216f)
        btnMenu.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.clearStack()
                game.navigationManager.navigate(WelcomeScreen::class.java.name)
            }
        }
        btnRestart.setBounds(595f,254f,206f,216f)
        btnRestart.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(GameScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addImgWinner() {
        addActor(imgWinner)
        imgWinner.setBounds(255f,1523f,596f,186f)
    }

    private fun AdvancedStage.addImgCoin() {
        addActor(imgCoin)
        imgCoin.setBounds(351f,557f,183f,177f)
    }

    private fun AdvancedStage.addLblCoin() {
        addActor(lblCoin)
        lblCoin.setBounds(545f,568f,195f,154f)
    }

    private fun AdvancedStage.addImgIcon() {
        addActor(imgIcon)
        imgIcon.setBounds(226f,829f,629f,652f)

        val imgPanel  = Image(game.all.PANEL_PLAYER)
        val lblPlayer = Label("Player " + GameScreen.WINNER_NUM.toString(), ls103)

        addActors(imgPanel, lblPlayer)
        imgPanel.setBounds(259f,774f,559f,163f)
        lblPlayer.setBounds(343f,803f,390f,105f)
        lblPlayer.setAlignment(Align.center)

    }

}