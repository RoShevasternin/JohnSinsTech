package com.tigerrealm.minimalizm.game.manager.util

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.tigerrealm.minimalizm.game.manager.SpriteManager

class SpriteUtil {

     class SplashAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.LOADING.data.atlas.findRegion(name)

          val startFront    = getRegion("start_front")
          val startLoading  = getRegion("start_loading")
          val startProgress = getRegion("start_progress")

          val YellowLoading = SpriteManager.EnumTexture.YellowLoading.data.texture
          val YellowMasakaj = SpriteManager.EnumTexture.YellowMasakaj.data.texture
     }

     class GameAssets {
          private fun getRegion(name: String): TextureRegion = SpriteManager.EnumAtlas.ALL.data.atlas.findRegion(name)

          val listItems = List(10) { getRegion("${it.inc()}") }

          val yBackDef       = getRegion("y_back_def")
          val yBackPress     = getRegion("y_back_press")
          val yBoxDef        = getRegion("y_box_def")
          val yBoxPress      = getRegion("y_box_press")
          val yCircleTiger   = getRegion("y_circle_tiger")
          val yDialogTiger   = getRegion("y_dialog_tiger")
          val yMusicCheck    = getRegion("y_music_check")
          val yMusicDef      = getRegion("y_music_def")
          val yPanelRules    = getRegion("y_panel_rules")
          val yPanelSettings = getRegion("y_panel_settings")
          val yPauseDef      = getRegion("y_pause_def")
          val yPauseCheck    = getRegion("y_pause_check")

          val YellowDefeat  = SpriteManager.EnumTexture.YellowDefeat.data.texture
          val YellowMain    = SpriteManager.EnumTexture.YellowMain.data.texture
          val YcupleMenu    = SpriteManager.EnumTexture.YcupleMenu.data.texture
          val YcupleStars   = SpriteManager.EnumTexture.YcupleStars.data.texture
          val YellowVictory = SpriteManager.EnumTexture.YellowVictory.data.texture
          val YcupleVolume  = SpriteManager.EnumTexture.YcupleVolume.data.texture
          val YellowLevel   = SpriteManager.EnumTexture.YellowLevel.data.texture
     }

}