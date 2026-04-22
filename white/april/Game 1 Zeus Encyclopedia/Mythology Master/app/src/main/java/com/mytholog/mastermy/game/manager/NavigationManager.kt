package com.mytholog.mastermy.game.manager

import com.badlogic.gdx.Gdx
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.screens.*
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.runGDX

class NavigationManager(val game: LibGDXGame) {

    val backStack = mutableListOf<String>()
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

    fun clearStack() {
        backStack.clear()
    }

    fun isBackStackEmpty() = backStack.isEmpty()

    private fun getScreenByName(name: String): AdvancedScreen = when (name) {
        SplashScreen          ::class.java.name -> SplashScreen(game)
        WelcomeScreen         ::class.java.name -> WelcomeScreen(game)
        SettingsScreen        ::class.java.name -> SettingsScreen(game)
        DeckOverviewScreen    ::class.java.name -> DeckOverviewScreen(game)
        OverviewScreen        ::class.java.name -> OverviewScreen(game)
        RulesScreen           ::class.java.name -> RulesScreen(game)
        TutorialScreen        ::class.java.name -> TutorialScreen(game)
        TutorialCompleteScreen::class.java.name -> TutorialCompleteScreen(game)
        SettingGameScreen     ::class.java.name -> SettingGameScreen(game)
        CustomDeckScreen      ::class.java.name -> CustomDeckScreen(game)
        SelectPlayerScreen    ::class.java.name -> SelectPlayerScreen(game)
        GameScreen            ::class.java.name -> GameScreen(game)
        WinnerScreen          ::class.java.name -> WinnerScreen(game)

        else -> WelcomeScreen(game)
    }

}