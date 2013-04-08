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
	
	final int NUM_BUTTONS = 8;
	final int PAUSE = 0, SELL = 1, UPGRADE = 2, CASTLE = 3, HAMMER = 4, PSYCHIC = 5, FIRE = 6, GRASS = 7;
	
	private OrthographicCamera camera;
	private Texture TowerMenu;
	private Texture[] tex;
	private Rectangle[] buttons;
	private boolean is_pressed;
	private BitmapFont font;
	
	public TowerSelect(OrthographicCamera camera)
	{
		this.camera = camera;
		is_pressed = true;
		font = new BitmapFont(Gdx.files.internal("data/nint.fnt"), Gdx.files.internal("data/nint_0.png"), false);
		
		TowerMenu = new Texture("data/tower_select.png");
    	
		// textures
		tex = new Texture[NUM_BUTTONS];
		tex[PAUSE] = new Texture("data/pauseButton.png");
		tex[SELL] = new Texture("data/sell.png");
		tex[UPGRADE] = new Texture("data/upgrade.png");
		tex[CASTLE] = new Texture("data/towers/tower.png");
		tex[HAMMER] = new Texture("data/towers/hammer_bros_left_idle.png");
		tex[PSYCHIC] = new Texture("data/towers/psychic_tower_1.png");
		tex[FIRE] = new Texture("data/towers/fire_tower_1.png");
		tex[GRASS] = new Texture("data/towers/grass_tower_1.png");
		
		// buttons
		buttons = new Rectangle[NUM_BUTTONS];
		buttons[PAUSE] = new Rectangle(710, 370, 30, 30);
		buttons[SELL] = new Rectangle(620, 20, 40, 40);
		buttons[UPGRADE] = new Rectangle(680, 20, 40, 40);
    	buttons[CASTLE] = new Rectangle(620, 320, 40, 40);
		buttons[HAMMER] = new Rectangle(680, 320, 40, 40);
		buttons[PSYCHIC] = new Rectangle(620, 260, 40, 40);
		buttons[FIRE] = new Rectangle(680, 260, 40, 40);
		buttons[GRASS] = new Rectangle(620, 200, 40, 40);
	}

	public void render(SpriteBatch batch)
	{	
		batch.draw(TowerMenu, 600, 0, 140, 400);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, "TOWERS", 635, 390);
		for (int i = 0; i < buttons.length; i++)
			batch.draw(tex[i],buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
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
}
