package com.tigerrealm.minimalizm.game.screens

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tigerrealm.minimalizm.game.LibGDXGame
import com.tigerrealm.minimalizm.game.actors.checkbox.ACheckBox
import com.tigerrealm.minimalizm.game.actors.checkbox.ACheckBox.Static.Type
import com.tigerrealm.minimalizm.game.utils.TIME_ANIM
import com.tigerrealm.minimalizm.game.utils.actor.animHide
import com.tigerrealm.minimalizm.game.utils.actor.animShow
import com.tigerrealm.minimalizm.game.utils.actor.setOnClickListener
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedScreen
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedStage
import com.tigerrealm.minimalizm.game.utils.region

class YellowMenuingScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val panel = Image(game.allAssets.YcupleMenu)
    private val music = ACheckBox(this, Type.MUSIC)

    override fun show() {
        stageUI.root.color.a = 0f
        setUIBackground(game.allAssets.YellowMain.region)
        super.show()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addMusic()
        addPanel()
        stageUI.root.animShow(TIME_ANIM)
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addMusic() {
        addActor(music)
        music.setBounds(893f, 1738f, 169f, 169f)

        game.musicUtil.music?.let { mmm ->
            if (mmm.isPlaying.not()) music.check(false)

            music.setOnCheckListener { if (it) mmm.pause() else mmm.play() }
        }
    }

    private fun AdvancedStage.addPanel() {
        addActor(panel)
        panel.setBounds(6f, 181f, 1056f, 1333f)

        val names = listOf(
            YellowLevelingScreen::class.java.name,
            YellowRulesingScreen::class.java.name,
            YellowSettingsingScreen::class.java.name,
        )

        var ny = 1017f

        names.onEach { screenName ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(359f, ny, 397f, 142f)
            ny -= 90f + 142f

            btn.setOnClickListener(game.soundUtil) { navigateGo(screenName) }
        }

        val exitBtn = Actor()
        addActor(exitBtn)
        exitBtn.apply {
            setBounds(340f, 227f, 385f, 134f)
            setOnClickListener(game.soundUtil) { game.navigationManager.exit() }
        }
    }

    // ------------------------------------------------------------------------
    // Logic
    // ------------------------------------------------------------------------

    private fun navigateGo(screenName: String) {
        stageUI.root.animHide(TIME_ANIM) {
            game.navigationManager.navigate(screenName, YellowMenuingScreen::class.java.name)
        }
    }


}