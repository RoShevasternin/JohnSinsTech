package com.stone.storm.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.stone.storm.game.manager.SpriteManager

class SpriteUtil {

     class StartAssets {
          val CBACA = SpriteManager.EnumTexture.CBACA.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val back           = getRegion("back")
          val catapult       = getRegion("catapult")
          val catapult_power = getRegion("catapult_power")
          val stone          = getRegion("stone")
          val lopata         = getRegion("lopata")
          val rama           = getRegion("rama")

          private val _1 = SpriteManager.EnumTexture._1.data.texture
          private val _2 = SpriteManager.EnumTexture._2.data.texture
          private val _3 = SpriteManager.EnumTexture._3.data.texture
          private val _4 = SpriteManager.EnumTexture._4.data.texture

          val CASTLES = SpriteManager.EnumTexture.CASTLES.data.texture

         val menu = SpriteManager.EnumTexture.menu.data.texture
         val moff = SpriteManager.EnumTexture.moff.data.texture
         val mon = SpriteManager.EnumTexture.mon.data.texture
         val rules = SpriteManager.EnumTexture.rules.data.texture
         val soff = SpriteManager.EnumTexture.soff.data.texture
         val son = SpriteManager.EnumTexture.son.data.texture

          val castles = listOf(_1,_2,_3,_4)
     }

}