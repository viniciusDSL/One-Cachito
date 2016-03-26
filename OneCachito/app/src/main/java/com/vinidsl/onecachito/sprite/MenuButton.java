package com.vinidsl.onecachito.sprite;

import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.Constants;

import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

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
public class MenuButton extends ButtonSprite {

    private float originPosX;

    private float originPosY;

    public MenuButton(float pX, float pY, ITiledTextureRegion iTiledTextureRegion, int value) {
        super(pX, pY, iTiledTextureRegion, ResourceManager.getInstance().getVertexBuffer());
        originPosX = pX;
        originPosY = pY;
    }

    public MenuButton(float pX, float pY,ITiledTextureRegion tiledTextureRegion) {
        super(pX, pY, tiledTextureRegion, ResourceManager.getInstance().getVertexBuffer());
        originPosX = pX;
        originPosY = pY;
        setX(pX+200);
    }

    public void show(){
        this.registerEntityModifier(
                new MoveXModifier(Constants.ANIMATION_TEXT_DURATION,this.getX(),originPosX));
    }

    public void hide(){
        this.registerEntityModifier(
                new MoveXModifier(Constants.ANIMATION_TEXT_DURATION,this.getX(),originPosX+200));
    }
}
