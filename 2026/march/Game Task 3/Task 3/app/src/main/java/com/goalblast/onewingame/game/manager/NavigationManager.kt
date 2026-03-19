package com.goalblast.onewingame.game.manager

import com.badlogic.gdx.Gdx
import com.goalblast.onewingame.game.screens.GameScreen
import com.goalblast.onewingame.game.screens.LoaderScreen
import com.goalblast.onewingame.game.screens.MenuScreen
import com.goalblast.onewingame.game.screens.ResultFailScreen
import com.goalblast.onewingame.game.screens.ResultDoneScreen
import com.goalblast.onewingame.game.utils.advanced.AdvancedScreen
import com.goalblast.onewingame.game.utils.gdxGame
import com.goalblast.onewingame.game.utils.runGDX

class NavigationManager {

    private val backStack = mutableListOf<String>()

    fun navigate(toScreenName: String, fromScreenName: String? = null) = runGDX {
        gdxGame.updateScreen(getScreenByName(toScreenName))
        backStack.filter { name -> name == toScreenName }.onEach { name -> backStack.remove(name) }
        fromScreenName?.let { fromName ->
            backStack.filter { name -> name == fromName }.onEach { name -> backStack.remove(name) }
            backStack.add(fromName)
        }
    }

    fun back() = runGDX {
        if (isBackStackEmpty()) exit() else gdxGame.updateScreen(getScreenByName(backStack.removeAt(backStack.lastIndex)))
    }

    fun exit() = runGDX { Gdx.app.exit() }


    fun isBackStackEmpty() = backStack.isEmpty()
    fun clearBackStack() = backStack.clear()

    private fun getScreenByName(name: String): AdvancedScreen = when(name) {
        LoaderScreen      ::class.java.name -> LoaderScreen()
        MenuScreen        ::class.java.name -> MenuScreen()
        ResultDoneScreen  ::class.java.name -> ResultDoneScreen()
        ResultFailScreen  ::class.java.name -> ResultFailScreen()
        GameScreen        ::class.java.name -> GameScreen()

        else -> MenuScreen()
    }

}