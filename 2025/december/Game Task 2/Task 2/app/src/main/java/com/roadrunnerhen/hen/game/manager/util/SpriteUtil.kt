package com.roadrunnerhen.hen.game.manager.util

import com.roadrunnerhen.hen.game.manager.SpriteManager
import com.badlogic.gdx.graphics.g2d.TextureRegion

class SpriteUtil {

     class Loader {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADER.data.atlas.findRegion(name)

          val loader = getRegion("load")

          val MASK = SpriteManager.EnumTexture.MASK.data.texture

     }

    class All {
        private fun getRegionAll(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)
        //private fun getNine(name: String): NinePatch = SpriteManager.EnumAtlas.ALL.data.atlas.createPatch(name)

        // atlas All ------------------------------------------------------------------------------

        val bd    = getRegionAll("bd")
        val bp    = getRegionAll("bp")
        val galca = getRegionAll("galca")
        val lose  = getRegionAll("lose")
        val menu  = getRegionAll("menu")
        val pip   = getRegionAll("pip")
        val prog  = getRegionAll("prog")
        val vic   = getRegionAll("vic")

        //val listItem = List(12) { getRegionAll("${it.inc()}") }

        // textures ------------------------------------------------------------------------------

        val B_BLUR       = SpriteManager.EnumTexture.B_BLUR.data.texture
        val B_DEF        = SpriteManager.EnumTexture.B_DEF.data.texture
        val B_GAME1      = SpriteManager.EnumTexture.B_GAME1.data.texture
        val B_GAME1_LOSE = SpriteManager.EnumTexture.B_GAME1_LOSE.data.texture
        val B_GAME1_WIN  = SpriteManager.EnumTexture.B_GAME1_WIN.data.texture
        val B_GAME2      = SpriteManager.EnumTexture.B_GAME2.data.texture
        val B_GAME2_LOSE = SpriteManager.EnumTexture.B_GAME2_LOSE.data.texture
        val B_GAME2_WIN  = SpriteManager.EnumTexture.B_GAME2_WIN.data.texture
        val GAME         = SpriteManager.EnumTexture.GAME.data.texture
        val RES          = SpriteManager.EnumTexture.RES.data.texture
        val SCH          = SpriteManager.EnumTexture.SCH.data.texture
        val SETT         = SpriteManager.EnumTexture.SETT.data.texture

        val _1 = SpriteManager.EnumTexture._1.data.texture
        val _2 = SpriteManager.EnumTexture._2.data.texture
        val _3 = SpriteManager.EnumTexture._3.data.texture
        val _4 = SpriteManager.EnumTexture._4.data.texture
        val _5 = SpriteManager.EnumTexture._5.data.texture
        val _6 = SpriteManager.EnumTexture._6.data.texture
        val _7 = SpriteManager.EnumTexture._7.data.texture
        val _8 = SpriteManager.EnumTexture._8.data.texture

        val listPuzzles = listOf(_1, _2, _3, _4, _5, _6, _7, _8)
     }

}