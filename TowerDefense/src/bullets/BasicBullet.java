package bullets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

public class BasicBullet extends Bullet
{
	
	// change WIDTH and HEIGHT for each new Bullet
	private final int WIDTH = 8, HEIGHT = 8;
	private Texture tex;
	
	public BasicBullet(Enemy target, float center_x, float center_y)
	{
		this.center_x = center_x;
		this.center_y = center_y;
		collider = new Rectangle(center_x, center_y, WIDTH, HEIGHT);
		this.target = target;
		active = true;
		
		// attributes - change for each new Bullet
		tex = new Texture("data/bullets/bullet.png");
		
		damage = 50;
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
			if (target == null)
				active = false;
			else
			{
				float xE = (target.getCollider().getWidth()/2 + target.getX()) - center_x;
				float yE = (target.getCollider().getHeight()/2 + target.getY()) - center_y;
				float hE = (float)Math.sqrt(xE*xE + yE*yE);
				collider.x += (xE/hE)*speed;
				collider.y += (yE/hE)*speed;
				if (collider.overlaps(target.getCollider()))
				{// hit target
					target.subHealth(damage);
					active = false;
				}
			}
		}
	}
	
}
