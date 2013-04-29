package com.me.td;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import enemies.*;
import enums.EnemyEnum;
import enums.Level;

import java.util.ArrayList;
import java.util.Queue;

import maps.*;
import towers.*;
import enums.TowerEnum;



public class World
{
	
	public static float volume = 1.0f;
	public static boolean mute;
	
	// constants
	//final int MUSHROOM = 0, GOOMBA = 1, KOOPA = 2, BOWSER = 3, SHYGUY = 4, ARCANINE = 5, GEODUDE = 6, WEEDLE = 7, VOLTORB = 8, TENTACOOL = 9, LAPRAS = 10, ONIX = 11;
	final int CASTLE = 3, HAMMER = 4, FLOWER = 5, FIRE = 6, WATER = 7, GRASS = 8, PSYCHIC = 9, BOMB = 10, BOOMERANG = 11, SLINGSHOT = 12, SWORD = 13,
			   G1 = 14, G2 = 15, G3 = 16, G4 = 17;
	final int NONE = -4, PAUSE = -3, SELL = -2, UPGRADE = -1;
	final int ENEMY_COUNT = 22, TOWER_COUNT = 15, TIME_BETWEEN_WAVES = 10;

	final int GRID_WIDTH = 40, GRID_HEIGHT = 40;
	final int BAR_WIDTH = 300, BAR_HEIGHT = 16, BAR_X = 5, BAR_Y = 375;
	final int SAFE_HEALTH = 30;

	// variables
	int health, gold, wave_number, current_tower, current_enemy, time_between_waves;
	float difficulty;
	
	//EnemyEnum current_enemy;
	float current_range;
	Map map;
	TowerSelect tower_select;
	OptionsMenu options_menu;
	Queue<Enemy> wave;
	ArrayList<Enemy> enemies;
	Tower[][] tower_grid;
	OrthographicCamera camera;
	BitmapFont font, katana_font;
	Texture health_bar_max, health_bar_unsafe, health_bar_safe, hover, coin, heart0, heart25, heart50, heart75, heart100;
//	Texture heart0, heart25, heart50, heart75, heart100;
	Sound congratulations;
	Vector3 touch_pos, circle_pos;
	boolean enable_enemy_spawn, enable_tower_spawn, gameover, timeKeeper, enable_enemy_switch,
		enable_tower_switch, enable_gold, congrats_played, enable_selling, selling_state, upgrade_state,
		pause_state, drawOptionMenu, enable_killing, DEBUG, spawn, wrote_to_file;
	long prevTime, time;
	Timer timer;
	EnableSpawn enable_spawn;
	Level level;
	private AssetManager manager;
	private FileManager file_manager;

