package com.olympusgoldrush.ulympus.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.olympusgoldrush.ulympus.game.actors.ATmpGroup
import com.olympusgoldrush.ulympus.game.actors.AVolume
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

class SettingsScreen: AdvancedScreen() {

    private val aXBtn   = AButton(this, AButton.Type.X)

    private val aPanelGroup = ATmpGroup(this)
    private val aSettImg    = Image(gdxGame.assetsAll.settings_pan)

    private val aMusicVolume = AVolume(this)
    private val aSoundVolume = AVolume(this)

    override fun show() {
        setBackBackground(gdxGame.assetsAll.background_def)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addXBtn()
        addSettPanel()

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

    private fun Group.addXBtn() {
        aXBtn.setSize(130f, 133f)
        addActorWithConstraints(aXBtn) {
            startToStartOf = this@addXBtn
            topToTopOf     = this@addXBtn
            marginStart    = 70f
            marginTop      = 70f
        }
        aXBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

    private fun Group.addSettPanel() {
        aPanelGroup.setSize(880f, 846f)
        addActorAligned(aPanelGroup, AlignH.CENTER, AlignV.CENTER)
        aPanelGroup.addAndFillActor(aSettImg)
        aPanelGroup.addMusSou()
    }

    private fun Group.addMusSou() {
        // music
        addActor(aMusicVolume)
        aMusicVolume.setBounds(237f, 473f, 406f, 48f)

        val aMinusMusic = Actor()
        val aPlusMusic  = Actor()
        addActors(aMinusMusic, aPlusMusic)
        aMinusMusic.setBounds(137f, 443f, 67f, 106f)
        aPlusMusic.setBounds(663f, 443f, 115f, 114f)
        aMinusMusic.setOnClickListener { 
            aMusicVolume.decrease()
            gdxGame.musicUtil.volumeLevelFlow.value = aMusicVolume.getPercent().toFloat()
        }
        aPlusMusic.setOnClickListener { 
            aMusicVolume.increase()
            gdxGame.musicUtil.volumeLevelFlow.value = aMusicVolume.getPercent().toFloat()
        }
        aMusicVolume.setPercent(gdxGame.musicUtil.volumeLevelFlow.value.toInt())

        // sound
        addActor(aSoundVolume)
        aSoundVolume.setBounds(237f, 227f, 406f, 48f)

        val aMinusSound = Actor()
        val aPlusSound  = Actor()
        addActors(aMinusSound, aPlusSound)
        aMinusSound.setBounds(118f, 192f, 101f, 112f)
        aPlusSound.setBounds(656f, 201f, 110f, 97f)
        aMinusSound.setOnClickListener {
            aSoundVolume.decrease()
            gdxGame.soundUtil.volumeLevel = aSoundVolume.getPercent().toFloat()
        }
        aPlusSound.setOnClickListener {
            aSoundVolume.increase()
            gdxGame.soundUtil.volumeLevel = aSoundVolume.getPercent().toFloat()
        }
        aSoundVolume.setPercent(gdxGame.soundUtil.volumeLevel.toInt())
    }

}