package com.roadrunnerhen.hen.game.actors

import com.roadrunnerhen.hen.game.utils.advanced.AdvancedGroup
import com.roadrunnerhen.hen.game.utils.advanced.AdvancedScreen

class ATmpGroup(
    override val screen: AdvancedScreen,
): AdvancedGroup() {

    override fun getPrefHeight(): Float {
        return height
    }

    override fun addActorsOnGroup() {}

}