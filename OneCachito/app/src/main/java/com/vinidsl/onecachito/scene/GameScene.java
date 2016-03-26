package com.vinidsl.onecachito.scene;

import com.afollestad.materialdialogs.MaterialDialog;
import com.vinidsl.onecachito.R;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.manager.SceneManager;
import com.vinidsl.onecachito.model.Player;
import com.vinidsl.onecachito.scene.callbacks.SceneCallbacks;
import com.vinidsl.onecachito.sprite.ButtonBorder;
import com.vinidsl.onecachito.sprite.ButtonRoll;
import com.vinidsl.onecachito.sprite.CachoSprite;
import com.vinidsl.onecachito.sprite.DiceSprite;
import com.vinidsl.onecachito.sprite.DormidaSprite;
import com.vinidsl.onecachito.sprite.NextTurnSprite;
import com.vinidsl.onecachito.sprite.NoteBookSprite;
import com.vinidsl.onecachito.sprite.TabSprite;
import com.vinidsl.onecachito.util.SpriteFactory;
import com.vinidsl.onecachito.util.StringHelper;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.input.sensor.acceleration.AccelerationData;
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
public class GameScene extends BasicScene implements SceneCallbacks {

    private int numberOfPlayers;

    public GameScene(int numberOfPLayers){
            this.numberOfPlayers = numberOfPLayers;
        init();
    }

    @Override
    public synchronized void init(){
        this.setTouchAreaBindingOnActionDownEnabled(true);
        this.setTouchAreaBindingOnActionMoveEnabled(true);
        setBackground(new SpriteBackground(SpriteFactory.getTableBackground()));
        GameManager.getInstance().noteBookSprite = new NoteBookSprite();
        GameManager.getInstance().players = new Player[numberOfPlayers];
        GameManager.getInstance().currentPlayer = 0;
        GameManager.getInstance().cachoSprite = new CachoSprite();
        GameManager.getInstance().noteBookSprite.tabSprite = new TabSprite(0){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (!GameManager.getInstance().noteBookSprite.justShow && !GameManager.getInstance().isRolling && !GameManager.getInstance().cachoSprite.haveDiceToRoll()) {
                switch (pSceneTouchEvent.getAction()) {
                        case TouchEvent.ACTION_DOWN:
                                if (GameManager.getInstance().noteBookSprite.isVisible()) {
                                    GameManager.getInstance().noteBookSprite.hide();
                                } else {
                                    GameManager.getInstance().noteBookSprite.showScoreButtonsOfCurrentUser(
                                            GameManager.getInstance().getCurrentPlayer()
                                    );
                                    GameManager.getInstance().noteBookSprite.show();
                                }
                            break;
                        case TouchEvent.ACTION_MOVE:
                            break;
                        case TouchEvent.ACTION_OUTSIDE:
                            break;
                        case TouchEvent.ACTION_CANCEL:
                            break;
                        case TouchEvent.ACTION_UP:
                            break;
                    }
                }else{
                    if(GameManager.getInstance().noteBookSprite.isVisible()){
                        GameManager.getInstance().cachoSprite.showDices();
                        GameManager.getInstance().noteBookSprite.hide();
                        if(GameManager.getInstance().cachoSprite.allDicesOnCacho()) {
                            GameManager.getInstance().noteBookSprite.outTab();
                            GameManager.getInstance().buttonRoll.show();
                        }
                    }
                }
                return true;
            }
        };
        GameManager.getInstance().initPlayers(numberOfPlayers);

        GameManager.getInstance().players[0].tabSprite = new TabSprite(1);
        this.attachChild(GameManager.getInstance().players[0].tabSprite);
        this.registerTouchArea(GameManager.getInstance().players[0].tabSprite);

        if(numberOfPlayers>1) {
            GameManager.getInstance().players[1].tabSprite = new TabSprite(2);
            this.attachChild(GameManager.getInstance().players[1].tabSprite);
            this.registerTouchArea(GameManager.getInstance().players[1].tabSprite);
        }
        if(numberOfPlayers>2) {
            GameManager.getInstance().players[2].tabSprite = new TabSprite(3);
            this.attachChild(GameManager.getInstance().players[2].tabSprite);
            this.registerTouchArea(GameManager.getInstance().players[2].tabSprite);
        }

        initDices();
        this.attachChild(GameManager.getInstance().cachoSprite);
        this.attachChild(GameManager.getInstance().noteBookSprite);
        this.attachChild(GameManager.getInstance().noteBookSprite.tabSprite);
        this.registerTouchArea(GameManager.getInstance().noteBookSprite.tabSprite);
        GameManager.getInstance().noteBookSprite.attachildAndRegisterButtons(this);
        GameManager.getInstance().startGame();
        GameManager.getInstance().dormidaSprite = new DormidaSprite();
        this.attachChild(GameManager.getInstance().dormidaSprite);
        GameManager.getInstance().nextTurnSprite = new NextTurnSprite();
        this.attachChild(GameManager.getInstance().nextTurnSprite);

