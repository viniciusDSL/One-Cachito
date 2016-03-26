package com.vinidsl.onecachito.sprite;


import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.R;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.model.Player;
import com.vinidsl.onecachito.util.Constants;
import com.vinidsl.onecachito.util.SpriteFactory;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;


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
public class NoteBookSprite extends Sprite implements ButtonSprite.OnClickListener{

    private boolean visible;
    private ButtonScoreSprite[] buttonScoreSprites;
    public TabSprite tabSprite;
    private boolean locked;
    private boolean isTabOut;
    public boolean force;
    private Text notebookTitle;
    public boolean justShow;
    private NextArrowSprite left;
    private NextArrowSprite right;
    private int justShowNumber;


    public NoteBookSprite() {
        super(GameActivity.WORLD_WIDTH / 2, GameActivity.WORLD_HEIGHT - GameActivity.WORLD_HEIGHT / 3,
                ResourceManager.getInstance().NOTE_BOOK_TEXTURE_REGION, ResourceManager.getInstance().getVertexBuffer());
        this.setY(GameActivity.WORLD_HEIGHT + this.getHeight() / 2);
        visible = false;
        buttonScoreSprites = new ButtonScoreSprite[11];
        notebookTitle = new Text(200,390,ResourceManager.getInstance().fontBig,"JUGADOR:PLAYER0123456789",25, ResourceManager.getInstance().getVertexBuffer());
        notebookTitle.setScale(0.6f);
        for(int i=0;i<11;i++){
            buttonScoreSprites[i] = SpriteFactory.getButtonScoreFromType(i + 1);
        }
        left = new NextArrowSprite(NextArrowSprite.LEFT);
        right = new NextArrowSprite(NextArrowSprite.RIGHT);
    }

    public void attachildAndRegisterButtons(Scene scene){
        this.attachChild(notebookTitle);
        for(int i=0;i<11;i++){
            this.attachChild(buttonScoreSprites[i]);
            scene.registerTouchArea(buttonScoreSprites[i]);
        }
        this.attachChild(left);
        this.attachChild(right);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        scene.registerTouchArea(left);
        scene.registerTouchArea(right);
    }

    public void show(){
        if(!locked) {
            clearEntityModifiers();
            if(!force) {
                showTab();
            }
            this.registerEntityModifier(
                    new MoveYModifier(Constants.ANIMATION_DURATION, this.getY(), GameActivity.WORLD_HEIGHT - GameActivity.WORLD_HEIGHT / 3,
                            EaseLinear.getInstance()
                    ));
            GameManager.getInstance().hideTabs();
            visible = true;
        }
    }

    public void hide(){
        if(justShow){
            left.hide();
            right.hide();
        }
        justShow = false;
        if(!locked) {
            hideScoreButtons();
            clearEntityModifiers();
            hideTab();
            this.registerEntityModifier(new MoveYModifier(Constants.ANIMATION_DURATION, this.getY(), GameActivity.WORLD_HEIGHT + this.getHeight() / 2, EaseLinear.getInstance()));
            GameManager.getInstance().showTabs();
            visible = false;
        }
    }

