package com.me.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import enums.Level;
import enums.TowerEnum;

import towers.*;


public class TowerSelect
{
	final int PAUSE = 0, SELL = 1, UPGRADE = 2; 
	
	private Level level;
	private int[] cost,upgrade_cost, sell_cost;
	private OrthographicCamera camera;
	private Texture TowerMenu;
	private Texture[] tex, util_tex;
	private Rectangle[] buttons, util_buttons;
	private boolean is_pressed, sell_state, upgrade_state;
	private BitmapFont font;
	private int current_tower;
	private Texture selected;
	
	public TowerSelect(OrthographicCamera camera, Level level, AssetManager manager)
	{
		this.camera = camera;
		this.level = level;
		is_pressed = true;
		
		font = manager.get("data/nint.fnt");
		if(level == Level.MARIO)
			TowerMenu = manager.get("data/mario_tower_select.png");
		else if(level == Level.POKEMON)
			TowerMenu = manager.get("data/pokemon_tower_select.png");
		else if(level == Level.ZELDA)
			TowerMenu = manager.get("data/zeldaTowerSelect.png");
		else if(level == Level.GALAGA)
			TowerMenu = manager.get("data/galagaTowerSelect.png");
		// textures
		selected = manager.get("data/selected.png");
		
		//starting towers
		if (level == Level.MARIO)
			current_tower = TowerEnum.CASTLE.index;
		else if (level == Level.POKEMON)
			current_tower = TowerEnum.FIRE.index;
		else if (level == Level.ZELDA)
			current_tower = TowerEnum.BOMB.index;
		else if (level == Level.GALAGA)
			current_tower = TowerEnum.G1.index;
		else
			current_tower = TowerEnum.CASTLE.index;
			
		// util buttons
		util_tex = new Texture[3];
		util_tex[PAUSE] = manager.get("data/pause_button.png");
		util_tex[SELL] = manager.get("data/sell.png");
		util_tex[UPGRADE] = manager.get("data/upgrade.png");
		
		util_buttons = new Rectangle[3];
		util_buttons[PAUSE] = new Rectangle(715, 375, 25, 25);
		util_buttons[SELL] = new Rectangle(620, 20, 40, 40);
		util_buttons[UPGRADE] = new Rectangle(680, 20, 40, 40);
		
		
		// buttons
		if(level == Level.MARIO)
		{
			tex = new Texture[TowerEnum.NUM_MARIO_TOWERS];
			tex[TowerEnum.CASTLE.index] = manager.get("data/towers/tower.png");
			tex[TowerEnum.HAMMER_BROS.index] = manager.get("data/towers/hammer_bros_left_idle.png");
			tex[TowerEnum.FLOWER.index] = manager.get("data/towers/firetower.png");
			
			buttons = new Rectangle[TowerEnum.NUM_MARIO_TOWERS];
			buttons[TowerEnum.CASTLE.index] = new Rectangle(620, 315, 40, 40);
			buttons[TowerEnum.HAMMER_BROS.index] = new Rectangle(620, 255, 40, 40);
			buttons[TowerEnum.FLOWER.index] = new Rectangle(615, 195, 40, 40);
		}
		else if(level == Level.POKEMON)
		{
			tex = new Texture[TowerEnum.NUM_POKEMON_TOWERS];
			tex[TowerEnum.FIRE.index] = manager.get("data/towers/charm.png");
			tex[TowerEnum.WATER.index] = manager.get("data/towers/squirtle.png");
			tex[TowerEnum.GRASS.index] = manager.get("data/towers/bulba.png");
			tex[TowerEnum.PSYCHIC.index] = manager.get("data/towers/Abra.png");
			
			buttons = new Rectangle[TowerEnum.NUM_POKEMON_TOWERS];
			buttons[TowerEnum.FIRE.index] = new Rectangle(615, 315, 40, 40);
			buttons[TowerEnum.WATER.index] = new Rectangle(615, 255, 40, 40);
			buttons[TowerEnum.GRASS.index] = new Rectangle(615, 195, 40, 40);
			buttons[TowerEnum.PSYCHIC.index] = new Rectangle(615, 135, 40, 40);
		}
		else if(level == Level.ZELDA)
		{
			tex = new Texture[TowerEnum.NUM_ZELDA_TOWERS];
			tex[TowerEnum.BOMB.index] = manager.get("data/towers/BombTower.png");
			tex[TowerEnum.BOOMERANG.index] = manager.get("data/towers/BoomerangTower.png");
			tex[TowerEnum.SLINGSHOT.index] = manager.get("data/towers/SlingShotTower.png");
			tex[TowerEnum.SWORD.index] = manager.get("data/towers/SwordTower.png");
			
			buttons = new Rectangle[TowerEnum.NUM_ZELDA_TOWERS];
			buttons[TowerEnum.BOMB.index] = new Rectangle(620, 315, 40, 40);
			buttons[TowerEnum.BOOMERANG.index] = new Rectangle(615, 255, 40, 40);
			buttons[TowerEnum.SLINGSHOT.index] = new Rectangle(615, 195, 40, 40);
			buttons[TowerEnum.SWORD.index] = new Rectangle(615, 135, 40, 40);
		}
		else if(level == Level.GALAGA)
		{
			tex = new Texture[TowerEnum.NUM_GALAGA_TOWERS];
			tex[TowerEnum.G1.index] = manager.get("data/towers/galagaTower.png");
			tex[TowerEnum.G2.index] = manager.get("data/towers/galagaTower2.png");
			tex[TowerEnum.G3.index] = manager.get("data/towers/GalagaTower3.png");
			tex[TowerEnum.G4.index] = manager.get("data/towers/GalagaTower4.png");
			
			buttons = new Rectangle[TowerEnum.NUM_GALAGA_TOWERS];
			buttons[TowerEnum.G1.index] = new Rectangle(620, 315, 40, 40);
			buttons[TowerEnum.G2.index] = new Rectangle(620, 255, 40, 40);
			buttons[TowerEnum.G3.index] = new Rectangle(620, 195, 40, 40);
			buttons[TowerEnum.G4.index] = new Rectangle(615, 135, 40, 40);
		}
		
		// get Tower costs
		initializeTowerCosts();
       
	   //initialize states
	   sell_state = false;
	   upgrade_state = false;
	}
	
