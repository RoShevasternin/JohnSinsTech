package com.epicjourney.seredmora.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.epicjourney.seredmora.game.LibGDXGame
import com.epicjourney.seredmora.game.utils.advanced.AdvancedScreen
import com.epicjourney.seredmora.game.utils.advanced.AdvancedStage
import com.epicjourney.seredmora.game.utils.region

abstract class IPanelScreen(final override val game: LibGDXGame) : AdvancedScreen() {

    val panelImg = Image(game.allAssets.VAL_PANEL)

    override fun show() {
        setBackgrounds(game.loadingAssets.ValBackground.region)
        super.show()
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        addPanel()
        addActorsOnStage()
    }

    abstract fun AdvancedStage.addActorsOnStage()

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addPanel() {
        addActor(panelImg)
        panelImg.setBounds(90f, 325f, 907f, 1208f)
    }

}