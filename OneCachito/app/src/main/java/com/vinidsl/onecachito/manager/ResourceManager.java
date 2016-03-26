package com.vinidsl.onecachito.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
public class ResourceManager {
    // instance of resources
    private static ResourceManager INSTANCE;

    // error message debug
    private final String ASSETS_ERROR_TAG = " One Cachito - assets: ";

    // bitmap field that help to load the textures
    private ITexture texture;

    // Simple Textures
    public ITextureRegion TABLE_BACKGROUND_TEXTURE_REGION;
    public ITextureRegion TITLE_TEXTURE_REGION;
    public ITextureRegion NOTE_BOOK_TEXTURE_REGION;
    public ITextureRegion NOTE_BOOK_EMPTY_TEXTURE_REGION;
    public ITextureRegion ONE_PLAYER_TEXTURE_REGION;
    public ITextureRegion TWO_PLAYERS_TEXTURE_REGION;
    public ITextureRegion THREE_PLAYERS_TEXTURE_REGION;
    public ITextureRegion NOTE_BOOK_BROKEN;

    // Animated textures
    public ITiledTextureRegion DICE_TEXTURE_REGION;
    public ITiledTextureRegion EXTRA_BUTTONS_TEXTURE_REGION;
    public ITiledTextureRegion CACHOS_TEXTURE_REGION;
    public ITiledTextureRegion TABS_TEXTURE_REGION;
    public ITiledTextureRegion MENU_BUTTON_RED;
    public ITiledTextureRegion MENU_BUTTON_YELLOW;
    public ITiledTextureRegion MENU_BUTTON_BLUE;
    public ITiledTextureRegion MENU_BUTTON_WHITE;
    public ITiledTextureRegion NEXT_TEXTURE;
    public ITiledTextureRegion MENU_BUTTON_LEADERBOARD;
    public ITiledTextureRegion MENU_BUTTON_AWARDS;
    public ITiledTextureRegion MENU_BUTTON_BORDER;
    public ITiledTextureRegion MENU_BUTTON_BORDER_LITTLE;
    public ITiledTextureRegion MENU_BUTTON_FACEBOOK;
    public ITiledTextureRegion MENU_BUTTON_TWITTER;

    public ITextureRegion textureRegionCurrentPlayer;
    public ITextureRegion textureRegionAnotherPlayer;

    // font
    public Font font;
    public Font fontNormal;
    public Font fontBig;

    public Engine engine;
    public Context context;
    public Activity activity;

    //music and sound
    public Music musicShake;
    public Music musicRoll;
    public Sound tic;

    //colors
    public org.andengine.util.adt.color.Color orange;
    public org.andengine.util.adt.color.Color yellow;
    public org.andengine.util.adt.color.Color blue;


