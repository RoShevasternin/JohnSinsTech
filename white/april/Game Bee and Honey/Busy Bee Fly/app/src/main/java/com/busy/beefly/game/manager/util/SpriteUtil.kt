package com.busy.beefly.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.busy.beefly.game.manager.SpriteManager

class SpriteUtil {

     class StartAssets {
          val YELLOW = SpriteManager.EnumTexture.YELLOW.data.texture
     }

     class AllAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val exit  = getRegion("exit")
          val honey = getRegion("honey")
          val play  = getRegion("play")
          val med   = getRegion("med")

          val bee = List(8) { getRegion("${it.inc()}") }

          val BLUE = SpriteManager.EnumTexture.BLUE.data.texture

         val mus_off        = SpriteManager.EnumTexture.mus_off.data.texture
         val mus_on     = SpriteManager.EnumTexture.mus_on.data.texture
         val rl_def     = SpriteManager.EnumTexture.rl_def.data.texture
         val rl_prs     = SpriteManager.EnumTexture.rl_prs.data.texture
         val rules      = SpriteManager.EnumTexture.rules.data.texture
     }

}