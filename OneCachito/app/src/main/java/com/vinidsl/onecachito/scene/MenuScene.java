package com.vinidsl.onecachito.scene;


import android.content.Intent;
import android.net.Uri;

import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.R;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.manager.SceneManager;

import com.vinidsl.onecachito.sprite.MenuButton;
import com.vinidsl.onecachito.sprite.NoteBookEmptySprite;
import com.vinidsl.onecachito.sprite.TitleSprite;
import com.vinidsl.onecachito.util.Paint;
import com.vinidsl.onecachito.util.SpriteFactory;
import com.vinidsl.onecachito.util.StringHelper;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;


/**
 * One Cachito
 * Copyright (C) 2016 Vini DSL vinicius.da.silva.limachi@gmail.com
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * @author vinicius
 */
public class MenuScene extends BasicScene implements ButtonSprite.OnClickListener {

    private TitleSprite titleSprite;
    private Text startGameText;
    private Text onlineText;
    private Text howPlayText;

    private MenuButton menuButtonMultiplayer;
    private MenuButton menuButtonOnline;
    private MenuButton menuButtonHowToPlay;

    private MenuButton menuButtonLeaderboard;
    private MenuButton menuButtonAwards;

    private MenuButton menuButtonOnePLayer;
    private MenuButton menuButtonTwoPLayers;
    private MenuButton menuButtonThreePlayers;

    private MenuButton menuButtonFacebook;
    private MenuButton menuButtonTwitter;


    private Sprite spriteOnePlayer;
    private Sprite spriteTwoPlayers;
    private Sprite spriteThreePlayers;
    private Text textMultiplayerTitle;
    public boolean multiplayerVisible;

    private NoteBookEmptySprite noteBookEmptySprite;

    public MenuScene(){
        init();
    }

    @Override
    public void init() {
        this.setTouchAreaBindingOnActionDownEnabled(true);
        setBackground(new SpriteBackground(SpriteFactory.getTableBackground()));
        titleSprite = new TitleSprite();
        noteBookEmptySprite = new NoteBookEmptySprite();
        noteBookEmptySprite.setY(noteBookEmptySprite.getY()-125);
        float posX = GameActivity.WORLD_WIDTH/2;
        float posY = 400;

        menuButtonMultiplayer = new MenuButton(posX,posY,ResourceManager.getInstance().MENU_BUTTON_RED,0);
        menuButtonMultiplayer.setOnClickListener(this);

        menuButtonOnline = new MenuButton(posX,posY-110,ResourceManager.getInstance().MENU_BUTTON_BLUE,0);
        menuButtonOnline.setOnClickListener(this);
        //menuButtonOnline.setEnabled(false);

        menuButtonHowToPlay = new MenuButton(posX,posY-220,ResourceManager.getInstance().MENU_BUTTON_YELLOW,0);
        menuButtonHowToPlay.setOnClickListener(this);

        menuButtonLeaderboard = new MenuButton(380, 690,ResourceManager.getInstance().MENU_BUTTON_LEADERBOARD);
        menuButtonLeaderboard.setOnClickListener(this);
        menuButtonAwards = new MenuButton(380, 570,ResourceManager.getInstance().MENU_BUTTON_AWARDS);
        menuButtonAwards.setOnClickListener(this);
        menuButtonLeaderboard.setEnabled(false);
        menuButtonAwards.setEnabled(false);

        menuButtonFacebook = new MenuButton(GameActivity.WORLD_WIDTH-88,posY-325,ResourceManager.getInstance().MENU_BUTTON_FACEBOOK,0);
        menuButtonFacebook.setOnClickListener(this);


        menuButtonTwitter = new MenuButton(menuButtonFacebook.getX()-80,posY-325,ResourceManager.getInstance().MENU_BUTTON_TWITTER,0);
        menuButtonTwitter.setOnClickListener(this);

        //main menu
        startGameText = new Text(posX,posY, ResourceManager.getInstance().font,
                StringHelper.getStringFromResource(R.string.menu_start_game), ResourceManager.getInstance().getVertexBuffer());
        onlineText = new Text(posX,posY-110, ResourceManager.getInstance().font,
                StringHelper.getStringFromResource(R.string.menu_online), ResourceManager.getInstance().getVertexBuffer());
        howPlayText = new Text(posX,posY-220, ResourceManager.getInstance().font,
                StringHelper.getStringFromResource(R.string.menu_how_to_play), ResourceManager.getInstance().getVertexBuffer());

        textMultiplayerTitle = new Text(posX,posY+70, ResourceManager.getInstance().font,
                StringHelper.getStringFromResource(R.string.menu_multiplayer_choise), ResourceManager.getInstance().getVertexBuffer());
        menuButtonOnePLayer = new MenuButton(posX,posY,ResourceManager.getInstance().MENU_BUTTON_WHITE,0);
        menuButtonTwoPLayers = new MenuButton(posX,posY-110,ResourceManager.getInstance().MENU_BUTTON_WHITE,0);
        menuButtonThreePlayers = new MenuButton(posX,posY-220,ResourceManager.getInstance().MENU_BUTTON_WHITE,0);
        spriteOnePlayer = SpriteFactory.getSimpleSprite(posX,posY,SpriteFactory.TYPE_MULTI_ONE);
        spriteTwoPlayers = SpriteFactory.getSimpleSprite(posX,posY-110,SpriteFactory.TYPE_MULTI_TWO);
        spriteThreePlayers = SpriteFactory.getSimpleSprite(posX,posY-220,SpriteFactory.TYPE_MULTI_THREE);
        menuButtonTwoPLayers.setOnClickListener(this);
        menuButtonThreePlayers.setOnClickListener(this);
        menuButtonOnePLayer.setOnClickListener(this);
        Paint.white(startGameText);
        Paint.white(onlineText);
        Paint.white(howPlayText);
        Paint.gray(textMultiplayerTitle);

        this.attachChild(titleSprite);
        this.attachChild(menuButtonLeaderboard);
        this.attachChild(menuButtonAwards);
        this.attachChild(menuButtonFacebook);
        this.attachChild(menuButtonTwitter);
        //this.attachChild(noteBookEmptySprite);
        this.attachChild(menuButtonMultiplayer);
        this.registerTouchArea(menuButtonMultiplayer);
        this.attachChild(menuButtonOnline);
        this.registerTouchArea(menuButtonOnline);
        this.attachChild(menuButtonHowToPlay);
        this.registerTouchArea(menuButtonHowToPlay);
        this.registerTouchArea(menuButtonOnePLayer);
        this.registerTouchArea(menuButtonTwoPLayers);
        this.registerTouchArea(menuButtonThreePlayers);
        this.registerTouchArea(menuButtonLeaderboard);
        this.registerTouchArea(menuButtonAwards);
        this.registerTouchArea(menuButtonFacebook);
        this.registerTouchArea(menuButtonTwitter);

        this.attachChild(startGameText);
        this.attachChild(onlineText);
        this.attachChild(howPlayText);

        // multiplayer options
        this.attachChild(textMultiplayerTitle);
        this.attachChild(menuButtonOnePLayer);
        this.attachChild(menuButtonTwoPLayers);
        this.attachChild(menuButtonThreePlayers);
        this.attachChild(spriteOnePlayer);
        this.attachChild(spriteTwoPlayers);
        this.attachChild(spriteThreePlayers);
        textMultiplayerTitle.setVisible(false);
        menuButtonOnePLayer.setVisible(false);
        menuButtonTwoPLayers.setVisible(false);
        menuButtonThreePlayers.setVisible(false);
        spriteOnePlayer.setVisible(false);
        spriteTwoPlayers.setVisible(false);
        spriteThreePlayers.setVisible(false);
        //end
        this.registerTouchArea(startGameText);
        setTouchAreaBindingOnActionMoveEnabled(true);
        setTouchAreaBindingOnActionDownEnabled(true);

        menuButtonOnline.setEnabled(false);
    }

