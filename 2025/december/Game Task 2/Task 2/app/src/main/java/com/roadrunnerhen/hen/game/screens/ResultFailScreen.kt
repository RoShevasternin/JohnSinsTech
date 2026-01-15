package com.roadrunnerhen.hen.game.screens

import com.roadrunnerhen.hen.game.actors.main.AMainResultFail
import com.roadrunnerhen.hen.game.actors.main.AMainSelecte
import com.roadrunnerhen.hen.game.utils.Block
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedMainScreen
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedStage
import com.roadrunnerhen.hen.game.utils.gdxGame
import com.roadrunnerhen.hen.game.utils.region

class ResultFailScreen: AdvancedMainScreen() {

    override val aMain = AMainResultFail(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(if (AMainSelecte.INDEX == 0) gdxGame.assetsAll.B_GAME1_LOSE.region else gdxGame.assetsAll.B_GAME2_LOSE.region)
        //addAndFillActor(Image(gdxGame.assetsAll.WIN))
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