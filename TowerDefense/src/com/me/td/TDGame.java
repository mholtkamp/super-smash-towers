package com.me.td;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TDGame implements ApplicationListener
{
	
	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	Menu menu;
	int game_state, level;
	boolean enable_pause;
	final int MENU_STATE = 0, GAME_STATE = 1, PAUSE_STATE = 2;
	final int WIDTH = 740, HEIGHT = 400;

	@Override
	public void create()
	{	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
//		world = new World(camera);
		menu = new Menu(camera);
		game_state = MENU_STATE;
		enable_pause = true;
	}

	@Override
	public void dispose()
	{

	}

	@Override
	public void render()
	{		
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		if (game_state == MENU_STATE)
		{
			level = menu.update();
			if (level != -1)
			{
				world = new World(camera, level);
				game_state = GAME_STATE;
				world.enable_tower_spawn = false;
			}
			batch.begin();
			menu.render(batch);
			batch.end();
		}
		else if (game_state == GAME_STATE)
		{
			// PAUSE
			if (Gdx.input.isKeyPressed(Keys.P))
			{
				if (enable_pause)
				{
					game_state = PAUSE_STATE;
					enable_pause = false;
				}
			}
			else
				enable_pause = true;
			
			if (world.gameover)
			{
//				world = new World(camera);
//				world.enable_tower_spawn = false;
				menu = new Menu(camera);
				game_state = MENU_STATE;
			}
			
			world.update();

			batch.begin();
			world.render(batch);
			batch.end();
		}
		else if (game_state == PAUSE_STATE)
		{
			if (Gdx.input.isKeyPressed(Keys.P))
			{
				if (enable_pause)
				{
					game_state = GAME_STATE;
					enable_pause = false;
				}
			}
			else
				enable_pause = true;
		}
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void pause()
	{
		
	}

	@Override
	public void resume()
	{
		
	}
}
