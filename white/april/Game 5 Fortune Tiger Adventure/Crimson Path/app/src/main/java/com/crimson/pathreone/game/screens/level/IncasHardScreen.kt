package com.crimson.pathreone.game.screens.level

import com.crimson.pathreone.game.LibGDXGame
import com.crimson.pathreone.game.actors.ATile
import com.crimson.pathreone.game.screens.IncasResultScreen
import com.crimson.pathreone.game.utils.actor.setOnClickListener
import com.crimson.pathreone.game.utils.advanced.AdvancedGroup
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IncasHardScreen(_game: LibGDXGame): ILevelScreen(_game, Static.TypeScreen.HARD) {

    private val COUNT_PAIR = 10
    private val COLUMN = 5

    private var tile1: ATile? = null
    private var tile2: ATile? = null

    private var clickCounter = 0
    private var winCounter   = 0

    override fun AdvancedGroup.addActorsOnTmpGroup() {
        addTiles()
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedGroup.addTiles() {
        val regions = game.allAssets.golds.shuffled().take(COUNT_PAIR)

        var n = 0
        val tiles = List<ATile>(COUNT_PAIR * 2) {
            if (it == COUNT_PAIR) n = COUNT_PAIR
            ATile(this@IncasHardScreen,it-n, regions[it-n])
        }

        var nx = 194f
        var ny = 1193f

        tiles.onEachIndexed { index, tile ->
            addActor(tile)
            tile.setBounds(nx, ny, 100f, 100f)

            nx += 98f + 100f
            if (index.inc() % COLUMN == 0) {
                nx = 194f
                ny -= 110f + 100f
            }

            tile.setOnClickListener(game.soundUtil) {
                clickCounter++

                if (clickCounter <= 2) {
                    tile.animOpen()

                    when (clickCounter) {
                        1 -> tile1 = tile
                        2 -> {
                            coroutine?.launch {
                                delay(700)
                                tile2 = tile

                                if (tile1?.id == tile2?.id) {
                                    winCounter++
                                    if (winCounter == COUNT_PAIR) {
                                        IncasResultScreen.isWin = true
                                        game.navigationManager.navigate(IncasResultScreen::class.java.name)
                                    }
                                } else {
                                    tile1?.animClose()
                                    tile2?.animClose()
                                }

                                clickCounter = 0
                            }
                        }
                    }
                }

            }
        }
    }

}