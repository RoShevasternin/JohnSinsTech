package com.mytholog.mastermy.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.mytholog.mastermy.game.screens.GameScreen
import com.mytholog.mastermy.game.screens.WelcomeScreen
import com.mytholog.mastermy.game.utils.Block
import com.mytholog.mastermy.game.utils.TIME_ANIM
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.setBounds
import com.mytholog.mastermy.game.utils.actor.setOnClickListener
import com.mytholog.mastermy.game.utils.advanced.AdvancedGroup
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen

class APauseGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    private val btnMenu    = AButton(screen, AButton.Static.Type.Menu)
    private val btnRestart = AButton(screen, AButton.Static.Type.Restart)

    var blockPlay = Block {}

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.drawerUtil.getRegion(Color.BLACK.cpy().apply { a = 0.4f })))
        val pause = Image(screen.game.all.PAUSE)
        val play  = Actor()
        addActors(pause, play, btnMenu, btnRestart)

        pause.setBounds(194f,482f,691f,956f)
        play.apply {
            setBounds(419f, 980f, 297f, 335f)
            setOnClickListener(screen.game.soundUtil) {
                blockPlay.invoke()
            }
        }

        btnMenu.setBounds(256f,580f,269f,282f)
        btnMenu.setOnClickListener {
            screen.stageUI.root.animHideScreen(TIME_ANIM) {
                screen.game.navigationManager.clearStack()
                screen.game.navigationManager.navigate(WelcomeScreen::class.java.name)
            }
        }
        btnRestart.setBounds(580f,580f,269f,282f)
        btnRestart.setOnClickListener {
            screen.stageUI.root.animHideScreen(TIME_ANIM) {
                screen.game.navigationManager.navigate(GameScreen::class.java.name)
            }
        }

    }

}