package com.tigerrealm.minimalizm.game.screens

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import com.tigerrealm.minimalizm.game.LibGDXGame
import com.tigerrealm.minimalizm.game.actors.button.AButton
import com.tigerrealm.minimalizm.game.actors.checkbox.ACheckBox
import com.tigerrealm.minimalizm.game.utils.Completer
import com.tigerrealm.minimalizm.game.utils.TIME_ANIM
import com.tigerrealm.minimalizm.game.utils.actor.animHide
import com.tigerrealm.minimalizm.game.utils.actor.animShow
import com.tigerrealm.minimalizm.game.utils.actor.animShow_Suspend
import com.tigerrealm.minimalizm.game.utils.actor.setOnClickListener
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedScreen
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedStage
import com.tigerrealm.minimalizm.game.utils.region
import com.tigerrealm.minimalizm.game.utils.runGDX
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

import com.tigerrealm.minimalizm.game.actors.button.AButton.Static.Type as BtnType
import com.tigerrealm.minimalizm.game.actors.checkbox.ACheckBox.Static.Type as BoxType

class YellowSettingsingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var isNotification = false
            private set
    }

    private val panel    = Image(game.allAssets.YcupleVolume)
    private val music    = ACheckBox(this, BoxType.MUSIC)
    private val back     = AButton(this, BtnType.BACK)
    private val settings = Image(game.allAssets.yPanelSettings)
    private val musicBox = ACheckBox(this, BoxType.BOX)
    private val soundBox = ACheckBox(this, BoxType.BOX)
    private val notifBox = ACheckBox(this, BoxType.BOX)

    // Field
    private val boxes = listOf(musicBox, soundBox, notifBox)

    override fun show() {
        stageUI.root.color.a = 0f
        setUIBackground(game.allAssets.YellowMain.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        stageUI.root.animShow(TIME_ANIM) {
            runGDX {
                addBack()
                addMusic()
                addPanel()
                addSettings()
                addBoxes()
            }
            coroutine?.launch {
                animBackAndMusic(0.5f)
                panel.animShow_Suspend(0.5f)
                animSettings(0.3f)
                boxes.onEach { it.animShow_Suspend(0.3f) }
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

    private fun AdvancedStage.addMusic() {
        addActor(music)
        music.setBounds(893f, 1920f, 169f, 169f)

        game.musicUtil.music?.let { mmm ->
            if (mmm.isPlaying.not()) music.check(false)

            music.setOnCheckListener { if (it) {
                mmm.pause()
                musicBox.uncheck(false)
            } else {
                mmm.play()
                musicBox.check(false)
            } }
        }
    }

    private fun AdvancedStage.addPanel() {
        panel.color.a = 0f
        addActor(panel)
        panel.setBounds(-13f, 225f, 1127f, 1139f)

        val backBtn = Actor()
        val saveBtn = Actor()
        addActors(backBtn, saveBtn)
        backBtn.apply {
            setBounds(145f, 225f, 357f, 125f)
            setOnClickListener(game.soundUtil) { stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            } }
        }
        saveBtn.apply {
            setBounds(578f, 225f, 357f, 125f)
            setOnClickListener(game.soundUtil) { stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            } }
        }
    }

    private fun AdvancedStage.addSettings() {
        addActor(settings)
        settings.setBounds(322f, 1920f, 459f, 172f)
    }

    private fun AdvancedStage.addBoxes() {
        boxes.onEach { it.color.a = 0f }
        addActors(boxes)

        var ny = 1061f
        boxes.onEach {
            it.setBounds(453f, ny, 362f, 87f)
            ny -= 172f + 87f
        }

        // Music
        game.musicUtil.music?.let { mmm ->
            if (mmm.isPlaying) musicBox.check(false)

            musicBox.setOnCheckListener { if (it) {
                mmm.play()
                music.uncheck(false)
            } else {
                mmm.pause()
                music.check(false)
            } }
        }

        // Sound
        if (game.soundUtil.isPause.not()) soundBox.check(false)
        soundBox.setOnCheckListener { game.soundUtil.isPause = !it }

        // Notification
        if (isNotification) notifBox.check(false)
        notifBox.setOnCheckListener { isNotification = it }
    }

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
            music.addAction(Actions.sequence(
                Actions.moveTo(893f, 1731f, duration, Interpolation.swingOut),
                Actions.run { completer.complete() }
            ))
        }

    }.await()

    private suspend fun animSettings(duration: Float) = suspendCoroutine<Unit> { continuation ->
        runGDX {
            settings.addAction(Actions.sequence(
                Actions.moveTo(322f, 1723f, duration, Interpolation.swingOut),
                Actions.run { continuation.resume(Unit) }
            ))
        }
    }


}