package com.me.td;

import util.Database;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import enums.Level;
import enums.State;


public class TDGame implements ApplicationListener
{
//	private final int MENU_STATE = 0, GAME_STATE = 1, PAUSE_STATE = 2;
	private final int WIDTH = 740, HEIGHT = 400;
	
	private SpriteBatch batch;
	private ShapeRenderer shape_renderer;
	private OrthographicCamera camera;
	private World world;
	private Menu menu;
	private State state;
	private Level level;
//	private boolean enable_pause;

	@Override
	public void create()
	{	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		shape_renderer = new ShapeRenderer();
		menu = new Menu(camera);
		state = State.MENU;
//		enable_pause = true;
	}

	@Override
	public void dispose()
	{
		Database.dispose();
	}

	@Override
	public void render()
	{		
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		shape_renderer.setProjectionMatrix(camera.combined);

		if (state == State.MENU)
		{
			level = menu.update();
			if (level != Level.NONE)
			{
				world = new World(camera, level, menu.getDiff());
				state = State.GAME;
			}

			menu.render(batch);
		}
		else if (state == State.GAME)
		{
			world.update();
			world.render(batch, shape_renderer);
			
/*			// PAUSE
			if (Gdx.input.isKeyPressed(Keys.P))
			{
				if (enable_pause)
				{
					state = State.PAUSE;
					enable_pause = false;
				}
			}
			else
				enable_pause = true;
*/

			if (world.gameover)
			{
//				menu = new Menu(camera);
				world = null;
				if (OptionsMenu.restart)
				{
					OptionsMenu.restart = false;
					world = new World(camera, level, menu.getDiff());
//					game_state = GAME_STATE;
//					menu.menu_state = 2;
				}
				else
				{
//					menu = new Menu(camera);
					menu.restart();
					state = State.MENU;
					menu.ssb_theme.play();
				}
			}
		}
/*		else if (state == State.PAUSE)
		{
			if (Gdx.input.isKeyPressed(Keys.P))
			{
				if (enable_pause)
				{
					state = State.GAME;
					enable_pause = false;
				}
			}
			else
				enable_pause = true;
		}
*/
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
