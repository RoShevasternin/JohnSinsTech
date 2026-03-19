package com.goalblast.onewingame.game.actors

import com.goalblast.onewingame.game.utils.advanced.AdvancedGroup
import com.goalblast.onewingame.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}