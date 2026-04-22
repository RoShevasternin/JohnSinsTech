package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.actors.ASelectPlayer
import com.mytholog.mastermy.game.utils.*
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.animShowScreen
import com.mytholog.mastermy.game.utils.actor.setBounds
import com.mytholog.mastermy.game.utils.actor.setOnClickListener
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage

class SelectPlayerScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var ARR_PLAYER_ICON_INDEX = Array(SettingGameScreen.NUM_PLAYER) { 0 }
            private set
    }

    private val NUM_PLAYER = SettingGameScreen.NUM_PLAYER

    private val btnBack  = AButton(this, AButton.Static.Type.Back)
    private val btnSett  = AButton(this, AButton.Static.Type.Settings)
    private val btnPlay  = AButton(this, AButton.Static.Type.Play)
    private val listPlayer = List(NUM_PLAYER) { ASelectPlayer(this, it.inc()) }

    override fun show() {
        ARR_PLAYER_ICON_INDEX = Array(SettingGameScreen.NUM_PLAYER) { 0 }

        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.all.BACKGROUND_MAIN.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtnBackAndSett()
        addBtnPlay()
        addListSelectPlayer()
    }

    private fun AdvancedStage.addBtnBackAndSett() {
        addActors(btnBack, btnSett)
        btnBack.setBounds(38f,1744f,188f,104f)
        btnBack.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
        btnSett.setBounds(894f,1734f,136f,142f)
        btnSett.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(SettingsScreen::class.java.name, SelectPlayerScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addBtnPlay() {
        addActor(btnPlay)
        btnPlay.setBounds(260f,96f,561f,163f)
        btnPlay.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(GameScreen::class.java.name, SelectPlayerScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addListSelectPlayer() {
        val posList = listOf(
            Vector2(19f,1237f),
            Vector2(433f,778f),
            Vector2(8f,319f),
        )
        listPlayer.onEachIndexed { index, player ->
            addActor(player)
            player.setBounds(posList[index],Vector2(622f,508f))
            player.blockSelectIcon = {
                ARR_PLAYER_ICON_INDEX[index] = it
            }
        }
    }

}