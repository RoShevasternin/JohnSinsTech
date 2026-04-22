package com.crimson.pathreone.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.crimson.pathreone.game.LibGDXGame
import com.crimson.pathreone.game.screens.common.IncasLevelScreen
import com.crimson.pathreone.game.screens.common.IncasMenuScreen
import com.crimson.pathreone.game.utils.actor.setOnClickListener
import com.crimson.pathreone.game.utils.advanced.AdvancedScreen
import com.crimson.pathreone.game.utils.advanced.AdvancedStage
import com.crimson.pathreone.game.utils.region

class IncasResultScreen(override val game: LibGDXGame): AdvancedScreen() {

    companion object {
        var isWin = true
    }

    override fun show() {
        setBackgrounds(if (isWin) game.allAssets.IncasPerfectly.region else game.allAssets.IncasVeryBad.region)
        super.show()
        if (isWin) game.soundUtil.apply { play(goodresult) } else game.soundUtil.apply { play(wrong_answer) }
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        val play = Actor()
        val exit = Actor()
        addActors(play, exit)

        play.apply {
            setBounds(304f, 912f, 494f, 158f)
            setOnClickListener(game.soundUtil) { game.navigationManager.navigate(IncasLevelScreen::class.java.name) }
        }
        exit.apply {
            setBounds(304f, 695f, 494f, 158f)
            setOnClickListener(game.soundUtil) { game.navigationManager.navigate(IncasMenuScreen::class.java.name) }
        }
    }

}