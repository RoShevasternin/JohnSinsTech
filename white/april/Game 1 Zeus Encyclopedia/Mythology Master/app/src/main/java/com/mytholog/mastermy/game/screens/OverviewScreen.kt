package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.github.tommyettinger.textra.Font
import com.mytholog.mastermy.R
import com.mytholog.mastermy.appContext
import com.mytholog.mastermy.game.actors.layout.AVerticalGroup
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.actors.AOverviewCard
import com.mytholog.mastermy.game.utils.GColor
import com.mytholog.mastermy.game.utils.TIME_ANIM
import com.mytholog.mastermy.game.utils.WIDTH_UI
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.animShowScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import com.mytholog.mastermy.game.utils.font.FontParameter
import com.mytholog.mastermy.game.utils.region

class OverviewScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var DECK_TYPE = DeckType.MONSTERS
    }

    private val fontParameter   = FontParameter()
    private val font78          = fontGenerator_Olympus.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(78))
    private val fontExtraBold22 = fontGenerator_Noah_Head_ExtraBold.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(22))
    private val fontMedium22    = fontGenerator_Noah_Head_Medium.generateFont(fontParameter.setCharacters(FontParameter.CharType.ALL).setSize(22))

    private val ls78 = Label.LabelStyle(font78, Color.WHITE)

    private val fontMedium_ExtraBold = Font().setFamily(
        Font.FontFamily(arrayOf(
        Font(fontMedium22).setName(AOverviewCard.NAME_FONT_MEDIUM),
        Font(fontExtraBold22).setName(AOverviewCard.NAME_FONT_EXTRABOLD),
    )))

    private val listRegionPanel = listOf(
        game.all.MONSTERS,
        game.all.ARTIFACTS,
        game.all.GODS,
        game.all.HEROES,
    )
    private val listRegionBG    = listOf(
        game.all.GAME_BG_1,
        game.all.GAME_BG_3,
        game.all.GAME_BG_5,
        game.all.GAME_BG_2,
    )
    private val listRegionCard  = listOf(
        game.all.listMonster,
        game.all.listArtefact,
        game.all.listGod,
        game.all.listHero,
    )[DECK_TYPE.ordinal]
    private val listNameCard    = listOf(
        appContext.resources.getStringArray(R.array.names_monsters),
        appContext.resources.getStringArray(R.array.names_artefacts),
        appContext.resources.getStringArray(R.array.names_gods),
        appContext.resources.getStringArray(R.array.names_heroes),
    )[DECK_TYPE.ordinal]
    private val listEffectCard  = listOf(
        appContext.resources.getStringArray(R.array.effects_monsters),
        appContext.resources.getStringArray(R.array.effects_artefacts),
        appContext.resources.getStringArray(R.array.effects_gods),
        appContext.resources.getStringArray(R.array.effects_heroes),
    )[DECK_TYPE.ordinal]
    private val listFailureCard = listOf(
        appContext.resources.getStringArray(R.array.failure_monsters),
        appContext.resources.getStringArray(R.array.failure_artefacts),
        appContext.resources.getStringArray(R.array.failure_gods),
        appContext.resources.getStringArray(R.array.failure_heroes),
    )[DECK_TYPE.ordinal]
    private val listVictoryCard = listOf(
        appContext.resources.getStringArray(R.array.victory_monsters),
        appContext.resources.getStringArray(R.array.victory_artefacts),
        appContext.resources.getStringArray(R.array.victory_gods),
        appContext.resources.getStringArray(R.array.victory_heroes),
    )[DECK_TYPE.ordinal]
    private val listDescripCard = listOf(
        appContext.resources.getStringArray(R.array.description_monsters),
        appContext.resources.getStringArray(R.array.description_artefacts),
        appContext.resources.getStringArray(R.array.description_gods),
        appContext.resources.getStringArray(R.array.description_heroes),
    )[DECK_TYPE.ordinal]

    private val btnBack  = AButton(this, AButton.Static.Type.Back)
    private val btnSett  = AButton(this, AButton.Static.Type.Settings)
    private val btnDown  = AButton(this, AButton.Static.Type.Down)
    private val imgPanel = Image(listRegionPanel[DECK_TYPE.ordinal])

    private val verticalGroup = AVerticalGroup(this, 54f, isWrap = true)
    private val scroll        = ScrollPane(verticalGroup)
    private val listOverviewCard = List(listRegionCard.size) { AOverviewCard(this,
        listRegionCard[it],
        listNameCard[it],
        listEffectCard[it],
        listFailureCard[it],
        listVictoryCard[it],
        listDescripCard[it],
        ls78,
        fontMedium_ExtraBold
    ) }

    enum class DeckType {
        MONSTERS, ARTIFACTS, GODS, HEROES
    }

    override fun show() {
        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(listRegionBG[DECK_TYPE.ordinal].region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtnBackAndSett()
        addImgPanel()
        addScroll()
        addBtnDown()
    }

    private fun AdvancedStage.addBtnBackAndSett() {
        addActors(btnBack, btnSett)
        btnBack.setBounds(38f,1744f,188f,104f)
        btnBack.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
        btnSett.setBounds(894f,1734f,136f,142f)
        btnSett.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(SettingsScreen::class.java.name, OverviewScreen::class.java.name)
            }
        }
    }

    private fun AdvancedStage.addBtnDown() {
        addActor(btnDown)
        btnDown.setBounds(937f,61f,95f,171f)
        btnDown.setOnClickListener {
            scroll.scrollPercentY += 0.1f
        }
    }

    private fun AdvancedStage.addImgPanel() {
        addActor(imgPanel)
        imgPanel.setBounds(304f,1601f,472f,138f)
    }

    private fun AdvancedStage.addScroll() {
        addActor(scroll)
        scroll.setBounds(117f,0f,849f,1526f)
        verticalGroup.setSize(849f,1526f)

        listOverviewCard.onEach {  card ->
            card.setSize(849f,543f)
            verticalGroup.addActor(card)
        }

    }

}