	public World(OrthographicCamera camera, Level level, float difficulty, AssetManager manager)
	{
				
		DEBUG = true;
		
		// SELECT MAP
		this.manager = manager;
		file_manager = new FileManager();
		this.level = level;
		if (level == Level.MARIO)
			map = new MarioMap(difficulty,manager);
		else if (level == Level.POKEMON)
			map = new PokemonMap(difficulty,manager);
		else if (level == Level.ZELDA)
			map = new ZeldaMap(difficulty, manager);
		else if (level == Level.GALAGA)
			map = new GalagaMap(difficulty, manager);
		
		this.difficulty = difficulty;
		health = 100;
		gold = 1000;
		wave_number = 0;
		time = 0;
		prevTime = 0;
		time_between_waves = 10000;
		wave = map.getWave(0);
		current_tower = CASTLE;
//		map = new PokemonMap();
		tower_select = new TowerSelect(camera,level,manager);
		options_menu = new OptionsMenu(camera, level.index,manager);
		wave = map.getWave(0);
//		current_tower = BASIC_TOWER;	// we are placing this type of Tower
		if(level == Level.MARIO)
			current_tower = TowerEnum.CASTLE.index;
		else if(level == Level.POKEMON)
			current_tower = TowerEnum.FIRE.index;
		else if(level == Level.ZELDA)
			current_tower = TowerEnum.BOMB.index;
		else if(level == Level.GALAGA)
			current_tower = TowerEnum.G1.index;
		else
			current_tower = TowerEnum.CASTLE.index;
		current_range = create_tower(current_tower, 0, 0).getRange();
		current_enemy = 0;//EnemyEnum.MUSHROOM;	// we are spawning this type of Enemy
		gameover = false;
		timeKeeper = true;
		spawn = false;
		wrote_to_file = false;
		enable_enemy_spawn = true;
		enable_tower_spawn = false;
		enable_enemy_switch = true;
		enable_tower_switch = true;
		enable_gold = true;
		enable_selling = true;
		enable_killing = true;
		selling_state = false;
		upgrade_state = false;
		pause_state = false;
		drawOptionMenu = false;
		enemies = new ArrayList<Enemy>();
		this.camera = camera;
		font = manager.get("data/nint.fnt");
		katana_font = manager.get("data/snes.fnt");
		// HEALTH BAR
		health_bar_max = manager.get("data/health_bar_max.png");
		health_bar_unsafe = manager.get("data/redfade.png");
		health_bar_safe = manager.get("data/greenfade.png");
		hover = manager.get("data/hover.png");
		coin = manager.get("data/coin.png");
		heart0 = new Texture("data/textures/heart0.png");
		heart25 = new Texture("data/textures/heart25.png");
		heart50 = new Texture("data/textures/heart50.png");
		heart75 = new Texture("data/textures/heart75.png");
		heart100 = new Texture("data/textures/heart100.png");
		congratulations = Gdx.audio.newSound(Gdx.files.internal("sounds/congratulations.mp3"));
		congrats_played = false;
		touch_pos = new Vector3();
		circle_pos = new Vector3();
		timer = new Timer();
		enable_spawn = new EnableSpawn();
		timer.scheduleTask(enable_spawn, 2.5f);
//		timer.schedule(spawn_enemy(), 3, )

		tower_grid = new Tower[map.getHeight()/GRID_HEIGHT][map.getWidth()/GRID_WIDTH];
//		System.out.println("tower_grid: " + tower_grid.length + " " + tower_grid[0].length);

	}

