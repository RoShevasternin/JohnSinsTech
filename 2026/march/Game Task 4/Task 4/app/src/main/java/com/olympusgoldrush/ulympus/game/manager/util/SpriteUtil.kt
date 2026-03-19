package com.olympusgoldrush.ulympus.game.manager.util

import com.olympusgoldrush.ulympus.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          val LOADER = SpriteManager.EnumTexture.LOADER.data.texture
     }

    class All {
        private val _1  = SpriteManager.EnumTexture._1.data.texture
        private val _2  = SpriteManager.EnumTexture._2.data.texture
        private val _3  = SpriteManager.EnumTexture._3.data.texture
        private val _4  = SpriteManager.EnumTexture._4.data.texture
        private val _5  = SpriteManager.EnumTexture._5.data.texture
        private val _6  = SpriteManager.EnumTexture._6.data.texture
        private val _7  = SpriteManager.EnumTexture._7.data.texture
        private val _8  = SpriteManager.EnumTexture._8.data.texture
        private val _9  = SpriteManager.EnumTexture._9.data.texture
        private val _10 = SpriteManager.EnumTexture._10.data.texture
        private val _11 = SpriteManager.EnumTexture._11.data.texture

        val listSettings = listOf(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11)

        private val item_1 = SpriteManager.EnumTexture.item_1.data.texture
        private val item_2 = SpriteManager.EnumTexture.item_2.data.texture
        private val item_3 = SpriteManager.EnumTexture.item_3.data.texture
        private val item_4 = SpriteManager.EnumTexture.item_4.data.texture
        private val item_5 = SpriteManager.EnumTexture.item_5.data.texture
        private val item_6 = SpriteManager.EnumTexture.item_6.data.texture
        private val item_7 = SpriteManager.EnumTexture.item_7.data.texture
        private val item_8 = SpriteManager.EnumTexture.item_8.data.texture

        val listItem = listOf(item_1, item_2, item_3, item_4, item_5, item_6, item_7, item_8)

        val background_def  = SpriteManager.EnumTexture.background_def.data.texture
        val background_game = SpriteManager.EnumTexture.background_game.data.texture
        val background_lose = SpriteManager.EnumTexture.background_lose.data.texture
        val background_win  = SpriteManager.EnumTexture.background_win.data.texture
        val game_pan        = SpriteManager.EnumTexture.game_pan.data.texture
        val lose_pan        = SpriteManager.EnumTexture.lose_pan.data.texture
        val menu_pan        = SpriteManager.EnumTexture.menu_pan.data.texture
        val rules_pan       = SpriteManager.EnumTexture.rules_pan.data.texture
        val sett_def        = SpriteManager.EnumTexture.sett_def.data.texture
        val sett_press      = SpriteManager.EnumTexture.sett_press.data.texture
        val settings_pan    = SpriteManager.EnumTexture.settings_pan.data.texture
        val win_pan         = SpriteManager.EnumTexture.win_pan.data.texture
        val x_def           = SpriteManager.EnumTexture.x_def.data.texture
        val x_press         = SpriteManager.EnumTexture.x_press.data.texture

     }

}