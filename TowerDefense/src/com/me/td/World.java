package com.me.td;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GLU;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class World {
	
	int health;
	int gold;
	Map map;
	ArrayList<Enemy> enemies;
	ArrayList<Tower> towers;
	OrthographicCamera camera;
	BitmapFont font;
	
	//These variables are used for the demo
	boolean enableEnemySpawn;
	boolean enableTowerSpawn;
	
	public World(OrthographicCamera camera)
	{
		health = 100;
		gold = 0;
		map = new BasicMap();
		enableEnemySpawn = true;
		enableTowerSpawn = true;
		enemies = new ArrayList<Enemy>();
		towers = new ArrayList<Tower>();
		this.camera = camera;
		font = new BitmapFont();
		
	}
	
	
	public void render(SpriteBatch batch)
	{
		//Render map
		map.render(batch);
		
		//Render enemies.
		for(int i = 0; i < enemies.size();i++)
		{
			enemies.get(i).render(batch);
		}
		
		//Render towers/bullets.
		for(int i = 0; i < towers.size(); i++)
		{
			towers.get(i).render(batch);
		}
		
		//Render UI
		font.draw(batch, "Enemy Count: "+enemies.size(),600,100);
		font.draw(batch, "Tower Count: "+towers.size(), 600, 80);
		font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(),600,60);
		
		
	}
	
	public void update()
	{
		//Spawn an Enemy if the Space Bar is pressed
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			if(enableEnemySpawn)
			{
				enemies.add(new BasicEnemy(this));
				enableEnemySpawn = false;
			}
		}
		else
			enableEnemySpawn = true;
		
		//Spawn a tower on the position that is clicked
		if(Gdx.input.isTouched())
		{
			if(enableTowerSpawn)
			{
				Vector3 touchPos = new Vector3();
			    touchPos.set(Gdx.input.getX() - 16, Gdx.input.getY(), 0);
			    camera.unproject(touchPos);
			    towers.add(new BasicTower(this,touchPos.x,touchPos.y));
			    enableTowerSpawn = false;
			}
		}
		else
			enableTowerSpawn = true;
		
		//Update the enemy Array	
		for(int i = 0; i < enemies.size();i++)
		{
			enemies.get(i).update();
			if(enemies.get(i).isDead())
			{
				enemies.remove(i);
				i--;
			}
		}
		
		//Update the Tower Array
		for(int i = 0; i < towers.size(); i++)
		{
			towers.get(i).update();
		}
		
	}
	
}
