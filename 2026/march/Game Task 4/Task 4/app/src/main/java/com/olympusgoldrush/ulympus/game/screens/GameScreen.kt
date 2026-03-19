package com.olympusgoldrush.ulympus.game.screens

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.olympusgoldrush.ulympus.game.actors.ATimer
import com.olympusgoldrush.ulympus.game.actors.ATmpGroup
import com.olympusgoldrush.ulympus.game.actors.WTF
import com.olympusgoldrush.ulympus.game.actors.button.AButton
import com.olympusgoldrush.ulympus.game.utils.AlignH
import com.olympusgoldrush.ulympus.game.utils.AlignV
import com.olympusgoldrush.ulympus.game.utils.Block
import com.olympusgoldrush.ulympus.game.utils.TIME_ANIM_SCREEN
import com.olympusgoldrush.ulympus.game.utils.actor.addActorAligned
import com.olympusgoldrush.ulympus.game.utils.actor.addActorWithConstraints
import com.olympusgoldrush.ulympus.game.utils.actor.addAndFillActor
import com.olympusgoldrush.ulympus.game.utils.actor.animDelay
import com.olympusgoldrush.ulympus.game.utils.actor.animHide
import com.olympusgoldrush.ulympus.game.utils.actor.animShow
import com.olympusgoldrush.ulympus.game.utils.actor.disable
import com.olympusgoldrush.ulympus.game.utils.actor.enable
import com.olympusgoldrush.ulympus.game.utils.actor.setOnClickListener
import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedScreen
import com.olympusgoldrush.ulympus.game.utils.gdxGame
import com.olympusgoldrush.ulympus.game.utils.region

class GameScreen : AdvancedScreen() {

    private val aXBtn      = AButton(this, AButton.Type.X)

    private val aTmpGroup  = ATmpGroup(this)
    private val aPanelImg  = Image(gdxGame.assetsAll.game_pan)
    private val aTimer     = ATimer(this)

    data class Data(
        val id    : Int,
        val region: TextureRegion,
    )

    private val list15   by lazy { List(8) { index -> Data(index.inc(), gdxGame.assetsAll.listItem[index].region) } }
    private val dataList by lazy { list15 + list15 }

    private var firstOpenClose : WTF? = null
    private var secondOpenClose: WTF? = null

    private var firstData : Data?    = null
    private var secondData: Data?    = null

    private var countPair = 0

    // ------------------------------------------------------------------------
    // Lifecycle
    // ------------------------------------------------------------------------

    override fun show() {
        setBackBackground(gdxGame.assetsAll.background_game)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        color.a = 0f

        addPanelGame()
        addXBtn()

        animShowScreen()
    }

    // ------------------------------------------------------------------------
    // Screen Animations
    // ------------------------------------------------------------------------
    override fun animHideScreen(blockEnd: Block) {
        stageUI.root.animHide(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animShowScreen(blockEnd: Block) {
        stageUI.root.animShow(TIME_ANIM_SCREEN)
        stageUI.root.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    // ------------------------------------------------------------------------
    // Add Actors
    // ------------------------------------------------------------------------

    private fun Group.addXBtn() {
        aXBtn.setSize(130f, 133f)
        addActorWithConstraints(aXBtn) {
            startToStartOf = this@addXBtn
            topToTopOf     = this@addXBtn
            marginStart    = 70f
            marginTop      = 70f
        }
        aXBtn.setOnClickListener { animHideScreen { gdxGame.navigationManager.back() } }
    }

    private fun Group.addPanelGame() {
        aTmpGroup.setSize(777f, 1474f)
        addActorAligned(aTmpGroup, AlignH.CENTER, AlignV.CENTER)

        aTmpGroup.addAndFillActor(aPanelImg)
        aTmpGroup.addTimer()
        aTmpGroup.addItems()
    }

    private fun Group.addTimer() {
        addActor(aTimer)
        aTimer.setBounds(343f, 1278f, 92f, 132f)
        aTimer.start()
        aTimer.timeOut = { animHideScreen { gdxGame.navigationManager.navigate(ResultFailScreen::class.java.name) } }
    }

    private fun Group.addItems() {
        var newX = 22f
        var newY = 951f

        dataList.shuffled().onEachIndexed { index, data ->
            WTF(this@GameScreen).also { item ->
                addActor(item)
                item.image.drawable = TextureRegionDrawable(data.region)

                item.setBounds(newX, newY, 140f, 140f)
                newX += 58f + 140f

                if (index.inc() % 4 == 0) {
                    newX = 22f
                    newY -= 152f + 140f
                }

                item.setOnClickListener(null) {
                    gdxGame.soundUtil.apply { play(bonus) }

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

}