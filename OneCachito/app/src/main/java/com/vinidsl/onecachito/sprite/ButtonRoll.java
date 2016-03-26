package com.vinidsl.onecachito.sprite;

import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.R;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.util.Paint;
import com.vinidsl.onecachito.util.StringHelper;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;

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
public class ButtonRoll extends ButtonSprite {

    private Text text;

    public ButtonRoll() {
        super(140+GameActivity.WORLD_WIDTH/2, 60, ResourceManager.getInstance().MENU_BUTTON_BORDER_LITTLE, ResourceManager.getInstance().getVertexBuffer());
        text = new Text(this.getWidth()/2,this.getHeight()/2,ResourceManager.getInstance().font,
                StringHelper.getStringFromResource(R.string.roll),ResourceManager.getInstance().getVertexBuffer());
        Paint.gray(text);
        this.attachChild(text);
        this.hide();
    }

    public void show(){
        if(GameManager.getInstance().totalTurns<11) {
            this.setEnabled(true);
            this.setVisible(true);
        }
    }

    public void hide(){
        this.setEnabled(false);
        this.setVisible(false);
    }
}