package com.vinidsl.onecachito.sprite;

import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.Constants;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.sprite.ButtonSprite;
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
public class NextArrowSprite extends ButtonSprite {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private int type;

    public NextArrowSprite(int type) {
        super(0,0,ResourceManager.getInstance().NEXT_TEXTURE, ResourceManager.getInstance().getVertexBuffer());
        switch (type) {
            case LEFT:
                this.setPosition(-25, 200);
                this.setFlippedHorizontal(true);
                break;
            case RIGHT:
                this.setPosition(404, 200);
                break;
        }
        this.type = type;
        this.setAlpha(0f);
    }

    public void show(){
        if(this.getAlpha()!=1f) {
            this.registerEntityModifier(new AlphaModifier(Constants.ANIMATION_DICE, 0f, 1f));
        }
    }

    public void hide(){
        if(this.getAlpha()!=0f) {
            this.registerEntityModifier(new AlphaModifier(Constants.ANIMATION_DICE, 1f, 0f));
        }
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }
}
