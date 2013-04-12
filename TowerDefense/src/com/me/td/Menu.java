package com.me.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class Menu
{

	private final int WIDTH = 740, HEIGHT = 400;
	private final int BUTTON_WIDTH = 145, BUTTON_HEIGHT = 55;
	private final int MARIO = 0, POKEMON = 1;
	private final int NUM_LEVELS = 2;

	OrthographicCamera camera;

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
	
//	private Rectangle marioWorldButton;
//	private Texture marioWorldButtonTex;
//	
//	private Rectangle pokemon_level_button;
//	private Texture pokemon_level_button_tex;

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

	public int menu_state;
	private boolean is_pressed = true;
	
	private BitmapFont font;

	private final int MAIN_STATE = 0;
	private final int INSTR_STATE = 1;
	private final int DIFF_STATE = 2;
	private final int LEVEL_STATE = 3;
	
	private Vector3 touch_pos;
	
	private int level;
	
	public Music ssb_theme;
	
//	public enum level
//	{
//		MARIO, POKEMON;
//	}
	
	public Menu(OrthographicCamera camera)
	{
		this.camera = camera;
		
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
		
		level_buttons = new Rectangle[NUM_LEVELS];
		level_buttons[MARIO] = new Rectangle(50, 200, 150, 100);
		level_buttons[POKEMON] = new Rectangle(260, 200, 150, 100);
		
		level_buttons_tex = new Texture[NUM_LEVELS];
		level_buttons_tex[MARIO] = new Texture("data/maps/mario_map_3.png");
		level_buttons_tex[POKEMON] = new Texture("data/maps/pokemon_map.png");

		
		// STARS
		// starsInfo = new Rectangle(10,150,160,50);
		stars_dimensions = new Rectangle[NUM_LEVELS];
		stars_dimensions[0] = new Rectangle(75, 170, 100, 30);
		stars_dimensions[1] = new Rectangle(285, 170, 100, 30);
		//depending on the success on the level, you load which ever one, for now
		//i will default to no stars, if anyone wants to implement it
		// starsTex = new Texture("data/no stars.png");
		// starsTex = new Texture("data/1 stars.png");
		// starsTex = new Texture("data/2 stars.png");
		// starsTex = new Texture("data/3 stars.png");
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
		
		level = -1;

		menu_state = MAIN_STATE;
		
		ssb_theme = Gdx.audio.newMusic(Gdx.files.internal("sounds/super_smash_bros_theme.mp3"));
		ssb_theme.setLooping(true);
		ssb_theme.play();
	}

	public void restart()
	{
		level = -1;
		menu_state = MAIN_STATE;
	}
	
	public void render(SpriteBatch batch)
	{
		batch.begin();
		if(World.mute)
			ssb_theme.setVolume(0.0f);
		else
			ssb_theme.setVolume(1.0f);
		
		if (menu_state == MAIN_STATE)
		{
			batch.draw(menubg, 0, 0, WIDTH, HEIGHT);
			batch.draw(startButtonTex, startButton.x, startButton.y, startButton.width, startButton.height);
			batch.draw(instrButtonTex, instrButton.x, instrButton.y, instrButton.width, instrButton.height);

		}
		else if (menu_state == INSTR_STATE)
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
		else if (menu_state == DIFF_STATE)
		{
			batch.draw(diffbg, 0, 0, WIDTH, HEIGHT);
			batch.draw(easyButtonTex,easyButton.x,easyButton.y,easyButton.width,easyButton.height);
			batch.draw(mediumButtonTex,mediumButton.x,mediumButton.y,mediumButton.width,mediumButton.height);
			batch.draw(hardButtonTex,hardButton.x,hardButton.y,hardButton.width,hardButton.height);
			batch.draw(backButtonTex,backButton.x,backButton.y,backButton.width,backButton.height);

		}
		else if (menu_state == LEVEL_STATE)
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

	public int update()
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



		if (menu_state == MAIN_STATE)
		{

			if (startButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
					menu_state = LEVEL_STATE;
			}
			if (instrButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					menu_state = INSTR_STATE;
			}
			if (mute_onButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					World.mute = !World.mute;
			}

		}
		else if (menu_state == INSTR_STATE)
		{
			if (backButton.contains(touch_pos.x, touch_pos.y))
			{	
				if (!is_pressed)
				menu_state = MAIN_STATE;
			}
			if (mute_onButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					World.mute = !World.mute;
			}
		}
		else if (menu_state == DIFF_STATE)
		{
			// isTouched = false;
			if (easyButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
				{
					ssb_theme.stop();
					return level;
				}
			}
			if (mediumButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
				{
					ssb_theme.stop();
					return level;
				}
					
			}
			if (hardButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
				{
					ssb_theme.stop();
					return level;
				}
			}
			if (backButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
					menu_state = LEVEL_STATE;
			}
			if (mute_onButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					World.mute = !World.mute;
			}
		}

		else if (menu_state == LEVEL_STATE)
		{
			if (mute_onButton.contains(touch_pos.x, touch_pos.y))
			{
				if(!is_pressed)
					World.mute = !World.mute;
			}
			if (backButton.contains(touch_pos.x, touch_pos.y))
			{
				if (!is_pressed)
					menu_state = MAIN_STATE;
			}
			
			for (int i = 0; i < level_buttons.length; i++)
			{
				if (level_buttons[i].contains(touch_pos.x, touch_pos.y))
				{
					if (!is_pressed)
					{
						level = i;
						menu_state = DIFF_STATE;
					}
				}
			}
		}
		return -1;
	}
}
