package com.epicjourney.seredmora.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.epicjourney.seredmora.game.manager.SpriteManager

class SpriteUtil {

     class SplashAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.valLoader.data.atlas.findRegion(name)

          val background = getRegion("background")
          val progress   = getRegion("progress")
          val shadow     = getRegion("shadow")
          val text       = getRegion("text")
          val tigrula    = getRegion("tigrula")

          val ValBackground = SpriteManager.EnumTexture.ValBackground.data.texture
          val vmaskav       = SpriteManager.EnumTexture.vmaskav.data.texture
     }

     class GameAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.valAll.data.atlas.findRegion(name)

          val listLevel = List(4) { getRegion("val_lev${it.inc()}") }
          val listThing = List(6) { getRegion("${it.inc()}") }

          val val_lose             = getRegion("val_lose")
          val val_menu_def         = getRegion("val_menu_def")
          val val_menu_press       = getRegion("val_menu_press")
          val val_off              = getRegion("val_off")
          val val_on               = getRegion("val_on")
          val val_pause_def        = getRegion("val_pause_def")
          val val_pause_press      = getRegion("val_pause_press")
          val val_timer_background = getRegion("val_timer_background")
          val val_victory          = getRegion("val_victory")

          val VAL_LEVEL    = SpriteManager.EnumTexture.VAL_LEVEL.data.texture
          val VAL_MENU     = SpriteManager.EnumTexture.VAL_MENU.data.texture
          val VAL_PAN1     = SpriteManager.EnumTexture.VAL_PAN1.data.texture
          val VAL_PAN2     = SpriteManager.EnumTexture.VAL_PAN2.data.texture
          val VAL_PAN3     = SpriteManager.EnumTexture.VAL_PAN3.data.texture
          val VAL_PAN4     = SpriteManager.EnumTexture.VAL_PAN4.data.texture
          val VAL_PANEL    = SpriteManager.EnumTexture.VAL_PANEL.data.texture
          val VAL_RULES    = SpriteManager.EnumTexture.VAL_RULES.data.texture
          val VAL_SETTINGS = SpriteManager.EnumTexture.VAL_SETTINGS.data.texture
          val BG2 = SpriteManager.EnumTexture.BG2.data.texture
          val BG3 = SpriteManager.EnumTexture.BG3.data.texture
          val BG4 = SpriteManager.EnumTexture.BG4.data.texture
          val RESULT = SpriteManager.EnumTexture.RESULT.data.texture
     }

}