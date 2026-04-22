package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.actors.checkbox.ACheckBox
import com.mytholog.mastermy.game.actors.checkbox.ACheckBoxGroup
import com.mytholog.mastermy.game.utils.*
import com.mytholog.mastermy.game.utils.actor.*
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import com.mytholog.mastermy.util.log
import kotlinx.coroutines.launch

class CustomDeckScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        val LIST_CARD_TYPE = MutableList(4) { CardType.entries[it] }
    }

    private val btnBack  = AButton(this, AButton.Static.Type.Back)
    private val btnSett  = AButton(this, AButton.Static.Type.Settings)
    private val btnSave  = AButton(this, AButton.Static.Type.Save)
    private val imgPanel = Image(game.all.CUSTOME_CARDS)
    private val imgDask  = Image(game.all.CUSTOME_DECK)

    private val listBox = List(4) { ACheckBox(this, ACheckBox.Static.Type.BOX) }

    // class

    enum class CardType {
        MONSTERS, ARTIFACTS, GODS, HEROES
    }

    override fun show() {
        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.all.BACKGROUND_MAIN.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtnBackAndSett()
        addBtnSave()
        addListBox()
        addImgDesk()
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
                game.navigationManager.navigate(SettingsScreen::class.java.name, CustomDeckScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addBtnSave() {
        addActor(btnSave)
        btnSave.setBounds(362f,57f,356f,104f)
        btnSave.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) { game.navigationManager.back() }
        }
    }

    private fun AdvancedStage.addListBox() {
        val listPos = listOf(
            Vector2(432f,923f),
            Vector2(905f,923f),
            Vector2(356f,203f),
            Vector2(881f,203f),
        )

        listBox.onEachIndexed { index, box ->
            addActor(box)
            box.setBounds(listPos[index].x, listPos[index].y,80f, 81f)
        }
        LIST_CARD_TYPE.onEach {
            listBox[it.ordinal].check()
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(109f,214f,897f,1321f)

        val aMonsters  = Actor()
        val aArtifacts = Actor()
        val aGods      = Actor()
        val aHeroes    = Actor()

        var nx = 92f
        var ny = 923f
        listOf(aMonsters,aArtifacts,aGods,aHeroes).onEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(nx, ny,418f,629f)
            nx += 80+418
            if (index.inc() % 2 == 0) {
                nx = 92f
                ny -= 91+629
            }
            actor.setOnClickListener(game.soundUtil) {
                listBox[index].also { box ->
                    if (box.checkFlow.value) {
                        if (LIST_CARD_TYPE.size > 1) {
                            LIST_CARD_TYPE.remove(CardType.entries[index])
                            box.uncheck()
                        }
                    } else {
                        LIST_CARD_TYPE.add(CardType.entries[index])
                        box.check()
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addImgDesk() {
        addActor(imgDask)
        imgDask.setBounds(315f,1577f,464f,241f)
    }

}