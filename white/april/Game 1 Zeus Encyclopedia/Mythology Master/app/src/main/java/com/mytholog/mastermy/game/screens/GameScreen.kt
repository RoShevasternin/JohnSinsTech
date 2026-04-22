package com.mytholog.mastermy.game.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.mytholog.mastermy.game.LibGDXGame
import com.mytholog.mastermy.game.actors.AGameCard
import com.mytholog.mastermy.game.actors.AGamePlayer
import com.mytholog.mastermy.game.actors.APauseGroup
import com.mytholog.mastermy.game.screens.SettingGameScreen.Companion
import com.mytholog.mastermy.game.utils.*
import com.mytholog.mastermy.game.utils.actor.*
import com.mytholog.mastermy.game.utils.advanced.AdvancedScreen
import com.mytholog.mastermy.game.utils.advanced.AdvancedStage
import com.mytholog.mastermy.game.utils.font.FontParameter
import com.mytholog.mastermy.util.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameScreen(override val game: LibGDXGame) : AdvancedScreen() {

    companion object {
        var WINNER_ICON_INDEX = 0
            private set
        var WINNER_COINS = 0
            private set
        var WINNER_NUM = 1
            private set
    }

    private val MAX_COINS             = SettingGameScreen.GAME_DIFFICULTY.coins
    private val NUM_PLAYER            = SettingGameScreen.NUM_PLAYER
    private val GAME_DURATION         = SettingGameScreen.GAME_DURATION
    private val ARR_PLAYER_ICON_INDEX = SelectPlayerScreen.ARR_PLAYER_ICON_INDEX
    private val LIST_CARD_TYPE        = CustomDeckScreen.LIST_CARD_TYPE

    private val fontParameter = FontParameter()
    private val font101       = fontGenerator_Noah_Head_Bold.generateFont(fontParameter.setCharacters(FontParameter.CharType.NUMBERS.chars + ":").setSize(101))

    private val ls101 = Label.LabelStyle(font101, GColor.yellow)

    private val imgPauseBtn = Image(game.all.PAUSE_BTN)
    private val listPlayer  = ArrayDeque(List(NUM_PLAYER) { AGamePlayer(this, it.inc(), ARR_PLAYER_ICON_INDEX[it]) })
    private val imgTurn     = Image(game.all.YOUR_TURN)
    private val listDeck    = List(2) { Image(game.all.listTask[0]) }
    private val imgTask     = Image()
    private val imgWin      = Image()
    private val listCoins   = List(15) { Image(game.all.COIN) }
    private val lblTimer    = Label("$GAME_DURATION:00", ls101)
    private val pauseGroup  = APauseGroup(this)

    private val listTextureCard = listOf(
        game.all.listMonster,
        game.all.listArtefact,
        game.all.listGod,
        game.all.listHero,
    )

    private val listListCard = List(NUM_PLAYER) {
        List(5) { AGameCard(this, listTextureCard[LIST_CARD_TYPE.random().ordinal].random()) }
    }

    // Field
    private var currentPlayerIndex = 0
    private var currentCardIndex   = 0

    private val listPosCard = listOf(
        Vector2(83f,-260f),
        Vector2(197f,-212f),
        Vector2(364f,-190f),
        Vector2(509f,-227f),
        Vector2(646f,-277f),
    )
    private val listAngleCard = listOf(10f, 5f, 0f, -7f, -15f)
    private val listPosPlayer = listOf(
        Vector2(315f,352f),
        Vector2(55f,1513f),
        Vector2(701f,1513f),
    )
    private val listPosPlayerHide = listOf(
        Vector2(315f,-600f),
        Vector2(-400f,1513f),
        Vector2(1100f,1513f),
    )
    private val scaleMiniPlayer = 0.72f

    private var isPause = false

    private fun randomCoinX() = (89..797).random().toFloat()
    private fun randomCoinY() = (632..1297).random().toFloat()

    override fun show() {
        stageUI.root.rotation = -75f
        stageUI.root.x        = WIDTH_UI
        setBackBackground(game.all.listGameBG.random().region)
        super.show()
        stageUI.root.animShowScreen(TIME_ANIM)
    }

    override fun AdvancedStage.addActorsOnStageUI() {
        stageBack.addAndFillActor(Image(drawerUtil.getRegion(Color.BLACK.cpy().apply { a = 0.4f })))

        addImgPauseBtn()
        addLblTimer()
        addListGamePlayer()
        addImgYourTurn()
        addListGameCard()

        addListDeck()
        addImgTask()
        addImgWin()
        addListCoin()

        addAndFillActor(pauseGroup)
        pauseGroup.color.a = 0f
        pauseGroup.disable()
        pauseGroup.blockPlay = Block {
            pauseGroup.animHide(TIME_ANIM) {
                pauseGroup.disable()
                isPause = false
            }
        }
    }

    private fun AdvancedStage.addLblTimer() {
        addActor(lblTimer)
        lblTimer.apply {
            setBounds(409f,1616f,262f,126f)
            setAlignment(Align.center)
        }

        var minutes = GAME_DURATION  // наприклад, 30 хвилин
        var seconds = 0  // стартуємо з 0 секунд

        coroutine?.launch {
            while (minutes >= 0 && isActive) {
                if (isPause.not()) {
                    runGDX {
                        val formattedMinutes = if (minutes < 10) "0$minutes" else "$minutes"
                        val formattedSeconds = if (seconds < 10) "0$seconds" else "$seconds"
                        lblTimer.setText("$formattedMinutes:$formattedSeconds")
                    }

                    delay(1000)  // очікуємо 1 секунду

                    // Оновлюємо лічильник секунд
                    if (seconds == 0) {
                        if (minutes > 0) {
                            minutes -= 1  // зменшуємо хвилини, якщо секунд стало 0
                            seconds = 59  // оновлюємо секунди на 59
                        } else {
                            break  // завершуємо таймер, якщо хвилини і секунди закінчились
                        }
                    } else {
                        seconds -= 1  // зменшуємо секунди
                    }
                }
            }

            listPlayer.maxBy { it.coin }.also { winner ->
                WINNER_ICON_INDEX  = winner.iconIndex
                WINNER_COINS       = winner.coin
                WINNER_NUM         = winner.num
                WelcomeScreen.COIN = WINNER_COINS
            }

            stageUI.root.animHideScreen(TIME_ANIM) {
                game.navigationManager.navigate(WinnerScreen::class.java.name)
            }

        }
    }

    private fun AdvancedStage.addImgPauseBtn() {
        addActor(imgPauseBtn)
        imgPauseBtn.setBounds(499f,1760f,80f,85f)
        imgPauseBtn.setOnClickListener(game.soundUtil) {
            isPause = true
            pauseGroup.enable()
            pauseGroup.animShow(TIME_ANIM)
        }
    }

    private fun AdvancedStage.addImgYourTurn() {
        addActor(imgTurn)
        imgTurn.setBounds(164f,923f,749f,224f)
        imgTurn.setOrigin(Align.center)
        imgTurn.addAction(Actions.forever(Actions.sequence(
            Actions.scaleBy(-0.2f, -0.2f, 0.3f),
            Actions.scaleBy(0.2f, 0.2f, 0.3f),
        )))
    }

    private fun AdvancedStage.addListGamePlayer() {
        listPlayer.onEachIndexed { index, player ->
            addActor(player)
            player.setBounds(listPosPlayer[index], Vector2(541f,526f))
            if (index != 0) player.setScale(scaleMiniPlayer)
        }
    }

    private fun AdvancedStage.addListGameCard() {
        listListCard.onEachIndexed { indexList, listCard ->
            listCard.onEachIndexed { index, card ->
                addActor(card)
                card.setBounds(listPosCard[index], Vector2(315f, 522f))
                card.setOrigin(Align.center)
                card.rotation = listAngleCard[index]

                if (indexList != 0) card.y = -600f

                card.setOnClickListener(null) {
                    game.soundUtil.apply { play(this.card) }

                    currentCardIndex = index

                    card.disable()
                    imgTurn.animHide(0.25f)
                    animHideCards()
                    listPlayer.first().animDown()
                    card.animShowCard {
                        listDeck.last().apply {
                            enable()
                            animDeck()
                        }
                    }
                }
            }
        }
    }

    private fun AdvancedStage.addListDeck() {
        listDeck.onEach { img ->
            addActor(img)
            img.setBounds(952f,706f,307f,507f)
        }
        listDeck.last().apply {
            setOrigin(Align.center)
            setOnClickListener(null) {
                game.soundUtil.apply { play(card) }

                this.disable()
                animShowTask {
                    animShowCoin {
                        animShowWin {
                            nextPlayer()
                        }
                    }
                }
            }
            disable()
        }
    }

    private fun AdvancedStage.addImgTask() {
        imgTask.color.a = 0f
        addActor(imgTask)
        imgTask.disable()
        imgTask.setBounds(408f,639f,440f,727f)
        imgTask.setOrigin(Align.center)
        imgTask.rotation = -10f
    }

    private fun AdvancedStage.addImgWin() {
        imgWin.color.a = 0f
        addActor(imgWin)
        imgWin.disable()
        imgWin.setBounds(268f,649f,440f,729f)
    }

    private fun AdvancedStage.addListCoin() {
        listCoins.onEach { coin ->
            coin.color.a = 0f
            addActor(coin)
            coin.disable()
            coin.setBounds(randomCoinX(), randomCoinY(), 100f, 97f)
            coin.setOrigin(Align.center)
        }
    }

    // Anim ----------------------------------------------------------------

    private fun animHideCards() {
        listListCard[currentPlayerIndex].filterIndexed { index, _ -> index != currentCardIndex }.onEach { card ->
            card.addAction(Actions.parallel(
                Actions.fadeOut(0.25f),
                Actions.moveBy(0f,-600f, 0.35f, Interpolation.fade)
            ))
        }
    }

    private fun animShowCards() {
        listListCard[currentPlayerIndex].onEachIndexed { index, card ->
            card.enable()
            card.addAction(Actions.parallel(
                Actions.fadeIn(0.40f),
                Actions.moveTo(listPosCard[index].x, listPosCard[index].y, 0.35f, Interpolation.swingOut)
            ))
        }
    }

    private fun AGameCard.animShowCard(blockEnd: Block) {
        addAction(Actions.sequence(
            Actions.parallel(
                Actions.rotateTo(10f, 0.45f),
                Actions.moveTo(213f, 744f, 0.45f, Interpolation.sine),
                Actions.scaleTo(1.3968f, 1.3968f, 0.45f)
            ),
            Actions.run { blockEnd.invoke() }
        ))
    }

    private fun animShowTask(blockEnd: Block) {
        listDeck.last().clearActions()
        listDeck.last().addAction(Actions.sequence(
            Actions.parallel(
                Actions.rotateTo(-10f, 0.35f),
                Actions.moveTo(475f, 748f, 0.35f, Interpolation.sine),
                Actions.scaleTo(1.4379f, 1.4379f, 0.35f)
            ),
            Actions.run {
                imgTask.drawable = TextureRegionDrawable(game.all.listTask.random())
                imgTask.animShow(0.25f)
            },
            Actions.delay(1.25f),
            Actions.run {
                blockEnd.invoke()
            }
        ))
    }

    private fun animDeck() {
        listDeck.last().addAction(Actions.forever(
            Actions.sequence(
                Actions.scaleBy(0.2f, 0.2f, 0.45f, Interpolation.bounceOut),
                Actions.scaleBy(-0.2f, -0.2f, 0.35f),
            )
        ))
    }

    private fun AGamePlayer.animDown() {
        addAction(Actions.moveTo(315f, 30f, 0.35f))
    }

    private fun animShowCoin(blockEnd: Block) {
        coroutine?.launch {
            listCoins.onEach { coin ->
                runGDX {
                    coin.animShow(0.35f)
                }
                delay((100..150L).random())
            }
            listCoins.onEach { coin ->
                runGDX {
                    coin.addAction(Actions.sequence(
                        Actions.parallel(
                            Actions.rotateTo((-90..90).random().toFloat(), 0.35f),
                            Actions.moveTo(628f, 443f, 0.35f, Interpolation.pow3),
                        ),
                        Actions.fadeOut(0.10f),
                        Actions.run {
                            listPlayer.first().coin += (1..5).random()
                            game.soundUtil.apply { play(this.listCoin.random()) }
                            coin.setPosition(randomCoinX(), randomCoinY())

                            if (listPlayer.first().coin >= MAX_COINS) {
                                WINNER_ICON_INDEX  = listPlayer.first().iconIndex
                                WINNER_COINS       = listPlayer.first().coin
                                WINNER_NUM         = listPlayer.first().num
                                WelcomeScreen.COIN = WINNER_COINS

                                stageUI.root.animHideScreen(TIME_ANIM) {
                                    game.navigationManager.navigate(WinnerScreen::class.java.name)
                                }
                            }

                        }
                    ))
                }
                delay((50..120L).random())
            }
            runGDX { blockEnd.invoke() }
        }
    }

    private fun animShowWin(blockEnd: Block) {
        val randomTextureCard = listTextureCard[LIST_CARD_TYPE.random().ordinal].random()

        listListCard[currentPlayerIndex][currentCardIndex].also { card ->
            card.animHide(0.55f) {
                card.image.drawable = TextureRegionDrawable(randomTextureCard)
                card.rotation = listAngleCard[currentCardIndex]
                card.setPosition(listPosCard[currentCardIndex].x, -600f)
                card.setScale(1f)
            }
        }
        listDeck.last().also { deck ->
            deck.animHide(0.55f) {
                deck.setPosition(952f,706f)
                deck.setScale(1f)
                deck.rotation = 0f
            }
        }
        imgTask.animHide(0.55f)

        imgWin.drawable = TextureRegionDrawable(randomTextureCard)
        imgWin.addAction(Actions.sequence(
            Actions.fadeIn(0.65f),
            Actions.run { game.soundUtil.apply { play(bonus) } },
            Actions.delay(1.65f),
            Actions.parallel(
                Actions.moveTo(341f,-900f, 0.55f, Interpolation.swingIn),
                Actions.fadeOut(0.65f)
            ),
            Actions.run {
                imgWin.setPosition(268f,649f)
                blockEnd.invoke()
            }
        ))
    }

    private fun animChangePlayers(blockEnd: Block) {
        listPlayer.onEachIndexed { index, player ->
            player.addAction(Actions.sequence(
                Actions.moveTo(listPosPlayerHide[index].x, listPosPlayerHide[index].y, 0.4f, Interpolation.sineIn),
                Actions.run {
                    if (index == listPlayer.lastIndex) {
                        coroutine?.launch {

                            val firstPlayer = listPlayer.removeFirst()
                            listPlayer.addLast(firstPlayer)
                            delay(200)

                            runGDX {
                                listPlayer.onEachIndexed { newIndex, newPlayer ->
                                    log("$newIndex | ${newPlayer.hashCode()}")
                                    val scale = if (newIndex == 0) 1f else scaleMiniPlayer

                                    newPlayer.setScale(scale)
                                    newPlayer.setPosition(listPosPlayerHide[newIndex].x, listPosPlayerHide[newIndex].y)

                                    newPlayer.addAction(Actions.sequence(
                                        Actions.moveTo(listPosPlayer[newIndex].x, listPosPlayer[newIndex].y, 0.4f, Interpolation.swingOut),
                                        Actions.run {
                                            if (newIndex == listPlayer.lastIndex) blockEnd.invoke()
                                        }
                                    ))
                                }
                            }

                        }
                    }
                }
            ))
        }
    }

    // Logic -------------------------------------------------------------------

    private fun nextPlayer() {
        animChangePlayers {
            currentPlayerIndex = if (currentPlayerIndex + 1 <= listPlayer.lastIndex) currentPlayerIndex + 1 else 0
            animShowCards()
            listDeck.last().animShow()
            imgTurn.animShow(0.3f)
        }
    }

}