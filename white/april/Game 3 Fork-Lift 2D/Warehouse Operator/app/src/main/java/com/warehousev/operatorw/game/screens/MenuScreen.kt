package com.warehousev.operatorw.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.warehousev.operatorw.game.LibGDXGame
import com.warehousev.operatorw.game.actors.checkbox.ACheckBox
import com.warehousev.operatorw.game.utils.TIME_ANIM
import com.warehousev.operatorw.game.utils.actor.animHide
import com.warehousev.operatorw.game.utils.actor.setOnClickListener
import com.warehousev.operatorw.game.utils.advanced.AdvancedScreen
import com.warehousev.operatorw.game.utils.advanced.AdvancedStage
import com.warehousev.operatorw.game.utils.region

class MenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    // Actor
    private val controlPanelImg = Image(game.allAssets.menu)

    override fun show() {
        setBackBackground(game.loaderAssets.GARAGES.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(controlPanelImg)
        controlPanelImg.setBounds(687f, 111f, 547f, 638f)

        val img = Image(game.allAssets.musonchaka)
        img.setBounds(20f, 49f, 374f, 136f)
        addActor(img)

        var ny = 540f

        val scrName = listOf(
            GameScreen::class.java.name,
            RulesScreen::class.java.name,
        )

        val actors = listOf(Actor(), Actor(), Actor())
        actors.forEachIndexed { index, actor ->
            addActor(actor)
            actor.setBounds(687f, ny, 547f, 198f)
            ny -= 42 + 198

            actor.setOnClickListener {
                if (index == 2) game.navigationManager.exit()
                else stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(scrName[index], MenuScreen::class.java.name)
                }
            }
        }



        val boxMus = ACheckBox(this@MenuScreen, ACheckBox.Static.Type.MUSIC)
        val boxSou = ACheckBox(this@MenuScreen, ACheckBox.Static.Type.SOUND)

        addActors(boxMus, boxSou)
        boxMus.setBounds(242f, 75f, 84f, 84f)
        boxSou.setBounds(42f, 52f, 128f, 128f)

        game.musicUtil.music?.let { mmm ->
            if (mmm.isPlaying.not()) boxMus.check(false)

            boxMus.setOnCheckListener { if (it) {
                mmm.pause()
            } else {
                mmm.play()
            } }
        }

        if (game.soundUtil.isPause) boxSou.check(false)
        boxSou.setOnCheckListener { game.soundUtil.isPause = it }
    }

}