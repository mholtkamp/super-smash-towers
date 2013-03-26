package com.me.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class TowerSelect
{
	
	final int NUM_BUTTONS = 6;
	final int SELL = 0, UPGRADE = 1, CASTLE = 2, HAMMER = 3, PSYCHIC = 4, FIRE = 5;
	
	OrthographicCamera camera;
	
	private Texture TowerMenu;
	
//	private Texture Tower1Tex;
//	private Texture Tower2Tex;
//	private Texture Tower3Tex;
//	private Texture Tower4Tex;
	
	private Texture[] tex;
	
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
	
	public TowerSelect(OrthographicCamera camera)
	{
		this.camera = camera;
		
		TowerMenu = new Texture("data/tower_select.png");
    	
		// tower menu textures
		tex = new Texture[NUM_BUTTONS];
		tex[SELL] = new Texture("data/sell.png");
		tex[UPGRADE] = new Texture("data/upgrade.png");
		tex[CASTLE] = new Texture("data/towers/tower.png");
		tex[HAMMER] = new Texture("data/towers/hammer_bros_left_idle.png");
		tex[PSYCHIC] = new Texture("data/towers/psychic_tower_1.png");
		tex[FIRE] = new Texture("data/towers/fire_tower_1.png");
		
		buttons = new Rectangle[NUM_BUTTONS];
		buttons[SELL] = new Rectangle(620, 20, 40, 40);
		buttons[UPGRADE] = new Rectangle(680, 20, 40, 40);
    	buttons[CASTLE] = new Rectangle(620, 320, 40, 40);
		buttons[HAMMER] = new Rectangle(680, 320, 40, 40);
		buttons[PSYCHIC] = new Rectangle(620, 260, 40, 40);
		buttons[FIRE] = new Rectangle(680, 260, 40, 40);
		
//      Tower1Tex = new Texture("data/BasicTower.png");
//		Tower2Tex = new Texture("data/BasicEnemy.png");
//		Tower3Tex = new Texture("data/easy.png");
//		Tower4Tex = new Texture("data/hard.png");
//    	Tower1Button = new Rectangle(0,75,100,75);
//		Tower2Button = new Rectangle(100,75,100,75);
//		Tower3Button = new Rectangle(0,0,100,75);
//		Tower4Button = new Rectangle(100,0,100,75);
		
		is_pressed = true;
		
		font = new BitmapFont(Gdx.files.internal("data/nint.fnt"), Gdx.files.internal("data/nint_0.png"), false);
	}

	public void render(SpriteBatch batch)
	{	
		batch.draw(TowerMenu, 600, 0, 140, 400);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, "TOWERS", 635, 390);
		for (int i = 0; i < buttons.length; i++)
			batch.draw(tex[i],buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
		
//		batch.draw(Tower1Tex,Tower1Button.x,Tower1Button.y,Tower1Button.width,Tower1Button.height);
//		batch.draw(Tower2Tex,Tower2Button.x,Tower2Button.y,Tower2Button.width,Tower2Button.height);
//		batch.draw(Tower3Tex,Tower3Button.x,Tower3Button.y,Tower3Button.width,Tower3Button.height);
//		batch.draw(Tower4Tex,Tower4Button.x,Tower4Button.y,Tower4Button.width,Tower4Button.height);
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
		
//		if (Tower1Button.contains(touchPos.x,touchPos.y))
//		{
//			if (!is_pressed)
//				return false;
//		}
//		if (Tower2Button.contains(touchPos.x,touchPos.y))
//		{
//			if(!is_pressed)
//				return false;
//		}
//		if (Tower3Button.contains(touchPos.x,touchPos.y))
//		{
//			if (!is_pressed)
//				return false;
//		}
//		if (Tower4Button.contains(touchPos.x,touchPos.y))
//		{
//			if (!is_pressed)
//				return false;
//		}
		
		return -1;
	}
}