	public void render(SpriteBatch batch, ShapeRenderer shape_renderer)
	{
		batch.begin();
		
		// render map
		map.render(batch);
		
		// render tower select
		tower_select.render(batch);

		// render enemies
		for (int i = 0; i < enemies.size(); i++)
		{
			enemies.get(i).render(batch, pause_state);
		}

		// render towers and bullets
		for (int i = 0; i < tower_grid.length; i++)
		{
			for (int j = 0; j < tower_grid[i].length; j++)
			{
				if (tower_grid[i][j] != null)
					tower_grid[i][j].render(batch);
					/*font.setScale((float)0.6);
					font.setColor(1.0f, 0.0f, 1.0f, 1.0f);
					font.draw(batch, "lvl "+tower_grid[i][j].getLevel(), tower_grid[i][j].getX(), tower_grid[i][j].getY() + 10);
					*/
			}
		}
		

		/**
		 * UI
		 */
		if (health > 0 && !(wave_number >= map.numWaves() && enemies.size() == 0))
		{// game is not over - we still have health and there are still enemies on the field
			
			// dimensions for health bars
			int bar_width = health*BAR_WIDTH/100;
			if(level == Level.ZELDA)
			{
				life(batch, BAR_X-30, BAR_Y);
				font.setColor(1.0f, 1.0f, 1.0f, 1.0f); // white
				font.draw(batch, "Wave: "+(wave_number+1)+"/"+map.numWaves(), 390, 388);
			}
			else
			{
				// back black bar
				batch.draw(health_bar_max, BAR_X+7, BAR_Y, BAR_WIDTH, BAR_HEIGHT);
	
				// choose between green bar and red health bar
				if (health > SAFE_HEALTH)
					batch.draw(health_bar_safe, BAR_X, BAR_Y, bar_width, BAR_HEIGHT);
				else
					batch.draw(health_bar_unsafe, BAR_X, BAR_Y, bar_width, BAR_HEIGHT);
				
				font.setScale(1);
				
				font.setColor(1.0f, 1.0f, 1.0f, 1.0f); // white letters for health
				font.draw(batch, " Castle Health: "+health, BAR_X+30, BAR_Y+13);
				
				font.setColor(0.16f, 0.14f, 0.13f, 1.0f); // ivoryblack
				if(level == Level.GALAGA)
					font.setColor(1.0f, 1.0f, 1.0f, 1.0f); // white
				
				font.draw(batch, "Wave: "+(wave_number+1)+"/"+map.numWaves(), 350, 388);
			}
			
			
//			font.draw(batch, "Enemy Count: "+enemies.size(), 450, 465);
//			font.draw(batch, "Tower: "+tower_name(current_tower), 5, 370);
//			font.draw(batch, "Enemy Health: "+(enemies.size()!=0 ? enemies.get(0).getHealth() : "-"), 1, 439);
//			font.draw(batch, "Incoming Enemy: "+(wave.isEmpty() ? "" : wave.peek().getName()), 1, 439);
//			font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 650, 465);
			
			// gold letters for gold
			font.setColor(0.16f, 0.14f, 0.13f, 1.0f); // ivoryblack
			batch.draw(coin, 530, 330);
//			System.out.println(font.getBounds("" + gold).width);
			font.draw(batch, ""+gold, 560 - font.getBounds("" + gold).width / 2, 365);
			
			if (drawOptionMenu)
				options_menu.render(batch);
			else
			{
				// testing location to represent current tile
				// TODO change it to a box representing the current tile
				batch.draw(hover, (int)(Gdx.input.getX()/GRID_WIDTH)*GRID_WIDTH, map.getHeight()-((int)(Gdx.input.getY()/GRID_HEIGHT)*GRID_HEIGHT + GRID_HEIGHT));


				// HEARTS
				// batch.draw(blackdot, 25, 25, 200, 30);
				// batch.draw(reddot, 25, 25, 2*health, 30);
				// life(batch, 642, 450);	// batch.draw(heart0, 25, 25);

				// RANGE CIRCLE
				circle_pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(circle_pos);
				circle_pos.x = ((int)(circle_pos.x / GRID_WIDTH)) * GRID_WIDTH + GRID_WIDTH / 2;
				circle_pos.y = ((int)(circle_pos.y / GRID_HEIGHT)) * GRID_HEIGHT + GRID_HEIGHT / 2;

				shape_renderer.begin(ShapeType.Circle);
				shape_renderer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
				shape_renderer.circle(circle_pos.x, circle_pos.y, current_range);
				shape_renderer.end();
			}
			
		}
		else
		{// NO LIFE
			map.song.stop();
			font.setScale(4);
			font.setColor(1.0f, 0.0f, 0.0f, 0.8f);
			if (health <= 0)
				font.draw(batch, "GAME OVER", (600 - font.getBounds("GAME OVER").width) / 2, (400 + font.getBounds("GAME OVER").height) / 2);
			else
			{
				font.draw(batch, "CONGRATULATIONS!", (600 - font.getBounds("CONGRATULATIONS!").width) / 2, (400 + font.getBounds("CONGRATULATIONS!").height) / 2);
				congrats_played = play_once(congratulations, congrats_played, 0.5f);
			}
		}
		
		batch.end();
	}

