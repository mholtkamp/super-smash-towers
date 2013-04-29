package com.me.td;

import towers.Tower;
import util.CreateTower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import enums.TowerEnum;


public class TowerSelect
{
	
	final int MARIO = 0, POKEMON = 1, ZELDA = 2, GALAGA = 3;
	final int NUM_BUTTONS = 18;
	final int PAUSE = 0, SELL = 1, UPGRADE = 2, CASTLE = 3, HAMMER = 4, FLOWER = 5, FIRE = 6, WATER = 7, GRASS = 8, PSYCHIC = 9, BOMB = 10, BOOMERANG = 11, SLINGSHOT = 12, SWORD = 13,
			           G1 = 14, G2 = 15, G3 = 16, G4 = 17;
	final int NUM_MARIO_BUTTONS = 3, NUM_POKEMON_BUTTONS = 4, NUM_ZELDA_BUTTONS = 4;
	final int NUM_UTIL_BUTTONS = 3;
	// final int CASTLE_PRICE = 250, HAMMER_PRICE = 500, PSYCHIC_PRICE = 750, FIRE_PRICE = 500, GRASS_PRICE = 1000, WATER_PRICE = 750;
	int CASTLE_PRICE, HAMMER_PRICE, FLOWER_PRICE, PSYCHIC_PRICE, FIRE_PRICE, GRASS_PRICE, WATER_PRICE, BOMB_PRICE, BOOMERANG_PRICE, SLINGSHOT_PRICE, SWORD_PRICE, 
	    G1_PRICE, G2_PRICE, G3_PRICE, G4_PRICE;
	
	private int mapID;
	private int[] upgrade_cost, sell_cost;
	private OrthographicCamera camera;
	private Texture TowerMenu;
	private Texture[] tex;
	private Rectangle[] buttons;
	private boolean is_pressed;
	private BitmapFont font;
	private int current_tower;
	private ShapeRenderer shape_renderer;
	private Texture white;
	
