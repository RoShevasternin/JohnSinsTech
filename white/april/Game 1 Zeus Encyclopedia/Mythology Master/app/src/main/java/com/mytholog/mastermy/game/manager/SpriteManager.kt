package com.mytholog.mastermy.game.manager

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

class SpriteManager(var assetManager: AssetManager) {

    var loadableAtlasList   = mutableListOf<AtlasData>()
    var loadableTexturesList   = mutableListOf<TextureData>()

    fun loadAtlas() {
        loadableAtlasList.onEach { assetManager.load(it.path, TextureAtlas::class.java) }
    }

    fun initAtlas() {
        loadableAtlasList.onEach { it.atlas = assetManager[it.path, TextureAtlas::class.java] }
        loadableAtlasList.clear()
    }

    // Texture
    fun loadTexture() {
        loadableTexturesList.onEach { assetManager.load(it.path, Texture::class.java) }
    }

    fun initTeture() {
        loadableTexturesList.onEach { it.texture = assetManager[it.path, Texture::class.java] }
        loadableTexturesList.clear()
    }


    enum class EnumAtlas(val data: AtlasData) {
        //ALL(AtlasData("atlas/all.atlas")),
    }

    enum class EnumTexture(val data: TextureData) {
        // Splash
        BACKGROUND_WELCOME(TextureData("textures/splash/background_welcome.png")),
        BLOCK             (TextureData("textures/splash/block.png")),

        // All
        BACKGROUND_MAIN  (TextureData("textures/background/background_main.png")),
        BACKGROUND_WINNER(TextureData("textures/background/background_winner.png")),
        GAME_BG_1        (TextureData("textures/background/game_bg_1.png")),
        GAME_BG_2        (TextureData("textures/background/game_bg_2.png")),
        GAME_BG_3        (TextureData("textures/background/game_bg_3.png")),
        GAME_BG_4        (TextureData("textures/background/game_bg_4.png")),
        GAME_BG_5        (TextureData("textures/background/game_bg_5.png")),

