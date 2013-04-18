package com.me.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class OptionsMenu
{
	
	final float SOUND_VOLUME = 0.5f;
	final int NUM_BUTTONS = 5;
	final int  MainMenu = 0, Restart = 1, Continue = 2, Mute = 3, Unmute = 4;
	final int mario = 0, pokemon = 1;
	int o_menu_width;
	int o_menu_height;
	int o_menu_x, o_menu_y;
	
	int buttons_x, buttons_width;
	int speaker_x, speaker_y;
	int speaker_width = 40, speaker_height = 40;
	int MM_Y, reset_y, continue_y;
	boolean drawOptionMenu, menuOpen, enable_menu, go_opt,  gameover;
	public static boolean restart;
	
	OrthographicCamera camera;
	
	private Texture OptionM;
	private Texture OptionBG;
	
//	private Texture Tower1Tex;
//	private Texture Tower2Tex;
//	private Texture Tower3Tex;
//	private Texture Tower4Tex;
	
	private Texture texMute, texUnmute;
	
//	private Rectangle Tower1Button;
//	private Rectangle Tower2Button;
//	private Rectangle Tower3Button;
//	private Rectangle Tower4Button;
	
	private Rectangle[] buttons;
	
	float centerx;
	float centery;
	
	// private int menuState;
	boolean is_pressed;
	
	BitmapFont font;
	
	// private final int MAIN_STATE = 0;
	
	public OptionsMenu(OrthographicCamera camera, int level)
	{
		this.camera = camera;
		
		OptionBG = new Texture("data/bgoverlay.png");
		if(level == mario)
		{
			o_menu_width = 300; o_menu_height = 322;
			buttons_width = o_menu_width-40;
			o_menu_x = ((int)camera.viewportWidth/2)-(o_menu_width/2);
		    o_menu_y = ((int)camera.viewportHeight/2)-(o_menu_height/2);
		    speaker_x = o_menu_x + o_menu_width-40; speaker_y = o_menu_y;
		    buttons_x = o_menu_x;
		    MM_Y = o_menu_y+150 ; reset_y = o_menu_y+70; continue_y = o_menu_y-10;
		    
			OptionM = new Texture("data/pausemenu.png");
		}
		if(level == pokemon)
		{
			o_menu_x = 0; o_menu_y = 0; 
			buttons_width = 130;
			o_menu_width = (int)camera.viewportWidth;  o_menu_height = (int)camera.viewportHeight;
		    buttons_x = 560;
		    MM_Y = o_menu_y+300 ; reset_y = o_menu_y+220; continue_y = o_menu_y+140;
		    speaker_x = buttons_x-45; speaker_y = continue_y-57;
			
			OptionM = new Texture("data/pokeMenu.png");
			
		}
		
		texMute = new Texture("data/speaker_off.png");
		texUnmute = new Texture("data/speaker_on.png");
		
		buttons = new Rectangle[NUM_BUTTONS-1];
		buttons[MainMenu] = new Rectangle(buttons_x,MM_Y, buttons_width, 40);
		buttons[Restart] = new Rectangle(buttons_x,reset_y,buttons_width,40);
    	buttons[Continue] = new Rectangle(buttons_x,continue_y,buttons_width,40);
		buttons[Mute] = new Rectangle(speaker_x, speaker_y, speaker_width, speaker_height);
		
		is_pressed = true;
		go_opt = false;
		gameover = false;
		restart = false;
		
	}

	public void render(SpriteBatch batch)
	{	
		batch.draw(OptionBG, 0, 0, 740, 400);
		batch.draw(OptionM,o_menu_x,o_menu_y, o_menu_width, o_menu_height);
		if(World.mute)
			batch.draw(texMute,buttons[3].x,buttons[3].y,buttons[3].width,buttons[3].height);
		else
			batch.draw(texUnmute,buttons[3].x,buttons[3].y,buttons[3].width,buttons[3].height);
		
	}
	
	public int update()
	{
		Vector3 touchPos = new Vector3();
		touchPos.set(0, 0, 0);
		if (Gdx.input.isTouched())
		{
			if (is_pressed)
			{
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				is_pressed = false;
			}
		}
		else
			is_pressed = true;
		
		
		for (int i = 0; i < buttons.length; i++)
		{
			if (buttons[i].contains(touchPos.x, touchPos.y))
			{
				if (!is_pressed)
					return i;
			}
		}
		
		return -1;
	}


	public final boolean pause()
	{
		boolean pause_state = true;
		Vector3 touch_pos = new Vector3();
		touch_pos.set(0, 0, 0);
		
		if(Gdx.input.isTouched())
		{    
			if(go_opt)
			{
				touch_pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touch_pos);
				go_opt = false;
			}
		}
		else
		  go_opt = true;
		
		//Main menu pressed
		if((touch_pos.x >= buttons[0].x) && (touch_pos.x <= buttons[0].x+buttons[0].width )
				&& (touch_pos.y >= buttons[0].y) && (touch_pos.y <= buttons[0].y+buttons[0].height))
		{
			if(!go_opt)
			gameover = true;		
		}
		
		//Restart pressed
		if((touch_pos.x >= buttons[1].x) && (touch_pos.x <= buttons[1].x+buttons[1].width )
				&& (touch_pos.y >= buttons[1].y) && (touch_pos.y <= buttons[1].y+buttons[1].height))
		{
			if(!go_opt)
			restart = true;	
			gameover = true;
		}
		
		//Continue pressed
		if((touch_pos.x >= buttons[2].x) && (touch_pos.x <= buttons[2].x+buttons[2].width )
				&& (touch_pos.y >= buttons[2].y) && (touch_pos.y <= buttons[2].y+buttons[2].height))
		{
			if(!go_opt)
			 pause_state = false;
		}
		
		//mute on/off
		if((touch_pos.x >= buttons[3].x) && (touch_pos.x <= buttons[3].x+buttons[3].width )
				&& (touch_pos.y >= buttons[3].y) && (touch_pos.y <= buttons[3].y+buttons[3].height))
		{
			if(!go_opt)
			World.mute = !World.mute;
			
		}
		
		//press pause button
		if(touch_pos.x >= 710 && touch_pos.x <= 740 && touch_pos.y >= 370 && touch_pos.y <= 400)
		{
			if(!go_opt)
				pause_state = false;		
		}
	    
		return pause_state;
	 }

}
