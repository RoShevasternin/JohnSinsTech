package com.goalblast.onewingame.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.goalblast.onewingame.game.actors.button.AButton
import com.goalblast.onewingame.game.actors.checkbox.ACheckBox
import com.goalblast.onewingame.game.utils.AlignH
import com.goalblast.onewingame.game.utils.AlignV
import com.goalblast.onewingame.game.utils.Block
import com.goalblast.onewingame.game.utils.TIME_ANIM_SCREEN
import com.goalblast.onewingame.game.utils.actor.addActorAligned
import com.goalblast.onewingame.game.utils.actor.addActorWithConstraints
import com.goalblast.onewingame.game.utils.actor.animDelay
import com.goalblast.onewingame.game.utils.actor.animHide
import com.goalblast.onewingame.game.utils.actor.animShow
import com.goalblast.onewingame.game.utils.advanced.AdvancedScreen
import com.goalblast.onewingame.game.utils.gdxGame

class MenuScreen: AdvancedScreen() {

    private val aGameBtn   = AButton(this, AButton.Type.GAME)
    private val aSoundBox  = ACheckBox(this, ACheckBox.Type.SOUND)
    private val aMusicBox  = ACheckBox(this, ACheckBox.Type.MUSIC)

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_DEF)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addGameBtn()
        addSoundBox()
        addMusicBox()

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

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun Group.addGameBtn() {
        aGameBtn.setSize(811f, 186f)
        addActorAligned(aGameBtn, AlignH.CENTER, AlignV.CENTER)
        aGameBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
    }

    private fun Group.addSoundBox() {
        aSoundBox.setSize(88f, 93f)
        addActorWithConstraints(aSoundBox) {
            endToEndOf = this@addSoundBox
            topToTopOf = this@addSoundBox

            marginEnd = 63f
            marginTop = 61f
        }

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }

    }

    private fun Group.addMusicBox() {
        aMusicBox.setSize(88f, 93f)
        addActorWithConstraints(aMusicBox) {
            startToStartOf = this@addMusicBox
            topToTopOf     = this@addMusicBox

            marginStart = 63f
            marginTop   = 61f
        }

        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }
}