package com.warehousev.operatorw.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.warehousev.operatorw.game.box2d.AbstractBody
import com.warehousev.operatorw.game.box2d.AbstractBodyGroup
import com.warehousev.operatorw.game.box2d.AbstractJoint
import com.warehousev.operatorw.game.box2d.BodyId
import com.warehousev.operatorw.game.box2d.bodies.BBigWheel
import com.warehousev.operatorw.game.box2d.bodies.BFork
import com.warehousev.operatorw.game.box2d.bodies.BMiniWheel
import com.warehousev.operatorw.game.box2d.bodies.BTractor
import com.warehousev.operatorw.game.utils.advanced.AdvancedBox2dScreen
import com.warehousev.operatorw.game.utils.toB2

class BGTractor(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = 734f

    private val bTractor   = BTractor(screenBox2d)
    private val bMiniWheel = BMiniWheel(screenBox2d)
    private val bBigWheel  = BBigWheel(screenBox2d)
    private val bFork      = BFork(screenBox2d)

    // Joint
    private val jBigWheel_Revolute  = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    private val jMiniWheel_Revolute = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    private val jFork_Prismatic     = AbstractJoint<PrismaticJoint, PrismaticJointDef>(screenBox2d)

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        initB_Tractor()

        createTractor()
        createWheel()
        createFork()

        createJ_BigWheel_Revolute()
        createJ_MiniWheel_Revolute()
        createJ_Fork_Prismatic()
    }


    // ---------------------------------------------------
    // Init
    // ---------------------------------------------------

    private fun initB_Tractor() {
        arrayOf(bTractor,bMiniWheel,bBigWheel).onEach { it.apply {
            id = BodyId.TRAC
            collisionList.addAll(arrayOf(
                BodyId.BORDERS, BodyId.BOX
            ))
        } }
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createTractor() {
        createBody(bTractor, 0f, 27f, 526f, 474f)
    }

    private fun createWheel() {
        createBody(bMiniWheel, 32f, 0f, 126f, 126f)
        createBody(bBigWheel, 374f, 0f, 150f, 150f)
    }

    private fun createFork() {
        createBody(bFork, 521f, 8f, 213f, 186f)
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_BigWheel_Revolute() {
        createJoint(jBigWheel_Revolute, RevoluteJointDef().apply {
            bodyA = bTractor.body
            bodyB = bBigWheel.body

            localAnchorA.set(Vector2(449f, 48f).subCenter(bodyA))
        })
    }

    private fun createJ_MiniWheel_Revolute() {
        createJoint(jMiniWheel_Revolute, RevoluteJointDef().apply {
            bodyA = bTractor.body
            bodyB = bMiniWheel.body

            localAnchorA.set(Vector2(95f, 36f).subCenter(bodyA))
        })
    }

    private fun createJ_Fork_Prismatic() {
        createJoint(jFork_Prismatic, PrismaticJointDef().apply {
            bodyA = bTractor.body
            bodyB = bFork.body

            localAnchorA.set(Vector2(521f, -27f).subCenter(bodyA))

            localAxisA.set(0f,1f)

            enableLimit = true
            lowerTranslation = 0f
            upperTranslation = 326f.toB2
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private var DIRECTION_H = Direction_H.NONE
    private var DIRECTION_V = Direction_V.NONE

    private var timerH = 0f
    private var timerV = 0f

    private val rbRight = AbstractBody.RenderBlock {
        if (DIRECTION_H == Direction_H.RIGHT) {
            timerH += it
            if (timerH >= 1.5) {
                timerH = 0f
                screenBox2d.game.soundUtil.apply { play(TRACTOR_GO, 25f) }
            }
        }
    }
    private val rbLeft = AbstractBody.RenderBlock {
        if (DIRECTION_H == Direction_H.LEFT) {
            timerH += it
            if (timerH >= 1.5) {
                timerH = 0f
                screenBox2d.game.soundUtil.apply { play(TRACTOR_GO, 25f) }
            }
        }
    }

    private val rbTop = AbstractBody.RenderBlock {
        if (DIRECTION_V == Direction_V.TOP) {
            timerV += it
            if (bFork.body!!.linearVelocity.y >= 1f) {
                if (timerV >= 0.8) {
                    timerV = 0f
                    screenBox2d.game.soundUtil.apply { play(FORK) }
                }
            }
        }
    }
    private val rbDown = AbstractBody.RenderBlock {
        if (DIRECTION_V == Direction_V.DOWN) {
            timerV += it
            if (bFork.body!!.linearVelocity.y <= -1f) {
                if (timerV >= 0.8) {
                    timerV = 0f
                    screenBox2d.game.soundUtil.apply { play(FORK) }
                }
            }
        }
    }

    fun goRight() {
        if (DIRECTION_H == Direction_H.RIGHT) return

        DIRECTION_H = Direction_H.RIGHT
        screenBox2d.game.soundUtil.apply { play(TRACTOR_START, 40f) }
        bTractor.renderBlockArray.removeValue(rbLeft, true)
        bTractor.renderBlockArray.add(rbRight)

        jMiniWheel_Revolute.joint?.apply {
            maxMotorTorque = 1000f
            motorSpeed     = -10f
            enableMotor(true)
        }
        jBigWheel_Revolute.joint?.apply {
            maxMotorTorque = 1000f
            motorSpeed     = -10f
            enableMotor(true)
        }
    }

    fun goLeft() {
        if (DIRECTION_H == Direction_H.LEFT) return

        DIRECTION_H = Direction_H.LEFT
        screenBox2d.game.soundUtil.apply { play(TRACTOR_START, 40f) }
        bTractor.renderBlockArray.removeValue(rbRight, true)
        bTractor.renderBlockArray.add(rbLeft)

        jMiniWheel_Revolute.joint?.apply {
            maxMotorTorque = 1000f
            motorSpeed     = 10f
            enableMotor(true)
        }
        jBigWheel_Revolute.joint?.apply {
            maxMotorTorque = 1000f
            motorSpeed     = 10f
            enableMotor(true)
        }
    }

    fun goUp() {
        if (DIRECTION_V == Direction_V.TOP) return

        DIRECTION_V = Direction_V.TOP
        screenBox2d.game.soundUtil.apply { play(FORK, 30f) }
        bTractor.renderBlockArray.removeValue(rbDown, true)
        bTractor.renderBlockArray.add(rbTop)

        jFork_Prismatic.joint?.apply {
            maxMotorForce = 4000f
            motorSpeed    = 3f
            enableMotor(true)
        }
    }

    fun goDown() {
        if (DIRECTION_V == Direction_V.DOWN) return

        DIRECTION_V = Direction_V.DOWN
        screenBox2d.game.soundUtil.apply { play(FORK, 30f) }
        bTractor.renderBlockArray.removeValue(rbTop, true)
        bTractor.renderBlockArray.add(rbDown)

        jFork_Prismatic.joint?.apply {
            maxMotorForce = 1000f
            motorSpeed    = -3f
            enableMotor(true)
        }
    }

    // ---------------------------------------------------
    // class
    // ---------------------------------------------------

    private enum class Direction_H {
        NONE, RIGHT, LEFT
    }

    private enum class Direction_V {
        NONE, TOP, DOWN
    }

}