package com.vinidsl.onecachito.model;

import com.vinidsl.onecachito.manager.GameManager;

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
public class Dice {
    //value of the dice rolled
    private int value;
    // flag to know if the dice can be startRoll
    private boolean readyToRoll;
    // flag to know if the dice is turned
    private boolean turned;

    public Dice(){
        setValue(0);
        setReadyToRoll(true);
        setTurned(false);
    }

    public void reset(){
        setValue(0);
        setReadyToRoll(true);
        setTurned(false);
    }

    public void turn(){
        GameManager.getInstance().noteBookSprite.showScoreButtonsOfCurrentUser(GameManager.getInstance().getCurrentPlayer());
        setTurned(!getTurned());
        switch (getValue()){
            case 1:
                setValue(6);
                break;
            case 2:
                setValue(5);
                break;
            case 3:
                setValue(4);
                break;
            case 4:
                setValue(3);
                break;
            case 5:
                setValue(2);
                break;
            case 6:
                setValue(1);
                break;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setReadyToRoll(boolean readyToRoll) {
        if(readyToRoll) {
            this.value = 0;
        }
            this.readyToRoll = readyToRoll;

    }

    public boolean getReadyToRoll(){
        return readyToRoll;
    }

    public void setTurned(boolean turned) {
        this.turned = turned;
    }

    public boolean getTurned(){
        return turned;
    }
}
