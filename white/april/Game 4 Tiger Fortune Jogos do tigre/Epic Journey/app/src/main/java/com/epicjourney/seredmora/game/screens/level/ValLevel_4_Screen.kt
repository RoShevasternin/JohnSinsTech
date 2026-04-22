package com.epicjourney.seredmora.game.screens.level

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import com.epicjourney.seredmora.game.LibGDXGame
import com.epicjourney.seredmora.game.actors.AThing
import com.epicjourney.seredmora.game.screens.ValResultScreen
import com.epicjourney.seredmora.game.utils.TIME_ANIM
import com.epicjourney.seredmora.game.utils.actor.disable
import com.epicjourney.seredmora.game.utils.actor.enable
import com.epicjourney.seredmora.game.utils.actor.setOnClickListener
import com.epicjourney.seredmora.game.utils.advanced.AdvancedGroup
import com.epicjourney.seredmora.game.utils.advanced.AdvancedStage
import com.epicjourney.seredmora.game.utils.runGDX
import com.epicjourney.seredmora.game.utils.toMS
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class ValLevel_4_Screen(_game: LibGDXGame) : ILevelScreen(_game, Static.ELevel.L4) {

    private val things1  = List(7) { AThing(this, game.allAssets.listThing[indexList[0]]) }
    private val things2  = List(7) { AThing(this, game.allAssets.listThing[indexList[1]]) }
    private val things3  = List(7) { AThing(this, game.allAssets.listThing[indexList[2]]) }
    private val things4  = List(7) { AThing(this, game.allAssets.listThing[indexList[3]]) }
    private val thingsAll = (things1 + things2 + things3 + things4).shuffled()

    override fun AdvancedStage.addActorsOnStage() {
        runGDX {
            addActor(tmpGroup)
            tmpGroup.setBounds(84f, 230f, 919f, 1208f)
        }
        coroutine?.launch(Dispatchers.Default) { tmpGroup.addThings() }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private suspend fun AdvancedGroup.addThings() {
        var nx = 142f
        var ny = 964f

        var counter = 0

        var thing1: AThing? = null
        var thing2: AThing? = null

        var tmpActions1: Action? = null
        var tmpActions2: Action? = null

        thingsAll.onEachIndexed { index, aThing ->
            runGDX {
                addActor(aThing)

                aThing.setBounds(nx, ny, 100f, 100f)
                ny -= 32f + 100f
                if (index.inc() % 7 == 0) {
                    ny = 964f
                    nx += 78f + 100f
                }
                aThing.setOrigin(Align.center)
                aThing.setOnClickListener {
                    counter++

                    if (counter > 2) return@setOnClickListener

                    game.soundUtil.apply { play(THING_TOUCH) }

                    aThing.disable()
                    aThing.selected()

                    if (counter == 1) {
                        thing1 = aThing
                    } else {
                        thing2 = aThing

                        thing1!!.apply {
                            unselected()
                            toFront()
                            enable()
                        }
                        thing2!!.apply {
                            unselected()
                            toFront()
                            enable()
                        }

                        tmpActions1 = Actions.moveTo(thing2!!.x, thing2!!.y, 0.4f)
                        tmpActions2 = Actions.sequence(
                            Actions.moveTo(thing1!!.x, thing1!!.y, 0.4f),
                            Actions.run {
                                counter = 0

                                if (
                                    things1.all { it.x == things1.first().x } &&
                                    things2.all { it.x == things2.first().x } &&
                                    things3.all { it.x == things3.first().x }
                                ) {
                                    stopTimer()
                                    ValResultScreen.apply {
                                        levelScreen = level
                                        isWin = true
                                    }
                                    game.navigationManager.navigate(ValResultScreen::class.java.name)
                                }
                            }
                        )

                        thing1!!.addAction(tmpActions1)
                        thing2!!.addAction(tmpActions2)

                    }
                }
            }
            delay(80)
        }
    }

}