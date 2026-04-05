package com.side.swipe.game.manager

import com.badlogic.gdx.Gdx
import com.side.swipe.game.LibGDXGame
import com.side.swipe.game.screens.MiniGameScreen
import com.side.swipe.game.screens.MiniLoaderScreen
import com.side.swipe.game.screens.MiniMenuScreen
import com.side.swipe.game.screens.MiniRulesScreen
import com.side.swipe.game.screens.MiniSettScreen
import com.side.swipe.game.utils.advanced.AdvancedScreen
import com.side.swipe.game.utils.runGDX

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
        MiniLoaderScreen::class.java.name -> MiniLoaderScreen(game)
        MiniGameScreen  ::class.java.name -> MiniGameScreen(game)
        MiniMenuScreen  ::class.java.name -> MiniMenuScreen(game)
        MiniRulesScreen ::class.java.name -> MiniRulesScreen(game)
        MiniSettScreen  ::class.java.name -> MiniSettScreen(game)

        else                                -> MiniLoaderScreen(game)
    }

}


