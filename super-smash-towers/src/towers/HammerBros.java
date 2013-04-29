package towers;

import bullets.Bullet;
import bullets.Hammer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;


public class HammerBros extends Tower
{

	private Texture[] tex_left, tex_right;
	private int current_tex;
	
	public HammerBros()
	{
		// attributes
		name = "HammerBros";
		width = 40;
		height = 48;
		range = 150;
		cost = 500;
		firing_speed = 1.0f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
	}
	
	public HammerBros(ArrayList<Enemy> enemies, float x, float y, AssetManager manager)
	{
		super(enemies, x, y, manager);
		
		// attributes
		name = "HammerBros";
		width = 40;
		height = 48;
		range = 150;
		cost = 500;
		firing_speed = 1.0f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
		
		damagemultiplier = 1;
		level = 1;
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex_left = new Texture[3];
		tex_right = new Texture[3];
		
		tex_left[0] = this.manager.get("data/towers/hammer_bros_left_idle.png");
		tex_right[0] = this.manager.get("data/towers/hammer_bros_right_idle.png");
		tex_left[1] = this.manager.get("data/towers/upgradedBroleft.png");
		tex_right[1] = this.manager.get("data/towers/upgradedBroright.png");
		tex_left[2] = this.manager.get("data/towers/upgradedBroleft.png");
		tex_right[2] = this.manager.get("data/towers/upgradedBroright.png");
		
		current_tex = 0;
	}
	
	
	public void levelUp()
	{
		level++;
		damagemultiplier++;
		firing_speed = firing_speed*0.75f;
		range = range*1.25f;	
		current_tex++;
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
					bullets.add(new Hammer(target, center_x, center_y, damagemultiplier, manager));
					time_since_last_shot = 0;
					break;
				}
			}
		}
	}
	
	public void render(SpriteBatch batch)
	{
		// render Tower
		if (face_left())
			batch.draw(tex_left[current_tex], collider.x, collider.y);
		else
			batch.draw(tex_right[current_tex], collider.x, collider.y);
		
		// render Bullets
		for (Bullet bullet : bullets)
			bullet.render(batch);
	}
}
