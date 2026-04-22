package com.crimson.pathreone.game.screens.common

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.crimson.pathreone.game.LibGDXGame
import com.crimson.pathreone.game.actors.progress.IncasValueProgress
import com.crimson.pathreone.game.utils.advanced.AdvancedGroup
import kotlinx.coroutines.launch

class IncasSettingsScreen(_game: LibGDXGame): ICommonScreen(_game, Static.TypeScreen.Menu) {

    private val soundImg = Image(game.allAssets.sound)
    private val musicImg = Image(game.allAssets.music)

    private val soundProgress by lazy { IncasValueProgress(this) }
    private val musicProgress by lazy { IncasValueProgress(this) }

    override fun AdvancedGroup.addActorsOnTmpGroup() {
        addSoundMusicImg()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedGroup.addSoundMusicImg() {
        addActors(soundImg, musicImg)
        soundImg.setBounds(271f, 1051f, 538f, 206f)
        musicImg.setBounds(264f, 644f, 553f, 213f)

        addActors(soundProgress, musicProgress)
        // sound
        soundProgress.apply {
            setBounds(429f, 1083f, 375f, 52f)

            setProgressPercent(screen.game.soundUtil.volumeLevel)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.soundUtil.volumeLevel = it
                }
            }
        }
        // music
        musicProgress.apply {
            setBounds(437f, 677f, 375f, 52f)

            setProgressPercent(screen.game.musicUtil.volumeLevelFlow.value)

            coroutine?.launch {
                progressPercentFlow.collect {
                    screen.game.musicUtil.volumeLevelFlow.value = it
                }
            }
        }
    }

}