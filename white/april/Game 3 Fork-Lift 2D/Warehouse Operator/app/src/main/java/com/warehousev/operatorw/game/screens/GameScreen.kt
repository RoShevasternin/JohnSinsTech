package com.warehousev.operatorw.game.screens

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.warehousev.operatorw.game.LibGDXGame
import com.warehousev.operatorw.game.box2d.WorldUtil
import com.warehousev.operatorw.game.box2d.bodies.BBox
import com.warehousev.operatorw.game.box2d.bodiesGroup.BGBorders
import com.warehousev.operatorw.game.box2d.bodiesGroup.BGTractor
import com.warehousev.operatorw.game.box2d.destroyAll
import com.warehousev.operatorw.game.utils.HEIGHT_UI
import com.warehousev.operatorw.game.utils.WIDTH_UI
import com.warehousev.operatorw.game.utils.actor.setOnClickListener
import com.warehousev.operatorw.game.utils.advanced.AdvancedBox2dScreen
import com.warehousev.operatorw.game.utils.advanced.AdvancedStage
import com.warehousev.operatorw.game.utils.region

class GameScreen(override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {

    // Actor
    private val controlPanelImg = Image(game.allAssets.control_panel)

    // Body
    private val bBoxList = List(6) { BBox(this) }

    // BodyGroup
    private val bgBorders = BGBorders(this)
    private val bgTractor = BGTractor(this)

    override fun show() {
        setBackBackground(game.loaderAssets.GARAGES.region)
        super.show()
    }

    override fun dispose() {
        listOf(bgBorders,bgTractor).destroyAll()
        super.dispose()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addControlPanelImg()
        addExit()
        addBtns()

        createBG_Borders()
        createBG_Tractor()

        createB_Box()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addControlPanelImg() {
        addActor(controlPanelImg)
        controlPanelImg.setBounds(0f, 0f, WIDTH, 118f)
    }

    private fun AdvancedStage.addExit() {
        val exitA = Actor()
        addActor(exitA)
        exitA.setBounds(794f, 0f, 333f, 102f)
        exitA.setOnClickListener(game.soundUtil) { game.navigationManager.back() }
    }

    private fun AdvancedStage.addBtns() {
        val right = Actor()
        val left  = Actor()
        val up    = Actor()
        val down  = Actor()
        addActors(right,left,up,down)
        right.apply {
            setBounds(229f, 0f, 146f, 104f)
            setOnClickListener(game.soundUtil) {
                bgTractor.goRight()
            }
        }
        left.apply {
            setBounds(67f, 0f, 156f, 104f)
            setOnClickListener(game.soundUtil) {
                bgTractor.goLeft()
            }
        }
        up.apply {
            setBounds(1725f, 0f, 116f, 104f)
            setOnClickListener(game.soundUtil) {
                bgTractor.goUp()
            }
        }
        down.apply {
            setBounds(1605f, 0f, 116f, 104f)
            setOnClickListener(game.soundUtil) {
                bgTractor.goDown()
            }
        }
    }

    // ---------------------------------------------------
    // create BodyGroup
    // ---------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

    private fun createBG_Tractor() {
        bgTractor.create(49f, 118f, 734f, 501f)
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Box() {
        val posList = listOf(
            Vector2(1193f, 138f),
            Vector2(1454f, 138f),
            Vector2(1715f, 138f),

            Vector2(1323f, 348f),
            Vector2(1584f, 348f),

            Vector2(1454f, 556f),
        )
        bBoxList.onEachIndexed { index, bBox ->
            bBox.create(posList[index], Vector2(186f, 186f))
        }
    }


}