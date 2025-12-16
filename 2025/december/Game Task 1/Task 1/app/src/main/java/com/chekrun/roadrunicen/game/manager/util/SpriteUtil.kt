package com.chekrun.roadrunicen.game.manager.util

import com.chekrun.roadrunicen.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val load_pan = getRegion("load_pan")
          val loader   = getRegion("loader")
          val loading  = getRegion("loading")

          val BACKGROUND_LOADER = SpriteManager.EnumTexture.BACKGROUND_LOADER.data.texture
          val MASK              = SpriteManager.EnumTexture.MASK.data.texture

     }

    class All {
        private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
        //private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

        // atlas All ------------------------------------------------------------------------------

        val md        = getRegionAll("md")
        val mp        = getRegionAll("mp")
        val pld       = getRegionAll("pld")
        val plp       = getRegionAll("plp")
        val prd       = getRegionAll("prd")
        val prp       = getRegionAll("prp")
        val rld       = getRegionAll("rld")
        val rlp       = getRegionAll("rlp")
        val sd        = getRegionAll("sd")
        val sp        = getRegionAll("sp")
        val timer_pan = getRegionAll("timer_pan")
        val xd        = getRegionAll("xd")
        val xp        = getRegionAll("xp")
        val med       = getRegionAll("med")
        val mep       = getRegionAll("mep")
        val nd        = getRegionAll("nd")
        val np        = getRegionAll("np")

        //val listItem = List(12) { getRegionAll("${it.inc()}") }

        // textures ------------------------------------------------------------------------------

        val BACKGROUND_FAIL = SpriteManager.EnumTexture.BACKGROUND_FAIL.data.texture
        val BACKGROUND_MENU = SpriteManager.EnumTexture.BACKGROUND_MENU.data.texture
        val BACKGROUND_GAME = SpriteManager.EnumTexture.BACKGROUND_GAME.data.texture
        val BACKGROUND_WIN  = SpriteManager.EnumTexture.BACKGROUND_WIN.data.texture
        val CHICKEN         = SpriteManager.EnumTexture.CHICKEN.data.texture
        val DONE            = SpriteManager.EnumTexture.DONE.data.texture
        val GAME_PAN        = SpriteManager.EnumTexture.GAME_PAN.data.texture
        val KOKI            = SpriteManager.EnumTexture.KOKI.data.texture
        val LOSE            = SpriteManager.EnumTexture.LOSE.data.texture
        val MENU_PAN        = SpriteManager.EnumTexture.MENU_PAN.data.texture
        val RULES           = SpriteManager.EnumTexture.RULES.data.texture

        val _1 = SpriteManager.EnumTexture._1.data.texture
        val _2 = SpriteManager.EnumTexture._2.data.texture
        val _3 = SpriteManager.EnumTexture._3.data.texture
        val _4 = SpriteManager.EnumTexture._4.data.texture
        val _5 = SpriteManager.EnumTexture._5.data.texture
        val _6 = SpriteManager.EnumTexture._6.data.texture

        val listPuzzles = listOf(_1, _2, _3, _4, _5, _6)
     }

}