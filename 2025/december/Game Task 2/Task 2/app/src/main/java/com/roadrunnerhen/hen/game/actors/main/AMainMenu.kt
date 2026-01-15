package com.roadrunnerhen.hen.game.actors.main

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.roadrunnerhen.hen.game.screens.GameScreen
import com.roadrunnerhen.hen.game.screens.MenuScreen
import com.roadrunnerhen.hen.game.screens.SelecteScreen
import com.roadrunnerhen.hen.game.screens.SettScreen
import com.roadrunnerhen.hen.game.utils.Block
import com.roadrunnerhen.hen.game.utils.TIME_ANIM_SCREEN
import com.roadrunnerhen.hen.game.utils.actor.animDelay
import com.roadrunnerhen.hen.game.utils.actor.animHide
import com.roadrunnerhen.hen.game.utils.actor.animShow
import com.roadrunnerhen.hen.game.utils.actor.setOnClickListener
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedMainGroup
import com.roadrunnerhen.hen.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val imgMenu    = Image(gdxGame.assetsAll.menu)
    private val listBtn    = List(3) { Actor() }

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtn()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(270f, 659f, 540f, 790f)
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(270f, 1078f, 540f, 181f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(SelecteScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(270f, 868f, 540f, 181f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(SettScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                2 -> {
                    image.setBounds(270f, 659f, 540f, 181f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        gdxGame.activity.webViewHelper.loadUrl("file:///android_asset/privacy_policy.html", false)
                    }
                }
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}