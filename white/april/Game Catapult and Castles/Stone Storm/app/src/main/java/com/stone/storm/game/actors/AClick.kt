package com.stone.storm.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.stone.storm.game.utils.actor.animHide
import com.stone.storm.game.utils.actor.disable
import com.stone.storm.game.utils.actor.enable
import com.stone.storm.game.utils.actor.setOnClickListener
import com.stone.storm.game.utils.advanced.AdvancedGroup
import com.stone.storm.game.utils.advanced.AdvancedScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AClick constructor(override val screen: AdvancedScreen): AdvancedGroup() {

    private val powerImg = Image(screen.game.allAssets.catapult_power)
    private val catapImg = Image(screen.game.allAssets.catapult)
    private val progress = AProgress(screen)

    var catapultBlock: () -> Unit = { }
    var showBlock    : () -> Unit = { }

    private var isPlus = true
    private var startProgressJob: Job? = null

    val coff get() = progress.progressPercentFlow.value / 100f

    override fun addActorsOnGroup() {
        disable()
        color.a = 0f

        addActors(powerImg, catapImg)
        addProgress()

        powerImg.setBounds(0f,523f,550f,120f)
        catapImg.setBounds(128f,0f,295f,295f)

        catapImg.setOnClickListener(screen.game.soundUtil) { catapult() }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun addProgress() {
        addActor(progress)
        progress.setBounds(0f, 557f, 550f, 31f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getCatapultActions() = Actions.sequence(
        Actions.fadeOut(0.3f),
        Actions.delay(0.3f),
        Actions.run { catapultBlock() },
        Actions.delay(3f),
        Actions.run { showBlock() },
        Actions.delay(3f),
        Actions.fadeIn(0.5f),
        Actions.run {
            startProgress()
            catapImg.enable()
        }
    )

    private fun catapult() {
        startProgressJob?.cancel()
        catapImg.disable()
        addAction(getCatapultActions())
    }

    fun showGroup() {
        addAction(Actions.sequence(
            Actions.delay(3f),
            Actions.fadeIn(0.5f),
            Actions.run {
                enable()
                startProgress()
            }
        ))
    }

    private fun startProgress() {
        startProgressJob = coroutine?.launch {
            progress.progressPercentFlow.collect { p ->
                delay(15)

                when(p) {
                    100f -> isPlus = false
                    0f   -> isPlus = true
                }

                progress.setProgressPercent(if (isPlus) p+1 else p-1)
            }
        }
    }

}