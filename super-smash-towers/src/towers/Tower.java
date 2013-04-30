package towers;

import bullets.Bullet;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
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
	protected int level, max_level, upgradecost, current_tex;
	protected AssetManager manager;
	protected Texture[] tex;
	protected boolean right;
	
	public Tower()
	{
		// used for getting stats only
	}
	
	public Tower(ArrayList<Enemy> enemies, AssetManager manager)
	{
		this.enemies = enemies;
		bullets = new ArrayList<Bullet>();
		target = null;
		this.manager = manager;
		time_since_last_shot = 0;
		current_tex = 0;
		damagemultiplier = 1;
		level = 1;
	}
	
	public int getLevel() {return level;}
	
	public int getUpgradeCost() {return upgradecost;}
	
	public float getX() {return collider.x;}
	
	public float getY() {return collider.y;}
	
	public float getRange() {return range;}
	
	public int getCost() {return cost;}
	
	public int getSellCost() {return (cost + upgradecost * (level - 1)) * 2 / 3;}
	
	public String getName() {return name;}
	
	public boolean face_left()
	{// returns true if target is to the left of Tower or target is null
	 // otherwise, returns false
		if (target == null || target.getX() <= center_x)
			return true;
		else
			return false;
	}
	
	public void levelUp()
	{
		level++;
		damagemultiplier++;
		current_tex++;
		firing_speed = firing_speed*0.75f;
		range = range*1.25f;
	}
	
	public abstract void update();
	
	public void render(SpriteBatch batch)
	{
		// face left?
		right = face_left() ? false : true;
		
		// render Tower
		batch.draw(tex[current_tex], collider.x, collider.y, (float)width, (float)height, 0, 0, width, height, right, false);
		
		// render Bullets
		for (Bullet bullet : bullets)
			bullet.render(batch);
	}
	
}
