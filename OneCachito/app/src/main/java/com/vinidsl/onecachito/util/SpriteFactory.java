package com.vinidsl.onecachito.util;



import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.sprite.ButtonScoreSprite;

import org.andengine.entity.sprite.Sprite;



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
public class SpriteFactory {

    public static final int TYPE_MULTI_TWO=0;
    public static final int TYPE_MULTI_THREE=1;
    public static final int TYPE_MULTI_ONE=2;

    public static Sprite getTableBackground(){
        return new Sprite(GameActivity.WORLD_WIDTH/2,GameActivity.WORLD_HEIGHT/2, ResourceManager.getInstance().TABLE_BACKGROUND_TEXTURE_REGION,
                ResourceManager.getInstance().getVertexBuffer());
    }

    public static ButtonScoreSprite getButtonScoreFromType(int position){
        ButtonScoreSprite buttonScoreSprite;
        float posX;
        float posY;
        switch (position){
            case 1:
                posX = 116;
                posY = 319;
                buttonScoreSprite = instance(posX,posY,position,false);
                break;
            case 2:
                posX = 116;
                posY = 234;
                buttonScoreSprite = instance(posX,posY,position,false);
                break;
            case 3:
                posX = 116;
                posY = 147;
                buttonScoreSprite = instance(posX,posY,position,false);
                break;
            case 7:
                posX = 205;
                posY = 319;
                buttonScoreSprite = instance(posX,posY,position,true);
                break;
            case 8:
                posX = 205;
                posY = 234;
                buttonScoreSprite = instance(posX,posY,position,true);
                break;
            case 9:
                posX = 205;
                posY = 147;
                buttonScoreSprite = instance(posX,posY,position,true);
                break;
            case 4:
                posX = 293;
                posY = 319;
                buttonScoreSprite = instance(posX,posY,position,false);
                break;
            case 5:
                posX = 293;
                posY = 234;
                buttonScoreSprite = instance(posX,posY,position,false);
                break;
            case 6:
                posX = 293;
                posY = 147;
                buttonScoreSprite = instance(posX,posY,position,false);
                break;
            case 10:
                posX = 116;
                posY = 57;
                buttonScoreSprite = instance(posX,posY,position,true);
                break;
            default:
                posX = 293;
                posY = 57;
                buttonScoreSprite = instance(posX,posY,position,true);
                break;
        }
            buttonScoreSprite.setScorePosition(position);
            buttonScoreSprite.setAlpha(0.5f);
            buttonScoreSprite.setScale(0.7f);

        return buttonScoreSprite;
    }

    public static Sprite getSimpleSprite(float posX, float posY,int type){
        Sprite sprite = null;
        switch (type){
            case TYPE_MULTI_TWO:
                sprite = new Sprite(posX,posY, ResourceManager.getInstance().TWO_PLAYERS_TEXTURE_REGION,ResourceManager.getInstance().getVertexBuffer());
                break;
            case TYPE_MULTI_THREE:
                sprite = new Sprite(posX,posY, ResourceManager.getInstance().THREE_PLAYERS_TEXTURE_REGION,ResourceManager.getInstance().getVertexBuffer());
                break;
            case TYPE_MULTI_ONE:
                sprite = new Sprite(posX,posY, ResourceManager.getInstance().ONE_PLAYER_TEXTURE_REGION,ResourceManager.getInstance().getVertexBuffer());
                break;
        }

        return sprite;
    }

    public static ButtonScoreSprite instance(float posX,float posY, int position, boolean extra){
        ButtonScoreSprite buttonScoreSprite;
        if(extra){
            buttonScoreSprite = new ButtonScoreSprite(posX,posY, ResourceManager.getInstance().EXTRA_BUTTONS_TEXTURE_REGION);
            switch (position){
                case 7:
                    buttonScoreSprite.setCurrentTileIndex(0);
                break;
                case 8:
                    buttonScoreSprite.setCurrentTileIndex(2);
                    break;
                case 9:
                    buttonScoreSprite.setCurrentTileIndex(1);
                    break;
                default:
                    buttonScoreSprite.setCurrentTileIndex(3);
                    break;
            }
        }else{
            buttonScoreSprite = new ButtonScoreSprite(posX,posY, ResourceManager.getInstance().DICE_TEXTURE_REGION);
            buttonScoreSprite.setCurrentTileIndex(position - 1);

        }
        return buttonScoreSprite;
    }
}
