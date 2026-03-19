package com.olympusgoldrush.ulympus.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.olympusgoldrush.ulympus.game.actors.ATmpGroup
import com.olympusgoldrush.ulympus.game.actors.button.AButton
import com.olympusgoldrush.ulympus.game.utils.AlignH
import com.olympusgoldrush.ulympus.game.utils.AlignV
import com.olympusgoldrush.ulympus.game.utils.Block
import com.olympusgoldrush.ulympus.game.utils.TIME_ANIM_SCREEN
import com.olympusgoldrush.ulympus.game.utils.actor.addActorAligned
import com.olympusgoldrush.ulympus.game.utils.actor.addActorWithConstraints
import com.olympusgoldrush.ulympus.game.utils.actor.addActors
import com.olympusgoldrush.ulympus.game.utils.actor.addAndFillActor
import com.olympusgoldrush.ulympus.game.utils.actor.animDelay
import com.olympusgoldrush.ulympus.game.utils.actor.animHide
import com.olympusgoldrush.ulympus.game.utils.actor.animShow
import com.olympusgoldrush.ulympus.game.utils.actor.setOnClickListener
import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedScreen
import com.olympusgoldrush.ulympus.game.utils.gdxGame

class ResultFailScreen: AdvancedScreen() {

    private val imgPanel = Image(gdxGame.assetsAll.lose_pan)
    private val aMenuBtn  = AButton(this, AButton.Type.X)


    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        gdxGame.soundUtil.apply { play(lose) }
        setBackBackground(gdxGame.assetsAll.background_lose)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanel()
        addXBtn()

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

    // Actors ------------------------------------------------------------------------

    private fun Group.addXBtn() {
        aMenuBtn.setSize(130f, 133f)
        addActorWithConstraints(aMenuBtn) {
            startToStartOf = this@addXBtn
            topToTopOf     = this@addXBtn
            marginStart    = 70f
            marginTop      = 70f
        }
        aMenuBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

    private fun Group.addPanel() {
        val tmp = ATmpGroup(this@ResultFailScreen)
        tmp.setSize(880f, 1330f)
        addActorAligned(tmp, AlignH.CENTER, AlignV.CENTER)

        tmp.addAndFillActor(imgPanel)

        val aMenu  = Actor()
        val aAgain = Actor()
        tmp.addActors(aMenu, aAgain)
        aMenu.setBounds(231f, 407f, 429f, 122f)
        aAgain.setBounds(231f, 237f, 429f, 122f)

        aMenu.setOnClickListener { gdxGame.navigationManager.back() }
        aAgain.setOnClickListener { gdxGame.navigationManager.navigate(GameScreen::class.java.name) }
    }

}