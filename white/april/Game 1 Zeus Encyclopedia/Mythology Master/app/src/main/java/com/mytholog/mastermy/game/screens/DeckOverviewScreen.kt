package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.utils.*
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.animShowScreen
import com.mytholog.mastermy.game.utils.actor.setOnClickListener
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class DeckOverviewScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnBack  = AButton(this, AButton.Static.Type.Back)
    private val btnSett  = AButton(this, AButton.Static.Type.Settings)
    private val imgPanel = Image(game.all.COLODES)

    override fun show() {
        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.all.BACKGROUND_MAIN.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtnBackAndSett()
        addImgPanel()
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
                game.navigationManager.navigate(SettingsScreen::class.java.name, DeckOverviewScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(119f,275f,880f,1312f)

        val aMonsters  = Actor()
        val aArtifacts = Actor()
        val aGods      = Actor()
        val aHeroes    = Actor()

        var nx = 92f
        var ny = 1002f
        listOf(aMonsters,aArtifacts,aGods,aHeroes).onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(nx, ny,418f,629f)
            nx += 80+418
            if (index.inc() % 2 == 0) {
                nx = 92f
                ny -= 141+629
            }
            actor.setOnClickListener(game.soundUtil) {
                OverviewScreen.DECK_TYPE = OverviewScreen.DeckType.entries[index]
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(OverviewScreen::class.java.name, DeckOverviewScreen::class.java.name)
                }
            }
        }
    }

}