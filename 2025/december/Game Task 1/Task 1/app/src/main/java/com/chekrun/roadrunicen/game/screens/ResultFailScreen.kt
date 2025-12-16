package com.chekrun.roadrunicen.game.screens

import com.chekrun.roadrunicen.game.actors.main.AMainResultFail
import com.chekrun.roadrunicen.game.utils.Block
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedMainScreen
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedStage
import com.chekrun.roadrunicen.game.utils.gdxGame
import com.chekrun.roadrunicen.game.utils.region

class ResultFailScreen: AdvancedMainScreen() {

    override val aMain = AMainResultFail(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_FAIL.region)
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