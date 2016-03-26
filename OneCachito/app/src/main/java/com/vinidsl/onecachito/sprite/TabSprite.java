package com.vinidsl.onecachito.sprite;


import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.Constants;

import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
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
public class TabSprite extends AnimatedSprite {

    private int current;

    public TabSprite(int currentTile) {
        super(0, 0, ResourceManager.getInstance().TABS_TEXTURE_REGION,
                ResourceManager.getInstance().getVertexBuffer());
        this.setCurrentTileIndex(currentTile);
        current = currentTile;
        switch (currentTile){
            case 0:
                this.setPosition(-this.getWidth() / 2, -29+GameActivity.WORLD_HEIGHT/2);
                break;
            case 1:
                this.setPosition(GameActivity.WORLD_WIDTH-this.getWidth()/2, 100+GameActivity.WORLD_HEIGHT/2);
                break;
            case 2:
                this.setPosition(GameActivity.WORLD_WIDTH-this.getWidth()/4, GameActivity.WORLD_HEIGHT/2);
                break;
            case 3:
                this.setPosition(GameActivity.WORLD_WIDTH-this.getWidth()/4, -100+GameActivity.WORLD_HEIGHT/2);
                break;
        }
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        //if (!GameManager.getInstance().isRolling && !GameManager.getInstance().cachoSprite.haveDiceToRoll()) {
            switch (pSceneTouchEvent.getAction()) {
                case TouchEvent.ACTION_DOWN:
                    GameManager.getInstance().buttonRoll.hide();
                    GameManager.getInstance().noteBookSprite.showPlayerScore(GameManager.getInstance().getPlayer(current-1));
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
        //}
        return true;
    }

    public void show(){
        clearEntityModifiers();
        int over = 4;
        if(current -1 == GameManager.getInstance().currentPlayer){
           over = 2;
        }
        this.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION,this.getX(),GameActivity.WORLD_WIDTH-this.getWidth()/over, EaseLinear.getInstance()));
    }

    public void hide(){
        clearEntityModifiers();
        this.registerEntityModifier(new MoveXModifier(Constants.ANIMATION_DURATION,this.getX(),this.getWidth() + GameActivity.WORLD_WIDTH-this.getWidth()/4, EaseLinear.getInstance()));
    }

}
