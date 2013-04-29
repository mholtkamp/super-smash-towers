package bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;


public abstract class Bullet
{
	
	protected Rectangle collider;
	protected Enemy target;
	protected boolean active;
	protected float speed, center_x, center_y;
	protected int damage;
	
	public float getX() {return collider.x;}
	
	public float getY() {return collider.y;}
	
	public boolean isActive() {return active;}
	
	public abstract void update();
	
	public abstract void render(SpriteBatch batch);
	
}
