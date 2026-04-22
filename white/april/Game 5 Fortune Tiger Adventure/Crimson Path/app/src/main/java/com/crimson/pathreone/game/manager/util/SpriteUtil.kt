package com.crimson.pathreone.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.crimson.pathreone.game.manager.SpriteManager

class SpriteUtil {

     class SplashAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.Loading.data.atlas.findRegion(name)

          val aca_background = getRegion("aca_background")
          val aca_loading    = getRegion("aca_loading")
          val aca_progress   = getRegion("aca_progress")
          val aca_tiger      = getRegion("aca_tiger")

          val aca_mask        = SpriteManager.EnumTexture.aca_mask.data.texture
          val IncasBackground = SpriteManager.EnumTexture.IncasBackground.data.texture
     }

     class GameAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.All.data.atlas.findRegion(name)

          val golds  = List(10) { getRegion("${it.inc()}") }
          val levels = List(3) { getRegion("l-${it.inc()}") }

          val card         = getRegion("card")
          val level        = getRegion("level")
          val menu         = getRegion("menu")
          val menu_def     = getRegion("menu_def")
          val menu_press   = getRegion("menu_press")
          val pause_def    = getRegion("pause_def")
          val pause_press  = getRegion("pause_press")
          val rules        = getRegion("rules")
          val scr_progress = getRegion("scr_progress")
          val scroller     = getRegion("scroller")
          val setting      = getRegion("setting")

          val enh                 = SpriteManager.EnumTexture.enh.data.texture
          val IncasGameBackground = SpriteManager.EnumTexture.IncasGameBackground.data.texture
          val IncasPerfectly      = SpriteManager.EnumTexture.IncasPerfectly.data.texture
          val IncasVeryBad        = SpriteManager.EnumTexture.IncasVeryBad.data.texture
          val lrseexit            = SpriteManager.EnumTexture.lrseexit.data.texture
          val mini_tiger          = SpriteManager.EnumTexture.mini_tiger.data.texture
          val music               = SpriteManager.EnumTexture.music.data.texture
          val panel               = SpriteManager.EnumTexture.panel.data.texture
          val rules_text          = SpriteManager.EnumTexture.rules_text.data.texture
          val shoto_progress      = SpriteManager.EnumTexture.shoto_progress.data.texture
          val sound               = SpriteManager.EnumTexture.sound.data.texture
          val sisi                = SpriteManager.EnumTexture.sisi.data.texture
          val mini                = SpriteManager.EnumTexture.mini.data.texture
          val bigi                = SpriteManager.EnumTexture.bigi.data.texture
     }

}