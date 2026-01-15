package com.roadrunnerhen.hen.game.screens

import com.roadrunnerhen.hen.game.actors.main.AMainSelecte
import com.roadrunnerhen.hen.game.actors.main.AMainSett
import com.roadrunnerhen.hen.game.utils.Block
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedMainScreen
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedStage
import com.roadrunnerhen.hen.game.utils.gdxGame
import com.roadrunnerhen.hen.game.utils.region

class SelecteScreen: AdvancedMainScreen() {

    override val aMain = AMainSelecte(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.B_BLUR.region)
        addMain()
    }

    override fun hideScreen(block: Block) {
        aMain.animHideMain { block.invoke() }
    }

    // Actors UI ------------------------------------------------------------------------

    override fun AdvancedStage.addMain() {
        addAndFillActor(aMain)
    }
}