    @Override
    public void onBackKeyPressed() {
        hideMultiplayerOptions();
    }

    @Override
    public void clear() {

    }

    private void showMultiplayerOptions(){
        multiplayerVisible = true;
        menuButtonHowToPlay.setVisible(false);
        menuButtonOnline.setVisible(false);
        menuButtonMultiplayer.setVisible(false);
        startGameText.setVisible(false);
        howPlayText.setVisible(false);
        onlineText.setVisible(false);

        textMultiplayerTitle.setVisible(true);
        menuButtonOnePLayer.setVisible(true);
        menuButtonTwoPLayers.setVisible(true);
        menuButtonThreePlayers.setVisible(true);
        spriteOnePlayer.setVisible(true);
        spriteTwoPlayers.setVisible(true);
        spriteThreePlayers.setVisible(true);

    }

    private void hideMultiplayerOptions(){
        multiplayerVisible = false;
        menuButtonHowToPlay.setVisible(true);
        menuButtonOnline.setVisible(true);
        menuButtonMultiplayer.setVisible(true);
        startGameText.setVisible(true);
        howPlayText.setVisible(true);
        onlineText.setVisible(true);

        textMultiplayerTitle.setVisible(false);
        menuButtonOnePLayer.setVisible(false);
        menuButtonTwoPLayers.setVisible(false);
        menuButtonThreePlayers.setVisible(false);
        spriteOnePlayer.setVisible(false);
        spriteTwoPlayers.setVisible(false);
        spriteThreePlayers.setVisible(false);
    }


    @Override
    public void onClick(ButtonSprite buttonSprite, float v, float v1) {
        if(buttonSprite.equals(menuButtonMultiplayer)){
                showMultiplayerOptions();
        }

        if(buttonSprite.equals(menuButtonHowToPlay)){
            ((GameActivity)ResourceManager.getInstance().activity).goToRules();
        }

        if(buttonSprite.equals(menuButtonOnePLayer)){
            SceneManager.getInstance().setNumberOfPlayers(1);
            SceneManager.getInstance().changeToScene(SceneManager.SCENE_ID_GAME);
        }
        if(buttonSprite.equals(menuButtonTwoPLayers)){
            SceneManager.getInstance().setNumberOfPlayers(2);
            SceneManager.getInstance().changeToScene(SceneManager.SCENE_ID_GAME);
        }
        if(buttonSprite.equals(menuButtonThreePlayers)){
            SceneManager.getInstance().setNumberOfPlayers(3);
            SceneManager.getInstance().changeToScene(SceneManager.SCENE_ID_GAME);
        }
        if(buttonSprite.equals(menuButtonFacebook)){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/viniciusdslgames"));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ResourceManager.getInstance().context.startActivity(browserIntent);
            //((GameActivity)ResourceManager.getInstance().activity).showSavedGamesUI();
        }
        if(buttonSprite.equals(menuButtonTwitter)){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/viniciusdsl"));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ResourceManager.getInstance().context.startActivity(browserIntent);
        }
    }
}
