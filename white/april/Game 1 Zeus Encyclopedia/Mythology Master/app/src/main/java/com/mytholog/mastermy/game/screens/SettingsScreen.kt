package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AButton
import com.mytholog.mastermy.game.actors.AProgressSettings
import com.mytholog.mastermy.game.utils.*
import com.mytholog.mastermy.game.utils.actor.animHideScreen
import com.mytholog.mastermy.game.utils.actor.animShowScreen
import com.mytholog.mastermy.game.utils.actor.setOnClickListener
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import kotlinx.coroutines.launch

class SettingsScreen(override val game: LibGDXGame) : AdvancedScreen() {

    private val btnBack       = AButton(this, AButton.Static.Type.Back)
    private val imgSouMusic   = Image(game.all.SOU_MUS)
    private val progressMusic = AProgressSettings(this)
    private val progressSound = AProgressSettings(this)

    override fun show() {
        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.all.BACKGROUND_MAIN.region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        addBtnBack()
        addImgSouMus()
        addProgress()

        addUpdates()
    }

    private fun AdvancedStage.addBtnBack() {
        addActor(btnBack)
        btnBack.setBounds(38f,1744f,188f,104f)
        btnBack.setOnClickListener {
            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.back()
            }
        }
    }

    private fun AdvancedStage.addImgSouMus() {
        addActor(imgSouMusic)
        imgSouMusic.setBounds(256f,761f,581f,754f)
    }

    private fun AdvancedStage.addProgress() {
        addActors(progressMusic, progressSound)

        progressMusic.apply {
            setBounds(298f,765f,496f,115f)

            progressPercentFlow.value = screen.game.musicUtil.volumeLevelFlow.value

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        screen.game.musicUtil.volumeLevelFlow.value = it
                    }
                }
            }
        }
        progressSound.apply {
            setBounds(298f,1172f,496f,115f)

            progressPercentFlow.value = screen.game.soundUtil.volumeLevel

            coroutine?.launch {
                progressPercentFlow.collect {
                    runGDX {
                        screen.game.soundUtil.volumeLevel = it
                    }
                }
            }
        }
    }


    private fun AdvancedStage.addUpdates() {
        val imgCheck = Image(game.all.DONE)
        addActor(imgCheck)
        imgCheck.setBounds(117f, 135f, 81f, 84f)

        val listCheckX = listOf(117f, 308f, 499f, 690f, 881f)

        var nx = 71f
        game.all.listGameBG.onEachIndexed { index, texture ->
            val img = Image(texture)
            addActor(img)
            img.setBounds(nx, 240f, 173f, 308f)
            nx += 18 + 173

            img.setOnClickListener(game.soundUtil) {
                imgCheck.x = listCheckX[index]
                backBackgroundImage.drawable = TextureRegionDrawable(texture)
            }
        }
    }

}