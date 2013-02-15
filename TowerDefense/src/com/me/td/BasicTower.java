package com.me.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BasicTower implements Tower{

	float range;
	Rectangle collider;
	Rectangle bulletCollider;
	Texture towerTex;
	Texture bulletTex;
	Enemy target;
	boolean bulletAlive;
	float timeSinceLastShot;
	float bulletSpeed;
	int bulletDamage;
	float centerX;
	float centerY;
	
	World world;
	
	public BasicTower(World world,float x,float y)
	{
		centerX = x + 16;
		centerY = y + 32;
		range = 250;
		bulletAlive = false;
		bulletSpeed = 10f;
		bulletDamage = 50;
		timeSinceLastShot = 0;
		collider = new Rectangle(x,y,32,64);
		bulletCollider = new Rectangle(centerX,centerY,8,8);
		towerTex = new Texture("data/BasicTower.png");
		bulletTex = new Texture("data/BasicTowerBullet.png");

		this.world = world;
	}
	
	public float getX()
	{
		return collider.x;
	}
	public float getY()
	{
		return collider.y;
	}
	public float getRange()
	{
		return range;
	}
	public void update()
	{
		timeSinceLastShot += Gdx.graphics.getDeltaTime();
		if(timeSinceLastShot > 2)
		{
			target = null;
			bulletCollider.x = centerX;
			bulletCollider.y = centerY;
			for(int i = 0; i < world.enemies.size(); i++)
			{
				float xE = (centerX - world.enemies.get(i).getX());
				float yE = (centerY - world.enemies.get(i).getY());
				if(Math.sqrt(xE*xE + yE*yE) < range)
				{
					target = world.enemies.get(i);
					bulletAlive = true;
					timeSinceLastShot = 0f;
					break;
				}
			}
		}
		if(bulletAlive)
		{
			if(target == null)
			{
				bulletAlive = false;
				bulletCollider.x = centerX;
				bulletCollider.y = centerY;
			}
			else
			{
				float xE = (target.getCollider().getWidth()/2 + target.getX()) - centerX;
				float yE = (target.getCollider().getHeight()/2 + target.getY()) - centerY;
				float hE = (float)Math.sqrt(xE*xE + yE*yE);
				bulletCollider.x += (xE/hE)*bulletSpeed;
				bulletCollider.y += (yE/hE)*bulletSpeed;
				if(bulletCollider.overlaps(target.getCollider()))
				{
					//HIT
					target.subHealth(bulletDamage);
					bulletAlive = false;
					bulletCollider.x = centerX;
					bulletCollider.y = centerY;
				}
			}
		}
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(towerTex, collider.x, collider.y);
		if(bulletAlive)
			batch.draw(bulletTex,bulletCollider.x,bulletCollider.y);
	}
}
