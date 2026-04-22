package com.warehousev.operatorw.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.warehousev.operatorw.game.actors.AImage
import com.warehousev.operatorw.game.box2d.AbstractBody
import com.warehousev.operatorw.game.box2d.BodyId
import com.warehousev.operatorw.game.utils.advanced.AdvancedBox2dScreen
import com.warehousev.operatorw.game.utils.advanced.AdvancedGroup

class BFork(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "fork"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density     = 1f
        restitution = 0.5f
        friction    = 0.4f
    }
    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.fork)

    override var id = BodyId.FORK
    override val collisionList = mutableListOf(BodyId.BOX)

}