        GameManager.getInstance().buttonDone = new ButtonBorder();
        this.attachChild(GameManager.getInstance().buttonDone);
        this.registerTouchArea(GameManager.getInstance().buttonDone);
        GameManager.getInstance().buttonDone.setVisible(false);
        GameManager.getInstance().buttonDone.setEnabled(false);

        GameManager.getInstance().buttonDone.setOnClickListener(new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite buttonSprite, float v, float v1) {
                SceneManager.getInstance().changeToScene(SceneManager.SCENE_ID_MAIN_MENU);
            }
        });
        GameManager.getInstance().buttonRoll = new ButtonRoll();
        this.attachChild(GameManager.getInstance().buttonRoll);
        this.registerTouchArea(GameManager.getInstance().buttonRoll);
        GameManager.getInstance().buttonRoll.setOnClickListener(new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite buttonSprite, float v, float v1) {

                if (GameManager.getInstance().totalTurns < 11 &&
                        !GameManager.getInstance().isRolling &&
                        GameManager.getInstance().cachoSprite.haveDiceToRoll() &&
                        !GameManager.getInstance().noteBookSprite.isVisible() &&
                        !GameManager.getInstance().changingTurnAnimation
                        && !GameManager.getInstance().cachoSprite.anyDiceIsDown()) {
                    roll();
                }
            }
        });
            GameManager.getInstance().nextTurnSprite.firstAnimate();
    }

    private void roll(){
        GameManager.getInstance().cachoSprite.rollDices();
        GameManager.getInstance().buttonRoll.hide();
        if (ResourceManager.getInstance().musicShake.isPlaying()) {
            ResourceManager.getInstance().musicShake.pause();
        }
        if (!ResourceManager.getInstance().musicShake.isPlaying()) {
            ResourceManager.getInstance().musicShake.pause();
            ResourceManager.getInstance().musicShake.seekTo(0);
        }
        ResourceManager.getInstance().musicRoll.play();
    }

    @Override
    public void onBackKeyPressed() {
        new MaterialDialog.Builder(ResourceManager.getInstance().activity)
                .title(StringHelper.getStringFromResource(R.string.title_out))
                .content(StringHelper.getStringFromResource(R.string.message_out))
                .positiveText(StringHelper.getStringFromResource(R.string.yes))
                .negativeText(StringHelper.getStringFromResource(R.string.no))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        SceneManager.getInstance().changeToScene(SceneManager.SCENE_ID_MAIN_MENU);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void clear() {

    }

    private void initDices(){
        int dicesLimit = 5;
        GameManager.getInstance().cachoSprite.diceSprites = new DiceSprite[dicesLimit];
        for(int i= dicesLimit -1;i>=0;i--){
            // initialize the dices objects
            GameManager.getInstance().cachoSprite.diceSprites[i] = new DiceSprite(i);
            // add each dice to the scene
            this.attachChild(GameManager.getInstance().cachoSprite.diceSprites[i]);
            // register touch listener of each dice in the scene
            this.registerTouchArea(GameManager.getInstance().cachoSprite.diceSprites[i]);
        }

    }

    private long lastShakeTime = 0;
    private float lastShakeX, lastShakeY, lastShakeZ = 0;

    @Override
    public void onAccelerometerChanged(AccelerationData accelerometerData) {
        if (GameManager.getInstance().totalTurns<11 &&
                !GameManager.getInstance().isRolling &&
                   GameManager.getInstance().cachoSprite.haveDiceToRoll() &&
                !GameManager.getInstance().noteBookSprite.isVisible() &&
                !GameManager.getInstance().changingTurnAnimation
                && !GameManager.getInstance().cachoSprite.anyDiceIsDown()) {



        long currentTime = System.currentTimeMillis();
        if((currentTime - lastShakeTime) > 100) {

            long diffTime = currentTime - lastShakeTime;


            lastShakeTime = currentTime;

            float x = accelerometerData.getX();
            float y = accelerometerData.getY();
            float z = accelerometerData.getZ();

            float speed = Math.abs(x+y+z-lastShakeX-lastShakeY-lastShakeZ) / diffTime * 10000;


            if(speed > 1000)
            {
                    if(!ResourceManager.getInstance().musicShake.isPlaying()){
                        ResourceManager.getInstance().musicShake.play();
                    }

            }

            lastShakeX = x;
            lastShakeY = y;
            lastShakeZ = z;

        }
            if ((accelerometerData.getZ() < -7 && accelerometerData.getY() < 7 && accelerometerData.getX() < 1)
                    || (accelerometerData.getZ() < -7 && accelerometerData.getY() < 1 && accelerometerData.getX() > 4)
                    || (accelerometerData.getZ() < -7 && accelerometerData.getY() < -6 && accelerometerData.getX() > 1)
                    || (accelerometerData.getZ() < -7 && accelerometerData.getY() < -1 && accelerometerData.getX() < -5)
                    ) {

                roll();
            }
    }
    }
}
