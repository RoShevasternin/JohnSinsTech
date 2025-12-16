package com.chekrun.roadrunicen.game.manager

import com.badlogic.gdx.Gdx
import com.chekrun.roadrunicen.game.screens.PuzzleScreen
import com.chekrun.roadrunicen.game.screens.LoaderScreen
import com.chekrun.roadrunicen.game.screens.MenuScreen
import com.chekrun.roadrunicen.game.screens.ResultFailScreen
import com.chekrun.roadrunicen.game.screens.ResultDoneScreen
import com.chekrun.roadrunicen.game.screens.RulesScreen
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedScreen
import com.chekrun.roadrunicen.game.utils.gdxGame
import com.chekrun.roadrunicen.game.utils.runGDX

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
        RulesScreen       ::class.java.name -> RulesScreen()
        PuzzleScreen      ::class.java.name -> PuzzleScreen()
        ResultDoneScreen ::class.java.name -> ResultDoneScreen()
        ResultFailScreen   ::class.java.name -> ResultFailScreen()

        else -> MenuScreen()
    }

}