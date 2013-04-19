package towers;

import bullets.Bullet;
import bullets.FireBall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;


public class FireTower extends Tower
{

	private final int CALLS_BETWEEN_TOGGLE = 15;
	private int current_tex, toggle_count;
	private Texture[] tex;
	
	public FireTower(ArrayList<Enemy> enemies, float x, float y)
	{
		super(enemies, x, y);
		
		// attributes - change for each new Tower
		name = "FireTower";
		width = 40;
		height = 48;
		range = 200;
		cost = 500;
		firing_speed = 2.0f;	// shoot every x seconds
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex = new Texture[2];
		tex[0] = new Texture("data/towers/fire_tower_1.png");
		tex[1] = new Texture("data/towers/fire_tower_2.png");
		current_tex = 0;
		toggle_count = CALLS_BETWEEN_TOGGLE;
	}
	
	public void update()
	{
		for (int i = 0; i < bullets.size(); i++)
		{
			bullets.get(i).update();
			if (!bullets.get(i).isActive())
				bullets.remove(i--);
		}
		time_since_last_shot += Gdx.graphics.getDeltaTime();
		if (time_since_last_shot > firing_speed)
		{
			for (int i = 0; i < enemies.size(); i++)
			{
				float xE = (center_x - enemies.get(i).getX());
				float yE = (center_y - enemies.get(i).getY());
				if (Math.sqrt(xE*xE + yE*yE) < range)
				{
					target = enemies.get(i);
					bullets.add(new FireBall(target, center_x, center_y));
					time_since_last_shot = 0;
					break;
				}
			}
		}
	}
	
	public void render(SpriteBatch batch)
	{
		// render Tower
		batch.draw(tex[current_tex], collider.x, collider.y);
		
		// toggle textures
		if (--toggle_count < 0)
		{
			current_tex = (current_tex + 1) % tex.length;
			toggle_count = CALLS_BETWEEN_TOGGLE;
		}
		
		// render Bullets
		for (Bullet bullet : bullets)
			bullet.render(batch);
	}
}
