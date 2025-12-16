package com.chekrun.roadrunicen.game.actors.puzzle

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Align
import com.chekrun.roadrunicen.game.utils.puzzle.NumberPuzzles
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedGroup
import com.chekrun.roadrunicen.game.utils.advanced.AdvancedScreen
import com.chekrun.roadrunicen.game.utils.puzzle.Puzzles

class APuzzlePanel(
    override val screen: AdvancedScreen,
    region: TextureRegion,
): AdvancedGroup() {

    private val numberPuzzles = getNumberPuzzlesByLevel()
    private val puzzles       = Puzzles.generatePuzzles(screen, region, numberPuzzles)

    var finishBlock: () -> Unit = { }


    override fun addActorsOnGroup() {
        val puzzleSize = width / numberPuzzles.horizontalNumberPuzzles

        var nx = 0f
        var ny = height-puzzleSize

        puzzles.onEachIndexed { index, puzzle ->
            puzzle.apply {
                setBounds(nx, ny, puzzleSize, puzzleSize)
                nx += puzzleSize
                if (index.inc() % numberPuzzles.horizontalNumberPuzzles == 0) {
                    nx = 0f
                    ny -= puzzleSize
                }
                setOrigin(Align.center)
                rotation = listOf(90f, 180f, 270f, 360f).random()
            }

            addActor(puzzle)
            Puzzles.add(puzzle)
        }

        Puzzles.doAfterAssembledPuzzles = {
            finishBlock()
        }
    }

    // ---------------------------------------------------
    // Logic
    // ---------------------------------------------------

    private fun getNumberPuzzlesByLevel() = NumberPuzzles._3x3 //NumberPuzzles.entries.random()

}