	public void update()
	{
		if (mute)
			volume = 0.0f;
		else
			volume = 1.0f;
		
		map.song.setVolume(volume);
		// update options menu
		// if in pause state, we want to halt operations
		if (pause_state)
		{
//			System.out.println("paused pressed");
			drawOptionMenu = true;
			pause_state = options_menu.pause();

			if (options_menu.gameover)
			{
				map.song.stop();
				gameover = true;
			}
		}
		else
		{
			drawOptionMenu = false;


			int select_number = tower_select.update(selling_state, upgrade_state);
			if (select_number != NONE)
			{
				if (select_number == PAUSE)
				{
					pause_state = true;
					upgrade_state = false;
					selling_state = false;
				}
				else if (select_number == SELL)
				{
					pause_state = false;
					upgrade_state = false;
					selling_state = true;
				}
				else if (select_number == UPGRADE)
				{
					pause_state = false;
					upgrade_state = true;
					selling_state = false;
				}
				else
				{
					pause_state = false;
					upgrade_state = false;
					selling_state = false;
					current_tower = select_number;
					current_range = create_tower(current_tower,0,0).getRange();
				}
			}
//			current_tower = (tower_number == -1) ? current_tower : tower_number;

			if (health > 0)
			{
				if (spawn)
				{
					if (wave_number < map.numWaves())
					{
						if (!wave.isEmpty())
						{
							// spawn an Enemy every at speed based on current wave
							long spawnInterval;
							time = System.currentTimeMillis();
							if (wave_number < 10)
								spawnInterval = time / (1000 - wave_number*100);
							else
								spawnInterval = time / 100;
							if (timeKeeper)
							{
								prevTime = spawnInterval;
								enemies.add(wave.poll());
								timeKeeper = false;
							}
							else
							{
								if (spawnInterval > prevTime)
								{
									timeKeeper = true;
								}
							}
						}
						else // go to next wave
						{
							long temp_time = System.currentTimeMillis();
							if ((temp_time - time) > time_between_waves)
							{
								if (++wave_number < map.numWaves())
								{
									wave = map.getWave(wave_number);
									//								time_between_waves -= 500;
								}
							}
						}
					}
				}


				// spawn a tower on the position that is clicked
				// TODO change this back to input.isTouched() so that it will work for Android
				// however, this will cause problems with the selling of Towers, since right click
				// technically falls under the isTouched() method
				// if (Gdx.input.isTouched())
				if (Gdx.input.isTouched())
				{
					touch_pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
					camera.unproject(touch_pos);
					// int x = (int) (touch_pos.x/GRID_WIDTH);
					// int x = xpos()/GRID_WIDTH;
					// int x = Gdx.input.getX()*(MAP_WIDTH/SCREEN_WIDTH);
					int x = (int)(touch_pos.x/GRID_WIDTH);
					int y = tower_grid.length - (int)(touch_pos.y/GRID_HEIGHT) - 1;
					//				int y = (int)(touch_pos.y/GRID_HEIGHT) - 1;

					//				System.out.println(x + " " + y);

					if (selling_state)
					{// SELL TOWER
						if (map.check_indices(x, y) && tower_grid[y][x] != null)
						{
							gold += tower_grid[y][x].getCost()/2;
							tower_grid[y][x] = null;
							map.set_placement(x, y, true);
						}
					}
					else if (upgrade_state && enable_tower_spawn)
					{// UPGRADE TOWER
						if (map.check_indices(x, y) && tower_grid[y][x] != null)
						{
							if (gold >= tower_grid[y][x].getUpgradeCost() && tower_grid[y][x].getLevel() < 3)
							{
								tower_grid[y][x].levelUp();
								gold -= tower_grid[y][x].getUpgradeCost();
							}
						}
						enable_tower_spawn = false;
					}
					else if (enable_tower_spawn)
					{// PLACE TOWER
						Tower t = create_tower(current_tower, x*GRID_WIDTH, camera.viewportHeight - (y+1)*GRID_HEIGHT);
						if (map.placement_valid(x, y) && gold >= t.getCost())
						{
							//						towers.add(t);
							tower_grid[y][x] = t;
							map.set_placement(x, y, false);
							gold -= t.getCost();
						}
						enable_tower_spawn = false;
					}
				}
				else
					enable_tower_spawn = true;
			}// update end (health > 0) update(); 

			/**
			 * GAME OVER
			 * game over occurs if health drains to 0 or you destroy the boss
			 * TODO when boss is dead, go to next level instead of ending the game
			 */
			if (health <= 0 || (wave_number >= map.numWaves() && enemies.size() == 0))
			{
				if (health > 0 && !wrote_to_file)
				{
					int diff = (difficulty == 0.75f) ? 1 : (difficulty == 1.0f) ? 2 : 3;
					file_manager.update_stars(level.index, diff);
					wrote_to_file = true;
				}
				if (Gdx.input.isTouched())
				{
					gameover = true;
					enable_tower_spawn = false;
				}
			}

			/**
			 * KEYBOARD INPUT
			 */
			if (DEBUG)
			{
				// if S is pressed, go into selling mode
				if (Gdx.input.isKeyPressed(Keys.S))
				{
					if (enable_selling)
					{
						selling_state = !selling_state;	// toggle selling state, initially false
						enable_selling = false;
					}
				}
				else
					enable_selling = true;

				// if space bar is pressed, spawn an Enemy
				if (Gdx.input.isKeyPressed(Keys.SPACE))
				{
					if (enable_enemy_spawn)
					{
						enemies.add(create_enemy(current_enemy));
						enable_enemy_spawn = false;
					}
				}
				else
					enable_enemy_spawn = true;

				// if E is pressed, change Enemy type
				if (Gdx.input.isKeyPressed(Keys.E))
				{
					if (enable_enemy_switch)
					{
						int enemies;
						if(level == Level.MARIO)
							enemies = EnemyEnum.NUM_MARIO_ENEMY;
						else if(level == Level.POKEMON)
							enemies = EnemyEnum.NUM_POKEMON_ENEMY;
						else if(level == Level.ZELDA)
							enemies = EnemyEnum.NUM_ZELDA_ENEMY;
						else if(level == Level.GALAGA)
							enemies = EnemyEnum.NUM_GALAGA_ENEMY;
						else
							enemies = 1;
						
						current_enemy = (current_enemy + 1) % enemies;
						enable_enemy_switch = false;
					}
				}
				else
					enable_enemy_switch = true;

				// if T is pressed, change Tower type
				if (Gdx.input.isKeyPressed(Keys.T))
				{
					if (enable_tower_switch)
					{
						current_tower = (current_tower + 1) % TOWER_COUNT;
						enable_tower_switch = false;
					}
				}
				else
					enable_tower_switch = true;

				// if G is pressed, obtain 10000 gold
				if (Gdx.input.isKeyPressed(Keys.G))
				{
					if (enable_gold)
					{
						gold += 10000;
						enable_gold = false;
					}
				}
				else
					enable_gold = true;

				// if K is pressed, kill all active enemies
				if (Gdx.input.isKeyPressed(Keys.K))
				{
					if (enable_killing)
					{
						for (Enemy e : enemies)
							e.kill();
						enable_killing = false;
					}
				}
				else
					enable_killing = true;
			}

			/**
			 * UPDATE ARRAYLISTS
			 */
			// update the enemy array
			for (int i = 0; i < enemies.size(); i++)
			{
				Enemy e = enemies.get(i);
				e.update();
				int damage = e.dealsDamage();
				if (damage != -1)
				{
					health -= damage;
				}
				if (e.isDead())
				{
					enemies.remove(i--);
					gold += e.getGold();
				}
			}

			// update the tower array
			for (int i = 0; i < tower_grid.length; i++)
			{
				for (int j = 0; j < tower_grid[i].length; j++)
				{
					if (tower_grid[i][j] != null)
						tower_grid[i][j].update();
				}
			}
		}
		
	}

