package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;

import com.me.td.Options;
import com.me.td.TDGame;
import com.me.td.World;

import enums.Type;

import util.Point;


public abstract class Enemy
{
	
	private final int WIDTH = 740, HEIGHT = 400;
	private final float WAYPOINT_ACCURACY = 1.0f;
	private float xE, yE, hE;
	
	protected Point[] waypoints;
	protected Texture[] tex;
	public Rectangle collider;
	protected int width, height, health, cur_waypoint, damage, gold_given, cur_tex, tex_count, animation_speed, burn_count;
	protected float speed, speed_multiplier;
	protected boolean dead, hit_tower, toggle, left, attack, can_attack;
	public boolean burning;
	protected String name;
	private float	maxHealth;	// enemy dmg bar
	private AssetManager manager = TDGame.manager;
	protected Texture red, green, burning_tex;
	protected Type type;
	public Timer burn_timer;
	
	public float getX() {return collider.x;}
	
	public float getY() {return collider.y;}
	
	public int getHealth() {return health;}
	
	public int getGold() {return gold_given;}
	
	public Type getType() {return type;}
	
	public Rectangle getCollider() {return collider;}
	
	public String getName() {return name;}
	
	public boolean isDead() {return dead;}
	
	public void kill() {this.dead = true;}
	
	public void burn()
	{
		if (!burning)
		{
			this.burning = true;
			burn_count = 5;
			Timer.schedule(new burnTask(), 1, 1, 5);
		}
	}
	
	private class burnTask extends Timer.Task
	{
		
		public void run()
		{
			if (burn_count-- <= 0)
			{
				burning = false;
			}
			else
				subHealth(10);
		}
		
	}
	
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
	
	public Enemy(Point[] waypoints, float maxHealth)
	{
		this.waypoints = waypoints;
		cur_waypoint = 0;
		cur_tex = 0;
		tex_count = 0;
		dead = false;
		hit_tower = false;
		toggle = false;
		left = false;
		attack = false;
		can_attack = false;
		burning = false;
		speed_multiplier = 1.0f;
		this.maxHealth = maxHealth;
		red = manager.get("data/redfade.png");
		green = manager.get("data/greenfade.png");
		burning_tex = manager.get("data/bullets/burning.png");
		type = Type.NEUTRAL;
		burn_timer = new Timer();
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
		{
			if (Math.abs(collider.y - (float) (waypoints[cur_waypoint+1].getY())) < WAYPOINT_ACCURACY)
			{
				cur_waypoint++;
				if (cur_waypoint + 2 >= waypoints.length)
					attack = true;
			}
		}
		
		if (cur_waypoint + 1 >= waypoints.length)
		{
			hit_tower = true;
			dead = true;
		}
		
		if (cur_waypoint > 0)
		{
			if (collider.x > WIDTH || collider.y > HEIGHT || collider.x + collider.width < 0 || collider.y + collider.y < 0)
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
			if (can_attack && attack)
			{
				batch.setColor(1, 0, 0, 1);
				batch.draw(tex[cur_tex], collider.x, collider.y, (float)width, (float)height, 0, 0, width, height, left, false);
				batch.setColor(1, 1, 1, 0.99607843f);
			}
			else
				batch.draw(tex[cur_tex], collider.x, collider.y, (float)width, (float)height, 0, 0, width, height, left, false);
			
			if (burning)
				batch.draw(burning_tex, collider.x, collider.y, (float)width, (float)height);
			//TODO damage bar
			//DamageBAR on enemies
			if (Options.isEnemyHealthDisplay())
			{
				batch.draw(red,collider.x,collider.y-10, (float)width, 10);
				batch.draw(green,collider.x,collider.y-10, (health/maxHealth)*(float)width, 10);
				//System.out.println(health);
				System.out.println("bowsermax"+maxHealth);
//
//				System.out.println("bowserglobal"+World.globaldiff);
//
//				System.out.println("bowserdiff"+difficulty);
//				

				System.out.println("bowserHealth"+health);
			}
		}
	}
	
}
