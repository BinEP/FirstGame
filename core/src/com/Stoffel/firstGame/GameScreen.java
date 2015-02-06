package com.Stoffel.firstGame;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final MyGame game;
	
	public OrthographicCamera camera;
	public Texture bucketImage;
	public Texture waterDropImage;
	public Rectangle bucket;
	public Rectangle drop;
	public Sound drip;
	public Music rain;
	public Vector3 touchPos = new Vector3();
	public Array<Rectangle> rainDrops;
	public long lastDropTime;
	public int dropsCollected;
	
	public GameScreen(final MyGame gam) {
		
		this.game = gam;
		
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		waterDropImage = new Texture(Gdx.files.internal("droplet.png"));
		
		bucket = new Rectangle(800 / 2 - 64 / 2, 20, 64, 64);
		
		drip = Gdx.audio.newSound(Gdx.files.internal("water-droplet-1.mp3"));
		rain = Gdx.audio.newMusic(Gdx.files.internal("rain-01.mp3"));
		
		rain.setLooping(true);
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		
		rainDrops = new Array<Rectangle>();
		spawnRaindrop();
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		rain.play();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		
		game.font.draw(game.batch, "Drops Collected" + dropsCollected, 0, 480);
		
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		for (Rectangle drop : rainDrops) {
			
			game.batch.draw(waterDropImage, drop.x, drop.y);
			
		}
		game.batch.end();
		
		if (Gdx.input.isTouched()) {
			
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
			
		}
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
		
		if (bucket.x > 800) bucket.x = 800 - 64;
		if (bucket.x < 0) bucket.x = 0;
		
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		
		Iterator<Rectangle> iter = rainDrops.iterator();
		while (iter.hasNext()) {
			
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 64 < 0) iter.remove();
			if (raindrop.overlaps(bucket)) {
				dropsCollected++;
				drip.play();
				iter.remove();
			}
		}
		

	}
	
public void spawnRaindrop() {
		
		Rectangle raindrop = new Rectangle();
		raindrop.set(MathUtils.random(0, 800 - 64), 480, 64, 64);
		
		rainDrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		bucketImage.dispose();
		waterDropImage.dispose();
		drip.dispose();
		rain.dispose();
	}

}
