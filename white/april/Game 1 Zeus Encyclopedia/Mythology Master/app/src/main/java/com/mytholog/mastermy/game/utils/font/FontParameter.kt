package com.mytholog.mastermy.game.utils.font

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

class FontParameter : FreeTypeFontParameter() {

    init {
        setLinear()
    }

    fun setLinear(): FontParameter {
        minFilter = Texture.TextureFilter.Linear
        magFilter = Texture.TextureFilter.Linear
        return this
    }
    fun setSize(size: Int): FontParameter {
        this.size = size
        return this
    }
    fun setCharacters(characters: CharType): FontParameter {
        this.characters = characters.chars
        return this
    }
    fun setCharacters(chars: String): FontParameter {
        this.characters = chars
        return this
    }
    fun setBorder(borderWidth: Float, borderColor: Color): FontParameter {
        this.borderWidth = borderWidth
        this.borderColor = borderColor
        return this
    }
    fun setSpaceY(spaceY: Int): FontParameter {
        this.spaceY = spaceY
        return this
    }

    enum class CharType(val chars: String) {
        NUMBERS("1234567890"),
        ALL("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,;:?!-'\"/()[]{}\\/@#\$%&*+=<>_^~|`°©®™§†∞µ\n"),
    }

}