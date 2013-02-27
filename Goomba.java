package com.me.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.math.*;

public class Goomba implements Enemy {

  private World world;
	private Rectangle collider;
	private int health, curWayPoint, tex_count;
	private Texture tex_right, tex_left;
	private float timer, speed;
	private boolean dead, is_tex_right;
	public Goomba(World world)
	{
		this.world = world;
		health = 100;
		tex_right = new Texture("data/goomba_right.png");
		tex_left = new Texture("data/goomba_left.png");
		is_tex_right = true;
		tex_count = 0;
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
		{
			// by toggling between left and right goomba sprites, we can create
			// the appearance of the goomba walking
			if (is_tex_right)
				batch.draw(tex_right,collider.x,collider.y,40,40);	// right sprite
			else
				batch.draw(tex_left,collider.x,collider.y,40,40);	// left sprite
			
			if (++tex_count > 7)
			{// 7 is an arbitrary number, it signifies that 7 calls are made to
			 // the render() method before we toggle between left and right sprites
				is_tex_right = !is_tex_right;	// toggle between left and right sprites
				tex_count = 0;	// reset count
			}
		}
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
