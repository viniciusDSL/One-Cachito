package com.vinidsl.onecachito.manager;
import com.vinidsl.onecachito.model.Player;
import com.vinidsl.onecachito.sprite.ButtonBorder;
import com.vinidsl.onecachito.sprite.ButtonRoll;
import com.vinidsl.onecachito.sprite.CachoSprite;
import com.vinidsl.onecachito.sprite.DormidaSprite;
import com.vinidsl.onecachito.sprite.NextTurnSprite;
import com.vinidsl.onecachito.sprite.NoteBookSprite;

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
public class GameManager {
    // Instance of the game
    private static GameManager INSTANCE;

    // flag to know if the game is rolling the dices
    public boolean isRolling;
    // players of the game
    public Player players[];
    // number of the current player
    public int currentPlayer;
    // cacho Sprite
    public CachoSprite cachoSprite;
    // notebook to show and write score
    public NoteBookSprite noteBookSprite;

    public DormidaSprite dormidaSprite;

    public int totalTurns;

    public int numberOfPlayers;

    public ButtonBorder buttonDone;

    public NextTurnSprite nextTurnSprite;
    public boolean changingTurnAnimation;
    public ButtonRoll buttonRoll;

    GameManager() {

    }

    /*return the instance of the game*/
    public static GameManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    public static void reset() {
        INSTANCE = null;
    }

    public Player getCurrentPlayer() {
        return players[currentPlayer];
    }


    public void initPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player(i);
        }
    }

    public Player getPlayer(int position) {
        return players[position];
    }

    public void showTabs() {
        for (int i = 0; i < players.length; i++) {
            players[i].tabSprite.show();
        }
    }

    public void hideTabs() {
        for (int i = 0; i < players.length; i++) {
            players[i].tabSprite.hide();
        }
    }

    public void nextTurn() {
        if (numberOfPlayers == 3) {
            if (currentPlayer >= 2) {
                currentPlayer = 0;
                totalTurns++;
            } else {
                currentPlayer++;
            }
        }
        if (numberOfPlayers == 2) {
            if (currentPlayer >= 1) {
                currentPlayer = 0;
                totalTurns++;
            } else {
                currentPlayer++;
            }
        }
        if (numberOfPlayers == 1) {
            currentPlayer = 0;
            totalTurns++;
        }

        GameManager.getInstance().noteBookSprite.unlock();
       /* for(int i=0;i<cachoSprite.diceSprites.length;i++){
            cachoSprite.diceSprites[i].resetTurn();
        }*/
        if (totalTurns == 11) {
            finishGame();
        } else {
            players[0].tabSprite.setCurrentTileIndex(1);
            if (numberOfPlayers > 1) {
                players[1].tabSprite.setCurrentTileIndex(2);
            }
            if (numberOfPlayers > 2) {
                players[2].tabSprite.setCurrentTileIndex(3);
            }
            switch (currentPlayer) {
                case 0:
                    players[0].tabSprite.setCurrentTileIndex(5);
                    nextTurnSprite.nextTurn.setColor(0.980392157f, 0.505882353f, 0.196078431f);
                    break;
                case 1:
                    if (numberOfPlayers > 1) {
                        players[1].tabSprite.setCurrentTileIndex(6);
                        nextTurnSprite.nextTurn.setColor(0.207843137f, 0.729411765f, 0.952941176f);
                    }
                    break;
                case 2:
                    if (numberOfPlayers > 2) {
                        players[2].tabSprite.setCurrentTileIndex(7);
                        nextTurnSprite.nextTurn.setColor(1f, 0.850980392f, 0.282352941f);
                    }
                    break;
            }
            players[currentPlayer].resetTurn();
            noteBookSprite.hide();
            noteBookSprite.outTab();
            noteBookSprite.force = false;
            cachoSprite.changePlayerCacho(currentPlayer);
        }
    }

    public void finishGame() {
        totalTurns = 11;
        noteBookSprite.hide();
        noteBookSprite.outTab();
        hideTabs();
        noteBookSprite.tabSprite.setVisible(false);
        cachoSprite.counterDices = GameManager.getInstance().cachoSprite.diceSprites.length - 1;
        cachoSprite.resetTurns();
    }

    public void startGame() {
        currentPlayer = 0;
        players[0].tabSprite.setCurrentTileIndex(5);
    }


}
