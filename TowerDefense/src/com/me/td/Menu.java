package com.me.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import enums.Level;
import enums.MenuState;


public class Menu
{

	private final int WIDTH = 740, HEIGHT = 400;
	private final int BUTTON_WIDTH = 145, BUTTON_HEIGHT = 55;

	private OrthographicCamera camera;

	private Texture menubg;
	private Texture instrbg;
	private Texture diffbg;
	private Texture levelbg;

	private Texture easyButtonTex;
	private Texture mediumButtonTex;
	private Texture hardButtonTex;

	private Rectangle easyButton;
	private Rectangle mediumButton;
	private Rectangle hardButton;

	private Rectangle[] level_buttons;
	private Texture[] level_buttons_tex;

	private Texture backButtonTex;
	private Texture startButtonTex;
	private Texture instrButtonTex;
	private Texture mute_on;
	private Texture mute_off;

	private Rectangle startButton;
	private Rectangle instrButton;
	private Rectangle backButton;
	private Rectangle mute_onButton;
	private Rectangle mute_offButton;

	private Rectangle[] stars_dimensions;
	private Texture[] stars;

	private MenuState state;
	private boolean is_pressed = true;
	
	private BitmapFont font;
	
	private Vector3 touch_pos;
	
	private Level level;
	
	private float difficulty;
	
	public Music ssb_theme;
	
	public Menu(OrthographicCamera camera)
	{
		this.camera = camera;
		difficulty = 1.0f;
		
		font = new BitmapFont(Gdx.files.internal("data/nint.fnt"), Gdx.files.internal("data/nint_0.png"), false);
		
		menubg = new Texture("data/menubg.png");
//		instrbg = new Texture("data/instrbg.png");
		instrbg = new Texture("data/black_bg.png");
		diffbg = new Texture("data/diffbg.png");
		levelbg = new Texture("data/levelbg.png");

		//difficulty screen texts
		easyButtonTex = new Texture("data/easy.png");
		mediumButtonTex = new Texture("data/medium.png");
		hardButtonTex = new Texture("data/hard.png");
		easyButton = new Rectangle(80, 170, 120, 30);
		mediumButton = new Rectangle(310, 170, 120, 30);
		hardButton = new Rectangle(530, 170, 120, 30);

		//start screen text
		startButtonTex = new Texture("data/menu_start.png");
		instrButtonTex = new Texture("data/menu_instr.png");
		backButtonTex = new Texture("data/instr_back.png");
		// startButton = new Rectangle(320,160,145,55);
		// instrButton = new Rectangle(320,90,145,55);
		// backButton = new Rectangle(650,80,128,70);
		startButton = new Rectangle(camera.viewportWidth/2 - BUTTON_WIDTH/2, 130, BUTTON_WIDTH, BUTTON_HEIGHT);
		instrButton = new Rectangle(camera.viewportWidth/2 - BUTTON_WIDTH/2, 60, BUTTON_WIDTH, BUTTON_HEIGHT);
		backButton = new Rectangle(camera.viewportWidth - 100 - 10, 10, 100, 50);

		
		// LEVEL SELECT
//		marioWorldButton = new Rectangle(10,200,160,160);
//		marioWorldButtonTex = new Texture("data/marioLevelSelect.png");
//		marioWorldButton = new Rectangle(50,200,150,100);
//		marioWorldButtonTex = new Texture("data/maps/mario_map_3.png");
//		pokemon_level_button = new Rectangle(260,200,150,100);
//		pokemon_level_button_tex = new Texture("data/maps/mario_map_3.png");
		
		level_buttons = new Rectangle[Level.values().length - 1];
		level_buttons[Level.MARIO.index] = new Rectangle(50, 200, 150, 100);
		level_buttons[Level.POKEMON.index] = new Rectangle(260, 200, 150, 100);
		
		level_buttons_tex = new Texture[Level.values().length - 1];
		level_buttons_tex[Level.MARIO.index] = new Texture("data/maps/mario_map_3.png");
		level_buttons_tex[Level.POKEMON.index] = new Texture("data/maps/pokemon_map.png");

		
		// STARS
		stars_dimensions = new Rectangle[Level.values().length - 1];
		stars_dimensions[0] = new Rectangle(75, 170, 100, 30);
		stars_dimensions[1] = new Rectangle(285, 170, 100, 30);
		
		stars = new Texture[4];
		stars[0] = new Texture("data/0_stars.png");
		stars[1] = new Texture("data/1_star.png");
		stars[2] = new Texture("data/2_stars.png");
		stars[3] = new Texture("data/3_stars.png");
		
		//mute texture and button
		mute_off = new Texture("data/speaker_on.png");
		mute_on = new Texture("data/speaker_off.png");
				
		mute_onButton = new Rectangle(0,0,40,40);
		mute_offButton = new Rectangle(0,0,40,40);
		touch_pos = new Vector3();
		
		level = Level.NONE;

		state = MenuState.MAIN;
		
		ssb_theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/super_smash_bros_theme.mp3"));
		ssb_theme.setLooping(true);
		ssb_theme.play();
	}

