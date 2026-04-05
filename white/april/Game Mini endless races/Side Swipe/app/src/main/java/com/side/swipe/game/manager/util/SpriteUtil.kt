package com.side.swipe.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.side.swipe.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          val mini = SpriteManager.EnumTexture.mini.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val car         = getRegion("car")
          val left_def    = getRegion("left_def")
          val left_press  = getRegion("left_press")
          val right_def   = getRegion("right_def")
          val right_press = getRegion("right_press")

          val leftCars  = List(5) { getRegion("l${it.inc()}") }
          val rightCars = List(5) { getRegion("r${it.inc()}") }

         val menu    = SpriteManager.EnumTexture.menu.data.texture
         val mus_off = SpriteManager.EnumTexture.mus_off.data.texture
         val mus_on  = SpriteManager.EnumTexture.mus_on.data.texture
         val rules   = SpriteManager.EnumTexture.rules.data.texture
         val sett    = SpriteManager.EnumTexture.sett.data.texture
         val sou_off = SpriteManager.EnumTexture.sou_off.data.texture
         val sou_on  = SpriteManager.EnumTexture.sou_on.data.texture
     }

}