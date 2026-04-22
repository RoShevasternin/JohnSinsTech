package com.warehousev.operatorw.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.warehousev.operatorw.game.manager.SpriteManager

class SpriteUtil {

     class LoaderAssets {
          val GARAGES = SpriteManager.EnumTexture.GARAGES.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val box           = getRegion("box")
          val big_wheel     = getRegion("big_wheel")
          val control_panel = getRegion("control_panel")
          val fork          = getRegion("fork")
          val mini_wheel    = getRegion("mini_wheel")
          val tractor       = getRegion("tractor")

         val menu   = SpriteManager.EnumTexture.menu.data.texture
         val mus_off    = SpriteManager.EnumTexture.mus_off.data.texture
         val mus_on = SpriteManager.EnumTexture.mus_on.data.texture
         val musonchaka = SpriteManager.EnumTexture.musonchaka.data.texture
         val reluser    = SpriteManager.EnumTexture.reluser.data.texture
         val sou_off    = SpriteManager.EnumTexture.sou_off.data.texture
         val sou_on = SpriteManager.EnumTexture.sou_on.data.texture
     }

}