package towers;

import bullets.Bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;


public abstract class Tower
{

	protected int width, height, cost;
	protected float range, firing_speed, center_x, center_y, time_since_last_shot;
	protected Rectangle collider;
	protected ArrayList<Enemy> enemies;
	protected ArrayList<Bullet> bullets;
	protected Enemy target;
	protected String name;
	protected int damagemultiplier;
	protected int level, upgradecost;
	
	public Tower(ArrayList<Enemy> enemies, float x, float y)
	{
		this.enemies = enemies;
		bullets = new ArrayList<Bullet>();
		time_since_last_shot = 0;
		target = null;
	}
	
	public int getLevel() {return level;}
	
	public int getUpgradeCost() {return upgradecost;}
	
	public float getX() {return collider.x;}
	
	public float getY() {return collider.y;}
	
	public float getRange() {return range;}
	
	public int getCost() {return cost;}
	
	public String getName() {return name;}
	
	public boolean face_left()
	{// returns true if target is to the left of Tower or target is null
	 // otherwise, returns false
		if (target == null || target.getX() <= center_x)
			return true;
		else
			return false;
	}
	
	public abstract void levelUp();
	
	public abstract void update();
	
	public abstract void render(SpriteBatch batch);
	
}
