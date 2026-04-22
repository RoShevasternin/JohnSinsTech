package com.crimson.pathreone.game.screens.common

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.crimson.pathreone.game.LibGDXGame
import com.crimson.pathreone.game.screens.IncasExitScreen
import com.crimson.pathreone.game.utils.actor.setOnClickListener
import com.crimson.pathreone.game.utils.advanced.AdvancedGroup

class IncasMenuScreen(_game: LibGDXGame): ICommonScreen(_game, Static.TypeScreen.Menu) {

    private val menuImg = Image(game.allAssets.lrseexit)

    override fun AdvancedGroup.addActorsOnTmpGroup() {
        addMenuImg()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedGroup.addMenuImg() {
        addActor(menuImg)
        menuImg.setBounds(318f, 465f, 494f, 782f)

        val screens = listOf(
            IncasLevelScreen::class.java.name,
            IncasRulesScreen::class.java.name,
            IncasSettingsScreen::class.java.name,
            IncasExitScreen::class.java.name,
        )

        var ny = 1089f

        screens.onEach { nameScreen ->
            val btn = Actor()
            addActor(btn)
            btn.setBounds(318f, ny, 494f, 158f)
            ny -= 50f + 158f

            btn.setOnClickListener(game.soundUtil) {
                animHideTmpGroup { game.navigationManager.navigate(nameScreen, IncasMenuScreen::class.java.name) }
            }
        }

    }

}