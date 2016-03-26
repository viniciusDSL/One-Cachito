package com.vinidsl.onecachito.sprite;


import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.Constants;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
public class TitleSprite extends Sprite {

    public TitleSprite() {
        super((GameActivity.WORLD_WIDTH/2), 625, ResourceManager.getInstance().TITLE_TEXTURE_REGION
                , ResourceManager.getInstance().getVertexBuffer());
    }

    public void unzip(){
        this.registerEntityModifier(new MoveModifier(Constants.ANIMATION_TEXT_DURATION,16+(GameActivity.WORLD_WIDTH/3), 625,GameActivity.WORLD_WIDTH/2, 625));
        this.registerEntityModifier(new ScaleModifier(Constants.ANIMATION_TEXT_DURATION, 0.8f, 1f));
    }

    public void zip(){
        this.registerEntityModifier(new MoveModifier(Constants.ANIMATION_TEXT_DURATION, GameActivity.WORLD_WIDTH/2, 625,16+(GameActivity.WORLD_WIDTH/3), 625));
        this.registerEntityModifier(new ScaleModifier(Constants.ANIMATION_TEXT_DURATION, 1f, 0.8f));
    }
}
