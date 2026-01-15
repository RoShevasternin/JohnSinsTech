package com.roadrunnerhen.hen.game.actors.main

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.roadrunnerhen.hen.game.actors.ATimer
import com.roadrunnerhen.hen.game.actors.WTF
import com.roadrunnerhen.hen.game.actors.button.AButton
import com.roadrunnerhen.hen.game.screens.GameScreen
import com.roadrunnerhen.hen.game.screens.ResultDoneScreen
import com.roadrunnerhen.hen.game.screens.ResultFailScreen
import com.roadrunnerhen.hen.game.utils.Block
import com.roadrunnerhen.hen.game.utils.TIME_ANIM_SCREEN
import com.roadrunnerhen.hen.game.utils.actor.animDelay
import com.roadrunnerhen.hen.game.utils.actor.animHide
import com.roadrunnerhen.hen.game.utils.actor.animShow
import com.roadrunnerhen.hen.game.utils.actor.disable
import com.roadrunnerhen.hen.game.utils.actor.enable
import com.roadrunnerhen.hen.game.utils.actor.setOnClickListener
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedMainGroup
import com.roadrunnerhen.hen.game.utils.gdxGame
import com.roadrunnerhen.hen.game.utils.region

class AMainGame(override val screen: GameScreen): AdvancedMainGroup() {

    private val imgPanel  = Image(gdxGame.assetsAll.GAME)
    private val btnBack   = AButton(screen, AButton.Type.X)
    private val aTimer    = ATimer(screen)

    data class Data(
        val id    : Int,
        val region: TextureRegion,
    )

    private val list15   by lazy { List(8) { index -> Data(index.inc(), gdxGame.assetsAll.listPuzzles[index].region) } }
    private val dataList by lazy { list15 + list15 }

    private var firstOpenClose : WTF? = null
    private var secondOpenClose: WTF? = null

    private var firstData : Data?    = null
    private var secondData: Data?    = null

    private var countPair = 0

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgPanel()
        addBtnBack()
        addATimer()
        addItems()

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------


    private fun addItems() {
        var newX = 143f
        var newY = 1160f

        dataList.shuffled().onEachIndexed { index, data ->
            WTF(screen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                item.setBounds(newX, newY, 144f, 144f)
                newX += 71 + 144

                if (index.inc() % 4 == 0) {
                    newX = 143f
                    newY -= 71 + 144
                }

                item.setOnClickListener {
                    gdxGame.soundUtil.apply { play(click_2) }

                    item.disable()
                    item.open {
                        if (firstOpenClose == null) {
                            firstOpenClose = item
                            firstData      = data
                        } else {
                            this.disable()
                            secondOpenClose = item
                            secondData      = data

                            if (firstData?.id != secondData?.id) {
                                this.clearActions()

                                // fail
                                gdxGame.soundUtil.apply { play(fail) }

                                animDelay(0.4f) {
                                    firstOpenClose?.enable()
                                    secondOpenClose?.enable()

                                    firstOpenClose?.close()
                                    secondOpenClose?.close {
                                        firstOpenClose  = null
                                        secondOpenClose = null
                                        this.enable()
                                    }
                                }
                            } else {
                                this.clearActions()

                                // win
                                gdxGame.soundUtil.apply { play(win) }

                                animDelay(0.4f) {
                                    this.enable()

                                    firstOpenClose  = null
                                    secondOpenClose = null

                                    if (++countPair == 8) gdxGame.navigationManager.navigate(ResultDoneScreen::class.java.name)
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(121f, -16f, 1014f, 1549f)
    }

    private fun addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(96f, 1724f, 100f, 100f)

        btnBack.setOnClickListener {
            screen.hideScreen {
                gdxGame.navigationManager.back()
            }
        }
    }

    private fun addATimer() {
        addActor(aTimer)
        aTimer.setBounds(465f, 1419f, 152f, 74f)

        aTimer.start()

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