    /**
     * Return the instance of the resources
     */
    public static ResourceManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ResourceManager();
        }
        return INSTANCE;
    }

    public static void reset() {
        INSTANCE = null;
    }

    private TextureRegion getTextureFromAsset(final String path) {
        try {
            texture = new BitmapTexture(engine.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return context.getAssets().open("gfx/" + path); // path of the image
                }
            }, TextureOptions.BILINEAR);
            texture.load();
            return TextureRegionFactory.extractFromTexture(texture);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(ASSETS_ERROR_TAG, e.toString());
        }
        return null;
    }

    private TiledTextureRegion getTiledTextureFromAsset(final String path, int rows, int columns) {
        try {
            texture = new BitmapTexture(engine.getTextureManager(), new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return context.getAssets().open("gfx/" + path); // path of the image
                }
            }, TextureOptions.BILINEAR);
            texture.load();
            return TextureRegionFactory.extractTiledFromTexture(texture, rows, columns);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(ASSETS_ERROR_TAG, e.toString());
        }
        return null;
    }

    private Font loadFont(String fontName, int fontSize) {
        FontFactory.setAssetBasePath("font/");
        Font fontRes;
        fontRes = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getAssets(), fontName
                , fontSize, true, Color.WHITE);
        fontRes.load();
        return fontRes;
    }

    /**
     * Load the first images to show the main menu
     */
    public synchronized void loadBasicResources() {
        TABLE_BACKGROUND_TEXTURE_REGION = getTextureFromAsset("table_background.png");
        TITLE_TEXTURE_REGION = getTextureFromAsset("title.png");
        NOTE_BOOK_TEXTURE_REGION = getTextureFromAsset("note_book_paper.png");
        NOTE_BOOK_EMPTY_TEXTURE_REGION = getTextureFromAsset("note_book_paper_empty.png");
        NOTE_BOOK_BROKEN = getTextureFromAsset("note_book_broken.png");
        ONE_PLAYER_TEXTURE_REGION = getTextureFromAsset("1player.png");
        TWO_PLAYERS_TEXTURE_REGION = getTextureFromAsset("2players.png");
        THREE_PLAYERS_TEXTURE_REGION = getTextureFromAsset("3players.png");
        DICE_TEXTURE_REGION = getTiledTextureFromAsset("dice.png", 6, 1);
        CACHOS_TEXTURE_REGION = getTiledTextureFromAsset("cachos.png", 3, 1);
        EXTRA_BUTTONS_TEXTURE_REGION = getTiledTextureFromAsset("extra_buttons.png", 4, 1);
        TABS_TEXTURE_REGION = getTiledTextureFromAsset("guides.png", 4, 2);
        MENU_BUTTON_RED = getTiledTextureFromAsset("menu_button_red.png", 3, 1);
        MENU_BUTTON_YELLOW = getTiledTextureFromAsset("menu_button_yellow.png", 3, 1);
        MENU_BUTTON_BLUE = getTiledTextureFromAsset("menu_button_blue.png", 3, 1);
        MENU_BUTTON_WHITE = getTiledTextureFromAsset("menu_button_white.png", 3, 1);
        MENU_BUTTON_AWARDS = getTiledTextureFromAsset("awards_button.png", 3, 1);
        MENU_BUTTON_LEADERBOARD = getTiledTextureFromAsset("leaderboard_button.png", 3, 1);
        NEXT_TEXTURE = getTiledTextureFromAsset("next_button.png", 3, 1);
        MENU_BUTTON_BORDER = getTiledTextureFromAsset("menu_button_border.png", 3, 1);
        MENU_BUTTON_BORDER_LITTLE = getTiledTextureFromAsset("menu_button_border_little.png", 3, 1);
        MENU_BUTTON_FACEBOOK = getTiledTextureFromAsset("facebook_button.png", 3, 1);
        MENU_BUTTON_TWITTER = getTiledTextureFromAsset("twitter_button.png", 3, 1);
        font = loadFont("04b.TTF", 30);
        fontNormal = loadFont("04b.TTF", 40);
        fontBig = loadFont("04b.TTF", 75);
        orange = new org.andengine.util.adt.color.Color(0.980392157f, 0.505882353f, 0.196078431f);
        blue = new org.andengine.util.adt.color.Color(0.207843137f, 0.729411765f, 0.952941176f);
        yellow = new org.andengine.util.adt.color.Color(1f, 0.850980392f, 0.282352941f);
    }

    /**
     * Load the images for the game
     */
    public synchronized void loadGameImages() {

    }

    public synchronized void loadAudio() {
        SoundFactory.setAssetBasePath("sfx/");
        try {
            tic = SoundFactory.createSoundFromAsset(
                    engine.getSoundManager(), context, "tic.ogg");
        } catch (final IOException e) {
            Debug.e(e);
        }
        MusicFactory.setAssetBasePath("sfx/");
        try {
            musicShake = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "diceShuffle.ogg");
            musicRoll = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "diceThrow.ogg");
        } catch (final IOException e) {
            Debug.e(e);
        }
    }

    /**
     * Return the engine instance of the game
     */
    public VertexBufferObjectManager getVertexBuffer() {
        return engine.getVertexBufferObjectManager();
    }

    public void setImageToAnotherUser(final String userImageUrl) {
        new Thread(new Runnable() {
            public void run() {
                new GetImageUser().execute(userImageUrl);
            }
        }).start();
    }

    public void setMyUserImage(final String userImageUrl) {
        new Thread(new Runnable() {
            public void run() {
                new GetMyImageUser().execute(userImageUrl);
            }
        }).start();
    }


    public class GetImageUser extends AsyncTask<String, Void, Sprite> {

        @Override
        protected Sprite doInBackground(final String... params) {
            try {
                ITexture mTexture = new BitmapTexture(engine.getTextureManager(), new IInputStreamOpener() {
                    @Override
                    public InputStream open() throws IOException {

                        URL url = new URL(params[0]);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        return new BufferedInputStream(input);
                    }
                });
                mTexture.load();
                textureRegionAnotherPlayer = TextureRegionFactory.extractFromTexture(mTexture);
                if (GameManager.getInstance() != null && GameManager.getInstance().players != null &&
                        GameManager.getInstance().players.length > 0 && engine != null) {
                    IEntity entity = GameManager.getInstance().players[1].tabSprite;
                    Sprite user = new Sprite(0, 0, textureRegionAnotherPlayer, engine.getVertexBufferObjectManager());
                    user.setWidth(entity.getHeight() - 27);
                    user.setHeight(entity.getHeight() - 27);
                    user.setX(-13 + entity.getWidth() / 2);
                    user.setY(+4 + entity.getHeight() / 2);
                    return user;
                }

            } catch (IOException e) {
                Debug.e(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Sprite sprite) {
            super.onPostExecute(sprite);
            GameManager.getInstance().players[1].tabSprite.attachChild(sprite);
        }
    }

    public class GetMyImageUser extends AsyncTask<String, Void, Sprite> {

        @Override
        protected Sprite doInBackground(final String... params) {
            try {
                ITexture mTexture = new BitmapTexture(engine.getTextureManager(), new IInputStreamOpener() {
                    @Override
                    public InputStream open() throws IOException {

                        URL url = new URL(params[0]);

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        return new BufferedInputStream(input);
                    }
                });
                mTexture.load();
                textureRegionAnotherPlayer = TextureRegionFactory.extractFromTexture(mTexture);
                if (GameManager.getInstance() != null && GameManager.getInstance().players != null &&
                        GameManager.getInstance().players.length > 0 && engine != null) {
                    IEntity entity = GameManager.getInstance().players[0].tabSprite;
                    Sprite user = new Sprite(0, 0, textureRegionAnotherPlayer, engine.getVertexBufferObjectManager());
                    user.setWidth(entity.getHeight() - 27);
                    user.setHeight(entity.getHeight() - 27);
                    user.setX(-13 + entity.getWidth() / 2);
                    user.setY(+4 + entity.getHeight() / 2);
                    return user;
                }
            } catch (IOException e) {
                Debug.e(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Sprite sprite) {
            super.onPostExecute(sprite);
            GameManager.getInstance().players[0].tabSprite.attachChild(sprite);
        }
    }

}
