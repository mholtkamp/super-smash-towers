package bullets;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

public class BillBullet extends Bullet
{
	private ArrayList<Enemy> enemies;
	// change WIDTH and HEIGHT for each new Bullet
	private final int WIDTH = 8, HEIGHT = 8;
	private Texture tex;
	private int orientation; 
	
	public BillBullet(ArrayList<Enemy> enemies,int orientation,float center_x, float center_y, int damagemultiplier,AssetManager manager)
	{
		this.orientation = orientation;
		this.center_x = center_x;
		this.center_y = center_y;
		collider = new Rectangle(center_x, center_y, WIDTH, HEIGHT);
		active = true;
		this.enemies = enemies;
		// attributes - change for each new Bullet
		tex = manager.get("data/bullets/bullet.png");
		
		damage = 30*damagemultiplier;
		speed = 10;
	}
	
	public void render(SpriteBatch batch)
	{
		if (active)
			batch.draw(tex, collider.x, collider.y);
	}
	
	public void update()
	{
		if (active)
		{
			if(orientation == 0)
				collider.x += -1*speed;
			else if (orientation == 1)
				collider.y += -1*speed;
			else if (orientation == 2)
				collider.x += speed;
			else if (orientation == 3)
				collider.y += speed;
			
			for(int i = 0; i < enemies.size(); i++)
			if (collider.overlaps(enemies.get(i).getCollider()))
			{// hit target
				enemies.get(i).subHealth(damage);
			}
			
			
			if((collider.x + collider.width < 0) || (collider.x > 600) || (collider.y > 400) || (collider.y + collider.height < 0))
				active = false;
		}
	}
}
	