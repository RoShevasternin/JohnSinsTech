package com.goalblast.onewingame.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.goalblast.onewingame.game.actors.ATmpGroup
import com.goalblast.onewingame.game.actors.button.AButton
import com.goalblast.onewingame.game.utils.AlignH
import com.goalblast.onewingame.game.utils.AlignV
import com.goalblast.onewingame.game.utils.Block
import com.goalblast.onewingame.game.utils.TIME_ANIM_SCREEN
import com.goalblast.onewingame.game.utils.actor.addActorAligned
import com.goalblast.onewingame.game.utils.actor.addActorWithConstraints
import com.goalblast.onewingame.game.utils.actor.addAndFillActor
import com.goalblast.onewingame.game.utils.actor.animDelay
import com.goalblast.onewingame.game.utils.actor.animHide
import com.goalblast.onewingame.game.utils.actor.animShow
import com.goalblast.onewingame.game.utils.advanced.AdvancedScreen
import com.goalblast.onewingame.game.utils.font.FontParameter
import com.goalblast.onewingame.game.utils.gdxGame

class ResultDoneScreen: AdvancedScreen() {
    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(36)
    private val font          = fontGenerator_DEF.generateFont(fontParameter)

    private val imgPanel = Image(gdxGame.assetsAll.WIN_PAN)
    private val btnHome  = AButton(this, AButton.Type.HOME)
    private val aLbl     = Label("${GameScreen.counterGoals}", Label.LabelStyle(font, Color.WHITE))

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        gdxGame.soundUtil.apply { play(win) }
        setBackBackground(gdxGame.assetsAll.BACKGROUND_WIN)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        val tmp = ATmpGroup(this@ResultDoneScreen)
        tmp.setSize(646f, 941f)
        addActorWithConstraints(tmp) {
            startToStartOf   = this@addActorsOnStageUI
            endToEndOf       = this@addActorsOnStageUI
            topToTopOf       = this@addActorsOnStageUI
            bottomToBottomOf = this@addActorsOnStageUI

            verticalBias = 0.6f
        }
        tmp.addAndFillActor(imgPanel)
        tmp.addActor(aLbl)
        aLbl.setBounds(459f, 3f, 39f, 39f)

        btnHome.setSize(792f, 168f)
        addActorWithConstraints(btnHome) {
            startToStartOf   = this@addActorsOnStageUI
            endToEndOf       = this@addActorsOnStageUI
            topToBottomOf    = tmp

            marginTop = 22f
        }
        btnHome.setOnClickListener { gdxGame.navigationManager.back() }

        animShowScreen()
    }

    // ------------------------------------------------------------------------
    // Screen Animations
    // ------------------------------------------------------------------------
    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}