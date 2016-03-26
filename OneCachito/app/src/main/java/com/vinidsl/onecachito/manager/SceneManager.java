package com.vinidsl.onecachito.manager;

import com.vinidsl.onecachito.scene.GameScene;
import com.vinidsl.onecachito.scene.MenuScene;

import org.andengine.input.sensor.acceleration.AccelerationData;

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
public class SceneManager {

    private static SceneManager INSTANCE;
    private int currentSceneId = -1;
    public static final int SCENE_ID_MAIN_MENU = 0;
    public static final int SCENE_ID_GAME = 1;
    private MenuScene menuScene;
    private GameScene gameScene;
    private int numberOfPlayers;

    SceneManager(){

    }

    public MenuScene getMenuScene(){
        return menuScene;
    }

    /** Return the instance of the scenes*/
    public static SceneManager getInstance()
    {
        if(INSTANCE==null) {
            INSTANCE = new SceneManager();
        }
        return INSTANCE;
    }

    public static void reset(){
        INSTANCE = null;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void changeToScene(int sceneId){
        switch (sceneId){
            case SCENE_ID_MAIN_MENU:
                menuScene = new MenuScene();
                ResourceManager.getInstance().engine.setScene(menuScene);
                break;
            case SCENE_ID_GAME:
                GameManager.reset();
                gameScene = new GameScene(numberOfPlayers);
                ResourceManager.getInstance().engine.setScene(gameScene);
                //this.sceneCallbacks = (SceneCallbacks) gameScene;
                break;
        }
        switch (currentSceneId){
            case SCENE_ID_MAIN_MENU:

                break;
            case SCENE_ID_GAME:

                break;
        }
        currentSceneId = sceneId;
    }

    public MenuScene getFirstScene(){
        menuScene = new MenuScene();
        currentSceneId = SCENE_ID_MAIN_MENU;
        return menuScene;
    }

    public void onBackPressed(){
        switch (currentSceneId){
            case SCENE_ID_MAIN_MENU:
                menuScene.onBackKeyPressed();
                break;
            case SCENE_ID_GAME:
                gameScene.onBackKeyPressed();
                break;
        }
    }

    public int getCurrentSceneId() {
        return currentSceneId;
    }

    public void accelerationChanged(AccelerationData accelerationData){
        if(currentSceneId==SCENE_ID_GAME){
            gameScene.onAccelerometerChanged(accelerationData);
        }
    }
}
