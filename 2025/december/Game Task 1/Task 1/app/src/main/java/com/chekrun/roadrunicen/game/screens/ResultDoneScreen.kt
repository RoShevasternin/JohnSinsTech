package com.chekrun.roadrunicen.game.screens

import com.chekrun.roadrunicen.game.actors.main.AMainResultDone
import com.chekrun.roadrunicen.game.utils.Block
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedMainScreen
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedStage
import com.chekrun.roadrunicen.game.utils.gdxGame
import com.chekrun.roadrunicen.game.utils.region

class ResultDoneScreen: AdvancedMainScreen() {

    override val aMain = AMainResultDone(this)

    override fun AdvancedStage.addActorsOnStageUI() {
        setBackBackground(gdxGame.assetsAll.BACKGROUND_WIN.region)
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