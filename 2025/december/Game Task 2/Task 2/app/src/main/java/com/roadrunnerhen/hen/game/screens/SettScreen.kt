package com.roadrunnerhen.hen.game.screens

import com.roadrunnerhen.hen.game.actors.main.AMainSett
import com.roadrunnerhen.hen.game.utils.Block
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedMainScreen
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedStage
import com.roadrunnerhen.hen.game.utils.gdxGame
import com.roadrunnerhen.hen.game.utils.region

class SettScreen: AdvancedMainScreen() {

    override val aMain = AMainSett(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.B_DEF.region)
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