        ALL_CUSTOME          (TextureData("textures/all_custome.png")),
        ARTIFACTS            (TextureData("textures/artifacts.png")),
        BACK_DEF             (TextureData("textures/back_def.png")),
        BACK_PRESS           (TextureData("textures/back_press.png")),
        BEGIN_TUTORIAL_DEF   (TextureData("textures/begin_tutorial_def.png")),
        BEGIN_TUTORIAL_PRESS (TextureData("textures/begin_tutorial_press.png")),
        BOX_CHECK            (TextureData("textures/box_check.png")),
        BOX_DEF              (TextureData("textures/box_def.png")),
        COIN                 (TextureData("textures/coin.png")),
        COLODES              (TextureData("textures/colodes.png")),
        CURSOR               (TextureData("textures/cursor.png")),
        CUSTOME_CARDS        (TextureData("textures/custome_cards.png")),
        CUSTOME_DECK         (TextureData("textures/custome_deck.png")),
        DECK_OVERVIEW_DEF    (TextureData("textures/deck_overview_def.png")),
        DECK_OVERVIEW_PRESS  (TextureData("textures/deck_overview_press.png")),
        DONE                 (TextureData("textures/done.png")),
        DOWN_DEF             (TextureData("textures/down_def.png")),
        DOWN_PRESS           (TextureData("textures/down_press.png")),
        EASY_CHECK           (TextureData("textures/easy_check.png")),
        EASY_DEF             (TextureData("textures/easy_def.png")),
        GODS                 (TextureData("textures/gods.png")),
        HARD_CHECK           (TextureData("textures/hard_check.png")),
        HARD_DEF             (TextureData("textures/hard_def.png")),
        HEROES               (TextureData("textures/heroes.png")),
        MEDIUM_CHECK         (TextureData("textures/medium_check.png")),
        MEDIUM_DEF           (TextureData("textures/medium_def.png")),
        MENU                 (TextureData("textures/menu.png")),
        MENU_DEF             (TextureData("textures/menu_def.png")),
        MENU_PRESS           (TextureData("textures/menu_press.png")),
        MONSTERS             (TextureData("textures/monsters.png")),
        NEXT_DEF             (TextureData("textures/next_def.png")),
        NEXT_PRESS           (TextureData("textures/next_press.png")),
        NUMBER_TIME          (TextureData("textures/number_time.png")),
        PAUSE                (TextureData("textures/pause.png")),
        PAUSE_BTN            (TextureData("textures/pause_btn.png")),
        PLAY_DEF             (TextureData("textures/play_def.png")),
        PLAY_PRESS           (TextureData("textures/play_press.png")),
        PLAYER_1             (TextureData("textures/player_1.png")),
        PLAYER_2             (TextureData("textures/player_2.png")),
        PLAYER_3             (TextureData("textures/player_3.png")),
        PLAYER_4             (TextureData("textures/player_4.png")),
        PLAYER_5             (TextureData("textures/player_5.png")),
        RESTART_DEF          (TextureData("textures/restart_def.png")),
        RESTART_PRESS        (TextureData("textures/restart_press.png")),
        RETURN_TO_GAME_DEF   (TextureData("textures/return_to_game_def.png")),
        RETURN_TO_GAME_PRESS (TextureData("textures/return_to_game_press.png")),
        SAVE_DEF             (TextureData("textures/save_def.png")),
        SAVE_PRESS           (TextureData("textures/save_press.png")),
        SETTINGS_DEF         (TextureData("textures/settings_def.png")),
        SETTINGS_PRESS       (TextureData("textures/settings_press.png")),
        SOU_MUS              (TextureData("textures/sou_mus.png")),
        TUT_1                (TextureData("textures/tut_1.png")),
        TUT_2                (TextureData("textures/tut_2.png")),
        TUT_3                (TextureData("textures/tut_3.png")),
        TUT_4                (TextureData("textures/tut_4.png")),
        TUT_5                (TextureData("textures/tut_5.png")),
        TUT_COMPLETE         (TextureData("textures/tut_complete.png")),
        WELCOME_TZE          (TextureData("textures/welcome_tze.png")),
        WINNER               (TextureData("textures/winner.png")),
        YELLOW_LEFT_DEF      (TextureData("textures/yellow_left_def.png")),
        YELLOW_LEFT_PRESS    (TextureData("textures/yellow_left_press.png")),
        YELLOW_RIGHT_DEF     (TextureData("textures/yellow_right_def.png")),
        YELLOW_RIGHT_PRESS   (TextureData("textures/yellow_right_press.png")),
        YOUR_TURN            (TextureData("textures/your_turn.png")),
        SELECT_PLAYER        (TextureData("textures/select_player.png")),
        PANEL_PLAYER         (TextureData("textures/panel_player.png")),

        task_1  (TextureData("textures/task/task_1.png")),
        task_2  (TextureData("textures/task/task_2.png")),
        task_3  (TextureData("textures/task/task_3.png")),
        task_4  (TextureData("textures/task/task_4.png")),
        task_5  (TextureData("textures/task/task_5.png")),
        task_6  (TextureData("textures/task/task_6.png")),
        task_7  (TextureData("textures/task/task_7.png")),
        task_8  (TextureData("textures/task/task_8.png")),
        task_9  (TextureData("textures/task/task_9.png")),
        task_10 (TextureData("textures/task/task_10.png")),
        task_11 (TextureData("textures/task/task_11.png")),
        task_12 (TextureData("textures/task/task_12.png")),
        task_13 (TextureData("textures/task/task_13.png")),
        task_14 (TextureData("textures/task/task_14.png")),
        task_15 (TextureData("textures/task/task_15.png")),
        task_16 (TextureData("textures/task/task_16.png")),
        task_17 (TextureData("textures/task/task_17.png")),
        task_18 (TextureData("textures/task/task_18.png")),
        task_19 (TextureData("textures/task/task_19.png")),
        task_20 (TextureData("textures/task/task_20.png")),
        task_21 (TextureData("textures/task/task_21.png")),
        task_22 (TextureData("textures/task/task_22.png")),
        task_23 (TextureData("textures/task/task_23.png")),
        task_24 (TextureData("textures/task/task_24.png")),
        task_25 (TextureData("textures/task/task_25.png")),
        task_26 (TextureData("textures/task/task_26.png")),
        task_27 (TextureData("textures/task/task_27.png")),
        task_28 (TextureData("textures/task/task_28.png")),
        task_29 (TextureData("textures/task/task_29.png")),

