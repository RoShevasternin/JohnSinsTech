package com.mytholog.mastermy.game.actors

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mytholog.mastermy.game.utils.advanced.AdvancedGroup
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen

open class AButton(
    override val screen: AdvancedScreen,
    type: Static.Type? = null
) : AdvancedGroup() {

    private val defaultImage  by lazy {  if (type != null) Image(getStyleByType(type).default)  else Image() }
    private val pressedImage  by lazy { (if (type != null) Image(getStyleByType(type).pressed)  else Image()).apply { isVisible = false } }
    private val disabledImage by lazy { (if (type != null) Image(getStyleByType(type).disabled) else Image()).apply { isVisible = false } }

    private var onClickBlock    : () -> Unit = { }

    var touchDownBlock   : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchDraggedBlock: AButton.(x: Float, y: Float) -> Unit = { _, _ -> }
    var touchUpBlock     : AButton.(x: Float, y: Float) -> Unit = { _, _ -> }

    private var area: Actor? = null
    private var sound: Sound? = null

    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
    }


    private fun getActors() = listOf<Actor>(
        defaultImage,
        pressedImage,
        disabledImage,
    )



    private fun getListener() = object : InputListener() {
        var isWithin     = false
        var isWithinArea = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchDownBlock(x, y)
            touchDragged(event, x, y, pointer)

            sound?.let { screen.game.soundUtil.apply { play(it, 0.3f) } }

            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            touchDraggedBlock(x, y)

            isWithin = x in 0f..width && y in 0f..height
            area?.let { isWithinArea = x in 0f..it.width && y in 0f..it.height }


            if (isWithin || isWithinArea) press() else unpress()

        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            touchUpBlock(x, y)

            if (isWithin || isWithinArea) {
                unpress()
                onClickBlock()
            }

        }
    }

    fun press() {
        defaultImage.isVisible = false
        pressedImage.isVisible = true
    }

    fun unpress() {
        defaultImage.isVisible = true
        pressedImage.isVisible = false
    }

    fun disable(useDisabledStyle: Boolean = true) {
        touchable = Touchable.disabled

        if (useDisabledStyle) {
            defaultImage.isVisible  = false
            pressedImage.isVisible  = false
            disabledImage.isVisible = true
        }

    }

    fun enable() {
        touchable = Touchable.enabled

        defaultImage.isVisible  = true
        pressedImage.isVisible  = false
        disabledImage.isVisible = false

    }

    fun pressAndDisable(useDisabledStyle: Boolean = false) {
        press()
        disable(useDisabledStyle)
    }

    fun unpressAndEnable() {
        unpress()
        enable()
    }

    fun setStyle(style: Static.AButtonStyle) {
        defaultImage.drawable  = style.default
        pressedImage.drawable  = style.pressed
        disabledImage.drawable = style.disabled
    }

    fun setOnClickListener(sound: Sound? = screen.game.soundUtil.CLICK, block: () -> Unit) {
        this.sound = sound
        onClickBlock = block
    }

    fun addArea(actor: Actor) {
        area = actor
        actor.addListener(getListener())
    }

    private fun getStyleByType(type: Static.Type) = when(type) {
        Static.Type.Next -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.NEXT_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.NEXT_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.NEXT_PRESS),
        )
        Static.Type.Back -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.BACK_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.BACK_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.BACK_PRESS),
        )
        Static.Type.Down -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.DOWN_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.DOWN_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.DOWN_PRESS),
        )
        Static.Type.ReturnToGame -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.RETURN_TO_GAME_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.RETURN_TO_GAME_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.RETURN_TO_GAME_PRESS),
        )
        Static.Type.BeginTutorial -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.BEGIN_TUTORIAL_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.BEGIN_TUTORIAL_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.BEGIN_TUTORIAL_PRESS),
        )
        Static.Type.Restart -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.RESTART_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.RESTART_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.RESTART_PRESS),
        )
        Static.Type.Settings -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.SETTINGS_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.SETTINGS_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.SETTINGS_PRESS),
        )
        Static.Type.Menu -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.MENU_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.MENU_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.MENU_PRESS),
        )
        Static.Type.Save -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.SAVE_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.SAVE_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.SAVE_PRESS),
        )
        Static.Type.YellowLeft -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.YELLOW_LEFT_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.YELLOW_LEFT_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.YELLOW_LEFT_PRESS),
        )
        Static.Type.YellowRight -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.YELLOW_RIGHT_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.YELLOW_RIGHT_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.YELLOW_RIGHT_PRESS),
        )
        Static.Type.DeckOverview -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.DECK_OVERVIEW_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.DECK_OVERVIEW_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.DECK_OVERVIEW_PRESS),
        )
        Static.Type.Play -> Static.AButtonStyle(
            default  = TextureRegionDrawable(screen.game.all.PLAY_DEF),
            pressed  = TextureRegionDrawable(screen.game.all.PLAY_PRESS),
            disabled = TextureRegionDrawable(screen.game.all.PLAY_PRESS),
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    object Static {
        data class AButtonStyle(
            val default: Drawable,
            val pressed: Drawable,
            val disabled: Drawable? = null,
        )

        enum class Type {
            Next, Back, Down, ReturnToGame, BeginTutorial, Restart, Settings, Menu, Save, YellowLeft, YellowRight, DeckOverview, Play
        }
    }

}