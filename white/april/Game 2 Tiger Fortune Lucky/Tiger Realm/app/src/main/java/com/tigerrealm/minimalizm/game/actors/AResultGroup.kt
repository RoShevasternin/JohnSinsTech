package com.tigerrealm.minimalizm.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.tigerrealm.minimalizm.game.screens.YellowLevelingScreen
import com.tigerrealm.minimalizm.game.utils.TIME_ANIM
import com.tigerrealm.minimalizm.game.utils.actor.animHide
import com.tigerrealm.minimalizm.game.utils.actor.disable
import com.tigerrealm.minimalizm.game.utils.actor.enable
import com.tigerrealm.minimalizm.game.utils.actor.setOnClickListener
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedGroup
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedScreen
import com.tigerrealm.minimalizm.game.utils.font.FontParameter

class AResultGroup(override val screen: AdvancedScreen, font: BitmapFont): AdvancedGroup() {

    private val result = Image()
    private val score  = Label("0", Label.LabelStyle(font, Color.WHITE))

    // Field
    private var isNext = true

    override fun addActorsOnGroup() {
        disable()
        color.a = 0f

        addAndFillActor(result)
        addBtns()

        addActor(score)
        score.apply {
            setAlignment(Align.center)
            setBounds(436f, 895f, 194f, 134f)
        }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun addBtns() {
        val restart = Actor()
        addActor(restart)
        restart.apply {
            setBounds(247f, 651f, 183f, 183f)
            setOnClickListener {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(YellowLevelingScreen::class.java.name)
                }
            }
        }

        if (isNext.not()) return

        val next = Actor()
        addActor(next)
        next.apply {
            setBounds(650f, 651f, 183f, 183f)
            setOnClickListener {
                screen.stageUI.root.animHide(TIME_ANIM) {
                    screen.game.navigationManager.navigate(YellowLevelingScreen::class.java.name)
                }
            }
        }
    }

    fun setResult(isVictory: Boolean, scoreValue: Int) {
        enable()
        score.setText(scoreValue)
        isNext = isVictory
        result.drawable = TextureRegionDrawable(if (isVictory) screen.game.allAssets.YellowVictory else screen.game.allAssets.YellowDefeat)
        if (isVictory) screen.game.soundUtil.apply { play(s_bonus) } else screen.game.soundUtil.apply { play(s_lose) }
    }

}