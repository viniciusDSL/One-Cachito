package com.vinidsl.onecachito;

import android.app.AlertDialog;
import android.content.Intent;

import com.vinidsl.onecachito.manager.GameManager;
import com.vinidsl.onecachito.manager.ResourceManager;
import com.vinidsl.onecachito.manager.SceneManager;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

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
public class GameActivity extends BaseGameActivity implements IAccelerationListener {

    //this is the width and height of the world resolution
    public static final float WORLD_WIDTH = 480;
    public static final float WORLD_HEIGHT = 800;
    private static final int LEADERBOARD_INTENT = 200;
    private static final int ACHIEVEMENTS_INTENT = 201;

    private String TAG = "ONE CACHITO ";


    private AlertDialog mAlertDialog;


    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera camera = new Camera(0,0,WORLD_WIDTH,WORLD_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.PORTRAIT_FIXED,new RatioResolutionPolicy(WORLD_WIDTH, WORLD_HEIGHT),camera);
        //engineOptions.getRenderOptions().setDithering(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.getAudioOptions().setNeedsSound(true);
        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback onCreateResourcesCallback) throws IOException {
        ResourceManager.getInstance().engine = mEngine;
        ResourceManager.getInstance().context = getApplicationContext();
        ResourceManager.getInstance().activity = this;
        ResourceManager.getInstance().loadBasicResources();
        ResourceManager.getInstance().loadAudio();
        onCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback onCreateSceneCallback) throws IOException {
        onCreateSceneCallback.onCreateSceneFinished(SceneManager.getInstance().getFirstScene());
    }

    @Override
    public void onPopulateScene(Scene scene, OnPopulateSceneCallback onPopulateSceneCallback) throws IOException {
        onPopulateSceneCallback.onPopulateSceneFinished();
    }

    @Override
    public void onBackPressed() {
        if(SceneManager.getInstance().getCurrentSceneId()== SceneManager.SCENE_ID_MAIN_MENU
            && !SceneManager.getInstance().getMenuScene().multiplayerVisible){
                SceneManager.reset();
                GameManager.reset();
                ResourceManager.reset();
            super.onBackPressed();
        }else{
            SceneManager.getInstance().onBackPressed();
        }
    }

    @Override
    public void onAccelerationAccuracyChanged(AccelerationData accelerationData) {
        //nothing to do here
    }

    @Override
    public void onAccelerationChanged(AccelerationData accelerationData) {
        SceneManager.getInstance().accelerationChanged(accelerationData);
    }

    @Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
        this.enableAccelerationSensor(this);
    }

    @Override
    public void onPauseGame() {
        super.onPauseGame();
        this.disableAccelerationSensor();
    }


    public void goToRules(){
        Intent intent = new Intent(this,RulesActivity.class);
        this.startActivity(intent);
    }

}