	/**
	 * @param x position of the first heart
	 * @param y height of hearts
	 */
	private void life(SpriteBatch batch, int x, int y) 
	{

		int x2 = x;

		//draw 5 hearts with no health
		for (int i = 0; i < 5; i++)
		{
			heart(batch, heart0, x2+=26, y);
		}
		x2 = x;
		for (int i = 0; i < Math.floor(health/20); i++)
		{
			heart(batch, heart100, x2+=26, y);
		}

		Texture currentHealth = null;
		if (health < 100) // execute the following only when not in full health
		{
			if (health%20 > 15)	
				currentHealth = heart100;
			else if (health%20 > 10)	
				currentHealth = heart75;
			else if (health%20 > 5)	
				currentHealth = heart50;
			else
				currentHealth = heart25;
			// displays the partial heart
			heart(batch, currentHealth, x2+26, y);
		}

	}

	private void heart(SpriteBatch batch, Texture corazon, int x, int y)
	{
		batch.draw(corazon, x, y);
	}


	private Enemy create_enemy(int current_enemy)
	{// returns an instance of Enemy class based on value of current_enemy
		// enables us to quickly switch which enemy we want to spawn
		if(level == Level.MARIO)
		{
			switch (current_enemy)
			{
			case 0: return new BasicEnemy(map.getWayPoints(),difficulty,manager);
			case 1: return new Goomba(map.getWayPoints(),difficulty,manager);
			case 2: return new ShyGuy(map.getWayPoints(),difficulty,manager);
			case 3: return new Koopa(map.getWayPoints(),difficulty,manager);
			case 4: return new Bowser(map.getWayPoints(),difficulty,manager);
			
			default: return new BasicEnemy(map.getWayPoints(),difficulty,manager);
			}
		}
		else if(level == Level.POKEMON)
		{
			switch(current_enemy)
			{
			case 0: return new Arcanine(map.getWayPoints(),difficulty,manager);
			case 1: return new Geodude(map.getWayPoints(),difficulty,manager);
			case 2: return new Weedle(map.getWayPoints(),difficulty,manager);
			case 3: return new Voltorb(map.getWayPoints(),difficulty,manager);
			case 4: return new Tentacool(map.getWayPoints(),difficulty,manager);
			case 5: return new Lapras(map.getWayPoints(),difficulty,manager);
			case 6: return new Onix(map.getWayPoints(),difficulty,manager);
			case 7: return new Victree(map.getWayPoints(),difficulty,manager);
			
			default: return new Arcanine(map.getWayPoints(),difficulty,manager);
			}
		}
		else if(level == Level.ZELDA)
		{
			switch(current_enemy)
			{
			case 0: return new Cactus(map.getWayPoints(),difficulty,manager);
			case 1: return new Eye(map.getWayPoints(),difficulty,manager);
			case 2: return new Jellyfish(map.getWayPoints(),difficulty,manager);
			case 3: return new Knight(map.getWayPoints(),difficulty,manager);
			case 4: return new KnightB(map.getWayPoints(),difficulty,manager);
			case 5: return new Gannon(map.getWayPoints(),difficulty,manager);
			
			default: return new Cactus(map.getWayPoints(),difficulty,manager);
			}
			
		}
		else if(level == Level.GALAGA)
		{
			switch(current_enemy)
			{
			case 0: return new galagaEnemy1(map.getWayPoints(),difficulty,manager);
			case 1: return new galagaEnemy2(map.getWayPoints(),difficulty,manager);
			case 2: return new galagaEnemy3(map.getWayPoints(),difficulty,manager);
			case 3: return new galagaEnemy4(map.getWayPoints(),difficulty,manager);
			
			default: return new galagaEnemy1(map.getWayPoints(),difficulty,manager);
			}
		}
		else
			return new BasicEnemy(map.getWayPoints(),difficulty,manager);
	}

