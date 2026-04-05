package com.side.swipe.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.side.swipe.game.LibGDXGame
import com.side.swipe.game.utils.TIME_ANIM
import com.side.swipe.game.utils.actor.animHide
import com.side.swipe.game.utils.actor.animShow
import com.side.swipe.game.utils.actor.setOnClickListener
import com.side.swipe.game.utils.advanced.AdvancedScreen
import com.side.swipe.game.utils.advanced.AdvancedStage
import com.side.swipe.game.utils.region

class MiniMenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    // Actor
    private val imgMenu = Image(game.allAssets.menu)

    override fun show() {
        setBackBackground(game.loaderAssets.mini.region)
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(imgMenu)
        imgMenu.setBounds(285f, 450f, 511f, 947f)

        var ny = 1176f

        val scrName = listOf(
            MiniGameScreen::class.java.name,
            MiniRulesScreen::class.java.name,
            MiniSettScreen::class.java.name,
        )

        val actors = listOf(Actor(), Actor(), Actor(), Actor())
        actors.forEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(285f, ny, 511f, 221f)
            ny -= 21 + 221

            actor.setOnClickListener {
                if (index == 3) game.navigationManager.exit()
                else stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(scrName[index], MiniMenuScreen::class.java.name)
                }
            }
        }
    }

}