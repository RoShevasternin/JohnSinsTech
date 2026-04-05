package com.side.swipe.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.side.swipe.game.utils.advanced.AdvancedGroup
import com.side.swipe.game.utils.advanced.AdvancedScreen

class ABackground constructor(override val screen: AdvancedScreen): AdvancedGroup() {

    override fun addActorsOnGroup() {
        addAndFillActor(Image(screen.game.loaderAssets.mini))
        addAndFillActor(Image(screen.game.loaderAssets.mini).also { it.y = height })

        addAction(Actions.forever(Actions.sequence(
            Actions.moveBy(0f, -height, 3f),
            Actions.moveBy(0f, height),
        )))
    }

}