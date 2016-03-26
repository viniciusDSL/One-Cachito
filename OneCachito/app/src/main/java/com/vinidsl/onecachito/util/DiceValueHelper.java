package com.vinidsl.onecachito.util;


import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.sprite.DiceSprite;

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
public class DiceValueHelper {

    public static int getValueFrom( int diceValue){
        DiceSprite[] diceSprites = GameManager.getInstance().cachoSprite.diceSprites;
        int value=0;
        for(int i=0;i<diceSprites.length;i++){
                    if(diceSprites[i].getValue()==diceValue){
                        value+=(diceValue);
            }
        }
        return value;
    }

    public static int calculateEscalera(){
        int value = 0;
        DiceSprite[] diceSprites = GameManager.getInstance().cachoSprite.diceSprites;
        boolean[] score = {false,false,false,false,false,false,false};
        for(int i=0;i<diceSprites.length;i++){
             score[diceSprites[i].getValue()]=true;
        }
        boolean escalera = true;
        for(int i=1;i<score.length-1;i++){
            if(!score[i]){
                escalera = false;
            }
        }
        if(!escalera) {
            escalera = true;
            for (int i = 2; i < score.length; i++) {
                if (!score[i]) {
                    escalera = false;
                }
            }
            if(!escalera){
                escalera = true;
                if(!score[1]){
                    escalera = false;
                }else {
                    for (int i = 3; i < score.length; i++) {
                        if (!score[i]) {
                            escalera = false;
                        }
                    }
                }
            }
        }
        if(escalera) {
            if (GameManager.getInstance().getCurrentPlayer().getTurns() == 0 &&
            GameManager.getInstance().getCurrentPlayer().getDicesRolling()==5
                    ) {
                value = 25;
            } else {
                value = 20;
            }
        }
        return value;
    }

    public static int calculateFull(){
        int value = 0;
        DiceSprite[] diceSprites = GameManager.getInstance().cachoSprite.diceSprites;
        int[] score = {0,0,0,0,0,0,0};
        for(int i=0;i<diceSprites.length;i++){
            score[diceSprites[i].getValue()]++;
        }
        boolean sw2=false;
        boolean sw3=false;
        for(int i=1;i<score.length;i++){
            if(score[i]==2){
                sw2=true;
            }
            if(score[i]==3){
                sw3=true;
            }
        }
        if(sw2 && sw3){
            if (GameManager.getInstance().getCurrentPlayer().getTurns() == 0
                    &&
                    GameManager.getInstance().getCurrentPlayer().getDicesRolling()==5 ) {
                value = 35;
            } else {
                value = 30;
            }
        }
        return value;
    }

    public static int calculatePoker(){
        int value = 0;
        DiceSprite[] diceSprites = GameManager.getInstance().cachoSprite.diceSprites;
        int[] score = {0,0,0,0,0,0,0};
        for(int i=0;i<diceSprites.length;i++){
            score[diceSprites[i].getValue()]++;
        }
        boolean sw1=false;
        boolean sw4=false;
        for(int i=1;i<score.length;i++){
            if(score[i]==1){
                sw1=true;
            }
            if(score[i]==4){
                sw4=true;
            }
        }
        if(sw1 && sw4){
            if (GameManager.getInstance().getCurrentPlayer().getTurns() == 0
                    &&
                    GameManager.getInstance().getCurrentPlayer().getDicesRolling()==5 ) {
                value = 45;
            } else {
                value = 40;
            }
        }
        return value;
    }

    public static boolean hasGoldBelly(){
        Integer[] score = GameManager.getInstance().getCurrentPlayer().getScore();
        if(score[6]!=25){
            return false;
        }
        if(score[7]!=35){
            return false;
        }
        if(score[8]!=45){
            return false;
        }
        if(score[9]==50 || score[10]==50){
            return true;
        }
        return false;
    }

    public static int calculateGrande(){
        int value = 0;
        DiceSprite[] diceSprites = GameManager.getInstance().cachoSprite.diceSprites;
        int[] score = {0,0,0,0,0,0,0};
        for(int i=0;i<diceSprites.length;i++){
            score[diceSprites[i].getValue()]++;
        }
        boolean grande=false;
        for(int i=1;i<score.length;i++){
            if(score[i]==5){
                grande=true;
                break;
            }
        }
        if(grande) {
            if (GameManager.getInstance().getCurrentPlayer().getTurns() == 0
                    &&
                    GameManager.getInstance().getCurrentPlayer().getDicesRolling()==5 ) {
                value = 100;
            } else {
                value = 50;
            }
        }
        return value;
    }

}