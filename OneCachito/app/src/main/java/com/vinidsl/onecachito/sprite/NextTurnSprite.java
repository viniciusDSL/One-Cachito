package com.vinidsl.onecachito.sprite;

import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.R;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.Constants;
import com.vinidsl.onecachito.util.Paint;
import com.vinidsl.onecachito.util.StringHelper;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseBackIn;
import org.andengine.util.modifier.ease.EaseBackOut;

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
public class NextTurnSprite extends Sprite {

    public Text nextTurn;

    public NextTurnSprite() {
        super(GameActivity.WORLD_WIDTH+200,GameActivity.WORLD_HEIGHT/6, ResourceManager.getInstance().NOTE_BOOK_BROKEN, ResourceManager.getInstance().getVertexBuffer());
        nextTurn = new Text(20+(this.getWidth()/2),-13+this.getHeight()/2,ResourceManager.getInstance().fontBig,
                ResourceManager.getInstance().context.getString(R.string.next_turn_)
                ,50, new TextOptions(HorizontalAlign.CENTER),ResourceManager.getInstance().getVertexBuffer());
        nextTurn.setScale(0.6f);
        attachText();
    }

    public void attachText(){
        this.attachChild(nextTurn);
    }

    public void animate(){

                NextTurnSprite.this.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION,
                        GameActivity.WORLD_WIDTH + 200, GameActivity.WORLD_WIDTH / 2,
                        new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {

                            }

                            @Override
                            public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                                NextTurnSprite.this.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifier.IEntityModifierListener() {
                                    @Override
                                    public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {

                                    }

                                    @Override
                                    public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                                        NextTurnSprite.this.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION, NextTurnSprite.this.getX(), -200,
                                                new IEntityModifier.IEntityModifierListener() {
                                                    @Override
                                                    public void onModifierStarted(IModifier<IEntity> iModifier, IEntity entity) {

                                                    }

                                                    @Override
                                                    public void onModifierFinished(IModifier<IEntity> iModifier, IEntity entity) {
                                                        GameManager.getInstance().buttonRoll.show();
                                                        GameManager.getInstance().changingTurnAnimation = false;
                                                    }
                                                },
                                                EaseBackIn.getInstance()));

                                    }
                                }));
                            }
                        },
                        EaseBackOut.getInstance()));


    }

    public void showWinner(){
        int numberOfPlayers = GameManager.getInstance().numberOfPlayers;
        int score1,score2,score3;
        score1 = GameManager.getInstance().getPlayer(0).getTotalScore();
        if(numberOfPlayers>1) {
            score2 = GameManager.getInstance().getPlayer(1).getTotalScore();
        }else{
            score2 = 0 ;
        }
        if(numberOfPlayers>2) {
            score3 = GameManager.getInstance().getPlayer(2).getTotalScore();
        }else{
            score3 = 0;
        }
        String winnerTitle="";
        if(score1>score2 && score1>score3){
            winnerTitle+=ResourceManager.getInstance().context.getString(R.string.player_one);
            nextTurn.setColor(0.980392157f, 0.505882353f, 0.196078431f);
            GameManager.getInstance().noteBookSprite.showPlayerScore(GameManager.getInstance().players[0]);
            GameManager.getInstance().cachoSprite.setCurrentTileIndex(0);
        }
        if(score2>score1 && score2>score3){
            winnerTitle+=ResourceManager.getInstance().context.getString(R.string.player_two);
            nextTurn.setColor(0.207843137f, 0.729411765f, 0.952941176f);
            GameManager.getInstance().noteBookSprite.showPlayerScore(GameManager.getInstance().players[1]);
            GameManager.getInstance().cachoSprite.setCurrentTileIndex(1);
        }
        if(score3>score1 && score3>score2){
            winnerTitle+=ResourceManager.getInstance().context.getString(R.string.player_three);
            nextTurn.setColor(1f, 0.850980392f, 0.282352941f);
            GameManager.getInstance().noteBookSprite.showPlayerScore(GameManager.getInstance().players[2]);
            GameManager.getInstance().cachoSprite.setCurrentTileIndex(2);
        }
        boolean draw=false;
        if(numberOfPlayers>2) {
            if(score1==score2 && score2==score3){
                draw = true;
            }
        }else{
            if(numberOfPlayers>1) {
                if(score1==score2){
                    draw = true;
                }
            }
        }

        if(!draw){
            winnerTitle+="\n"+ResourceManager.getInstance().context.getString(R.string.winner);
        }else{
            winnerTitle= StringHelper.getStringFromResource(R.string.draw);
            Paint.gold(nextTurn);
            GameManager.getInstance().noteBookSprite.showPlayerScore(GameManager.getInstance().players[0]);
            GameManager.getInstance().cachoSprite.setCurrentTileIndex(0);
        }
        nextTurn.setText(winnerTitle);

        NextTurnSprite.this.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION,
                GameActivity.WORLD_WIDTH + 200, GameActivity.WORLD_WIDTH / 2,
                new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> iModifier, IEntity entity) {

                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> iModifier, IEntity entity) {
                        GameManager.getInstance().buttonDone.show();
                    }
                },
                EaseBackOut.getInstance()));
    }

    public void firstAnimate() {
        GameManager.getInstance().changingTurnAnimation = true;
        nextTurn.setColor(0.980392157f, 0.505882353f, 0.196078431f);
        nextTurn.setText(StringHelper.getStringFromResource(R.string.first_turn));
        NextTurnSprite.this.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION,
                GameActivity.WORLD_WIDTH + 200, GameActivity.WORLD_WIDTH / 2,
                new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {

                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                        NextTurnSprite.this.registerEntityModifier(new DelayModifier(0.5f, new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {

                            }

                            @Override
                            public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                                NextTurnSprite.this.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION, NextTurnSprite.this.getX(), -200,
                                        new IEntityModifier.IEntityModifierListener() {
                                            @Override
                                            public void onModifierStarted(IModifier<IEntity> iModifier, IEntity entity) {

                                            }

                                            @Override
                                            public void onModifierFinished(IModifier<IEntity> iModifier, IEntity entity) {
                                                nextTurn.setText(ResourceManager.getInstance().context.getString(R.string.next_turn_));
                                                GameManager.getInstance().buttonRoll.show();
                                                GameManager.getInstance().changingTurnAnimation = false;
                                            }
                                        },
                                        EaseBackIn.getInstance()));


                            }
                        }));
                    }
                },
                EaseBackOut.getInstance()));
    }
}
