package com.warehousev.operatorw.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.warehousev.operatorw.game.LibGDXGame
import com.warehousev.operatorw.game.utils.TIME_ANIM
import com.warehousev.operatorw.game.utils.actor.animHide
import com.warehousev.operatorw.game.utils.actor.setOnClickListener
import com.warehousev.operatorw.game.utils.advanced.AdvancedScreen
import com.warehousev.operatorw.game.utils.advanced.AdvancedStage
import com.warehousev.operatorw.game.utils.region

class RulesScreen(override val game: LibGDXGame): AdvancedScreen() {

    // Actor
    private val controlPanelImg = Image(game.allAssets.reluser)

    override fun show() {
        setBackBackground(game.loaderAssets.GARAGES.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(controlPanelImg)
        controlPanelImg.setBounds(292f, 135f, 1340f, 771f)

        val img = Actor()
        img.setBounds(758f, 135f, 404f, 146f)
        addActor(img)

        img.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }


}