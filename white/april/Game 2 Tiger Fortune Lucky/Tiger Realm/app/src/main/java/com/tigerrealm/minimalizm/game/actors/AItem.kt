package com.tigerrealm.minimalizm.game.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.tigerrealm.minimalizm.game.utils.actor.disable
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedGroup
import com.tigerrealm.minimalizm.game.utils.advanced.AdvancedScreen

class AItem(override val screen: AdvancedScreen, region: TextureRegion): AdvancedGroup() {

    private val img = Image(region)

    override fun addActorsOnGroup() {
        addAndFillActor(img)
        img.disable()
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    fun selected() {
        clearActions()
        addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-0.3f, -0.3f, 0.3f, Interpolation.fade),
            Actions.scaleBy(0.3f, 0.3f, 0.3f, Interpolation.fade),
        )))
    }

    fun unselected() {
        clearActions()
        addAction(Actions.scaleTo(1f, 1f, 0.2f, Interpolation.fade))
    }

}