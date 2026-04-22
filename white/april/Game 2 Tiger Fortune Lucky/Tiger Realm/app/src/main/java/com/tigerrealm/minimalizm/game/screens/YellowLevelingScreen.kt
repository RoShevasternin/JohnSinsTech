package com.tigerrealm.minimalizm.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.tigerrealm.minimalizm.game.LibGDXGame
import com.tigerrealm.minimalizm.game.actors.AItem
import com.tigerrealm.minimalizm.game.actors.AResultGroup
import com.tigerrealm.minimalizm.game.actors.ATimerGroup
import com.tigerrealm.minimalizm.game.actors.TmpGroup
import com.tigerrealm.minimalizm.game.actors.button.AButton
import com.tigerrealm.minimalizm.game.actors.checkbox.ACheckBox
import com.tigerrealm.minimalizm.game.utils.Completer
import com.tigerrealm.minimalizm.game.utils.TIME_ANIM
import com.tigerrealm.minimalizm.game.utils.actor.animHide
import com.tigerrealm.minimalizm.game.utils.actor.animShow
import com.tigerrealm.minimalizm.game.utils.actor.animShow_Suspend
import com.tigerrealm.minimalizm.game.utils.actor.disable
import com.tigerrealm.minimalizm.game.utils.actor.enable
import com.tigerrealm.minimalizm.game.utils.actor.setOnClickListener
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedGroup
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedScreen
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedStage
import com.tigerrealm.minimalizm.game.utils.font.FontParameter
import com.tigerrealm.minimalizm.game.utils.region
import com.tigerrealm.minimalizm.game.utils.runGDX
import kotlin.coroutines.suspendCoroutine

import com.tigerrealm.minimalizm.game.actors.button.AButton.Static.Type as BtnType
import com.tigerrealm.minimalizm.game.actors.checkbox.ACheckBox.Static.Type as BoxType

class YellowLevelingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var LEVEL = 1
    }

    private val fontParameterNum = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars+":")
    private val fontParameterLev = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars+"Level:")
    private val font52        = fontGeneratorAksharRegular.generateFont(fontParameterNum.setSize(52))
    private val font50        = fontGeneratorAksharRegular.generateFont(fontParameterNum.setSize(50))
    private val font60        = fontGeneratorAksharRegular.generateFont(fontParameterLev.setSize(60))
    private val font122       = fontGeneratorAksharSemiBold.generateFont(fontParameterLev.setSize(122))

    private val SCORE        = 300 + (LEVEL * 40)
    private var scoreCounter = 0

    private val pause  = ACheckBox(this, BoxType.PAUSE)
    private val back   = AButton(this, BtnType.BACK)
    private val timer  = ATimerGroup(this, font52)
    private val level      = Label("Level: $LEVEL", Label.LabelStyle(font60, Color.WHITE))
    private val levelScore = Label("$SCORE", Label.LabelStyle(font50, Color.WHITE))
    private val score      = Label("$scoreCounter", Label.LabelStyle(font52, Color.WHITE))
    private val result     = AResultGroup(this, font122)
    private val gamePanel  = TmpGroup(this)

    private val indexList = (0..9).shuffled()

    private val items1   = List(4) { AItem(this, game.allAssets.listItems[indexList[0]]) }
    private val items2   = List(4) { AItem(this, game.allAssets.listItems[indexList[1]]) }
    private val items3   = List(4) { AItem(this, game.allAssets.listItems[indexList[2]]) }
    private val items4   = List(4) { AItem(this, game.allAssets.listItems[indexList[3]]) }
    private val items5   = List(4) { AItem(this, game.allAssets.listItems[indexList[4]]) }
    private val itemsAll = (items1 + items2 + items3 + items4 + items5).shuffled()

    override fun show() {
        stageUI.root.color.a = 0f
        setUIBackground(game.allAssets.YellowLevel.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        coroutine?.launch {
            runGDX {
                stageUI.root.animShow(TIME_ANIM) {
                    addAndFillActor(gamePanel)
                    addBack()
                    addPause()
                    addTimer()
                    addLevel()
                    addLevelScore()
                    addScore()
                }
            }
            launch { animBackAndMusic(0.5f) }
            launch {
                gamePanel.addItems()

                runGDX {
                    addAndFillActor(result)

                    timer.startTimer {
                        result.apply {
                            setResult(false, scoreCounter)
                            animShow(TIME_ANIM)
                        }
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addBack() {
        addActor(back)
        back.apply {
            setBounds(21f, 1920f, 169f, 169f)
            setOnClickListener(game.soundUtil) { stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            } }
        }
    }

    private fun AdvancedStage.addPause() {
        addActor(pause)
        pause.setBounds(893f, 1920f, 169f, 169f)

        pause.setOnCheckListener { timer.isPause = it }
    }

    private fun AdvancedStage.addTimer() {
        addActor(timer)
        timer.setBounds(902f, 1599f, 89f, 58f)
    }

    private fun AdvancedStage.addLevel() {
        addActor(level)
        level.setBounds(405f, 1778f, 271f, 60f)
        level.setAlignment(Align.center)
    }

    private fun AdvancedStage.addLevelScore() {
        addActor(levelScore)
        levelScore.setBounds(516f, 1712f, 72f, 55f)
    }

    private fun AdvancedStage.addScore() {
        addActor(score)
        score.setBounds(126f, 1599f, 75f, 58f)
        score.setAlignment(Align.center)
    }

    private suspend fun AdvancedGroup.addItems() = CompletableDeferred<Unit>().also { continuation ->
        var nx = 225f
        var ny = 1162f

        var counter = 0

        var item1: AItem? = null
        var item2: AItem? = null

        var tmpActions1: Action? = null
        var tmpActions2: Action? = null

        itemsAll.onEachIndexed { index, aItem ->
            runGDX {
                addActor(aItem)

                aItem.setBounds(nx, ny, 150f, 150f)
                nx += 10f + 150f
                if (index.inc() % 4 == 0) {
                    nx = 225f
                    ny -= 73f + 150f
                }
                aItem.setOrigin(Align.center)
                aItem.setOnClickListener(game.soundUtil) {
                    counter++

                    if (counter > 2) return@setOnClickListener

//                    game.soundUtil.apply { play(s_click) }

                    aItem.disable()
                    aItem.selected()

                    if (counter == 1) {
                        item1 = aItem
                    } else {
                        item2 = aItem

                        item1!!.apply {
                            unselected()
                            toFront()
                            enable()
                        }
                        item2!!.apply {
                            unselected()
                            toFront()
                            enable()
                        }

                        tmpActions1 = Actions.moveTo(item2!!.x, item2!!.y, 0.33f)
                        tmpActions2 = Actions.sequence(
                            Actions.moveTo(item1!!.x, item1!!.y, 0.33f),
                            Actions.run {
                                counter = 0

                                scoreCounter += (1..25).random()
                                score.setText(scoreCounter)

                                if (
                                    items1.all { it.y == items1.first().y } &&
                                    items2.all { it.y == items2.first().y } &&
                                    items3.all { it.y == items3.first().y } &&
                                    items4.all { it.y == items4.first().y } &&
                                    items5.all { it.y == items5.first().y }
                                ) {
                                    timer.isPause = true
                                    LEVEL++
                                    result.apply {
                                        setResult(true, SCORE)
                                        animShow(TIME_ANIM)
                                    }
                                }
                            }
                        )

                        item1!!.addAction(tmpActions1)
                        item2!!.addAction(tmpActions2)

                    }
                }
            }
            delay(50)
            if(index == itemsAll.lastIndex) continuation.complete(Unit)
        }
    }.await()

    // ------------------------------------------------------------------------
    // Anim
    // ------------------------------------------------------------------------

    private suspend fun animBackAndMusic(duration: Float) = CompletableDeferred<Unit>().also { continuation ->
        val completer = Completer(coroutine,2) { continuation.complete(Unit) }

        runGDX {
            back.addAction(Actions.sequence(
                Actions.moveTo(21f, 1731f, duration, Interpolation.swingOut),
                Actions.run { completer.complete() }
            ))
            pause.addAction(Actions.sequence(
                Actions.moveTo(893f, 1731f, duration, Interpolation.swingOut),
                Actions.run { completer.complete() }
            ))
        }

    }.await()

}