	public void restart()
	{
		level = Level.NONE;
		state = MenuState.MAIN;
		ssb_theme.play();
	}
	
	public void render(SpriteBatch batch)
	{
		batch.begin();
		if(World.mute)
			ssb_theme.setVolume(0.0f);
		else
			ssb_theme.setVolume(1.0f);
		
		if (state == MenuState.MAIN)
		{
			batch.draw(menubg, 0, 0, WIDTH, HEIGHT);
			batch.draw(startButtonTex, startButton.x, startButton.y, startButton.width, startButton.height);
			batch.draw(instrButtonTex, instrButton.x, instrButton.y, instrButton.width, instrButton.height);
		}
		else if (state == MenuState.INSTRUCTION)
		{
			batch.draw(instrbg, 0, 0, WIDTH, HEIGHT);
			batch.draw(backButtonTex, backButton.x, backButton.y, backButton.width, backButton.height);
			
			// write instructions
			font.setScale(1);
			font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			font.draw(batch, "INSTRUCTIONS", camera.viewportWidth/2 - 100, 350);
			font.draw(batch, "Place towers to stop enemies from", camera.viewportWidth/2 - 230, 275);
			font.draw(batch, "reaching your castle!", camera.viewportWidth/2 - 150, 255);
		}
		else if (state == MenuState.DIFFICULTY)
		{
			batch.draw(diffbg, 0, 0, WIDTH, HEIGHT);
			batch.draw(easyButtonTex,easyButton.x,easyButton.y,easyButton.width,easyButton.height);
			batch.draw(mediumButtonTex,mediumButton.x,mediumButton.y,mediumButton.width,mediumButton.height);
			batch.draw(hardButtonTex,hardButton.x,hardButton.y,hardButton.width,hardButton.height);
			batch.draw(backButtonTex,backButton.x,backButton.y,backButton.width,backButton.height);
		}
		else if (state == MenuState.LEVEL)
		{
			batch.draw(levelbg, 0, 0, WIDTH, HEIGHT);
			batch.draw(backButtonTex,backButton.x,backButton.y,backButton.width,backButton.height);
			for (int i = 0; i < level_buttons.length; i++)
				batch.draw(level_buttons_tex[i], level_buttons[i].x, level_buttons[i].y, level_buttons[i].width, level_buttons[i].height);
			for (int i = 0; i < stars_dimensions.length; i++)
				batch.draw(stars[0], stars_dimensions[i].x, stars_dimensions[i].y, stars_dimensions[i].width, stars_dimensions[i].height);
		}
		//draw mute button
		if(World.mute)
			batch.draw(mute_on, mute_onButton.x, mute_onButton.y, mute_onButton.width, mute_onButton.height);
		else
			batch.draw(mute_off, mute_offButton.x, mute_offButton.y, mute_offButton.width, mute_offButton.height);
		
		batch.end();
	}

	public float getDiff()
	{
		return difficulty;
	}
	
	public Level update()
	{// returns -1 if we want to stay in the menu state
	 // else, it returns an index 'level' that tells World.java which Map to use
		touch_pos.set(0, 0, 0);
		if (Gdx.input.isTouched())
		{
			if (is_pressed)
			{
				touch_pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touch_pos);
				is_pressed = false;
			}
		}
		else
			is_pressed = true;



		if (state == MenuState.MAIN)
		{
			if (startButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
					state = MenuState.LEVEL;
			}
			if (instrButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					state = MenuState.INSTRUCTION;
			}
			if (mute_onButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					World.mute = !World.mute;

			}
			
		}
		else if (state == MenuState.INSTRUCTION)
		{
			if (backButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
					state = MenuState.MAIN;
			}
			if (mute_onButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					World.mute = !World.mute;
			}
		}
		else if (state == MenuState.DIFFICULTY)
		{
			// isTouched = false;
			if (easyButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
				{
					ssb_theme.stop();
					difficulty = 0.75f;
					return level;
				}
			}
			if (mediumButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
				{
					ssb_theme.stop();
					difficulty = 1.0f;
					return level;
				}
					
			}
			if (hardButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
				{
					ssb_theme.stop();
					difficulty = 1.25f;
					return level;
				}
			}
			if (backButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
					state = MenuState.LEVEL;
			}
			if (mute_onButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					World.mute = !World.mute;
			}
		}

		else if (state == MenuState.LEVEL)
		{
			if (mute_onButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					World.mute = !World.mute;
			}
			if (backButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
					state = MenuState.MAIN;
			}
			
			for (int i = 0; i < level_buttons.length; i++)
			{
				if (level_buttons[i].contains(touch_pos.x, touch_pos.y))
				{
					if (!is_pressed)
					{
						level = Level.values()[i + 1];
						state = MenuState.DIFFICULTY;
					}
				}
			}
		}
		return Level.NONE;
	}
}
