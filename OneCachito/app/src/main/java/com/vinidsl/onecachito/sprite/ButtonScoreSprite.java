package com.vinidsl.onecachito.sprite;

import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.DiceValueHelper;
import com.vinidsl.onecachito.util.Paint;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.adt.color.Color;

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
public class ButtonScoreSprite extends AnimatedSprite {

    private int scorePosition;
    public Text text;
    private int counterTouch;
    private int value;

    public ButtonScoreSprite(float pX, float pY, ITiledTextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion, ResourceManager.getInstance().getVertexBuffer());
        text = new Text(this.getWidth()/2,this.getHeight()/2, ResourceManager.getInstance().fontBig,"123456789",10, ResourceManager.getInstance().getVertexBuffer());
        text.setText("");
        text.setColor(Color.BLACK);
        this.attachChild(text);
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

            if (this.counterTouch<2) {
                switch (pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
                    switch (GameManager.getInstance().currentPlayer){
                        case 0:
                            text.setColor(ResourceManager.getInstance().orange);
                            break;
                        case 1:
                            text.setColor(ResourceManager.getInstance().blue);
                            break;
                        default:
                            text.setColor(ResourceManager.getInstance().yellow);
                            break;
                    }
                    setScore();
                    //this.setColor(0.7f, 0f, 0f);
                    break;
                case TouchEvent.ACTION_UP:
                    this.setColor(1f, 1f, 1f);
                    break;
                case TouchEvent.ACTION_OUTSIDE:
                    this.setColor(1f, 1f, 1f);
                    break;
                case TouchEvent.ACTION_CANCEL:
                    this.setColor(1f, 1f, 1f);
                    break;
            }
        }

        return true;
    }

    private void setScore(){
        if(counterTouch==0) {
            GameManager.getInstance().noteBookSprite.showScoreButtonsOfCurrentUser(GameManager.getInstance().getCurrentPlayer());
            if (scorePosition <= 6) {
                value = DiceValueHelper.getValueFrom(scorePosition);
            }
            switch (scorePosition) {
                case 7:
                    value = DiceValueHelper.calculateEscalera();
                    break;
                case 8:
                    value = DiceValueHelper.calculateFull();
                    break;
                case 9:
                    value = DiceValueHelper.calculatePoker();
                    break;
                case 10:
                    value = DiceValueHelper.calculateGrande();
                    break;
                case 11:
                    value = DiceValueHelper.calculateGrande();
                    break;
            }
            counterTouch++;
            switch (GameManager.getInstance().currentPlayer){
                case 0:
                    text.setColor(ResourceManager.getInstance().orange);
                    break;
                case 1:
                    text.setColor(ResourceManager.getInstance().blue);
                    break;
                default:
                    text.setColor(ResourceManager.getInstance().yellow);
                    break;
            }
            text.setText(""+value);
            this.setAlpha(0f);
        }else{
            Paint.gray(text);
            counterTouch=0;
            GameManager.getInstance().getCurrentPlayer().saveScore(scorePosition - 1, value);
            GameManager.getInstance().getCurrentPlayer().updateScore();
            GameManager.getInstance().getCurrentPlayer().initRoll();
            if(DiceValueHelper.hasGoldBelly()){
                GameManager.getInstance().finishGame();
            }else{
                GameManager.getInstance().nextTurn();
            }

        }
    }

    public void setUserScore(int userValue){
        counterTouch=2;
        text.setText("" + userValue);

        this.setAlpha(0f);
        switch (scorePosition) {
            case 7:
                if(userValue==25){
                    Paint.gold(text);
                }else{
                    Paint.gray(text);
                }
                break;
            case 8:
                if(userValue==35){
                    Paint.gold(text);
                }else{
                    Paint.gray(text);
                }
                break;
            case 9:
                if(userValue==45){
                    Paint.gold(text);
                }else{
                    Paint.gray(text);
                }
                break;
            case 10:
                if(userValue==50){
                    Paint.gold(text);
                }else{
                    Paint.gray(text);
                }
                break;
            case 11:
                if(userValue==50){
                    Paint.gold(text);
                }else{
                    Paint.gray(text);
                }
                break;
            default:
                Paint.gray(text);
                break;
        }

    }

    public void hide(){
        counterTouch=0;
        text.setText("");
        Paint.gray(text);
        this.setAlpha(1f);
    }

    public void setScorePosition(int scorePosition) {

        this.scorePosition = scorePosition;
    }

    public int getScorePosition() {
        return scorePosition;
    }
}
