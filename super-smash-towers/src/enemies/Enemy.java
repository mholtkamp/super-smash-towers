package enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public abstract class Enemy
{
	
	private final float WAYPOINT_ACCURACY = 1.0f;
	private float xE, yE, hE;
	
	protected Point[] waypoints;
	protected Texture[] tex;
	protected Rectangle collider;
	protected int width, height, health, cur_waypoint, damage, gold_given, cur_tex, tex_count, animation_speed;
	protected float speed, speed_multiplier;
	protected boolean dead, hit_tower, toggle, left;
	protected String name;
	
	public float getX() {return collider.x;}
	
	public float getY() {return collider.y;}
	
	public int getHealth() {return health;}
	
	public int getGold() {return gold_given;}
	
	public Rectangle getCollider() {return collider;}
	
	public String getName() {return name;}
	
	public boolean isDead() {return dead;}
	
	public void kill() {this.dead = true;}
	
	public void changeSpeedMultiplier(float new_speed_multiplier)
	{
		speed_multiplier = new_speed_multiplier;
	}
	
	public int dealsDamage()
	{
		if (hit_tower)
			return damage;
		else
			return -1;
	}
	
	public void subHealth(int negHealth)
	{
		health -= negHealth;
		if (health <= 0)
			dead = true;
	}
	
	private boolean face_left()
	{
		if (Math.abs(collider.x - waypoints[cur_waypoint+1].getX()) < WAYPOINT_ACCURACY)
			return left;
		if (collider.x > waypoints[cur_waypoint+1].getX())
			return true;
		return false;
	}
	
	public Enemy(Point[] waypoints)
	{
		this.waypoints = waypoints;
		cur_waypoint = 0;
		cur_tex = 0;
		tex_count = 0;
		dead = false;
		hit_tower = false;
		toggle = false;
		left = false;
		speed_multiplier = 1.0f;
	}
	
	public void update()
	{
		left = face_left();
		
		xE = (float) (waypoints[cur_waypoint+1].getX() - waypoints[cur_waypoint].getX());
		yE = (float) (waypoints[cur_waypoint+1].getY() - waypoints[cur_waypoint].getY());
		hE = (float) Math.sqrt(xE*xE + yE*yE);
		
		collider.x = collider.x + (xE/hE)*speed*speed_multiplier;
		collider.y = collider.y + (yE/hE)*speed*speed_multiplier;
		
		if (Math.abs(collider.x - (float) (waypoints[cur_waypoint+1].getX())) < WAYPOINT_ACCURACY)
			if (Math.abs(collider.y - (float) (waypoints[cur_waypoint+1].getY())) < WAYPOINT_ACCURACY)
				cur_waypoint++;
		
		if (cur_waypoint + 1 >= waypoints.length)
		{
			hit_tower = true;
			dead = true;
		}
	}
	
	public void render(SpriteBatch batch, boolean paused)
	{
		if (!dead)
		{
			if (!paused)
			{
				if (toggle)
				{
					cur_tex = (cur_tex + 1) % tex.length;
					toggle = false;
				}
				if (tex_count++ >= animation_speed)
				{
					toggle = true;
					tex_count = 0;
				}
			}
//			batch.draw(tex[cur_tex], collider.x, collider.y, width, height);
			batch.draw(tex[cur_tex], collider.x, collider.y, (float)width, (float)height, 0, 0, width, height, left, false);
		}
	}
	
}
