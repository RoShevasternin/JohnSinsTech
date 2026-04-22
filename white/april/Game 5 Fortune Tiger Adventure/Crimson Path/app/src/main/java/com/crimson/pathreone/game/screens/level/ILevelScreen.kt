package com.crimson.pathreone.game.screens.level

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.crimson.pathreone.game.LibGDXGame
import com.crimson.pathreone.game.actors.ATimer
import com.crimson.pathreone.game.actors.TmpGroup
import com.crimson.pathreone.game.actors.button.AButton
import com.crimson.pathreone.game.actors.checkbox.ACheckBox
import com.crimson.pathreone.game.screens.IncasResultScreen
import com.crimson.pathreone.game.screens.level.ILevelScreen.Static.TypeScreen.*
import com.crimson.pathreone.game.utils.Layout
import com.crimson.pathreone.game.utils.TIME_ANIM
import com.crimson.pathreone.game.utils.actor.animHide
import com.crimson.pathreone.game.utils.actor.animShow
import com.crimson.pathreone.game.utils.actor.setBounds
import com.crimson.pathreone.game.utils.advanced.AdvancedGroup
import com.crimson.pathreone.game.utils.advanced.AdvancedScreen
import com.crimson.pathreone.game.utils.advanced.AdvancedStage
import com.crimson.pathreone.game.utils.region

abstract class ILevelScreen(
    final override val game: LibGDXGame,
    val typeScreen: Static.TypeScreen,
) : AdvancedScreen() {

    private val nameImg  = Image(getNameRegionByTypeScreen())
    private val panelImg = Image(getPanelTextureByTypeScreen())
    private val menuBtn  by lazy { AButton(this, AButton.Static.Type.MENU) }
    private val pauseBox by lazy { ACheckBox(this, ACheckBox.Static.Type.PAUSE) }
    private val timer    by lazy { ATimer(this) }

    protected val tmpGroup by lazy { TmpGroup(this).apply { color.a = 0f } }

    override fun show() {
        setBackBackground(game.loadingAssets.IncasBackground.region)
        super.show()
    }

    final override fun AdvancedStage.addActorsOnStageUI() {
        addNameImg()
        addPanelImg()
        addTimer()

        addAndFillActor(tmpGroup)
        tmpGroup.addActorsOnTmpGroup()
        tmpGroup.animShow(TIME_ANIM) { timer.goTime {
            IncasResultScreen.isWin = false
            game.navigationManager.navigate(IncasResultScreen::class.java.name)
        } }

        addMenuBtn()
        addPauseBox()
    }

    abstract fun AdvancedGroup.addActorsOnTmpGroup()

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun AdvancedStage.addNameImg() {
        addActor(nameImg)
        nameImg.setBounds(315f, 1667f, 451f, 152f)
    }

    private fun AdvancedStage.addPanelImg() {
        addActor(panelImg)

        val layoutData: Layout.LayoutData = when (typeScreen) {
            EASY   -> Layout.LayoutData(92f,119f,936f,1394f)
            NORMAL -> Layout.LayoutData(76f,119f,933f,1394f)
            HARD   -> Layout.LayoutData(76f,97f,953f,1416f)
        }

        panelImg.setBounds(layoutData)
    }

    private fun AdvancedStage.addMenuBtn() {
        addActor(menuBtn)
        menuBtn.setBounds(80f, 1668f, 159f, 168f)
        menuBtn.setOnClickListener { game.navigationManager.back() }
    }

    private fun AdvancedStage.addPauseBox() {
        addActor(pauseBox)
        pauseBox.setBounds(857f, 1668f, 159f, 168f)
        pauseBox.setOnCheckListener { timer.isPause = it }
    }

    private fun AdvancedStage.addTimer() {
        addActor(timer)
        timer.setBounds(368f, 1355f, 348f, 152f)
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getNameRegionByTypeScreen(): TextureRegion = when (typeScreen) {
        EASY   -> game.allAssets.levels[0]
        NORMAL -> game.allAssets.levels[1]
        HARD   -> game.allAssets.levels[2]
    }

    private fun getPanelTextureByTypeScreen(): Texture = when (typeScreen) {
        EASY   -> game.allAssets.sisi
        NORMAL -> game.allAssets.mini
        HARD   -> game.allAssets.bigi
    }

    protected fun animHideTmpGroup(block: () -> Unit) {
        tmpGroup.animHide(TIME_ANIM, block)
    }

    // ---------------------------------------------------
    // Class
    // ---------------------------------------------------

    object Static {
        enum class TypeScreen {
            EASY, NORMAL, HARD
        }
    }

}