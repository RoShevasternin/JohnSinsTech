package com.roadrunnerhen.hen.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.roadrunnerhen.hen.game.actors.AProgress
import com.roadrunnerhen.hen.game.actors.button.AButton
import com.roadrunnerhen.hen.game.screens.SettScreen
import com.roadrunnerhen.hen.game.utils.Block
import com.roadrunnerhen.hen.game.utils.TIME_ANIM_SCREEN
import com.roadrunnerhen.hen.game.utils.actor.animDelay
import com.roadrunnerhen.hen.game.utils.actor.animHide
import com.roadrunnerhen.hen.game.utils.actor.animMoveTo
import com.roadrunnerhen.hen.game.utils.actor.animShow
import com.roadrunnerhen.hen.game.utils.actor.setOnClickListener
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedMainGroup
import com.roadrunnerhen.hen.game.utils.gdxGame
import com.roadrunnerhen.hen.game.utils.runGDX
import kotlinx.coroutines.launch

class AMainSett(override val screen: SettScreen): AdvancedMainGroup() {

    private val imgRules = Image(gdxGame.assetsAll.SETT)
    private val btnX     = AButton(screen, AButton.Type.X)
//    private val mus      = ACheckBox(screen, ACheckBox.Type.Mus)
//    private val snd      = ACheckBox(screen, ACheckBox.Type.Sod)

    private val progMusic = AProgress(screen)
    private val progSound = AProgress(screen)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgRules()
        addBtnX()
//        addMus()
//        addSod()
        addMusSou()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgRules() {
        addActor(imgRules)
        imgRules.setBounds(205f, 527f, 670f, 927f)
    }

    private fun addBtnX() {
        addActor(btnX)
        btnX.setBounds(96f, 1724f, 100f, 100f)
        btnX.setOnClickListener(gdxGame.soundUtil) {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addMusSou() {
        addActor(progMusic)
        progMusic.setBounds(370f, 981f, 407f, 25f)
        addActor(progSound)
        progSound.setBounds(370f, 767f, 407f, 25f)

        progMusic.progressPercentFlow.value = gdxGame.musicUtil.volumeLevelFlow.value
        progSound.progressPercentFlow.value = gdxGame.soundUtil.volumeLevel

        coroutine?.launch {
            launch {
                progMusic.progressPercentFlow.collect {
                    runGDX {
                        gdxGame.musicUtil.volumeLevelFlow.value = it
                    }
                }
            }
            progSound.progressPercentFlow.collect {
                runGDX {
                    gdxGame.soundUtil.volumeLevel = it
                }
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