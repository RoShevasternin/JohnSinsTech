package com.stone.storm.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.stone.storm.game.actors.AImage
import com.stone.storm.game.box2d.AbstractBody
import com.stone.storm.game.box2d.BodyId
import com.stone.storm.game.utils.advanced.AdvancedBox2dScreen
import com.stone.storm.game.utils.advanced.AdvancedGroup

class BRama(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "rama"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.StaticBody
    }
    override val fixtureDef = FixtureDef()

    override var actor: AdvancedGroup? = AImage(screenBox2d, screenBox2d.game.allAssets.rama)

    override var id = BodyId.BORDERS
    override val collisionList = mutableListOf(BodyId.BORDERS, BodyId.CASTLE, BodyId.STONE)

}