package com.epicjourney.seredmora.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.epicjourney.seredmora.game.LibGDXGame
import com.epicjourney.seredmora.game.utils.TIME_ANIM
import com.epicjourney.seredmora.game.utils.actor.animHide
import com.epicjourney.seredmora.game.utils.actor.animShow
import com.epicjourney.seredmora.game.utils.actor.setOnClickListener
import com.epicjourney.seredmora.game.utils.advanced.AdvancedStage

class ValRulesScreen(_game: LibGDXGame) : IPanelScreen(_game) {

    private val rulesImg = Image(game.allAssets.VAL_RULES).apply { color.a = 0f }

    override fun AdvancedStage.addActorsOnStage() {
        addRules()
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addRules() {
        addActor(rulesImg)
        rulesImg.setBounds(187f, 751f, 628f, 753f)
        rulesImg.animShow(TIME_ANIM)

        val xBtn = Actor()
        addActor(xBtn)
        xBtn.apply {
            setBounds(812f, 350f, 171f, 175f)
            setOnClickListener(game.soundUtil) {
                rulesImg.animHide(TIME_ANIM) { game.navigationManager.back() }
            }
        }
    }

}