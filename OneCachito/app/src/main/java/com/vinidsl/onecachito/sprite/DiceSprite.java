package com.vinidsl.onecachito.sprite;



import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.model.Dice;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;

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
public class DiceSprite extends AnimatedSprite {

    /**
     * default position (x,y) on the screen
     * y
     * ^
     * |      [ * ]
     * | [ * ]     [ * ]
     * |      [ * ]
     * |           [ * ]
     * ------------------->X
     */
    private float defaultPosX;
    private float defaultPosY;
    private boolean down;

    // dice model
    public Dice dice;

    // bottom padding
    private float padding=50;

    // velocity of the animation in milliseconds
    private int animateVelocity = 60;

    private float turnScale = 0.8f;

    private float upScale = 1.5f;

    /**Constructor
     * @param type is a int value of the dice number when is added to the scene
     *       [ 3 ]
     *  [ 0 ]     [ 4 ]
     *       [ 2 ]
     *            [ 1 ]
     */
    public DiceSprite(int type) {
        super(0, 0, ResourceManager.getInstance().DICE_TEXTURE_REGION,
                ResourceManager.getInstance().getVertexBuffer());
        dice = new Dice();
        switch (type){
            case 0:
                //defaultPosX = GameActivity.WORLD_WIDTH/4;
                defaultPosX = (GameActivity.WORLD_WIDTH/2)+GameActivity.WORLD_WIDTH/4;
                defaultPosY = padding+this.getHeight()+this.getHeight()/2;
                break;
            case 1:
                //defaultPosX = (GameActivity.WORLD_WIDTH/2)+GameActivity.WORLD_WIDTH/4;
                defaultPosX = GameActivity.WORLD_WIDTH/4;
                defaultPosY = padding+this.getHeight()/2;
                break;
            case 2:
                defaultPosX = GameActivity.WORLD_WIDTH/2;
                defaultPosY = padding+this.getHeight();
                break;
            case 3:
                defaultPosX = GameActivity.WORLD_WIDTH/2;
                defaultPosY = ((padding+this.getHeight())*2)-padding/2;
                break;
            case 4:
                //defaultPosX = (GameActivity.WORLD_WIDTH/2)+GameActivity.WORLD_WIDTH/4;
                defaultPosX = GameActivity.WORLD_WIDTH/4;
                defaultPosY = padding/2+((padding+this.getHeight()/2)*2);
                break;
        }
        setOffScreen();
        // TODO delete
        //setDefaultPosition();
    }

    /**Override the touch events on the dice*/
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(!GameManager.getInstance().isRolling && GameManager.getInstance().totalTurns<11) {
            switch (pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
                    if (GameManager.getInstance().noteBookSprite.isVisible()) {
                        verifyTurn();
                    } else {
                        down = true;
                        this.setScale(upScale);
                        GameManager.getInstance().cachoSprite.moveDown();
                    }

                    break;
                case TouchEvent.ACTION_MOVE:
                    if (!GameManager.getInstance().noteBookSprite.isVisible() && down) {
                        this.setPosition(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
                    }
                    break;
                case TouchEvent.ACTION_OUTSIDE:
                    down = false;
                    if (!GameManager.getInstance().noteBookSprite.isVisible()) {
                        if (!dice.getTurned()) {
                            this.setScale(1f);
                        } else {
                            this.setScale(turnScale);
                        }
                        setDefaultPosition();
                    }
                    break;
                case TouchEvent.ACTION_CANCEL:
                    down = false;
                    if (!GameManager.getInstance().noteBookSprite.isVisible()) {
                        if (!dice.getTurned()) {
                            this.setScale(1f);
                        } else {
                            this.setScale(turnScale);
                        }
                        setDefaultPosition();
                    }
                    break;
                case TouchEvent.ACTION_UP:
                    down = false;
                    if (!GameManager.getInstance().noteBookSprite.isVisible()) {
                        if (this.collidesWith(GameManager.getInstance().cachoSprite)) {
                            setReadyToRoll();
                        } else {
                            setDefaultPosition();
                        }
                        if (!dice.getTurned()) {
                            this.setScale(1f);
                        } else {
                            this.setScale(turnScale);
                        }
                        GameManager.getInstance().cachoSprite.moveUp();
                    }
                    break;
            }
        }
        return true;
    }

    /**
     * Setter of the tiled
     * we have the random value  1<=x<=6 and we need to set the tiled but the tiled array
     * start from zero, so we rest 1 to the current value
     */
    public void updateTiled(){
      this.setCurrentTileIndex(dice.getValue() - 1);
    }

    /**
     * Set the dice in the "cup" to roll
     */
    public void setReadyToRoll() {
        down = false;
        GameManager.getInstance().noteBookSprite.outTab();
        dice.setReadyToRoll(true);
        ResourceManager.getInstance().tic.play();
        setOffScreen();
        if(!GameManager.getInstance().changingTurnAnimation) {
            GameManager.getInstance().buttonRoll.show();
        }
    }

    /**
     * Set the dice in the default position
     */
    public void setDefaultPosition(){
        this.setPosition(defaultPosX, defaultPosY);
    }

    /**
     * Set the dice off scren
     */
    private void setOffScreen(){
        this.setPosition(240,GameActivity.WORLD_HEIGHT+100);
    }

    /**
     * Rolling the dice
     */
    public void startRoll(){
        if(dice.getReadyToRoll()) {
            if(dice.getTurned()){
                turn();
                GameManager.getInstance().getCurrentPlayer().removeTurn();
            }
            animate(animateVelocity);
            setDefaultPosition();
        }
    }

    /**
     * Finish the roll and set the values
     */
    public void finishRoll(){
        down = false;
        if(dice.getReadyToRoll()) {
            dice.setTurned(false);
            int value = (int) (Math.random() * 6);
            //int value = 1;
            value += 1;
            dice.setValue(value);
            this.stopAnimation();
            updateTiled();
            dice.setReadyToRoll(false);
        }
    }


    /**
     * Reset turn
     */
    public void resetTurn(){
        dice.reset();
        this.setScale(1f);
        setReadyToRoll();
    }

    /**
     * Verify if the player can turn the dice
     */
    public void verifyTurn(){
        if(dice.getTurned()){
            turn();
            GameManager.getInstance().getCurrentPlayer().removeTurn();
        }else {
            if (GameManager.getInstance().getCurrentPlayer().getTurns() < 2) {
                turn();
                GameManager.getInstance().getCurrentPlayer().addTurn();
            }
        }
    }

    /**
     * CachoTurn the dice
     */
    private void turn(){
        dice.turn();
        updateTiled();
        if(dice.getTurned()){
            this.setScale(turnScale);
        }else{
            this.setScale(1f);
        }
    }

    public void setTurnScale(){
        this.setScale(turnScale);
    }


    public int getValue(){
        return dice.getValue();
    }

    public boolean isReadyToRoll(){
        return dice.getReadyToRoll();
    }

    public boolean isDown(){
        return down;
    }

}
