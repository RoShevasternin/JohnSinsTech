package com.chekrun.roadrunicen.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.chekrun.roadrunicen.game.actors.button.AButton
import com.chekrun.roadrunicen.game.actors.checkbox.ACheckBox
import com.chekrun.roadrunicen.game.screens.PuzzleScreen
import com.chekrun.roadrunicen.game.screens.MenuScreen
import com.chekrun.roadrunicen.game.screens.RulesScreen
import com.chekrun.roadrunicen.game.utils.Acts
import com.chekrun.roadrunicen.game.utils.Block
import com.chekrun.roadrunicen.game.utils.TIME_ANIM_SCREEN
import com.chekrun.roadrunicen.game.utils.actor.animDelay
import com.chekrun.roadrunicen.game.utils.actor.animHide
import com.chekrun.roadrunicen.game.utils.actor.animMoveTo
import com.chekrun.roadrunicen.game.utils.actor.animShow
import com.chekrun.roadrunicen.game.utils.actor.setOnClickListener
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedMainGroup
import com.chekrun.roadrunicen.game.utils.gdxGame

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val listBtnType = listOf(AButton.Type.Play, AButton.Type.Rules, AButton.Type.Privacy)

    private val imgMenu    = Image(gdxGame.assetsAll.MENU_PAN)
    private val listBtn    = List(3) { AButton(screen, listBtnType[it]) }
    private val music      = ACheckBox(screen, ACheckBox.Type.Music)
    private val sound      = ACheckBox(screen, ACheckBox.Type.Sound)
    private val imgChicken = Image(gdxGame.assetsAll.CHICKEN)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtn()
        addMusic()
        addSound()
        addImgChicken()

        animShowMain {
            imgChicken.animMoveTo(-53f, -39f, 0.7f)
            music.animMoveTo(music.x, 1711f, 0.5f)
            sound.animMoveTo(sound.x, 1711f, 0.5f)
        }
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgMenu)
        imgMenu.setBounds(112f, 606f, 860f, 1066f)
    }

    private fun addImgChicken() {
        addActor(imgChicken)
        imgChicken.setBounds(-800f, -800f, 765f, 765f)

        imgChicken.addAction(Acts.forever(Acts.sequence(
            Acts.moveBy(15f, 20f, 0.8f),
            Acts.moveBy(-15f, -20f, 0.7f),
        )))
    }

    private fun addBtn() {
        listBtn.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> {
                    image.setBounds(286f, 1190f, 513f, 198f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(PuzzleScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                1 -> {
                    image.setBounds(286f, 961f, 513f, 198f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        screen.hideScreen {
                            gdxGame.navigationManager.navigate(RulesScreen::class.java.name, screen::class.java.name)
                        }
                    }
                }
                2 -> {
                    image.setBounds(286f, 733f, 513f, 198f)
                    image.setOnClickListener(gdxGame.soundUtil) {
                        gdxGame.activity.webViewHelper.loadUrl("file:///android_asset/privacy_policy.html", false)
                    }
                }
            }
        }
    }


    private fun addMusic() {
        addActor(music)
        music.setBounds(886f, 2000f, 150f, 150f)
        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) music.check()
        music.setOnCheckListener {
            if (it) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }
    }

    private fun addSound() {
        addActor(sound)
        sound.setBounds(71f, 2000f, 150f, 150f)
        if (gdxGame.soundUtil.isPause) sound.check()
        sound.setOnCheckListener {
            gdxGame.soundUtil.isPause = it
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