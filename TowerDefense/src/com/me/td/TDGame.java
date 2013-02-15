package com.me.td;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TDGame implements ApplicationListener {
	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	
	
	@Override
	public void create() {	
		 camera = new OrthographicCamera();
	     camera.setToOrtho(false, 800, 480);
	     batch = new SpriteBatch();
	     world = new World(camera);
	     

	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {		
	      Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
	      Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	      camera.update();
	      batch.setProjectionMatrix(camera.combined);
	      
	      world.update();
	      
	      batch.begin();
	      world.render(batch);
	      batch.end();
	      
	      
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
