package com.me.td;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private AssetManager manager;
	private LoadBar load;
	private boolean enable_pause;
	private Texture tex34;
	public Options options;

	@Override
	public void create()
	{	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		shape_renderer = new ShapeRenderer();
		manager = new AssetManager();
		load();
		state = State.MENU;	
		tex34 = new Texture(Gdx.files.internal("data/triangle.png"));
		load = new LoadBar(manager);
		enable_pause = true;
		options = new Options();
	}

	@Override
	public void dispose()
	{

	}

	@Override
	public void render()
	{		
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		shape_renderer.setProjectionMatrix(camera.combined);
		if (!manager.update())
		{
			Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			load.update();
			batch.begin();
			load.render(batch);
			batch.end();
		}
		else
		{
			if (menu == null)
				menu = new Menu(camera,manager);
			if (state == State.MENU)
			{
				level = menu.update();
				if (level != Level.NONE)
				{	
					world = new World(camera, level, menu.getDiff(),manager);
					state = State.GAME;
				}

				menu.render(batch);
			}
			else if (state == State.GAME)
			{
				world.update();
				world.render(batch, shape_renderer);

				// PAUSE
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


				if (world.gameover)
				{
//					menu = new Menu(camera);
					world = null;
					if (OptionsMenu.restart)
					{
						OptionsMenu.restart = false;
						world = new World(camera, level, menu.getDiff(), manager);
//						game_state = GAME_STATE;
//						menu.menu_state = 2;
					}
					else
					{
//						menu = new Menu(camera);
						menu.restart();
						state = State.MENU;
						menu.ssb_theme.play();
					}
				}
			}
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

	private void load()
	{
		// Map Textures
		manager.load("data/white.png",Texture.class);
		manager.update();
		manager.finishLoading();
		manager.load("data/maps/mario_map_6.png",Texture.class);
		manager.load("data/maps/pokemon_map.png",Texture.class);
		manager.load("data/maps/GalagaMap.png",Texture.class);
		manager.load("data/maps/zelda.png",Texture.class);
		manager.load("data/maps/zelda_map.png",Texture.class);
		manager.load("data/maps/galaga_map.png",Texture.class);

		// Tower Textures
		manager.load("data/towers/tower.png",Texture.class);
//		public static final Texture fireTower1 = new Texture(Gdx.files.internal("data/towers/fire_tower_1.png"));
//		public static final Texture fireTower2 = new Texture(Gdx.files.internal("data/towers/fire_tower_2.png"));
		manager.load("data/towers/charm.png",Texture.class);
		manager.load("data/towers/charmealon.png",Texture.class);
		manager.load("data/towers/charz.png",Texture.class);
		manager.load("data/towers/squirtle.png",Texture.class);
		manager.load("data/towers/warturtle.png",Texture.class);
		manager.load("data/towers/blastoise.png",Texture.class);
		manager.load("data/towers/hammer_bros_left_idle.png",Texture.class);
		manager.load("data/towers/hammer_bros_right_idle.png",Texture.class);
		manager.load("data/towers/Abra.png",Texture.class);
		manager.load("data/towers/kadabra.png",Texture.class);
		manager.load("data/towers/alakazam.png",Texture.class);
		manager.load("data/bullets/leaf.png",Texture.class);
		manager.load("data/towers/bulba.png",Texture.class);
		manager.load("data/towers/ivy.png",Texture.class);
		manager.load("data/towers/venus.png",Texture.class);
		manager.load("data/towers/Galaga_Flagship.png", Texture.class);
		manager.load("data/towers/ship.png", Texture.class);
		manager.load("data/towers/forceField.png", Texture.class);
		manager.load("data/towers/galagaTower.png", Texture.class);
		manager.load("data/towers/galagaTowerUpgrade.png", Texture.class);
		manager.load("data/towers/galagaTower2.png", Texture.class);
		manager.load("data/towers/galagaTower2Upgrade.png", Texture.class);
		manager.load("data/towers/GalagaTower3.png", Texture.class);
		manager.load("data/towers/GalagaTower3Upgrade.png", Texture.class);
		manager.load("data/towers/GalagaTower4.png", Texture.class);
		manager.load("data/towers/GalagaTower4Upgrade.png", Texture.class);
		manager.load("data/towers/castle1.png", Texture.class);
		manager.load("data/towers/towerUpgraded.png", Texture.class);
		manager.load("data/towers/firetower.png", Texture.class);
		manager.load("data/towers/firetower2.png", Texture.class);
		manager.load("data/towers/fireUpgrade.png", Texture.class);
		manager.load("data/towers/fireUpgrade2.png", Texture.class);
		manager.load("data/towers/upgradedBroleft.png", Texture.class);
		manager.load("data/towers/upgradedBroright.png", Texture.class);
		manager.load("data/towers/BombTower.png", Texture.class);
		manager.load("data/towers/BombTowerUpgrade.png", Texture.class);
		manager.load("data/towers/BoomerangTower.png", Texture.class);
		manager.load("data/towers/BoomerangTowerUpgrade.png", Texture.class);
		manager.load("data/towers/SlingShotTower.png", Texture.class);
		manager.load("data/towers/SlingShotTowerUpgrade.png", Texture.class);
		manager.load("data/towers/SwordTower.png", Texture.class);
		manager.load("data/towers/SwordTowerUpgrade.png", Texture.class);
		
		// Bullet Textures
		manager.load("data/bullets/bullet.png",Texture.class);
		manager.load("data/bullets/fire_ball_1.png",Texture.class);
		manager.load("data/bullets/fire_ball_2.png",Texture.class);
		manager.load("data/bullets/hammer/hammer_left_up.png",Texture.class);
		manager.load("data/bullets/hammer/hammer_left_left.png",Texture.class);
		manager.load("data/bullets/hammer/hammer_left_down.png",Texture.class);
		manager.load("data/bullets/hammer/hammer_left_right.png",Texture.class);
		manager.load("data/bullets/pball1.png",Texture.class);
		manager.load("data/bullets/pball2.png",Texture.class);
		manager.load("data/bullets/water_ball.png",Texture.class);
		manager.load("data/bullets/waterExp.png",Texture.class);

		// Enemy Textures
		manager.load("data/enemies/mushroom.png",Texture.class);
		manager.load("data/enemies/bowserR1.png",Texture.class);
		manager.load("data/enemies/bowserR2.png",Texture.class);
		manager.load("data/enemies/goomba1.png",Texture.class);
		manager.load("data/enemies/goomba2.png",Texture.class);
		manager.load("data/enemies/koopa_right_down.png",Texture.class);
		manager.load("data/enemies/koopa_right_up.png",Texture.class);
		manager.load("data/enemies/shy_guy_right_1.png",Texture.class);
		manager.load("data/enemies/shy_guy_right_2.png",Texture.class);
		manager.load("data/enemies/arcanine_right_1.png",Texture.class);
		manager.load("data/enemies/arcanine_right_2.png",Texture.class);
		manager.load("data/enemies/geodude_right_1.png",Texture.class);
		manager.load("data/enemies/geodude_right_2.png",Texture.class);
		manager.load("data/enemies/tentacool_right_1.png",Texture.class);
		manager.load("data/enemies/tentacool_right_2.png",Texture.class);
		manager.load("data/enemies/voltorb_right_1.png",Texture.class);
		manager.load("data/enemies/voltorb_right_2.png",Texture.class);
		manager.load("data/enemies/weedle_right_1.png",Texture.class);
		manager.load("data/enemies/weedle_right_2.png",Texture.class);




		// UI Textures
		manager.load("data/health_bar_max.png",Texture.class);
		manager.load("data/greenfade.png",Texture.class);
		manager.load("data/redfade.png",Texture.class);
//		manager.load("data/health_bar_safe.png",Texture.class);
//		manager.load("data/health_bar_unsafe.png",Texture.class);
		manager.load("data/hover.png",Texture.class);
		manager.load("data/textures/heart0.png",Texture.class);
		manager.load("data/textures/heart25.png",Texture.class);
		manager.load("data/textures/heart50.png",Texture.class);
		manager.load("data/textures/heart75.png",Texture.class);
		manager.load("data/textures/heart100.png",Texture.class);
		manager.load("data/coin.png",Texture.class);
		manager.load("data/pause_button.png",Texture.class);
		manager.load("data/sell.png",Texture.class);
		manager.load("data/upgrade.png",Texture.class);
		manager.load("data/bgoverlay.png",Texture.class);
		manager.load("data/pausemenu.png",Texture.class);
		manager.load("data/mario_tower_select.png",Texture.class);
		manager.load("data/pokemon_tower_select.png",Texture.class);
		manager.load("data/pokeMenu.png",Texture.class);
		manager.load("data/selected.png",Texture.class);
		manager.load("data/delete.png",Texture.class);




		// Menu Textures
		manager.load("data/menubg.png",Texture.class);
		manager.load("data/black_bg.png",Texture.class);
		manager.load("data/diffbg.png",Texture.class);
		manager.load("data/levelbg.png",Texture.class);
		manager.load("data/easy.png",Texture.class);
		manager.load("data/medium.png",Texture.class);
		manager.load("data/hard.png",Texture.class);

		manager.load("data/menu_start.png",Texture.class);
		manager.load("data/menu_instr.png",Texture.class);
		manager.load("data/options.png",Texture.class);
		manager.load("data/instr_back.png",Texture.class);
		manager.load("data/maps/mario_map_3.png",Texture.class);
		manager.load("data/0_stars.png",Texture.class);
		manager.load("data/1_star.png",Texture.class);
		manager.load("data/2_stars.png",Texture.class);
		manager.load("data/3_stars.png",Texture.class);
		manager.load("data/speaker_on.png",Texture.class);
		manager.load("data/speaker_off.png",Texture.class);



		// Sounds
		manager.load("sounds/mario.mp3",Music.class);
		manager.load("sounds/pokemon.mp3",Music.class);
		manager.load("sounds/super_smash_bros_theme.mp3",Music.class);
		manager.load("sounds/firework.mp3",Sound.class);
		manager.load("sounds/congratulations.mp3",Sound.class);
		manager.load("sounds/zeldaOverworld.mp3", Music.class);
		manager.load("sounds/GameOverZelda.mp3", Sound.class);

		// Bitmap Fonts
		manager.load("data/nint.fnt",BitmapFont.class);//("data/nint_0.png"), false);
		manager.load("data/snes.fnt",BitmapFont.class);//, Gdx.files.internal("data/snes_0.png"), false);
		
	}
	@Override
	public void resume()
	{

	}

}
