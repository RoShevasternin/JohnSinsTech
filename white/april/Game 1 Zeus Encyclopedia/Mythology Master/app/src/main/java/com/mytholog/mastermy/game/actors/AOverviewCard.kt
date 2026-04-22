package com.mytholog.mastermy.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.utils.Align
import com.github.tommyettinger.textra.Font
import com.github.tommyettinger.textra.TypingLabel
import com.mytholog.mastermy.game.utils.GColor
import com.mytholog.mastermy.game.utils.advanced.AdvancedGroup
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen

class AOverviewCard(
    override val screen: AdvancedScreen,
    val textureCard: Texture,
    val nameCard   : String,
    val effectOverview     : String,
    val failureOverview    : String,
    val victoryOverview    : String,
    val descriptionOverview: String,
    val lsName  : LabelStyle,
    val font    : Font,
): AdvancedGroup() {

    companion object {
        val NAME_FONT_MEDIUM    = "Medium"
        val NAME_FONT_EXTRABOLD = "ExtraBold"
    }

    private val overview = "[#${GColor.yellow}][@$NAME_FONT_EXTRABOLD]Effect: [#${Color.WHITE}][@$NAME_FONT_MEDIUM]$effectOverview\n" +
            "[#${GColor.yellow}][@$NAME_FONT_EXTRABOLD]Failure: [#${Color.WHITE}][@$NAME_FONT_MEDIUM]$failureOverview\n" +
            "[#${GColor.yellow}][@$NAME_FONT_EXTRABOLD]Victory: [#${Color.WHITE}][@$NAME_FONT_MEDIUM]$victoryOverview\n" +
            "\n" + descriptionOverview

    private val imgCard        = Image(textureCard)
    private val lblName: Actor = if (nameCard.length > 12) ASpinningLabel(screen, nameCard, lsName, alignment = Align.left) else Label(nameCard, lsName)
    private val lblOverview    = TypingLabel(overview, font)

    override fun addActorsOnGroup() {
        addActors(imgCard, lblName, lblOverview)
        imgCard.setSize(328f,543f)
        lblName.setBounds(363f,418f,486f,79f)
        lblOverview.setBounds(362f,0f,487f,403f)
        lblOverview.wrap = true

        lblOverview.alignment = Align.topLeft
    }

}