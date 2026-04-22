package com.crimson.pathreone.game.screens

import com.crimson.pathreone.game.LibGDXGame
import com.crimson.pathreone.game.utils.advanced.AdvancedScreen

class IncasExitScreen(override val game: LibGDXGame): AdvancedScreen() {

    override fun show() {
        game.navigationManager.exit()
        super.show()
    }

}