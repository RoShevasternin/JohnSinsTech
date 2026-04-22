package com.mytholog.mastermy.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.mytholog.mastermy.game.utils.actor.setOnClickListener
import com.mytholog.mastermy.game.utils.advanced.AdvancedGroup
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.font.FontParameter

class AGamePlayer(
    override val screen: AdvancedScreen,
    val num: Int,
    val iconIndex: Int,
): AdvancedGroup() {

    var coin = 0
        set(value) {
            field = value
            lblCoin.setText(field)
        }

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "Player")
    private val font74        = screen.fontGenerator_Olympus.generateFont(fontParameter.setSize(74))
    private val font67        = screen.fontGenerator_Noah_Head_Bold.generateFont(fontParameter.setSize(67))

    private val ls74 = Label.LabelStyle(font74, Color.BLACK)
    private val ls67 = Label.LabelStyle(font67, Color.WHITE)

    private val imageIcon  = Image(screen.game.all.listPlayer[iconIndex])
    private val imagePanel = Image(screen.game.all.PANEL_PLAYER)
    private val imageCoin  = Image(screen.game.all.COIN)
    private val lblName    = Label("Player $num", ls74)
    private val lblCoin    = Label(coin.toString(), ls67)

    override fun addActorsOnGroup() {
        addActors(imageIcon, imagePanel, lblName, imageCoin, lblCoin)

        imageIcon.setBounds(0f,40f,451f,468f)
        imagePanel.setBounds(23f,0f,401f,117f)
        lblName.setBounds(84f,21f,280f,75f)
        lblName.setAlignment(Align.center)
        imageCoin.setBounds(313f,413f,100f,97f)
        lblCoin.setBounds(413f,419f,106f,84f)
    }

}