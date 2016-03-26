package com.vinidsl.onecachito.util;

import org.andengine.entity.IEntity;
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
public class Paint {

    public static void gold(IEntity entity){
        entity.setColor(0.8313725f,0.6666667f,0f);
    }

    public static void gray(IEntity entity){
        entity.setColor( 0.25490196f, 0.25490196f, 0.25490196f);
    }

    public static void grayHide(IEntity entity){
        entity.setColor( 0.925490196f,0.925490196f,0.925490196f);
    }

    public static void white(IEntity entity) {
        entity.setColor( 1f,1f,1f);
    }
}