        monster_1  (TextureData("textures/monster/monster_1.png")),
        monster_2  (TextureData("textures/monster/monster_2.png")),
        monster_3  (TextureData("textures/monster/monster_3.png")),
        monster_4  (TextureData("textures/monster/monster_4.png")),
        monster_5  (TextureData("textures/monster/monster_5.png")),
        monster_6  (TextureData("textures/monster/monster_6.png")),
        monster_7  (TextureData("textures/monster/monster_7.png")),
        monster_8  (TextureData("textures/monster/monster_8.png")),
        monster_9  (TextureData("textures/monster/monster_9.png")),
        monster_10 (TextureData("textures/monster/monster_10.png")),
        monster_11 (TextureData("textures/monster/monster_11.png")),
        monster_12 (TextureData("textures/monster/monster_12.png")),
        monster_13 (TextureData("textures/monster/monster_13.png")),
        monster_14 (TextureData("textures/monster/monster_14.png")),
        monster_15 (TextureData("textures/monster/monster_15.png")),
        monster_16 (TextureData("textures/monster/monster_16.png")),

        artefact_1  (TextureData("textures/artefact/artefact_1.png")),
        artefact_2  (TextureData("textures/artefact/artefact_2.png")),
        artefact_3  (TextureData("textures/artefact/artefact_3.png")),
        artefact_4  (TextureData("textures/artefact/artefact_4.png")),
        artefact_5  (TextureData("textures/artefact/artefact_5.png")),
        artefact_6  (TextureData("textures/artefact/artefact_6.png")),
        artefact_7  (TextureData("textures/artefact/artefact_7.png")),
        artefact_8  (TextureData("textures/artefact/artefact_8.png")),
        artefact_9  (TextureData("textures/artefact/artefact_9.png")),
        artefact_10 (TextureData("textures/artefact/artefact_10.png")),
        artefact_11 (TextureData("textures/artefact/artefact_11.png")),
        artefact_12 (TextureData("textures/artefact/artefact_12.png")),
        artefact_13 (TextureData("textures/artefact/artefact_13.png")),
        artefact_14 (TextureData("textures/artefact/artefact_14.png")),
        artefact_15 (TextureData("textures/artefact/artefact_15.png")),

        hero_1(TextureData("textures/hero/hero_1.png")),
        hero_2(TextureData("textures/hero/hero_2.png")),
        hero_3(TextureData("textures/hero/hero_3.png")),
        hero_4(TextureData("textures/hero/hero_4.png")),
        hero_5(TextureData("textures/hero/hero_5.png")),
        hero_6(TextureData("textures/hero/hero_6.png")),
        hero_7(TextureData("textures/hero/hero_7.png")),

        god_1  (TextureData("textures/god/god_1.png")),
        god_2  (TextureData("textures/god/god_2.png")),
        god_3  (TextureData("textures/god/god_3.png")),
        god_4  (TextureData("textures/god/god_4.png")),
        god_5  (TextureData("textures/god/god_5.png")),
        god_6  (TextureData("textures/god/god_6.png")),
        god_7  (TextureData("textures/god/god_7.png")),
        god_8  (TextureData("textures/god/god_8.png")),
        god_9  (TextureData("textures/god/god_9.png")),
        god_10 (TextureData("textures/god/god_10.png")),
        god_11 (TextureData("textures/god/god_11.png")),
        god_12 (TextureData("textures/god/god_12.png")),
        god_13 (TextureData("textures/god/god_13.png")),
        god_14 (TextureData("textures/god/god_14.png")),
        god_15 (TextureData("textures/god/god_15.png")),
        god_16 (TextureData("textures/god/god_16.png")),
        god_17 (TextureData("textures/god/god_17.png")),
        god_18 (TextureData("textures/god/god_18.png")),
        god_19 (TextureData("textures/god/god_19.png")),
        god_20 (TextureData("textures/god/god_20.png")),
        god_21 (TextureData("textures/god/god_21.png")),
        god_22 (TextureData("textures/god/god_22.png")),
    }

    data class AtlasData(val path: String) {
        lateinit var atlas: TextureAtlas
    }

    data class TextureData(val path: String) {
        lateinit var texture: Texture
    }

}