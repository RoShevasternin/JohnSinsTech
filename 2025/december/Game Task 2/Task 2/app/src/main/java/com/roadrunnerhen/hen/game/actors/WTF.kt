package com.roadrunnerhen.hen.game.actors

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedGroup
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedScreen

class WTF(override val screen: AdvancedScreen): AdvancedGroup(), OpenClose {

    //private val grey = Image(gdxGame.assetsAll.pit)
    val image        = Image()

    override fun addActorsOnGroup() {
        addAndFillActor(image)
        //addAndFillActor(grey)

        image.apply {
            addAction(Actions.alpha(0f))
            //setBounds(34f, 30f, 200f, 200f)
        }
    }

    override fun open(block: () -> Unit) {
        //grey.addAction(Actions.fadeOut(0.1f))
        image.addAction(Actions.sequence(
            Actions.fadeIn(0.1f),
            Actions.run { block() }
        ))
    }

    override fun close(block: () -> Unit) {
        //grey.addAction(Actions.fadeIn(0.1f))
        image.addAction(Actions.sequence(
            Actions.fadeOut(0.1f),
            Actions.run { block() }
        ))
    }

}

interface OpenClose {
    fun open(block: () -> Unit = {})
    fun close(block: () -> Unit = {})
}