	public TowerSelect(OrthographicCamera camera, int mapID, AssetManager manager)
	{
		this.camera = camera;
		this.mapID = mapID;
		is_pressed = true;
		font = manager.get("data/nint.fnt");
		if(mapID == MARIO)
			TowerMenu = manager.get("data/mario_tower_select.png");
		else if(mapID == POKEMON)
			TowerMenu = manager.get("data/pokemon_tower_select.png");
		else if(mapID == ZELDA)
			TowerMenu = manager.get("data/pokemon_tower_select.png");
		else if(mapID == GALAGA)
			TowerMenu = manager.get("data/pokemon_tower_select.png");
		// textures
		white = manager.get("data/selected.png");
		tex = new Texture[NUM_BUTTONS];

		tex[PAUSE] = manager.get("data/pause_button.png");
		tex[SELL] = manager.get("data/sell.png");
		tex[UPGRADE] = manager.get("data/upgrade.png");
		tex[CASTLE] = manager.get("data/towers/castle1.png");
		tex[HAMMER] = manager.get("data/towers/hammer_bros_left_idle.png");
		tex[FLOWER] = manager.get("data/towers/firetower.png");
		tex[PSYCHIC] = manager.get("data/towers/Abra.png");
		tex[FIRE] = manager.get("data/towers/charm.png");
		tex[GRASS] = manager.get("data/towers/bulba.png");
		tex[WATER] = manager.get("data/towers/squirtle.png");
		tex[BOMB] = manager.get("data/towers/BombTower.png");
		tex[BOOMERANG] = manager.get("data/towers/BoomerangTower.png");
		tex[SLINGSHOT] = manager.get("data/towers/SlingShotTower.png");
		tex[SWORD] = manager.get("data/towers/SwordTower.png");
		tex[G1] = manager.get("data/towers/galagaTower.png");
		tex[G2] = manager.get("data/towers/galagaTower2.png");
		tex[G3] = manager.get("data/towers/GalagaTower3.png");
		tex[G4] = manager.get("data/towers/GalagaTower4.png");
		
		if (mapID == MARIO)
			current_tower = CASTLE;
		else if (mapID == POKEMON)
			current_tower = FIRE;
		else if (mapID == ZELDA)
			current_tower = BOMB;
		else if (mapID == GALAGA)
			current_tower = G1;
		else
			current_tower = -1;
		
		// buttons
		if(mapID == MARIO)
		{
			buttons = new Rectangle[NUM_MARIO_BUTTONS + NUM_UTIL_BUTTONS];
			//UTIL BUTTONS
			buttons[0] = new Rectangle(715, 375, 25, 25);
			buttons[1] = new Rectangle(620, 20, 40, 40);
			buttons[2] = new Rectangle(680, 20, 40, 40);
			tex[0] = tex[PAUSE];
			tex[1] = tex[SELL];
			tex[2] = tex[UPGRADE];
			//MARIO BUTTONS
			buttons[3] = new Rectangle(620, 315, 40, 40);
			buttons[4] = new Rectangle(620, 255, 40, 40);
			buttons[5] = new Rectangle(615, 195, 40, 40);
			tex[3] = tex[CASTLE];
			tex[4] = tex[HAMMER];
			tex[5] = tex[FLOWER];
		}
		else if(mapID == POKEMON)
		{
			buttons = new Rectangle[NUM_POKEMON_BUTTONS + NUM_UTIL_BUTTONS];
			//UTIL BUTTONS
			buttons[0] = new Rectangle(715, 375, 25, 25);
			buttons[1] = new Rectangle(620, 20, 40, 40);
			buttons[2] = new Rectangle(680, 20, 40, 40);
			tex[0] = tex[PAUSE];
			tex[1] = tex[SELL];
			tex[2] = tex[UPGRADE];
			//POKEMON BUTTONS
			buttons[3] = new Rectangle(615, 315, 40, 40);
			buttons[4] = new Rectangle(615, 255, 40, 40);
			buttons[5] = new Rectangle(615, 195, 40, 40);
			buttons[6] = new Rectangle(615, 135, 40, 40);
			tex[3] = tex[FIRE];
			tex[4] = tex[WATER];
			tex[5] = tex[GRASS];
			tex[6] = tex[PSYCHIC];
		}
		else if(mapID == ZELDA)
		{
			buttons = new Rectangle[NUM_POKEMON_BUTTONS + NUM_UTIL_BUTTONS];
			//UTIL BUTTONS
			buttons[0] = new Rectangle(715, 375, 25, 25);
			buttons[1] = new Rectangle(620, 20, 40, 40);
			buttons[2] = new Rectangle(680, 20, 40, 40);
			tex[0] = tex[PAUSE];
			tex[1] = tex[SELL];
			tex[2] = tex[UPGRADE];
			//POKEMON BUTTONS
			buttons[3] = new Rectangle(615, 315, 40, 40);
			buttons[4] = new Rectangle(615, 255, 40, 40);
			buttons[5] = new Rectangle(615, 195, 40, 40);
			buttons[6] = new Rectangle(615, 135, 40, 40);
			tex[3] = tex[BOMB];
			tex[4] = tex[BOOMERANG];
			tex[5] = tex[SLINGSHOT];
			tex[6] = tex[SWORD];
		}
		else if(mapID == GALAGA)
		{
			buttons = new Rectangle[NUM_POKEMON_BUTTONS + NUM_UTIL_BUTTONS];
			//UTIL BUTTONS
			buttons[0] = new Rectangle(715, 375, 25, 25);
			buttons[1] = new Rectangle(620, 20, 40, 40);
			buttons[2] = new Rectangle(680, 20, 40, 40);
			tex[0] = tex[PAUSE];
			tex[1] = tex[SELL];
			tex[2] = tex[UPGRADE];
			//POKEMON BUTTONS
			buttons[3] = new Rectangle(615, 315, 40, 40);
			buttons[4] = new Rectangle(615, 255, 40, 40);
			buttons[5] = new Rectangle(615, 195, 40, 40);
			buttons[6] = new Rectangle(615, 135, 40, 40);
			tex[3] = tex[G1];
			tex[4] = tex[G2];
			tex[5] = tex[G3];
			tex[6] = tex[G4];
		}
		
//		buttons[PAUSE] = new Rectangle(710, 370, 30, 30);
//		buttons[SELL] = new Rectangle(620, 20, 40, 40);
//		buttons[UPGRADE] = new Rectangle(680, 20, 40, 40);
//    	buttons[CASTLE] = new Rectangle(620, 320, 40, 40);
//		buttons[HAMMER] = new Rectangle(680, 320, 40, 40);
//		buttons[PSYCHIC] = new Rectangle(620, 260, 40, 40);
//		buttons[FIRE] = new Rectangle(680, 260, 40, 40);
//		buttons[GRASS] = new Rectangle(620, 200, 40, 40);
		
		// get Tower costs
		initializeTowerCosts();
	}
	
