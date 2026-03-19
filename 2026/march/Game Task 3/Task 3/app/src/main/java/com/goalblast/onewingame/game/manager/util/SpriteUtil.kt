package com.goalblast.onewingame.game.manager.util

import com.goalblast.onewingame.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)
          val loader = getRegion("loader")
          //val MASK = SpriteManager.EnumTexture.MASK.data.texture
     }

    class All {
        private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

        // atlas All ------------------------------------------------------------------------------

        val arrows     = getRegionAll("arrows")
        val bag        = getRegionAll("bag")
        val ball       = getRegionAll("ball")
        val game_def   = getRegionAll("game_def")
        val game_press = getRegionAll("game_press")
        val home_def   = getRegionAll("home_def")
        val home_press = getRegionAll("home_press")
        val md         = getRegionAll("md")
        val mp         = getRegionAll("mp")
        val panel      = getRegionAll("panel")
        val sd         = getRegionAll("sd")
        val sp         = getRegionAll("sp")

        // textures ------------------------------------------------------------------------------

        val BACKGROUND_DEF  = SpriteManager.EnumTexture.BACKGROUND_DEF.data.texture
        val BACKGROUND_GAME = SpriteManager.EnumTexture.BACKGROUND_GAME.data.texture
        val BACKGROUND_LOSE = SpriteManager.EnumTexture.BACKGROUND_LOSE.data.texture
        val BACKGROUND_WIN  = SpriteManager.EnumTexture.BACKGROUND_WIN.data.texture
        val LOSE_PAN        = SpriteManager.EnumTexture.LOSE_PAN.data.texture
        val WIN_PAN         = SpriteManager.EnumTexture.WIN_PAN.data.texture

     }

}