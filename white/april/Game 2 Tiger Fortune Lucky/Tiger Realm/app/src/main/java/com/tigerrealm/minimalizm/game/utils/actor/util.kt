package com.tigerrealm.minimalizm.game.utils.actor

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Align
import com.tigerrealm.minimalizm.game.manager.util.SoundUtil
import com.tigerrealm.minimalizm.game.utils.Layout
import com.tigerrealm.minimalizm.game.utils.runGDX
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

 fun Actor.setOnClickListener(sound: SoundUtil? = null, block: (Actor) -> Unit) {
    addListener(object : InputListener() {
        var isWithin = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            sound?.apply { play(s_click) }

            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            if (isWithin) {
                isWithin = false
                block(this@setOnClickListener)
            }
        }
    })
 }

fun Actor.disable() {
    touchable = Touchable.disabled
}

fun Actor.enable() {
    touchable = Touchable.enabled
}

fun List<Actor>.setFillParent() {
    onEach { actor ->
        when (actor) {
            is Widget      -> actor.setFillParent(true)
            is WidgetGroup -> actor.setFillParent(true)
        }
    }
}

fun Actor.setBounds(layoutData: Layout.LayoutData) {
    with(layoutData) { setBounds(x, y, w, h) }
}

fun Actor.setBounds(position: Vector2, size: Vector2) {
    setBounds(position.x, position.y, size.x, size.y)
}
//
//fun Actor.setPosition(position: Vector2) {
//    setPosition(position.x, position.y)
//}
//
//fun Actor.setOrigin(vector: Vector2) {
//    setOrigin(vector.x, vector.y)
//}

fun Actor.animShow(time: Float=0f, block: () -> Unit = {}) {
    addAction(Actions.sequence(
        Actions.fadeIn(time, Interpolation.fade),
        Actions.run(block)
    ))
}
fun Actor.animHide(time: Float=0f, block: () -> Unit = {}) {
    addAction(Actions.sequence(
        Actions.fadeOut(time, Interpolation.fade),
        Actions.run(block)
    ))
}


suspend fun Actor.animShow_Suspend(time: Float) = suspendCoroutine<Unit> { continuation ->
    runGDX { animShow(time) { continuation.resume(Unit) } }
}