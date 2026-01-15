package com.roadrunnerhen.hen.game.utils.advanced

abstract class AdvancedMainScreen : AdvancedScreen() {

    abstract val aMain: AdvancedMainGroup

    abstract fun AdvancedStage.addMain()

}