    public void showTab(){
        this.tabSprite.setCurrentTileIndex(4);
        this.tabSprite.clearEntityModifiers();
        this.tabSprite.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION, this.tabSprite.getX(),this.tabSprite.getWidth() / 2, EaseLinear.getInstance()));
    }

    public void hideTab(){
        isTabOut=false;
        this.tabSprite.clearEntityModifiers();
        this.tabSprite.setCurrentTileIndex(0);
        this.tabSprite.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION, this.tabSprite.getX(),  -25 + this.tabSprite.getWidth() / 2, EaseLinear.getInstance()));
    }

    public void outTab(){
        if(!isTabOut) {
            isTabOut=true;
            this.tabSprite.clearEntityModifiers();
            this.tabSprite.setCurrentTileIndex(0);
            this.tabSprite.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION, this.tabSprite.getX(), -this.tabSprite.getWidth() / 2, EaseLinear.getInstance()));
        }
    }

    public void hideScoreButtons(){
        for(int i=0;i<buttonScoreSprites.length;i++){
            buttonScoreSprites[i].hide();
        }
    }

    public void showPlayerScore(final Player player){
        this.registerEntityModifier(new MoveYModifier(Constants.ANIMATION_DURATION, this.getY(), GameActivity.WORLD_HEIGHT - GameActivity.WORLD_HEIGHT / 3,
                new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {

                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                        showArrows(player);
                    }
                },EaseLinear.getInstance()));
        showNumberScore(player);
        GameManager.getInstance().hideTabs();
        showTab();
        visible = true;
    }

    public void showNumberScore(Player player){
        justShowNumber = player.getNumberPlayer();
        justShow = true;
        GameManager.getInstance().cachoSprite.hideDices();
        switch (player.getNumberPlayer()){
            case 0:
                if(GameManager.getInstance().totalTurns<11){
                    notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.player_1));
                }else{
                    notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.p1) +" "+ GameManager.getInstance().getPlayer(0).getTotalScore());
                }
                notebookTitle.setColor(0.980392157f,0.505882353f,0.196078431f);
                break;
            case 1:
                if(GameManager.getInstance().totalTurns<11) {
                    notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.player_2));
                }else{
                    notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.p2) +" "+ GameManager.getInstance().getPlayer(1).getTotalScore());
                }
                notebookTitle.setColor(0.207843137f, 0.729411765f, 0.952941176f);
                break;
            case 2:
                if(GameManager.getInstance().totalTurns<11) {
                    notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.player_3));
                }else{
                    notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.p3) +" "+ GameManager.getInstance().getPlayer(2).getTotalScore());
                }
                notebookTitle.setColor(1f, 0.850980392f, 0.282352941f);
                break;
        }
        Integer[] userScore = player.getScore();
        for(int i=0;i<buttonScoreSprites.length;i++){
            if(userScore[i]<0){
                buttonScoreSprites[i].setUserScore(0);
            }else{
                buttonScoreSprites[i].setUserScore(userScore[i]);
                /*switch (player.getNumberPlayer()){
                    case 0:
                        buttonScoreSprites[i].text.setColor(0.980392157f, 0.505882353f, 0.196078431f);
                        break;
                    case 1:
                        buttonScoreSprites[i].text.setColor(0.207843137f, 0.729411765f, 0.952941176f);
                        break;
                    case 2:
                        buttonScoreSprites[i].text.setColor(1f, 0.850980392f, 0.282352941f);
                        break;
                }*/
            }
        }
    }

    public void showArrows(Player player){
        int limitMax = GameManager.getInstance().numberOfPlayers-1;
        switch (player.getNumberPlayer()){
            case 0:
                if(limitMax!=0) {
                    left.hide();
                    right.show();
                }
                break;
            case 1:
                if(limitMax>1) {
                    right.show();
                }else{
                    right.hide();
                }
                left.show();
                break;
            case 2:
                left.show();
                right.hide();
                break;
        }
    }

    public void showScoreButtonsOfCurrentUser(Player player){
        switch (player.getNumberPlayer()){
            case 0:
                notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.player_1));
                notebookTitle.setColor(0.980392157f,0.505882353f,0.196078431f);
                break;
            case 1:
                notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.player_2));
                notebookTitle.setColor(0.207843137f, 0.729411765f, 0.952941176f);
                break;
            case 2:
                notebookTitle.setText(ResourceManager.getInstance().context.getString(R.string.player_3));
                notebookTitle.setColor(1f, 0.850980392f, 0.282352941f);
                break;
        }
        Integer[] userScore = player.getScore();
        for(int i=0;i<buttonScoreSprites.length;i++){
            if(userScore[i]>=0){
                buttonScoreSprites[i].setUserScore(userScore[i]);
            }else{
                buttonScoreSprites[i].hide();
            }
        }
    }

    public boolean isVisible(){
        return visible;
    }

    public void setLocked(){
        outTab();
        locked = true;
    }

    public void unlock(){
        locked = false;
    }


    public void moveIn() {
        if(!GameManager.getInstance().cachoSprite.haveDiceToRoll()) {
            this.tabSprite.clearEntityModifiers();
            this.tabSprite.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION, this.tabSprite.getX(), -25 + this.tabSprite.getWidth() / 2, EaseLinear.getInstance()));
        }
    }

    public void moveOut() {
        if(!locked) {
            this.tabSprite.clearEntityModifiers();
            this.tabSprite.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION, this.tabSprite.getX(), -this.tabSprite.getWidth() / 2, EaseLinear.getInstance()));
        }
    }

    @Override
    public void onClick(ButtonSprite buttonSprite, float v, float v1) {
        if(buttonSprite.equals(left)){
            if(justShowNumber>0){
                justShowNumber-=1;
                Player player = GameManager.getInstance().getPlayer(justShowNumber);
                showNumberScore(player);
                showArrows(player);
                /*if(justShowNumber==0){
                    left.hide();
                }*/
            }
        }
        if(buttonSprite.equals(right)){
            int limitMax = GameManager.getInstance().numberOfPlayers-1;
            if(justShowNumber<limitMax){
                justShowNumber+=1;
                Player player = GameManager.getInstance().getPlayer(justShowNumber);
                showNumberScore(player);
                showArrows(player);
                /*if(justShowNumber==limitMax){
                    right.hide();
                }*/
            }
        }
    }
}
