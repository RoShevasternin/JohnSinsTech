package com.busy.beefly.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.busy.beefly.game.LibGDXGame
import com.busy.beefly.game.actors.AButton
import com.busy.beefly.game.actors.checkbox.ACheckBox
import com.busy.beefly.game.actors.checkbox.gdxGame
import com.busy.beefly.game.utils.TIME_ANIM
import com.busy.beefly.game.utils.actor.animHide
import com.busy.beefly.game.utils.actor.animShow
import com.busy.beefly.game.utils.actor.setBounds
import com.busy.beefly.game.utils.actor.setOnClickListener
import com.busy.beefly.game.utils.advanced.AdvancedScreen
import com.busy.beefly.game.utils.advanced.AdvancedStage
import com.busy.beefly.game.utils.advanced.isBlue
import com.busy.beefly.game.utils.region

class BeeMenuScreen(override val game: LibGDXGame): AdvancedScreen() {

    private val playImg = Image(game.allAssets.play)
    private val exitImg = Image(game.allAssets.exit)

    private val btnRules = AButton(this, AButton.Type.Rules)
    private val cbMus    = ACheckBox(this, ACheckBox.Type.MUS)

    override fun show() {
        isBlue = false
        stageUI.root.animHide()
        setBackBackground(game.startAssets.YELLOW.region)
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addActor(btnRules)
        btnRules.setBounds(173f, 381f, 318f, 298f)
        btnRules.setOnClickListener {
            stageUI.root.animHide(TIME_ANIM) {
                game.navigationManager.navigate(BeeRulesScreen::class.java.name, BeeMenuScreen::class.java.name)
            }
        }

        addActor(cbMus)
        cbMus.setBounds(20f, 30f, 171f, 160f)
        if (gdxGame.musicUtil.music?.isPlaying == false) cbMus.check()
        cbMus.setOnCheckListener {
            if (it) gdxGame.musicUtil.music?.pause() else gdxGame.musicUtil.music?.play()
        }


        addActors(playImg, exitImg)
        playImg.apply {
            setBounds(680f, 260f, 560f, 560f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.navigate(BeeGameScreen::class.java.name, BeeMenuScreen::class.java.name)
                }
            }
        }
        exitImg.apply {
            setBounds(1575f, 73f, 293f, 293f)
            setOnClickListener(game.soundUtil) {
                stageUI.root.animHide(TIME_ANIM) {
                    game.navigationManager.exit()
                }
            }
        }
    }

}