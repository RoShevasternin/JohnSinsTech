package com.olympusgoldrush.ulympus.game.manager

import com.badlogic.gdx.Gdx
import com.olympusgoldrush.ulympus.game.screens.SettingsScreen
import com.olympusgoldrush.ulympus.game.screens.GameScreen
import com.olympusgoldrush.ulympus.game.screens.LoaderScreen
import com.olympusgoldrush.ulympus.game.screens.MenuScreen
import com.olympusgoldrush.ulympus.game.screens.ResultFailScreen
import com.olympusgoldrush.ulympus.game.screens.ResultDoneScreen
import com.olympusgoldrush.ulympus.game.screens.RulesScreen
import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedScreen
import com.olympusgoldrush.ulympus.game.utils.gdxGame
import com.olympusgoldrush.ulympus.game.utils.runGDX

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
        RulesScreen       ::class.java.name -> RulesScreen()
        SettingsScreen    ::class.java.name -> SettingsScreen()

        else -> MenuScreen()
    }

}