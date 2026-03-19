package com.goalblast.onewingame.game.box2d.bodies

import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.goalblast.onewingame.game.actors.AImage
import com.goalblast.onewingame.game.box2d.AbstractBody
import com.goalblast.onewingame.game.box2d.BodyId
import com.goalblast.onewingame.game.utils.advanced.AdvancedBox2dScreen
import com.goalblast.onewingame.game.utils.advanced.AdvancedGroup
import com.goalblast.onewingame.game.utils.gdxGame
import java.util.concurrent.atomic.AtomicBoolean

class BEnemy(override val screenBox2d: AdvancedBox2dScreen): AbstractBody() {
    override val name       = "circle"
    override val bodyDef    = BodyDef().apply {
        type = BodyDef.BodyType.DynamicBody
    }
    override val fixtureDef = FixtureDef().apply {
        density = 1f
    }

    override var actor: AdvancedGroup? = AImage(screenBox2d, gdxGame.assetsAll.bag)

    override var id            = BodyId.Game.ENEMY
    override val collisionList = mutableListOf(BodyId.Game.AVIA)

    // Field
    val atomicBoolean = AtomicBoolean(true)

}