package com.mytholog.mastermy.game.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.mytholog.mastermy.game.utils.Block
import com.mytholog.mastermy.game.utils.actor.setOnClickListener
import com.mytholog.mastermy.game.utils.advanced.AdvancedGroup
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.font.FontParameter

class ASelectPlayer(
    override val screen: AdvancedScreen,
    val num: Int,
): AdvancedGroup() {

    private val listTextureIcon = screen.game.all.listPlayer

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.NUMBERS.chars + "Player")
    private val font74        = screen.fontGenerator_Olympus.generateFont(fontParameter.setSize(74))

    private val ls74 = Label.LabelStyle(font74, Color.BLACK)

    private val image     = Image(screen.game.all.SELECT_PLAYER)
    private val lblName   = Label("Player $num", ls74)
    private val imageIcon = Image(listTextureIcon.first())

    private var iconIndex = 0

    var blockSelectIcon: (Int) -> Unit = {}

    override fun addActorsOnGroup() {
        addActor(imageIcon)
        addAndFillActor(image)
        addActor(lblName)

        lblName.setBounds(176f,21f,270f,75f)
        lblName.setAlignment(Align.center)
        imageIcon.setBounds(87f,40f,451f,468f)

        val aLeft  = Actor()
        val aRight = Actor()
        addActors(aLeft, aRight)
        aLeft.apply {
            setBounds(0f,219f,82f,93f)
            setOnClickListener(screen.game.soundUtil) {
                if (iconIndex - 1 >= 0) {
                    iconIndex--
                    imageIcon.drawable = TextureRegionDrawable(listTextureIcon[iconIndex])
                    blockSelectIcon(iconIndex)
                }
            }
        }
        aRight.apply {
            setBounds(539f,219f,82f,93f)
            setOnClickListener(screen.game.soundUtil) {
                if (iconIndex + 1 <= listTextureIcon.lastIndex) {
                    iconIndex++
                    imageIcon.drawable = TextureRegionDrawable(listTextureIcon[iconIndex])
                    blockSelectIcon(iconIndex)
                }
            }
        }
    }

}