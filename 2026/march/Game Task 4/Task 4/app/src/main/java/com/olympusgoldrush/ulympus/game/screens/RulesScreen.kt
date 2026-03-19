package com.olympusgoldrush.ulympus.game.screens

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.olympusgoldrush.ulympus.game.actors.button.AButton
import com.olympusgoldrush.ulympus.game.utils.AlignH
import com.olympusgoldrush.ulympus.game.utils.AlignV
import com.olympusgoldrush.ulympus.game.utils.Block
import com.olympusgoldrush.ulympus.game.utils.TIME_ANIM_SCREEN
import com.olympusgoldrush.ulympus.game.utils.actor.addActorAligned
import com.olympusgoldrush.ulympus.game.utils.actor.addActorWithConstraints
import com.olympusgoldrush.ulympus.game.utils.actor.animDelay
import com.olympusgoldrush.ulympus.game.utils.actor.animHide
import com.olympusgoldrush.ulympus.game.utils.actor.animShow
import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedScreen
import com.olympusgoldrush.ulympus.game.utils.gdxGame

class RulesScreen: AdvancedScreen() {

    private val aMenuBtn  = AButton(this, AButton.Type.X)
    private val aRulesImg = Image(gdxGame.assetsAll.rules_pan)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.background_def)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addXBtn()
        addRulesImg()

        animShowScreen()
    }

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

    private fun Group.addRulesImg() {
        aRulesImg.setSize(880f, 845f)
        addActorAligned(aRulesImg, AlignH.CENTER, AlignV.CENTER)
    }



}