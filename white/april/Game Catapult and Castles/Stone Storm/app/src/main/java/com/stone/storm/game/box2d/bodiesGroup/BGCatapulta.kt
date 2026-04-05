package com.stone.storm.game.box2d.bodiesGroup

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef
import com.badlogic.gdx.physics.box2d.joints.WeldJoint
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.stone.storm.game.box2d.AbstractBodyGroup
import com.stone.storm.game.box2d.AbstractJoint
import com.stone.storm.game.box2d.BodyId
import com.stone.storm.game.box2d.bodies.BLopata
import com.stone.storm.game.box2d.bodies.BRama
import com.stone.storm.game.box2d.bodies.BStone
import com.stone.storm.game.utils.DEGTORAD
import com.stone.storm.game.utils.advanced.AdvancedBox2dScreen
import com.stone.storm.game.utils.runGDX
import com.stone.storm.game.utils.toB2
import com.stone.storm.util.log

class BGCatapulta(override val screenBox2d: AdvancedBox2dScreen) : AbstractBodyGroup() {

    override val standartW = 283f

    private val bRama   = BRama(screenBox2d)
    private val bLopata = BLopata(screenBox2d)

    private val bStoneList = MutableList(5) { BStone(screenBox2d) }

    private val jRevolute = AbstractJoint<RevoluteJoint, RevoluteJointDef>(screenBox2d)
    private val jWeld     = AbstractJoint<WeldJoint, WeldJointDef>(screenBox2d)

    // Field
    private val staticStoneList = bStoneList.toList()
    private var currentStone: BStone? = null

    private var isFirst = true

    var coff = 0f

    override fun create(x: Float, y: Float, w: Float, h: Float) {
        super.create(x, y, w, h)

        createB_Rama()
        createB_Lopata()
        createB_StoneList()

        createJ_Revolute()

        prepare()
    }

    // ---------------------------------------------------
    // Create Body
    // ---------------------------------------------------

    private fun createB_Rama() {
        createBody(bRama, 11f, 0f, 272f, 137f)
    }

    private fun createB_Lopata() {
        createBody(bLopata, 0f, 115f, 130f, 93f)
    }

    private fun createB_StoneList() {
        bStoneList.onEach { stone ->
            stone.apply {
                id = BodyId.STONE
                setNoneId()

                bodyDef.gravityScale = 0f
            }

            createBody(stone, -700f, 0f, 54f, 54f)
        }
    }

    // ---------------------------------------------------
    // Create Joint
    // ---------------------------------------------------

    private fun createJ_Revolute() {
        createJoint(jRevolute, RevoluteJointDef().apply {
            bodyA = bRama.body
            bodyB = bLopata.body

            localAnchorA.set(Vector2(109f, 126f).subCenter(bodyA))
            localAnchorB.set(Vector2(120f, 11f).subCenter(bodyB))

            lowerAngle  = 0f
            upperAngle  = 65f * DEGTORAD
            enableLimit = true

            motorSpeed     = 20f * DEGTORAD
            maxMotorTorque = 1000f
            enableMotor    = true

        })
    }

    private fun createJ_Weld(bStone: BStone) {
        createJoint(jWeld, WeldJointDef().apply {
            bodyA = bLopata.body
            bodyB = bStone.body

            localAnchorA.set(Vector2(35f, 95f).subCenter(bodyA))
        })
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun prepare() {
        fun getStone(): BStone = if (isFirst) {
            isFirst = false
            staticStoneList.first()
        } else {
            isFirst = true
            staticStoneList.last()
        }

        jRevolute.joint?.motorSpeed = 20f * DEGTORAD
        (bStoneList.removeFirstOrNull() ?: getStone()).also { stone ->
            runGDX {
                stone.setNoneId()
                stone.body?.setLinearVelocity(0f,0f)
                stone.body?.gravityScale = 0f

                currentStone = stone
                stone.body?.setTransform(position.cpy().add(Vector2(8f, 183f)).toStandart.toB2, 0f)
                createJ_Weld(stone)
            }
        }
    }

    fun launch() {
        jRevolute.joint?.motorSpeed = -100f * DEGTORAD
        bLopata.actor?.addAction(Actions.sequence(
            Actions.delay(0.785f),
            Actions.run {
                jWeld.destroy()
                currentStone?.apply {
                    setOriginalId()
                    body?.gravityScale = 1f
                }
                currentStone?.body?.apply { applyLinearImpulse(Vector2(500f*coff, 40f*coff), worldCenter, true) }
            }
        ))
    }

}