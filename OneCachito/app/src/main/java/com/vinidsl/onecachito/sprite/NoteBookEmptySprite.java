package com.vinidsl.onecachito.sprite;



import com.vinidsl.onecachito.GameActivity;
import com.vinidsl.onecachito.manager.ResourceManager;

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
public class NoteBookEmptySprite extends Sprite{
    public NoteBookEmptySprite() {
        super(GameActivity.WORLD_WIDTH/2, GameActivity.WORLD_HEIGHT/2,
                ResourceManager.getInstance().NOTE_BOOK_EMPTY_TEXTURE_REGION,
                ResourceManager.getInstance().getVertexBuffer());
    }
}
