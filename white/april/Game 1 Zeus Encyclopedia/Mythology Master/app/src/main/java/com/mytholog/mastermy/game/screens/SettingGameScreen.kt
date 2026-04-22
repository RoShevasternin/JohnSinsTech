package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.actors.AProgressSettingsGame
import com.mytholog.mastermy.game.actors.checkbox.ACheckBox
import com.mytholog.mastermy.game.actors.checkbox.ACheckBoxGroup
import com.mytholog.mastermy.game.screens.CustomDeckScreen.CardType
import com.mytholog.mastermy.game.utils.*
import com.mytholog.mastermy.game.utils.actor.*
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import com.mytholog.mastermy.game.utils.font.FontParameter
import com.mytholog.mastermy.util.log
import kotlinx.coroutines.launch

class SettingGameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        private const val MAX_PLAYER   = 3
        private const val MAX_DURATION = 30

        var NUM_PLAYER = 2
            private set

        var GAME_DURATION = 15
            private set
        var GAME_DIFFICULTY = GameDifficulty.EASY
            private set
        var CARD_DECK = CardDeck.All
            private set
    }

    private val fontParameter = FontParameter()
    private val font296       = fontGenerator_Noah_Head_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(296))
    private val font180       = fontGenerator_Noah_Head_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS).setSize(180))

    private val ls296 = Label.LabelStyle(font296, GColor.yellow)
    private val ls180 = Label.LabelStyle(font180, GColor.yellow)

    private val btnBack      = AButton(this, AButton.Static.Type.Back)
    private val btnSett      = AButton(this, AButton.Static.Type.Settings)
    private val btnNext      = AButton(this, AButton.Static.Type.Next)
    private val imgPanel     = Image(game.all.NUMBER_TIME)
    private val imgAllCustom = Image(game.all.ALL_CUSTOME)
    private val imgCheck     = Image(game.all.DONE)

    private val progressNumPlayer = AProgressSettingsGame(this)
    private val progressDuration  = AProgressSettingsGame(this)

    private val lblNumPlayer = Label(NUM_PLAYER.toString(), ls296)
    private val lblDuration  = Label(GAME_DURATION.toString(), ls180)

    private val listBox  = List(3) { ACheckBox(this, ACheckBox.Static.Type.entries[it]) }

    // class

    enum class GameDifficulty(val coins: Int) {
        EASY(250), MEDIUM(500), HARD(750)
    }
    enum class CardDeck {
        All, Custom
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
        addImgPanel()
        addBtnNext()
        addProgress()
        addListBox()
        addImgAllCustom()
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
                game.navigationManager.navigate(SettingsScreen::class.java.name, SettingGameScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addBtnNext() {
        addActor(btnNext)
        btnNext.setBounds(302f,95f,476f,138f)
        btnNext.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(SelectPlayerScreen::class.java.name, SettingGameScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addListBox() {
        val cbg = ACheckBoxGroup()
        var nx  = 85f

        listBox.onEachIndexed { index, box ->
            box.checkBoxGroup = cbg

            addActor(box)
            box.setBounds(nx, 843f, 300f, 300f)
            nx += 5+300
            box.setOnCheckListener { isCheck ->
                if (isCheck) {
                    GAME_DIFFICULTY = GameDifficulty.entries[index]
                }
            }
        }

        listBox[GAME_DIFFICULTY.ordinal].check()
    }

    private fun AdvancedStage.addImgAllCustom() {
        addActor(imgAllCustom)
        imgAllCustom.setBounds(207f,380f,666f,293f)

        val aAll    = Actor()
        val aCustom = Actor()

        val listPosCheck = listOf(Vector2(440f,612f), Vector2(813f,612f))
        addActors(imgCheck, aAll, aCustom)
        imgCheck.setBounds(listPosCheck[CARD_DECK.ordinal], Vector2(81f,84f))

        aAll.apply {
            setBounds(207f,380f,293f,293f)
            setOnClickListener(game.soundUtil) {
                CARD_DECK = CardDeck.All
                imgCheck.setPosition(listPosCheck[CARD_DECK.ordinal])
                CustomDeckScreen.LIST_CARD_TYPE.clear()
                CustomDeckScreen.LIST_CARD_TYPE.addAll(CustomDeckScreen.CardType.entries)
            }
        }
        aCustom.apply {
            setBounds(580f,380f,293f,293f)
            setOnClickListener(game.soundUtil) {
                CARD_DECK = CardDeck.Custom
                imgCheck.setPosition(listPosCheck[CARD_DECK.ordinal])

                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(CustomDeckScreen::class.java.name, SettingGameScreen::class.java.name)
                }
            }
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(98f,1255f,884f,425f)

        addActors(lblNumPlayer, lblDuration)
        lblNumPlayer.apply {
            setBounds(180f,1301f,257f,370f)
            setAlignment(Align.center)
        }
        lblDuration.apply {
            setBounds(653f,1388f,235f,260f)
            setAlignment(Align.center)
        }
    }

    private fun AdvancedStage.addProgress() {
        addActors(progressNumPlayer, progressDuration)

        val percentCoff_NumPlayer = 100f / MAX_PLAYER.inc()
        val percentCoff_Duration  = 100f / MAX_DURATION

        progressNumPlayer.apply {
            setBounds(129f,1258f,358f,84f)

            val result = when(NUM_PLAYER) {
                1 -> 0
                3 -> 4
                else -> NUM_PLAYER
            }
            progressPercentFlow.value = result * percentCoff_NumPlayer

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        (it / percentCoff_NumPlayer).toInt().also { result ->
                            NUM_PLAYER = when(result) {
                                0 -> 1
                                4 -> 3
                                else -> result
                            }
                        }
                        lblNumPlayer.setText(NUM_PLAYER)
                    }
                }
            }
        }
        progressDuration.apply {
            setBounds(593f,1258f,358f,84f)

            val result = when(GAME_DURATION) {
                1 -> 0
                else -> GAME_DURATION
            }
            progressPercentFlow.value = result * percentCoff_Duration

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        (it / percentCoff_Duration).toInt().also { result ->
                            GAME_DURATION = when(result) {
                                0 -> 1
                                else -> result
                            }
                        }
                        lblDuration.setText(GAME_DURATION)
                    }
                }
            }
        }
    }


}