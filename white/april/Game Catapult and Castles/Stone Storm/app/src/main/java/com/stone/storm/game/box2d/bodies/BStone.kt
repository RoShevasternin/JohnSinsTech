package com.stone.storm.game.box2d.bodies

import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor
import com.stone.storm.game.actors.AImage
import com.stone.storm.game.box2d.AbstractBody
import com.stone.storm.game.box2d.BodyId
import com.stone.storm.game.utils.actor.setPosition
import com.stone.storm.game.utils.advanced.AdvancedBox2dScreen
import com.stone.storm.game.utils.advanced.AdvancedGroup
import com.stone.storm.game.utils.currentTimeMinus
import com.stone.storm.game.utils.toUI

class BStone(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 10f
        restitution = 0.1f
        friction    = 0.5f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.stone)

    override val collisionList = mutableListOf(BodyId.BORDERS, BodyId.CASTLE, BodyId.STONE)

    private val particleEffect = ParticleEffectActor(ParticleEffect(screenBox2d.game.particleEffectUtil.dwm),false)


    private fun addEffect() {
        screenBox2d.stageUI.addActor(particleEffect)
    }

    private fun startEffect() {
        particleEffect.apply {
            setPosition(body!!.position.toUI)
            start()
        }
    }

    init {
        var timeContact = 0L

        addEffect()

        beginContactBlockArray.add(ContactBlock { body ->
            if (body.id == BodyId.CASTLE || body.id == BodyId.BORDERS) {
                if (currentTimeMinus(timeContact) >= 700) {
                    startEffect()

                    screenBox2d.game.soundUtil.apply { play(BRICK, 35f) }
                    timeContact = System.currentTimeMillis()
                }
            }

        })
    }

}