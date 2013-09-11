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
	int o_menu_width = 300;
	int o_menu_height = 322;
	int o_menu_x, o_menu_y;
	
	int cont_x,cont_y;
	int speaker_x, speaker_y;
	int speaker_width = 40, speaker_height = 40;
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
	
	public OptionsMenu(OrthographicCamera camera)
	{
		this.camera = camera;
		
		o_menu_x = ((int)camera.viewportWidth/2)-(o_menu_width/2);
	    o_menu_y = ((int)camera.viewportHeight/2)-(o_menu_height/2);
	    speaker_x = o_menu_x + o_menu_width-50;
	    speaker_y = o_menu_y;
	    cont_x = o_menu_x;
	    cont_y = o_menu_y-10;
		
		OptionBG = new Texture("data/bgoverlay.png");
		OptionM = new Texture("data/pausemenu.png");
    	
		texMute = new Texture("data/speaker_off.png");
		texUnmute = new Texture("data/speaker_on.png");
		
		buttons = new Rectangle[NUM_BUTTONS-1];
		buttons[MainMenu] = new Rectangle(cont_x,cont_y+160, o_menu_width-40, 40);
		buttons[Restart] = new Rectangle(cont_x,cont_y+80,o_menu_width-40,40);
    	buttons[Continue] = new Rectangle(cont_x,cont_y,o_menu_width-40,40);
		buttons[Mute] = new Rectangle(cont_x+o_menu_width-40, speaker_y, speaker_width, speaker_height);
		//buttons[Unmute] = new Rectangle(cont_x+o_menu_width-80, speaker_y-40, speaker_width, 2*speaker_height);
		
		is_pressed = true;
		//mute = false;
		go_opt = false;
		gameover = false;
		restart = false;
		
	}

	public void render(SpriteBatch batch)
	{	
		batch.draw(OptionBG, 0, 0, 740, 400);
		batch.draw(OptionM, o_menu_x, o_menu_y, o_menu_width, o_menu_height);
		
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
