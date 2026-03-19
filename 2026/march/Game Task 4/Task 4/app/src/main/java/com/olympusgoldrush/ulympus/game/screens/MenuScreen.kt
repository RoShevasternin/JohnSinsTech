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

class MenuScreen: AdvancedScreen() {

    private val aSettBtn = AButton(this, AButton.Type.SETT)

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------
    override fun show() {
        setBackBackground(gdxGame.assetsAll.background_def)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addSettBtn()
        addMenuPanel()

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

    private fun Group.addSettBtn() {
        aSettBtn.setSize(130f, 133f)
        addActorWithConstraints(aSettBtn) {
            startToStartOf = this@addSettBtn
            topToTopOf     = this@addSettBtn
            marginStart    = 70f
            marginTop      = 70f
        }
        aSettBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(SettingsScreen::class.java.name, MenuScreen::class.java.name) } }
    }

    private fun Group.addMenuPanel() {
        val tmpGroup = ATmpGroup(this@MenuScreen)
        tmpGroup.setSize(880f, 845f)
        addActorAligned(tmpGroup, AlignH.CENTER, AlignV.CENTER)
        tmpGroup.addAndFillActor(Image(gdxGame.assetsAll.menu_pan))

        val aPlay  = Actor()
        val aRules = Actor()
        tmpGroup.addActors(aPlay, aRules)
        aPlay.setBounds(231f, 405f, 429f, 122f)
        aRules.setBounds(231f, 235f, 429f, 122f)

        aPlay.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(GameScreen::class.java.name, MenuScreen::class.java.name) } }
        aRules.setOnClickListener { animHideScreen { gdxGame.navigationManager.navigate(RulesScreen::class.java.name, MenuScreen::class.java.name) } }
    }

}