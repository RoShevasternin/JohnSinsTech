package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.actors.TmpGroup
import com.mytholog.mastermy.game.utils.*
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.animShowScreen
import com.mytholog.mastermy.game.utils.actor.setPosition
import com.mytholog.mastermy.game.utils.advanced.AdvancedGroup
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TutorialScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val listRegionTutorial       = game.all.listTutorial
    private val currentTutorialIndexFlow = MutableStateFlow(0)

    private val btnBack  = AButton(this, AButton.Static.Type.Back)
    private val btnSett  = AButton(this, AButton.Static.Type.Settings)
    private val imgPanel = Image(listRegionTutorial[currentTutorialIndexFlow.value])
    private val btnLeft  = AButton(this, AButton.Static.Type.YellowLeft)
    private val btnRight = AButton(this, AButton.Static.Type.YellowRight)

    private val tmpGroup = TmpGroup(this)

    // Field
    private val listPosArrows = listOf(
        Vector2(0f,-300f) to Vector2(466f,539f),
        Vector2(325f,510f) to Vector2(593f,510f),
        Vector2(307f,190f) to Vector2(574f,190f),
        Vector2(301f,469f) to Vector2(568f,469f),
        Vector2(301f,439f) to Vector2(568f,439f),
    )

    override fun show() {
        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.all.BACKGROUND_MAIN.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        stageBack.addAndFillActor(Image(drawerUtil.getRegion(Color.BLACK.cpy().apply { a = 0.4f })))
        addAndFillActor(tmpGroup)
        addBtnBackAndSett()

        tmpGroup.apply {
            y = -HEIGHT

            addAndFillActor(imgPanel)
            addBtnArrows()
        }

        coroutine?.launch { collectCurrentIndexFlow() }
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
                game.navigationManager.navigate(SettingsScreen::class.java.name, TutorialScreen::class.java.name)
            }
        }
    }

    private fun AdvancedGroup.addBtnArrows() {
        addActors(btnLeft, btnRight)
        btnLeft.setSize(211f,117f)
        btnLeft.setOnClickListener { if (currentTutorialIndexFlow.value - 1 >= 0) currentTutorialIndexFlow.value-- }

        btnRight.setSize(211f,117f)
        btnRight.setOnClickListener {
            if (currentTutorialIndexFlow.value + 1 <= listRegionTutorial.lastIndex) {
                currentTutorialIndexFlow.value++
            } else {
                stageUI.root.animHideScreen(TIME_ANIM) {
                    game.navigationManager.navigate(TutorialCompleteScreen::class.java.name)
                }
            }
        }
    }

    // Logic ----------------------------------------------------------------------------

    private suspend fun collectCurrentIndexFlow() {
        currentTutorialIndexFlow.collect { index ->
            runGDX {
                animHideTutorial {
                    imgPanel.drawable = TextureRegionDrawable(listRegionTutorial[index])
                    btnLeft.setPosition(listPosArrows[index].first)
                    btnRight.setPosition(listPosArrows[index].second)

                    animShowTutorial()
                }
            }
        }
    }

    private fun animHideTutorial(block: Block = Block {  }) {
        tmpGroup.addAction(Actions.sequence(
            Actions.moveTo(0f, -HEIGHT, TIME_ANIM, Interpolation.pow2),
            Actions.run { block.invoke() }
        ))
    }

    private fun animShowTutorial(block: Block = Block {  }) {
        tmpGroup.addAction(Actions.sequence(
            Actions.moveTo(0f, 0f, TIME_ANIM, Interpolation.pow2),
            Actions.run { block.invoke() }
        ))
    }


}