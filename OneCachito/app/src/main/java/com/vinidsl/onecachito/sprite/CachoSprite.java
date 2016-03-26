package com.vinidsl.onecachito.sprite;



import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.R;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.Constants;
import com.vinidsl.onecachito.util.DiceValueHelper;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.AnimatedSprite;
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
public class CachoSprite extends AnimatedSprite {

    private static final float baseY = GameActivity.WORLD_HEIGHT+50;
    private static final float time = 0.3f;

    // dices of the game
    public DiceSprite[] diceSprites;

    /**
     * Constructor
     */
    public CachoSprite() {
        super(GameActivity.WORLD_WIDTH / 2, baseY,
                ResourceManager.getInstance().CACHOS_TEXTURE_REGION,
                ResourceManager.getInstance().getVertexBuffer());
    }

    public void moveDown(){
        GameManager.getInstance().hideTabs();
        GameManager.getInstance().noteBookSprite.moveOut();
        this.clearEntityModifiers();
        this.registerEntityModifier(new MoveYModifier(time, this.getY(), baseY - 270, EaseLinear.getInstance()));
    }

    public void moveUp(){
        GameManager.getInstance().showTabs();
        GameManager.getInstance().noteBookSprite.moveIn();
        this.clearEntityModifiers();
        this.registerEntityModifier(new MoveYModifier(time, this.getY(), baseY, EaseLinear.getInstance()));
    }

    public boolean allDicesOnCacho(){
        for(DiceSprite diceSprite :diceSprites){
            if(diceSprite.isReadyToRoll()){
                return true;
            }
        }
        return false;
    }

    public void hideDices(){
        for(DiceSprite diceSprite :diceSprites){
             if(!diceSprite.isReadyToRoll()){
                 if(diceSprite.getAlpha()!=0f) {
                     diceSprite.registerEntityModifier(new AlphaModifier(Constants.ANIMATION_DURATION, 1f, 0f));
                 }
             }
        }
    }

    public void showDices(){
        for(DiceSprite diceSprite :diceSprites){
            if(!diceSprite.isReadyToRoll()){
                diceSprite.registerEntityModifier(new AlphaModifier(Constants.ANIMATION_DURATION,0f,1f));
            }
        }
    }

    public synchronized void rollDices(){
        moveDown();
        GameManager.getInstance().getCurrentPlayer().resetDicesRolling();
        for(DiceSprite diceSprite :diceSprites){
            if(diceSprite.isReadyToRoll()){
                GameManager.getInstance().getCurrentPlayer().addDiceRolling();
                GameManager.getInstance().isRolling=true;
            }
        }
        for(DiceSprite diceSprite :diceSprites){
            diceSprite.startRoll();
        }
        GameManager.getInstance().getCurrentPlayer().addRoll();
        float rollTime = 2f;
        TimerHandler timerHandler = new TimerHandler(rollTime, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler timerHandler) {
                moveUp();
                for(DiceSprite dice:diceSprites){
                    dice.finishRoll();
                }
                GameManager.getInstance().isRolling=false;

                if(DiceValueHelper.calculateGrande()==100){
                    GameManager.getInstance().buttonDone.setPosition(35+GameActivity.WORLD_WIDTH / 2,11+GameActivity.WORLD_HEIGHT / 2);
                    GameManager.getInstance().dormidaSprite.show();
                    GameManager.getInstance().totalTurns=11;
                    GameManager.getInstance().noteBookSprite.outTab();
                    GameManager.getInstance().hideTabs();
                    GameManager.getInstance().noteBookSprite.tabSprite.setVisible(false);

                }else {
                    if (GameManager.getInstance().getCurrentPlayer().getRolls() >= 2) {
                        GameManager.getInstance().noteBookSprite.force = true;
                        GameManager.getInstance().noteBookSprite.showScoreButtonsOfCurrentUser(GameManager.getInstance().getCurrentPlayer());
                        GameManager.getInstance().noteBookSprite.show();
                        GameManager.getInstance().noteBookSprite.setLocked();
                    }
                    if (!GameManager.getInstance().noteBookSprite.force) {
                        GameManager.getInstance().noteBookSprite.hideTab();
                    }
                }

            }
        });
        this.registerUpdateHandler(timerHandler);
    }

    public boolean haveDiceToRoll(){
        for(DiceSprite diceSprite :diceSprites){
            if(diceSprite.isReadyToRoll()){
               return true;
            }
        }
        return false;
    }

    public void changePlayerCacho(final int currentPlayer) {
        GameManager.getInstance().changingTurnAnimation = true;
        this.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {

            }

            @Override
            public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                CachoSprite.this.registerEntityModifier(new MoveYModifier(0.2f, CachoSprite.this.getY()
                        , CachoSprite.this.getY() + 150
                        , new IEntityModifier.IEntityModifierListener() {
                    @Override
                    public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {
                    }

                    @Override
                    public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                        CachoSprite.this.setCurrentTileIndex(currentPlayer);
                        CachoSprite.this.registerEntityModifier(new DelayModifier(0.2f, new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {
                                CachoSprite.this.registerEntityModifier(new MoveYModifier(Constants.ANIMATION_DURATION, CachoSprite.this.getY()
                                        , CachoSprite.this.getY() - 150, EaseLinear.getInstance()));
                            }

                            @Override
                            public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                                counterDices = GameManager.getInstance().cachoSprite.diceSprites.length - 1;

                                resetTurns();
                            }
                        }));
                    }
                }, EaseLinear.getInstance()));
            }
        }));

    }

    public int counterDices;

    public void resetTurns(){
        if(counterDices>=0) {
            GameManager.getInstance().cachoSprite.diceSprites[counterDices].registerEntityModifier(new MoveModifier(Constants.ANIMATION_DICE,
                    GameManager.getInstance().cachoSprite.diceSprites[counterDices].getX(), GameManager.getInstance().cachoSprite.diceSprites[counterDices].getY(),
                    GameManager.getInstance().cachoSprite.getX(), GameManager.getInstance().cachoSprite.getY(),
                    new IEntityModifier.IEntityModifierListener() {
                        @Override
                        public void onModifierStarted(IModifier<IEntity> iModifier, IEntity iEntity) {
                            GameManager.getInstance().cachoSprite.diceSprites[counterDices].setScale(1.3f);
                        }

                        @Override
                        public void onModifierFinished(IModifier<IEntity> iModifier, IEntity iEntity) {
                            GameManager.getInstance().cachoSprite.diceSprites[counterDices].setScale(1f);
                            GameManager.getInstance().cachoSprite.diceSprites[counterDices].dice.reset();
                            GameManager.getInstance().cachoSprite.diceSprites[counterDices].setReadyToRoll();
                            counterDices--;
                            resetTurns();
                        }
                    },
                    EaseLinear.getInstance()));
        }else{

            if(GameManager.getInstance().totalTurns>=11){
                GameManager.getInstance().nextTurnSprite.showWinner();
            }else {

                if (GameManager.getInstance().numberOfPlayers > 1) {

                    GameManager.getInstance().nextTurnSprite.animate();
                } else {
                    GameManager.getInstance().changingTurnAnimation = false;
                    GameManager.getInstance().buttonRoll.show();
                }
            }
        }

    }

    public boolean anyDiceIsDown(){
        for (int i=0;i<5;i++) {
            if (diceSprites[i].isDown()) {
                return true;
            }
        }
        return false;
    }
}