	private void initializeTowerCosts()
	{
		// get costs
		CASTLE_PRICE = new CreateTower(TowerEnum.CASTLE).getTower().getCost();
		HAMMER_PRICE = new CreateTower(TowerEnum.HAMMER_BROS).getTower().getCost();
		FLOWER_PRICE = new CreateTower(TowerEnum.FLOWER).getTower().getCost();
		PSYCHIC_PRICE = new CreateTower(TowerEnum.PSYCHIC).getTower().getCost();
		FIRE_PRICE = new CreateTower(TowerEnum.FIRE).getTower().getCost();
		GRASS_PRICE = new CreateTower(TowerEnum.GRASS).getTower().getCost();
		WATER_PRICE = new CreateTower(TowerEnum.WATER).getTower().getCost();
		BOMB_PRICE = new CreateTower(TowerEnum.BOMB).getTower().getCost();
		BOOMERANG_PRICE = new CreateTower(TowerEnum.BOOMERANG).getTower().getCost();
		SLINGSHOT_PRICE = new CreateTower(TowerEnum.SLINGSHOT).getTower().getCost();
		SWORD_PRICE = new CreateTower(TowerEnum.SWORD).getTower().getCost();
		G1_PRICE = new CreateTower(TowerEnum.G1).getTower().getCost();
		G2_PRICE = new CreateTower(TowerEnum.G2).getTower().getCost();
		G3_PRICE = new CreateTower(TowerEnum.G3).getTower().getCost();
		G4_PRICE = new CreateTower(TowerEnum.G4).getTower().getCost();
		
		// get upgrade costs
		upgrade_cost = new int[TowerEnum.values().length];
		upgrade_cost[TowerEnum.CASTLE.index] = new CreateTower(TowerEnum.CASTLE).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.HAMMER_BROS.index] = new CreateTower(TowerEnum.HAMMER_BROS).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.FLOWER.index] = new CreateTower(TowerEnum.FLOWER).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.PSYCHIC.index] = new CreateTower(TowerEnum.PSYCHIC).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.FIRE.index] = new CreateTower(TowerEnum.FIRE).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.GRASS.index] = new CreateTower(TowerEnum.GRASS).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.WATER.index] = new CreateTower(TowerEnum.G1).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.BOMB.index] = new CreateTower(TowerEnum.BOMB).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.BOOMERANG.index] = new CreateTower(TowerEnum.BOOMERANG).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.SLINGSHOT.index] = new CreateTower(TowerEnum.SLINGSHOT).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.SWORD.index] = new CreateTower(TowerEnum.SWORD).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.G1.index] = new CreateTower(TowerEnum.G1).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.G2.index] = new CreateTower(TowerEnum.G2).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.G3.index] = new CreateTower(TowerEnum.G3).getTower().getUpgradeCost();
		upgrade_cost[TowerEnum.G4.index] = new CreateTower(TowerEnum.G4).getTower().getUpgradeCost();
		
		// get sell costs
		sell_cost = new int[TowerEnum.values().length];
		sell_cost[TowerEnum.CASTLE.index] = new CreateTower(TowerEnum.CASTLE).getTower().getSellCost();
		sell_cost[TowerEnum.HAMMER_BROS.index] = new CreateTower(TowerEnum.HAMMER_BROS).getTower().getSellCost();
		sell_cost[TowerEnum.FLOWER.index] = new CreateTower(TowerEnum.FLOWER).getTower().getSellCost();
		sell_cost[TowerEnum.PSYCHIC.index] = new CreateTower(TowerEnum.PSYCHIC).getTower().getSellCost();
		sell_cost[TowerEnum.FIRE.index] = new CreateTower(TowerEnum.FIRE).getTower().getSellCost();
		sell_cost[TowerEnum.GRASS.index] = new CreateTower(TowerEnum.GRASS).getTower().getSellCost();
		sell_cost[TowerEnum.WATER.index] = new CreateTower(TowerEnum.WATER).getTower().getSellCost();
		sell_cost[TowerEnum.BOMB.index] = new CreateTower(TowerEnum.BOMB).getTower().getSellCost();
		sell_cost[TowerEnum.BOOMERANG.index] = new CreateTower(TowerEnum.BOOMERANG).getTower().getSellCost();
		sell_cost[TowerEnum.SLINGSHOT.index] = new CreateTower(TowerEnum.SLINGSHOT).getTower().getSellCost();
		sell_cost[TowerEnum.SWORD.index] = new CreateTower(TowerEnum.SWORD).getTower().getSellCost();
		sell_cost[TowerEnum.G1.index] = new CreateTower(TowerEnum.G1).getTower().getSellCost();
		sell_cost[TowerEnum.G2.index] = new CreateTower(TowerEnum.G2).getTower().getSellCost();
		sell_cost[TowerEnum.G3.index] = new CreateTower(TowerEnum.G3).getTower().getSellCost();
		sell_cost[TowerEnum.G4.index] = new CreateTower(TowerEnum.G4).getTower().getSellCost();
	}
	
	public void render(SpriteBatch batch)
	{	
		batch.draw(TowerMenu, 600, 0, 140, 400);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(1.0f);
		font.draw(batch, "TOWERS", 635, 380);
		for (int i = 0; i < buttons.length; i++)
		{
			if(mapID == MARIO)
			{
				if(current_tower == i)
				{
					batch.draw(white,buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);


				}
				font.setColor(0f,0f,0f,1f);
				if (current_tower == UPGRADE)
				{
					if(i == CASTLE)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.CASTLE.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i == HAMMER)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.HAMMER_BROS.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i == FLOWER)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.FLOWER.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				else if (current_tower == SELL)
				{
					if(i == CASTLE)
						font.draw(batch, "$"+sell_cost[TowerEnum.CASTLE.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i == HAMMER)
						font.draw(batch, "$"+sell_cost[TowerEnum.HAMMER_BROS.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i == FLOWER)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.FLOWER.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				else
				{
					if(i == CASTLE)
						font.draw(batch, "$"+CASTLE_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i == HAMMER)
						font.draw(batch, "$"+HAMMER_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i == FLOWER)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.FLOWER.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}

				batch.draw(tex[i],buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				font.setColor(1f,1f,1f,1f);

			}
			else if(mapID == POKEMON)
			{
				if((i >= NUM_UTIL_BUTTONS - 1) && (current_tower == i + NUM_MARIO_BUTTONS))
					batch.draw(white,buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				else if((i < NUM_UTIL_BUTTONS) && (current_tower == i))
					batch.draw(white,buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				
				font.setColor(0f,0f,0f,1f);
				if (current_tower == UPGRADE)
				{
					if(i + NUM_MARIO_BUTTONS == PSYCHIC)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.PSYCHIC.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == FIRE)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.FIRE.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == GRASS)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.GRASS.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == WATER)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.WATER.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				else if (current_tower == SELL)
				{
					if(i + NUM_MARIO_BUTTONS == PSYCHIC)
						font.draw(batch, "$"+sell_cost[TowerEnum.PSYCHIC.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == FIRE)
						font.draw(batch, "$"+sell_cost[TowerEnum.FIRE.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == GRASS)
						font.draw(batch, "$"+sell_cost[TowerEnum.GRASS.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == WATER)
						font.draw(batch, "$"+sell_cost[TowerEnum.WATER.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				else
				{
					if(i + NUM_MARIO_BUTTONS == PSYCHIC)
						font.draw(batch, "$"+PSYCHIC_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == FIRE)
						font.draw(batch, "$"+FIRE_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == GRASS)
						font.draw(batch, "$"+GRASS_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS == WATER)
						font.draw(batch, "$"+WATER_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				
				batch.draw(tex[i],buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				font.setColor(1f,1f,1f,1f);

			}
			else if(mapID == ZELDA)
			{
				if((i >= NUM_UTIL_BUTTONS - 1) && (current_tower == i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS))
					batch.draw(white,buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				else if((i < NUM_UTIL_BUTTONS) && (current_tower == i))
					batch.draw(white,buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				
				font.setColor(0f,0f,0f,1f);
				if (current_tower == UPGRADE)
				{
					if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == BOMB)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.BOMB.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == BOOMERANG)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.BOOMERANG.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == SLINGSHOT)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.SLINGSHOT.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == SWORD)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.SWORD.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				else if (current_tower == SELL)
				{
					if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == BOMB)
						font.draw(batch, "$"+sell_cost[TowerEnum.BOMB.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == BOOMERANG)
						font.draw(batch, "$"+sell_cost[TowerEnum.BOOMERANG.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == SLINGSHOT)
						font.draw(batch, "$"+sell_cost[TowerEnum.SLINGSHOT.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == SWORD)
						font.draw(batch, "$"+sell_cost[TowerEnum.SWORD.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				else
				{
					if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == BOMB)
						font.draw(batch, "$"+BOMB_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == BOOMERANG)
						font.draw(batch, "$"+BOOMERANG_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == SLINGSHOT)
						font.draw(batch, "$"+SLINGSHOT_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS == SWORD)
						font.draw(batch, "$"+SWORD_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				
				batch.draw(tex[i],buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				font.setColor(1f,1f,1f,1f);

			}
			else if(mapID == GALAGA)
			{
				if((i >= NUM_UTIL_BUTTONS - 1) && (current_tower == i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS))
					batch.draw(white,buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				else if((i < NUM_UTIL_BUTTONS) && (current_tower == i))
					batch.draw(white,buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				
				font.setColor(0f,0f,0f,1f);
				if (current_tower == UPGRADE)
				{
					if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G1)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.G1.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G2)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.G2.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G3)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.G3.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS  + NUM_ZELDA_BUTTONS == G4)
						font.draw(batch, "$"+upgrade_cost[TowerEnum.G4.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				else if (current_tower == SELL)
				{
					if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G1)
						font.draw(batch, "$"+sell_cost[TowerEnum.G1.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G2)
						font.draw(batch, "$"+sell_cost[TowerEnum.G2.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G3)
						font.draw(batch, "$"+sell_cost[TowerEnum.G3.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS  + NUM_ZELDA_BUTTONS == G4)
						font.draw(batch, "$"+sell_cost[TowerEnum.G4.index], buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				else
				{
					if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G1)
						font.draw(batch, "$"+G1_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G2)
						font.draw(batch, "$"+G2_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G3)
						font.draw(batch, "$"+G3_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
					else if(i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS == G4)
						font.draw(batch, "$"+G4_PRICE, buttons[i].x + buttons[i].width*1.25f,  buttons[i].y + buttons[i].height/2);
				}
				
				batch.draw(tex[i],buttons[i].x,buttons[i].y,buttons[i].width,buttons[i].height);
				font.setColor(1f,1f,1f,1f);

			}

		}
			
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
				{
					if(mapID == MARIO)
					{
						current_tower = i;
						return i;
					}
					else if(mapID == POKEMON)
					{
						if(i > 2)
						{
							current_tower = i + NUM_MARIO_BUTTONS;
							return i + NUM_MARIO_BUTTONS;
						}
						else
						{
							current_tower = i;
							return i;
						}
					}
					else if(mapID == ZELDA)
					{
						if(i > 2)
						{
							current_tower = i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS;
							return i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS;
						}
						else
						{
							current_tower = i;
							return i;
						}
					}
					else if(mapID == GALAGA)
					{
						if(i > 2)
						{
							current_tower = i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS;
							return i + NUM_MARIO_BUTTONS + NUM_POKEMON_BUTTONS + NUM_ZELDA_BUTTONS;
						}
						else
						{
							current_tower = i;
							return i;
						}
					}
				}
			}
		}
		
		return -1;
	}
}
