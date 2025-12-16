package com.chekrun.roadrunicen.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.chekrun.roadrunicen.game.actors.button.AButton
import com.chekrun.roadrunicen.game.actors.checkbox.ACheckBox
import com.chekrun.roadrunicen.game.screens.RulesScreen
import com.chekrun.roadrunicen.game.utils.Block
import com.chekrun.roadrunicen.game.utils.TIME_ANIM_SCREEN
import com.chekrun.roadrunicen.game.utils.actor.animDelay
import com.chekrun.roadrunicen.game.utils.actor.animHide
import com.chekrun.roadrunicen.game.utils.actor.animMoveTo
import com.chekrun.roadrunicen.game.utils.actor.animShow
import com.chekrun.roadrunicen.game.utils.actor.setOnClickListener
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedMainGroup
import com.chekrun.roadrunicen.game.utils.gdxGame

class AMainRules(override val screen: RulesScreen): AdvancedMainGroup() {

    private val imgRules = Image(gdxGame.assetsAll.RULES)
    private val btnX     = AButton(screen, AButton.Type.X)
//    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
//    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnX()
//        addMus()
//        addSod()

        animShowMain {
            btnX.animMoveTo(btnX.x, 1709f, 0.5f)
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(112f, 427f, 860f, 1066f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(54f, 2000f, 150f, 150f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }
//
//    private fun addMus() {
//        addActor(mus)
//        mus.setBounds(40f, 1761f, 100f, 100f)
//        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) mus.check()
//        mus.setOnCheckListener {
//            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
//        }
//    }
//
//    private fun addSod() {
//        addActor(snd)
//        snd.setBounds(925f, 1761f, 100f, 100f)
//        if (gdxGame.soundUtil.isPause) snd.check()
//        snd.setOnCheckListener {
//            gdxGame.soundUtil.isPause = it
//        }
//    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}