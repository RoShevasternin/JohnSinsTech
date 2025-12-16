package com.chekrun.roadrunicen.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.chekrun.roadrunicen.game.actors.ATimer
import com.chekrun.roadrunicen.game.actors.button.AButton
import com.chekrun.roadrunicen.game.actors.puzzle.APuzzlePanel
import com.chekrun.roadrunicen.game.screens.PuzzleScreen
import com.chekrun.roadrunicen.game.screens.ResultDoneScreen
import com.chekrun.roadrunicen.game.screens.ResultFailScreen
import com.chekrun.roadrunicen.game.utils.Acts
import com.chekrun.roadrunicen.game.utils.Block
import com.chekrun.roadrunicen.game.utils.TIME_ANIM_SCREEN
import com.chekrun.roadrunicen.game.utils.actor.animDelay
import com.chekrun.roadrunicen.game.utils.actor.animHide
import com.chekrun.roadrunicen.game.utils.actor.animMoveTo
import com.chekrun.roadrunicen.game.utils.actor.animShow
import com.chekrun.roadrunicen.game.utils.actor.disable
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedMainGroup
import com.chekrun.roadrunicen.game.utils.gdxGame
import com.chekrun.roadrunicen.game.utils.puzzle.Puzzles
import com.chekrun.roadrunicen.game.utils.region

class AMainPuzzle(override val screen: PuzzleScreen): AdvancedMainGroup() {

    private val puzzleRegion = gdxGame.assetsAll.listPuzzles.random().region

    private val imgPanel  = Image(gdxGame.assetsAll.GAME_PAN)
    private val btnBack   = AButton(screen, AButton.Type.X)

    private val puzzlesPanel = APuzzlePanel(screen, puzzleRegion)
    private val aTimer       = ATimer(screen)

    private val imgChicken  = Image(gdxGame.assetsAll.CHICKEN)
    private val imgKoki     = Image(gdxGame.assetsAll.KOKI)

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPanel()
        addBtnBack()
        addPuzzlePanel()
        addATimer()

        addImgKoki()
        addImgChicken()

        animShowMain {
            btnBack.animMoveTo(btnBack.x, 1709f, 0.5f)
            imgKoki.animMoveTo(imgKoki.x, -103f, 0.5f)
            imgChicken.animMoveTo(139f, -77f, 0.5f)

            aTimer.start()
        }
    }

    override fun dispose() {
        Puzzles.dispose()
        super.dispose()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgKoki() {
        addActor(imgKoki)
        imgKoki.setBounds(-114f, -600f, 1297f, 526f)

        imgKoki.addAction(Acts.forever(Acts.sequence(
            Acts.moveBy(0f, -20f, 0.6f),
            Acts.moveBy(0f, 20f, 0.6f),
        )))
    }

    private fun addImgChicken() {
        addActor(imgChicken)
        imgChicken.setBounds(-765f, -765f, 765f, 765f)

        imgChicken.disable()

        imgChicken.addAction(Acts.forever(Acts.sequence(
            Acts.moveBy(0f, 20f, 0.6f),
            Acts.moveBy(0f, -20f, 0.6f),
        )))
    }

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(96f, 520f, 889f, 880f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(54f, 2000f, 150f, 150f)

        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addPuzzlePanel() {
        addActor(puzzlesPanel)
        puzzlesPanel.setBounds(198f, 618f, 684f, 684f)

        puzzlesPanel.finishBlock = {
            this.disable()

            //gdxGame.soundUtil.apply { play(win) }

            animDelay(0.35f) {
                screen.hideScreen {
                    gdxGame.navigationManager.navigate(ResultDoneScreen::class.java.name)
                }
            }
        }
    }

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(281f, 1456f, 527f, 202f)

        aTimer.timeOut = {
            screen.hideScreen {
                gdxGame.navigationManager.navigate(ResultFailScreen::class.java.name)
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