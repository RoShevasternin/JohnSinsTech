package com.crimson.pathreone.game.manager

import com.badlogic.gdx.Gdx
import com.crimson.pathreone.game.LibGDXGame
import com.crimson.pathreone.game.screens.IncasExitScreen
import com.crimson.pathreone.game.screens.IncasLoadingScreen
import com.crimson.pathreone.game.screens.IncasResultScreen
import com.crimson.pathreone.game.screens.common.IncasLevelScreen
import com.crimson.pathreone.game.screens.common.IncasMenuScreen
import com.crimson.pathreone.game.screens.common.IncasRulesScreen
import com.crimson.pathreone.game.screens.common.IncasSettingsScreen
import com.crimson.pathreone.game.screens.level.IncasEasyScreen
import com.crimson.pathreone.game.screens.level.IncasHardScreen
import com.crimson.pathreone.game.screens.level.IncasNormalScreen
import com.crimson.pathreone.game.utils.advanced.AdvancedScreen
import com.crimson.pathreone.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    private val backStack = mutableListOf<String>()
    var key: Int? = null
        private set

    fun navigate(toScreenName: String, fromScreenName: String? = null, key: Int? = null) = runGDX {
        this.key = key

        game.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back(key: Int? = null) = runGDX {
        this.key = key

        if (isBackStackEmpty()) exit() else game.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }


    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        IncasLoadingScreen ::class.java.name -> IncasLoadingScreen(game)
        IncasExitScreen    ::class.java.name -> IncasExitScreen(game)
        IncasMenuScreen    ::class.java.name -> IncasMenuScreen(game)
        IncasRulesScreen   ::class.java.name -> IncasRulesScreen(game)
        IncasSettingsScreen::class.java.name -> IncasSettingsScreen(game)
        IncasLevelScreen   ::class.java.name -> IncasLevelScreen(game)
        IncasResultScreen  ::class.java.name -> IncasResultScreen(game)

        // Levels
        IncasEasyScreen  ::class.java.name -> IncasEasyScreen(game)
        IncasNormalScreen::class.java.name -> IncasNormalScreen(game)
        IncasHardScreen  ::class.java.name -> IncasHardScreen(game)

        else -> IncasMenuScreen(game)
    }

}