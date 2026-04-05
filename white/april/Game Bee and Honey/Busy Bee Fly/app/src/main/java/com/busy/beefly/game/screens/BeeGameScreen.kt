package com.busy.beefly.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.busy.beefly.game.LibGDXGame
import com.busy.beefly.game.box2d.AbstractBody
import com.busy.beefly.game.box2d.BodyId
import com.busy.beefly.game.box2d.WorldUtil
import com.busy.beefly.game.box2d.bodies.BBee
import com.busy.beefly.game.box2d.bodies.BHoney
import com.busy.beefly.game.box2d.bodiesGroup.BGBorders
import com.busy.beefly.game.utils.HEIGHT_UI
import com.busy.beefly.game.utils.TIME_ANIM
import com.busy.beefly.game.utils.WIDTH_BOX2D
import com.busy.beefly.game.utils.WIDTH_UI
import com.busy.beefly.game.utils.actor.animHide
import com.busy.beefly.game.utils.advanced.AdvancedBox2dScreen
import com.busy.beefly.game.utils.advanced.AdvancedStage
import com.busy.beefly.game.utils.advanced.isBlue
import com.busy.beefly.game.utils.font.FontParameter
import com.busy.beefly.game.utils.runGDX
import com.busy.beefly.game.utils.toB2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BeeGameScreen(override val game: LibGDXGame): AdvancedBox2dScreen(WorldUtil()) {

    private val fontParameter = FontParameter().setCharacters(FontParameter.CharType.ALL).setSize(50)
    private val font50        = fontGenerator_Itim.generateFont(fontParameter)

    // Actor
    private val medImg      = Image(game.allAssets.med)
    private val aCounterLbl = Label("0", Label.LabelStyle(font50, Color.WHITE))

    // Body
    private val bBee       = BBee(this)
    private val bHoneyList = List(10) { BHoney(this) }

    // BodyGroup
    private val bgBorders  = BGBorders(this)

    // Field
    private val honeyFlow = MutableSharedFlow<BHoney>(replay = 10)
    private val beeFlow   = MutableSharedFlow<BBee>(replay = 1)
    private var counter = 0
    private val randomY get() = (230..1000).random().toFloat()

    override fun show() {
        isBlue = true
        super.show()
    }

    override fun dispose() {
        bgBorders.destroy()
        super.dispose()
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        createBG_Borders()
        createB_Bee()
        createB_Honey()

        addMed()
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        game.soundUtil.apply { play(SLASH,) }

        bBee.body?.applyLinearImpulse(Vector2(0f, 20f), bBee.body?.position, true)
        return super.touchDown(screenX, screenY, pointer, button)
    }

    // ---------------------------------------------------
    // Add Actor
    // ---------------------------------------------------

    private fun AdvancedStage.addMed() {
        addActors(medImg, aCounterLbl)
        medImg.setBounds(758f, 17f, 330f, 102f)
        aCounterLbl.apply {
            setBounds(927f, 28f, 105f, 74f)
            setAlignment(Align.center)
        }
    }

    // ---------------------------------------------------
    // create BodyGroup
    // ---------------------------------------------------

    private fun createBG_Borders() {
        bgBorders.create(0f, 0f, WIDTH_UI, HEIGHT_UI)
    }

    // ---------------------------------------------------
    // create Body
    // ---------------------------------------------------

    private fun createB_Bee() {
        bBee.create(141f, 800f, 270f, 214f)
        bBee.body?.apply { applyLinearImpulse(Vector2(5f, 0f), worldCenter, true) }

        bBee.beginContactBlockArray.add(AbstractBody.ContactBlock {
            when(it.id) {
                BodyId.DOWN -> {
                    game.soundUtil.apply { play(DOWN,) }
                    stageUI.root.animHide(TIME_ANIM) { game.navigationManager.navigate(BeeMenuScreen::class.java.name) }
                }
                BodyId.HONEY -> {
                    game.soundUtil.apply { play(MED) }
                    honeyFlow.tryEmit(it as BHoney)
                    counter++
                    aCounterLbl.setText("$counter")

                    it.startEffect()
                }
            }
        })
        bBee.renderBlockArray.add(AbstractBody.RenderBlock {
            if (bBee.body!!.position.x > WIDTH_BOX2D) {
                if (bBee.isStart.getAndSet(false)) beeFlow.tryEmit(bBee)
            }
        })

        coroutine?.launch {
            beeFlow.collect { bee ->
                runGDX {
                    bee.body?.apply {
                        setLinearVelocity(0f,0f)
                        setTransform(Vector2(0f, position.y), 0f)
                        applyLinearImpulse(Vector2(5f, 0f), worldCenter, true)
                        bee.isStart.set(true)
                    }
                }
            }
        }
    }

    private fun createB_Honey() {
        bHoneyList.onEach { honey ->
            honey.id = BodyId.HONEY
            honey.create(WIDTH_UI+100, 0f, 120f, 120f)
            honeyFlow.tryEmit(honey)

            honey.renderBlockArray.add(AbstractBody.RenderBlock {
                if (honey.body!!.position.x < -3f) {
                    if (honey.isStart.getAndSet(false)) honeyFlow.tryEmit(honey)
                }
            })
        }

        coroutine?.launch {
            launch {
                honeyFlow.collect { honey ->
                    runGDX {
                        honey.body?.apply {
                        setLinearVelocity(0f,0f)
                            setTransform(Vector2(WIDTH_UI+100, 0f).toB2, 0f)
                        }
                    }
                }
            }
            launch {
                honeyFlow.collect { honey ->
                    runGDX {
                        honey.body?.apply {
                            setTransform(Vector2(WIDTH_UI+100, randomY).toB2, 0f)
                            applyLinearImpulse(Vector2(-5f, 0f), worldCenter, true)
                            honey.isStart.set(true)
                        }
                    }
                    delay((1000..2000L).random())
                }
            }
        }
    }

}