	private void initializeTowerCosts()
	{  
		
		if (level == Level.MARIO)
		{
			// get costs
			cost = new int[TowerEnum.NUM_MARIO_TOWERS];
			cost[TowerEnum.CASTLE.index] = (new CastleTower()).getCost();
			cost[TowerEnum.HAMMER_BROS.index] = (new HammerBros()).getCost();
			cost[TowerEnum.FLOWER.index] = (new FlowerTower()).getCost();
			
			// get sell costs
			sell_cost = new int[TowerEnum.NUM_MARIO_TOWERS];
			sell_cost[TowerEnum.CASTLE.index] = (new CastleTower()).getSellCost();
			sell_cost[TowerEnum.HAMMER_BROS.index] = (new HammerBros()).getSellCost();
			sell_cost[TowerEnum.FLOWER.index] = (new FlowerTower()).getSellCost();
			
			// get upgrade costs
			upgrade_cost = new int[TowerEnum.NUM_MARIO_TOWERS];
			upgrade_cost[TowerEnum.CASTLE.index] = (new CastleTower()).getUpgradeCost();
			upgrade_cost[TowerEnum.HAMMER_BROS.index] = (new HammerBros()).getUpgradeCost();
			upgrade_cost[TowerEnum.FLOWER.index] = (new FlowerTower()).getUpgradeCost();
		}
		else if (level == Level.POKEMON)
		{
			// get costs
			cost = new int[TowerEnum.NUM_POKEMON_TOWERS];
			cost[TowerEnum.FIRE.index] = (new FireTower()).getCost();
			cost[TowerEnum.WATER.index] = (new WaterTower()).getCost();
			cost[TowerEnum.GRASS.index] = (new GrassTower()).getCost();
			cost[TowerEnum.PSYCHIC.index] = (new PsychicTower()).getCost();
			
			// get sell costs
			sell_cost = new int[TowerEnum.NUM_POKEMON_TOWERS];
			sell_cost[TowerEnum.FIRE.index] = (new FireTower()).getSellCost();
			sell_cost[TowerEnum.WATER.index] = (new WaterTower()).getSellCost();
			sell_cost[TowerEnum.GRASS.index] = (new GrassTower()).getSellCost();
			sell_cost[TowerEnum.PSYCHIC.index] = (new PsychicTower()).getSellCost();
			
			// get upgrade costs
			upgrade_cost = new int[TowerEnum.NUM_POKEMON_TOWERS];
			upgrade_cost[TowerEnum.FIRE.index] = (new FireTower()).getUpgradeCost();
			upgrade_cost[TowerEnum.WATER.index] = (new WaterTower()).getUpgradeCost();
			upgrade_cost[TowerEnum.GRASS.index] = (new GrassTower()).getUpgradeCost();
			upgrade_cost[TowerEnum.PSYCHIC.index] = (new PsychicTower()).getUpgradeCost();
		}
		else if (level == Level.ZELDA)
		{
			// get costs
			cost = new int[TowerEnum.NUM_ZELDA_TOWERS];
			cost[TowerEnum.BOMB.index] = (new BombTower()).getCost();
			cost[TowerEnum.BOOMERANG.index] = (new BoomerangTower()).getCost();
			cost[TowerEnum.SLINGSHOT.index] = (new SlingshotTower()).getCost();
			cost[TowerEnum.SWORD.index] = (new SwordTower()).getCost();

			// get sell costs
			sell_cost = new int[TowerEnum.NUM_ZELDA_TOWERS];
			sell_cost[TowerEnum.BOMB.index] = (new BombTower()).getSellCost();
			sell_cost[TowerEnum.BOOMERANG.index] = (new BoomerangTower()).getSellCost();
			sell_cost[TowerEnum.SLINGSHOT.index] = (new SlingshotTower()).getSellCost();
			sell_cost[TowerEnum.SWORD.index] = (new SwordTower()).getSellCost();

			// get upgrade costs
			upgrade_cost = new int[TowerEnum.NUM_ZELDA_TOWERS];
			upgrade_cost[TowerEnum.BOMB.index] = (new BombTower()).getUpgradeCost();
			upgrade_cost[TowerEnum.BOOMERANG.index] = (new BoomerangTower()).getUpgradeCost();
			upgrade_cost[TowerEnum.SLINGSHOT.index] = (new SlingshotTower()).getUpgradeCost();
			upgrade_cost[TowerEnum.SWORD.index] = (new SwordTower()).getUpgradeCost();
		}
		else if (level == Level.GALAGA)
		{
			// get costs
			cost = new int[TowerEnum.NUM_GALAGA_TOWERS];
			cost[TowerEnum.G1.index] = (new G1Tower()).getCost();
			cost[TowerEnum.G2.index] = (new G2Tower()).getCost();
			cost[TowerEnum.G3.index] = (new G3Tower()).getCost();
			cost[TowerEnum.G4.index] = (new G4Tower()).getCost();

			// get sell costs
			sell_cost = new int[TowerEnum.NUM_GALAGA_TOWERS];
			sell_cost[TowerEnum.G1.index] = (new G1Tower()).getSellCost();
			sell_cost[TowerEnum.G2.index] = (new G2Tower()).getSellCost();
			sell_cost[TowerEnum.G3.index] = (new G3Tower()).getSellCost();
			sell_cost[TowerEnum.G4.index] = (new G4Tower()).getSellCost();

			// get upgrade costs
			upgrade_cost = new int[TowerEnum.NUM_GALAGA_TOWERS];
			upgrade_cost[TowerEnum.G1.index] = (new G1Tower()).getUpgradeCost();
			upgrade_cost[TowerEnum.G2.index] = (new G2Tower()).getUpgradeCost();
			upgrade_cost[TowerEnum.G3.index] = (new G3Tower()).getUpgradeCost();
			upgrade_cost[TowerEnum.G4.index] = (new G4Tower()).getUpgradeCost();
		}
	}
	
