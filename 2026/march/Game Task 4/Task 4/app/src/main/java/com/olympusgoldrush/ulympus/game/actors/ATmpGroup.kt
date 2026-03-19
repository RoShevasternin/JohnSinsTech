package com.olympusgoldrush.ulympus.game.actors

import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedGroup
import com.olympusgoldrush.ulympus.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}