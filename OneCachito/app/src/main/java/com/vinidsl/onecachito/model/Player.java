package com.vinidsl.onecachito.model;


import com.vinidsl.onecachito.sprite.TabSprite;

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
public class Player {

    // save the score of each field
    private Integer score[];
    // save the total score
    private int totalScore;
    // number of rolls max 2
    private int rolls;
    // number of dices turned max 2
    private int turns;
    //number of dices rolling
    private int dicesRolling;

    public TabSprite tabSprite;

    private int numberPlayer;

    public Player(int numberPlayer) {
        this.numberPlayer = numberPlayer;
        dicesRolling = 0;
        score = new Integer[11];
        for(int i=0; i<11;i++){
            score[i] = -1;
        }
        updateScore();
        initRoll();
    }

    public void initRoll(){
        rolls = 0;
        turns = 0;
    }

    public void setScore(Integer[] score) {
        this.score = score;
    }

    public void saveScore(int position,int value){
        score[position]=value;
    }

    public void addTurn(){
        turns++;
    }

    public void removeTurn(){
        turns--;
    }

    public void addRoll(){
        rolls++;
    }

    public Integer[] getScore() {
        return score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getRolls() {
        return rolls;
    }

    public int getTurns() {
        return turns;
    }

    public void updateScore(){
        totalScore = 0;
        for(int i=0; i<11;i++){
            if(score[i]> 0){
                totalScore+=score[i];
            }
        }
    }

    public void addDiceRolling(){
        dicesRolling++;
    }

    public void resetDicesRolling(){
        dicesRolling=0;
    }

    public int getDicesRolling() {
        return dicesRolling;
    }

    public void resetTurn() {
        turns = 0;
    }

    public int getNumberPlayer() {
        return numberPlayer;
    }

    public void setNumberPlayer(int numberPlayer) {
        this.numberPlayer = numberPlayer;
    }
}
