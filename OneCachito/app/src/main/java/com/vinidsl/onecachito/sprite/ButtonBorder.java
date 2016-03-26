package com.vinidsl.onecachito.sprite;

import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.R;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.Constants;
import com.vinidsl.onecachito.util.Paint;
import com.vinidsl.onecachito.util.StringHelper;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.ease.EaseLinear;

import javax.microedition.khronos.opengles.GL10;

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
public class ButtonBorder extends ButtonSprite {

    private Text text;

    public ButtonBorder() {
        super(GameActivity.WORLD_WIDTH/2, 270, ResourceManager.getInstance().MENU_BUTTON_BORDER, ResourceManager.getInstance().getVertexBuffer());
        text = new Text(this.getWidth()/2,this.getHeight()/2,ResourceManager.getInstance().fontNormal,
                StringHelper.getStringFromResource(R.string.done),ResourceManager.getInstance().getVertexBuffer());
        Paint.gray(text);
        this.attachChild(text);

    }

    public void show(){
        this.setEnabled(true);
        this.setVisible(true);
    }
}

