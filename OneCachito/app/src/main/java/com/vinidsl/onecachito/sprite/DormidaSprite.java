package com.vinidsl.onecachito.sprite;

import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.R;
import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.manager.SceneManager;
import com.vinidsl.onecachito.util.Constants;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
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
public class DormidaSprite extends Sprite {

    private Text winnerTitle;
    private Text winnerPlayer;

    public DormidaSprite() {
        super(GameActivity.WORLD_WIDTH / 2, GameActivity.WORLD_HEIGHT / 2,
                ResourceManager.getInstance().NOTE_BOOK_EMPTY_TEXTURE_REGION, ResourceManager.getInstance().getVertexBuffer());
        this.setY(GameActivity.WORLD_HEIGHT + this.getHeight() / 2);
        float posY = 100;
        winnerTitle = new Text((this.getWidth()/2)+30,this.getHeight()-posY, ResourceManager.getInstance().fontBig,"123456789",20, new TextOptions(HorizontalAlign.CENTER),ResourceManager.getInstance().getVertexBuffer());
        winnerTitle.setText(ResourceManager.getInstance().context.getString(R.string.dormida));
        winnerTitle.setColor(0.25490196f, 0.25490196f, 0.25490196f);
        winnerTitle.setScale(0.7f);
        this.attachChild(winnerTitle);
        posY += 120;
        winnerPlayer = new Text((this.getWidth()/2)+30,this.getHeight()-posY, ResourceManager.getInstance().fontNormal,"123456789",20, new TextOptions(HorizontalAlign.CENTER),ResourceManager.getInstance().getVertexBuffer());
        this.attachChild(winnerPlayer);
        this.setVisible(false);

    }

    public void show(){
        switch (GameManager.getInstance().currentPlayer){
            case 0:
                winnerPlayer.setText(ResourceManager.getInstance().context.getString(R.string.player_one)+"\n"
                +ResourceManager.getInstance().context.getString(R.string.winner));
                winnerPlayer.setColor(0.980392157f, 0.505882353f, 0.196078431f);
                break;
            case 1:
                winnerPlayer.setText(ResourceManager.getInstance().context.getString(R.string.player_two)+"\n"
                        +ResourceManager.getInstance().context.getString(R.string.winner));
                winnerPlayer.setColor(0.207843137f, 0.729411765f, 0.952941176f);
                break;
            case 2:
                winnerPlayer.setText(ResourceManager.getInstance().context.getString(R.string.player_three)+"\n"
                        +ResourceManager.getInstance().context.getString(R.string.winner));
                winnerPlayer.setColor(1f, 0.850980392f, 0.282352941f);
                break;
        }
        this.setVisible(true);
        this.registerEntityModifier(
                new MoveYModifier(Constants.ANIMATION_DURATION, this.getY(), GameActivity.WORLD_HEIGHT - GameActivity.WORLD_HEIGHT / 3,
                        new IEntityModifier.IEntityModifierListener() {
                            @Override
                            public void onModifierStarted(IModifier<IEntity> iModifier, IEntity entity) {

                            }

                            @Override
                            public void onModifierFinished(IModifier<IEntity> iModifier, IEntity entity) {
                                GameManager.getInstance().buttonDone.show();
                            }
                        },
                        EaseLinear.getInstance()
                ));
    }
}
