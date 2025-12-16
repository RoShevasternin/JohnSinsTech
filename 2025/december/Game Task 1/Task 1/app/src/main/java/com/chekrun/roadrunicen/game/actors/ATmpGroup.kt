package com.chekrun.roadrunicen.game.actors

import com.chekrun.roadrunicen.game.utils.advanced.AdvancedGroup
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}