	private Tower create_tower(int current_tower, float x, float y)
	{
		
		if (level == Level.MARIO)
		{
			switch (current_tower)
			{
				case 0:	return new CastleTower(enemies, x, y, manager);
				case 1: return new HammerBros(enemies, x, y, manager);
				case 2: return new FlowerTower(enemies, x, y, manager);
				default: return new CastleTower(enemies, x, y, manager);
			}
		}
		else if (level == Level.POKEMON)
		{
			switch (current_tower)
			{
				case 0: return new FireTower(enemies, x, y, manager);
				case 1: return new WaterTower(enemies, x, y, manager);
				case 2: return new GrassTower(enemies, x, y, manager);
				case 3: return new PsychicTower(enemies, x, y, manager);
				default: return new FireTower(enemies, x, y, manager);
			}
		}
		else if (level == Level.ZELDA)
		{
			switch (current_tower)
			{
				case 0: return new BombTower(enemies, x, y, manager);
				case 1: return new BoomerangTower(enemies, x, y, manager);
				case 2: return new SlingshotTower(enemies, x, y, manager);
				case 3: return new SwordTower(enemies, x, y, manager);
				default: return new BombTower(enemies, x, y, manager);
			}
		}
		else if (level == Level.GALAGA)
		{
			switch (current_tower)
			{
				case 0: return new G1Tower(enemies, x, y, manager);
				case 1: return new G2Tower(enemies, x, y, manager);
				case 2: return new G3Tower(enemies, x, y, manager);
				case 3: return new G4Tower(enemies, x, y, manager);
				default: return new G1Tower(enemies, x, y, manager);
			}
		}
		else
			return new CastleTower(enemies, x, y, manager);
		
	}


	private String tower_name(int current_tower)
	{// returns the name of the current Tower
		switch(current_tower)
		{
		case CASTLE: return "Castle";
		case HAMMER: return "HammerBros";
		case PSYCHIC: return "PsychicTower";
		case FIRE: return "FireTower";
		case GRASS: return "GrassTower";
		case WATER: return "WaterTower";
		default: return "Error";
		}
	}
	
	private boolean play_once(Sound sound, boolean has_been_played, float volume)
	{
		if (!has_been_played)
			sound.play(volume);
		return true;
	}
	
	
	private class EnableSpawn extends Task
	{
		
		public EnableSpawn() {}
		
		public void run()
		{
			spawn = true;
		}
		
	}
	
}
