package com.me.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.math.*;

public class BasicEnemy implements Enemy {

	private World world;
	private Rectangle collider;
	private int health;
	private Texture tex;
	private float timer;
	private float speed;
	private int curWayPoint;
	private boolean dead;
	public BasicEnemy(World world)
	{
		this.world = world;
		health = 100;
		tex = new Texture("data/BasicEnemy.png");
		collider = new Rectangle((float)world.map.getWayPoint(1).getX(),(float)world.map.getWayPoint(1).getY(),32,32);
		timer = 0f;
		speed = 2f;
		curWayPoint = 1;
		dead = false;
	}
	
	public int getHealth()
	{
		return health;
	}
	public void subHealth(int negHealth)
	{
		health -= negHealth;
		if(health <= 0)
			dead = true;
	}
	
	public float getX()
	{
		return collider.x;
	}
	
	public float getY()
	{
		return collider.y;
	}
	
	public void render(SpriteBatch batch)
	{
		if(!dead)
			batch.draw(tex,collider.x,collider.y,40,40);
	}
	
	public void update()
	{
		timer += Gdx.graphics.getDeltaTime();
		float xE = (float) (world.map.getWayPoint(curWayPoint + 1).getX() - world.map.getWayPoint(curWayPoint).getX());
		float yE = (float) (world.map.getWayPoint(curWayPoint + 1).getY() - world.map.getWayPoint(curWayPoint).getY());
		float hE = (float) Math.sqrt(xE*xE + yE*yE);
		collider.x = collider.x + (xE/hE)*speed;
		collider.y = collider.y + (yE/hE)*speed;
		
		if(Math.abs(collider.x -((float) (world.map.getWayPoint(curWayPoint + 1).getX())))< 2)
		{
			if(Math.abs(collider.y -((float) (world.map.getWayPoint(curWayPoint + 1).getY())))< 2)
			{
				curWayPoint++;
			}
		}
		
		if(curWayPoint == world.map.getWayPointCount())
		{
			world.health -= 10;
			dead = true;
		}
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public Rectangle getCollider()
	{
		return collider;
	}
}
