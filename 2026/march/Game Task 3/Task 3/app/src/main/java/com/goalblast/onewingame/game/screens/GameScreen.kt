package com.goalblast.onewingame.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.goalblast.onewingame.game.actors.ATimer
import com.goalblast.onewingame.game.actors.ATmpGroup
import com.goalblast.onewingame.game.actors.checkbox.ACheckBox
import com.goalblast.onewingame.game.box2d.AbstractBody
import com.goalblast.onewingame.game.box2d.AbstractJoint
import com.goalblast.onewingame.game.box2d.BodyId
import com.goalblast.onewingame.game.box2d.bodies.BBag
import com.goalblast.onewingame.game.box2d.bodies.BEgg
import com.goalblast.onewingame.game.box2d.bodies.BEnemy
import com.goalblast.onewingame.game.box2d.bodies.standart.BStatic
import com.goalblast.onewingame.game.utils.AlignH
import com.goalblast.onewingame.game.utils.AlignV
import com.goalblast.onewingame.game.utils.Block
import com.goalblast.onewingame.game.utils.TIME_ANIM_SCREEN
import com.goalblast.onewingame.game.utils.actor.addActorAligned
import com.goalblast.onewingame.game.utils.actor.addActorWithConstraints
import com.goalblast.onewingame.game.utils.actor.addActors
import com.goalblast.onewingame.game.utils.actor.addAndFillActor
import com.goalblast.onewingame.game.utils.actor.animDelay
import com.goalblast.onewingame.game.utils.actor.animHide
import com.goalblast.onewingame.game.utils.actor.animShow
import com.goalblast.onewingame.game.utils.actor.setBounds
import com.goalblast.onewingame.game.utils.advanced.AdvancedMouseScreen
import com.goalblast.onewingame.game.utils.font.FontParameter
import com.goalblast.onewingame.game.utils.gdxGame
import com.goalblast.onewingame.game.utils.runGDX
import com.goalblast.onewingame.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class GameScreen : AdvancedMouseScreen() {

    companion object {
        var counterGoals  = 0
        var counterMissed = 0
    }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS).setSize(36)
    private val font          = fontGenerator_DEF.generateFont(fontParameter)

    // Actor
    private val aSoundBox       = ACheckBox(this, ACheckBox.Type.SOUND)
    private val aMusicBox       = ACheckBox(this, ACheckBox.Type.MUSIC)
    private val aPanelImg       = Image(gdxGame.assetsAll.panel)
    private val aCountGoalsLbl  = Label("0", Label.LabelStyle(font, Color.valueOf("FFFFFF")))
    private val aCountMissedLbl = Label("0", Label.LabelStyle(font, Color.valueOf("FF0000")))
    private val aTimer          = ATimer(this)
    // Body
    private val bStatic = BStatic(this)
    private val bBag    = BBag(this)

    // Joint
    private val jPrismatic = AbstractJoint<PrismaticJoint, PrismaticJointDef>(this)

    // Field
    private val itemFlow = MutableSharedFlow<BEgg>(replay = 15)


    override fun show() {
        counterGoals  = 0
        counterMissed = 0

        setBackBackground(gdxGame.assetsAll.BACKGROUND_GAME)
        super.show()
    }

    override fun Group.addActorsOnStageUI() {
        createB_Static()
        createB_Bag()
        createJ_Prismatic()
        createB_Items()

        addSoundBox()
        addMusicBox()
        addPanel()

        val aArrowsImg = Image(gdxGame.assetsAll.arrows)
        addActorAligned(aArrowsImg, AlignH.CENTER, AlignV.BOTTOM)
        aArrowsImg.y += 65f
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

    private fun Group.addSoundBox() {
        aSoundBox.setSize(88f, 93f)
        addActorWithConstraints(aSoundBox) {
            endToEndOf = this@addSoundBox
            topToTopOf = this@addSoundBox

            marginEnd = 63f
            marginTop = 61f
        }

        if (gdxGame.soundUtil.isPause) aSoundBox.check()

        aSoundBox.setOnCheckListener { isCheck ->
            gdxGame.soundUtil.isPause = isCheck
        }

    }

    private fun Group.addMusicBox() {
        aMusicBox.setSize(88f, 93f)
        addActorWithConstraints(aMusicBox) {
            startToStartOf = this@addMusicBox
            topToTopOf     = this@addMusicBox

            marginStart = 63f
            marginTop   = 61f
        }

        if (gdxGame.musicUtil.currentMusic?.isPlaying == false) aMusicBox.check()

        aMusicBox.setOnCheckListener { isCheck ->
            if (isCheck) gdxGame.musicUtil.currentMusic?.pause() else gdxGame.musicUtil.currentMusic?.play()
        }

    }

    private fun Group.addPanel() {
        val tmpG = ATmpGroup(this@GameScreen)
        tmpG.setSize(377f, 228f)
        addActorAligned(tmpG, AlignH.CENTER, AlignV.TOP)
        tmpG.y -= 100f

        tmpG.addAndFillActor(aPanelImg)
        tmpG.addActors(aCountGoalsLbl, aCountMissedLbl, aTimer)

        aCountGoalsLbl.setBounds(311f, 188f, 39f, 39f)
        aCountMissedLbl.setBounds(321f, 130f, 39f, 39f)
        aTimer.setBounds(156f, 26f, 65f, 62f)
        aTimer.timeOut = {
            if (counterGoals > counterMissed) {
                gdxGame.navigationManager.navigate(ResultDoneScreen::class.java.name)
            } else {
                gdxGame.navigationManager.navigate(ResultFailScreen::class.java.name)
            }
        }
        aTimer.start()
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Static() {
        bStatic.create(-2f, 100f, 1f, 1f)
    }

    private fun createB_Bag() {
        bBag.create(239f, 105f, 603f, 603f)

        bBag.beginContactBlockArray.add(AbstractBody.ContactBlock { b2 ->
            when (b2.id) {
                BodyId.Game.COIN -> {
                    b2 as BEgg
                    if (b2.atomicBoolean.getAndSet(false)) {
                        itemFlow.tryEmit(b2)
                        counterGoals += 1
                        aCountGoalsLbl.setText("$counterGoals")
                        gdxGame.soundUtil.apply { play(bonus) }
                    }
                }
            }
        })
    }

    private fun createB_Items() {
        repeat(15) {
            BEgg(this).also { bItem ->
                bItem.renderBlockArray.add(AbstractBody.RenderBlock {
                    bItem.body?.let {
                        if (it.position.y <= 0f) {
                            if (bItem.atomicBoolean.getAndSet(false)) {

                                counterMissed += 1
                                aCountMissedLbl.setText("$counterMissed")

                                itemFlow.tryEmit(bItem)
                            }
                        }
                    }
                })

                bItem.bodyDef.gravityScale = 0f

                val size = (150..250).random().toFloat()
                bItem.create(-300f, 0f, size, size)

                itemFlow.tryEmit(bItem)
            }
        }

        coroutine?.launch {
            itemFlow.collect { bItem ->
                runGDX {
                    bItem.body?.apply {
                        bItem.setNoneId()

                        this.setLinearVelocity(0f, 0f)
                        this.gravityScale = 0f
                        this.isAwake = false

                        this.setTransform(Vector2(-300f, 0f).toB2, 0f)
                    }
                }
            }
        }
        coroutine?.launch {
            itemFlow.collect { bItem ->
                delay((500L..1000L).random())
                runGDX {
                    bItem.body?.apply {
                        this.setTransform((50..900).random().toFloat().toB2, 1920f.toB2, 0f)

                        this.gravityScale = 1f
                        this.isAwake = true
                    }
                }
                delay(100)
                runGDX {
                    bItem.id = BodyId.Game.COIN
                    bItem.atomicBoolean.set(true)
                }
            }
        }
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Prismatic() {
        jPrismatic.create(PrismaticJointDef().apply {
            bodyA = bStatic.body
            bodyB = bBag.body

            this.localAnchorB.add(0f, -1.8f)

            lowerTranslation = 100f.toB2
            upperTranslation = (1000f).toB2
            enableLimit      = true
        })
    }

}