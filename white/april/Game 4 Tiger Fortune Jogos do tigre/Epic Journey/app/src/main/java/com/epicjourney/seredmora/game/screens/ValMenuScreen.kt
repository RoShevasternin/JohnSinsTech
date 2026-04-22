package com.epicjourney.seredmora.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.epicjourney.seredmora.game.LibGDXGame
import com.epicjourney.seredmora.game.utils.TIME_ANIM
import com.epicjourney.seredmora.game.utils.actor.animHide
import com.epicjourney.seredmora.game.utils.actor.animShow
import com.epicjourney.seredmora.game.utils.actor.setOnClickListener
import com.epicjourney.seredmora.game.utils.advanced.AdvancedStage
import com.epicjourney.seredmora.game.utils.region

class ValMenuScreen(_game: LibGDXGame) : IPanelScreen(_game) {

    private val menuImg = Image(game.allAssets.VAL_MENU).apply { color.a = 0f }

    override fun AdvancedStage.addActorsOnStage() {
        addMenu()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addMenu() {
        addActor(menuImg)
        menuImg.setBounds(179f, 612f, 670f, 892f)
        menuImg.animShow(TIME_ANIM)

        val names = listOf(
            ValLevelScreen::class.java.name,
            ValSettingsScreen::class.java.name,
            ValRulesScreen::class.java.name,
        )

        var ny = 1070f

        names.onEach { screenName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(179f, ny, 670f, 133f)
            ny -= 96f + 133f

            btn.setOnClickListener(game.soundUtil) { navToByScreenName(screenName) }
        }

        val xBtn = Actor()
        addActor(xBtn)
        xBtn.apply {
            setBounds(812f, 350f, 171f, 175f)
            setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navToByScreenName(screenName: String) {
        menuImg.animHide(TIME_ANIM) {
            if (screenName == ValLevelScreen::class.java.name) panelImg.animHide(TIME_ANIM) {
                game.navigationManager.navigate(screenName, ValMenuScreen::class.java.name)
            } else game.navigationManager.navigate(screenName, ValMenuScreen::class.java.name)
        }
    }


}