	public void render(SpriteBatch batch)
	{	
		batch.draw(TowerMenu, 600, 0, 140, 400);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(1.0f);
		font.draw(batch, "TOWERS", 635, 380);
		
		if (sell_state)
			batch.draw(selected, util_buttons[SELL].x, util_buttons[SELL].y, util_buttons[SELL].width, util_buttons[SELL].height);
		else if (upgrade_state)
			batch.draw(selected, util_buttons[UPGRADE].x, util_buttons[UPGRADE].y, util_buttons[UPGRADE].width, util_buttons[UPGRADE].height);
		else
			batch.draw(selected, buttons[current_tower].x, buttons[current_tower].y, buttons[current_tower].width, buttons[current_tower].height);
		
		for (int i = 0; i < util_buttons.length; i++)
			batch.draw(util_tex[i], util_buttons[i].x, util_buttons[i].y, util_buttons[i].width, util_buttons[i].height);
		
		for (int i = 0; i < buttons.length; i++)
		{
			batch.draw(tex[i], buttons[i].x, buttons[i].y, buttons[i].width, buttons[i].height);
			if (sell_state)
				font.draw(batch, "$"+sell_cost[i], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
			else if (upgrade_state)
				font.draw(batch, "$"+upgrade_cost[i], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
			else
				font.draw(batch, "$"+cost[i], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
		}
			
	}
	
	
	public int update(boolean sell_state, boolean upgrade_state)
	{
		this.sell_state = sell_state;
		this.upgrade_state = upgrade_state;
		
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
		
		
		for (int i = 0; i < util_buttons.length; i++)
		{
			if (util_buttons[i].contains(touchPos.x, touchPos.y))
			{
				if (!is_pressed)
				{
					if (i == PAUSE)
						return -3;
					else if (i == SELL)
						return -2;
					else if (i == UPGRADE)
						return -1;
				}
			}
		}
		
		for (int i = 0; i < buttons.length; i++)
		{
			if (buttons[i].contains(touchPos.x, touchPos.y))
			{
				if (!is_pressed)
				{
					current_tower = i;
					return i;
				}
			}
		}
		
		return -4;
	}
}
