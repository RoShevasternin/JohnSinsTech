package com.side.swipe.game.screens

import com.badlogic.gdx.math.Vector2
import com.side.swipe.game.LibGDXGame
import com.side.swipe.game.actors.AButton
import com.side.swipe.game.box2d.AbstractBody
import com.side.swipe.game.box2d.BodyId
import com.side.swipe.game.box2d.WorldUtil
import com.side.swipe.game.box2d.bodies.BEnemy
import com.side.swipe.game.box2d.bodies.BCar
import com.side.swipe.game.utils.TIME_ANIM
import com.side.swipe.game.utils.actor.animHide
import com.side.swipe.game.utils.actor.animShow
import com.side.swipe.game.utils.advanced.AdvancedBox2dScreen
import com.side.swipe.game.utils.advanced.AdvancedStage
import com.side.swipe.game.utils.advanced.isBackgroundMove
import com.side.swipe.game.utils.runGDX
import com.side.swipe.game.utils.toB2
import com.side.swipe.game.utils.toUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class MiniGameScreen(override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {

    // Actor
    private val leftBtn  = AButton(this, AButton.Static.Type.LEFT)
    private val rightBtn = AButton(this, AButton.Static.Type.RIGHT)

    // Body
    private val bCar = BCar(this)

    private val bLeftCars  = List(10) { BEnemy(this, true) }
    private val bRightCars = List(10) { BEnemy(this, false) }

    // Field
    private val leftCarsFlow  = MutableSharedFlow<BEnemy>(replay = 10)
    private val rightCarsFlow = MutableSharedFlow<BEnemy>(replay = 10)

    private val leftListX  = listOf(200f, 387f).map { it.toB2 }
    private val rightListX = listOf(589f, 776f).map { it.toB2 }

    override fun show() {
        isBackgroundMove = true
        stageUI.root.animHide()
        super.show()
        stageUI.root.animShow(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createB_LeftCars()
        createB_RightCars()
        createB_Car()

        addBtnImg()

        bLeftCars.onEach { it.addEffect() }
        bRightCars.onEach { it.addEffect() }
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addBtnImg() {
        addActors(leftBtn, rightBtn)
        leftBtn.apply {
            setBounds(0f, 0f, 155f, 663f)

            touchDownBlock = {_,_ ->
                bCar.body?.setLinearVelocity(-5f, 0f)
            }
            touchUpBlock = {_,_ ->
                bCar.body?.setLinearVelocity(0f, 0f)
            }
        }
        rightBtn.apply {
            setBounds(925f, 0f, 155f, 663f)

            touchDownBlock = {_,_ ->
                bCar.body?.setLinearVelocity(5f, 0f)
            }
            touchUpBlock = {_,_ ->
                bCar.body?.setLinearVelocity(0f, 0f)
            }
        }
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_LeftCars() {
        bLeftCars.onEach { bLeft ->
//            bA.addEffect()

            bLeft.renderBlockArray.add(AbstractBody.RenderBlock {
                bLeft.body?.let {
                    if (it.position.y <= -1.5f) {
                        if (bLeft.atomicBoolean.getAndSet(false)) leftCarsFlow.tryEmit(bLeft)
                    }
                }
            })

            bLeft.originalId = BodyId.ENEMY
            bLeft.setNoneId()

            bLeft.bodyDef.gravityScale = 0f
            bLeft.create(-200f, HEIGHT + 100f, 116f, 203f)

            leftCarsFlow.tryEmit(bLeft)
        }

        coroutine?.launch {
            leftCarsFlow.collect { bLeft ->
                runGDX {
                    bLeft.setNoneId()
                    bLeft.body?.apply {
                        setTransform(Vector2(-200f, HEIGHT + 100f).toB2, 0f)

                        setLinearVelocity(0f, 0f)
                        gravityScale = 0f
                        isAwake      = false
                    }
                }
            }
        }
        coroutine?.launch {
            leftCarsFlow.collect { bItem ->
                delay((800L..2000L).random())
                runGDX {
                    bItem.body?.apply {
                        setTransform(leftListX.random(), position.y,0f)
                        gravityScale = (5..10).random() / 10f
                        isAwake      = true
                    }
                }
                delay(100)
                bItem.atomicBoolean.set(true)
                bItem.setOriginalId()
            }
        }
    }

    private fun createB_RightCars() {
        bRightCars.onEach { bRight ->
//            bA.addEffect()

            bRight.renderBlockArray.add(AbstractBody.RenderBlock {
                bRight.body?.let {
                    if (it.position.y <= -1.5f) {
                        if (bRight.atomicBoolean.getAndSet(false)) rightCarsFlow.tryEmit(bRight)
                    }
                }
            })

            bRight.originalId = BodyId.ENEMY
            bRight.setNoneId()

            bRight.bodyDef.gravityScale = 0f
            bRight.create(-200f, HEIGHT + 100f, 116f, 203f)

            rightCarsFlow.tryEmit(bRight)
        }

        coroutine?.launch {
            rightCarsFlow.collect { bRight ->
                runGDX {
                    bRight.setNoneId()

                    bRight.body?.apply {
                        setTransform(Vector2(-200f, HEIGHT + 100f).toB2, 0f)

                        setLinearVelocity(0f, 0f)
                        gravityScale = 0f
                        isAwake      = false
                    }
                }
            }
        }
        coroutine?.launch {
            rightCarsFlow.collect { bItem ->
                delay((800L..2000L).random())
                runGDX {
                    bItem.body?.apply {
                        setTransform(rightListX.random(), position.y,0f)
                        gravityScale = (5..10).random() / 10f
                        isAwake      = true
                    }
                }
                delay(100)
                bItem.atomicBoolean.set(true)
                bItem.setOriginalId()
            }
        }
    }

    private fun createB_Car() {
        bCar.create(590f, 218f, 116f, 203f)

        val minX = 175f.toB2 + bCar.center.x
        val maxX = 801f.toB2 + bCar.center.x

        bCar.renderBlockArray.add(AbstractBody.RenderBlock {
            if (bCar.body!!.position.x <= minX || bCar.body!!.position.x >= maxX) bCar.body?.setLinearVelocity(0f, 0f)
        })

        bCar.beginContactBlockArray.add(AbstractBody.ContactBlock { enemy, contact ->
            if (enemy.id == BodyId.ENEMY) {
                (enemy as BEnemy).startEffect(contact.worldManifold.points.first().toUI)
            }
        })
    }

}