package towers;

import bullets.BasicBullet;
import bullets.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.me.td.World;

import enemies.Enemy;

import java.util.ArrayList;


public class BasicTower extends Tower
{

//	private final float SOUND_VOLUME = 0.5f;
	private Texture tex;
	private Sound shoot;
	
	public BasicTower()
	{
		// attributes
		name = "Castle";
		width = 40;
		height = 50;
		range = 200;
		cost = 250;
		firing_speed = 1.5f;	// shoot every x seconds		
		upgradecost = cost * 3 / 4;
	}
	
	public BasicTower(ArrayList<Enemy> enemies, float x, float y, AssetManager manager)
	{
		super(enemies, x, y, manager);
		
		// attributes
		name = "Castle";
		width = 40;
		height = 50;
		range = 200;
		cost = 250;
		firing_speed = 1.5f;	// shoot every x seconds		
		upgradecost = cost * 3 / 4;
		
		damagemultiplier = 1;
		level = 1;
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex = this.manager.get("data/towers/tower.png");
		/*tex = new Texture[3];
		 * tex[0] = new Texture("data/towers/tower.png");
		 * tex[1] = new Texture("data/towers/tower_lvl2.png");
		 * tex[2] = new Texture("data/towers/tower_lvl3.png");
		 */
		shoot = this.manager.get("sounds/firework.mp3");
	}
	
	public void levelUp()
	{
		level++;
		damagemultiplier++;
		firing_speed = firing_speed*0.75f;
		range = range*1.25f;		
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
					shoot.play(World.volume);
					bullets.add(new BasicBullet(enemies.get(i), center_x, center_y, damagemultiplier, manager));
					time_since_last_shot = 0;
					break;
				}
			}
		}
	}
	
	public void render(SpriteBatch batch)
	{
		/* different textures for each level of tower
		 * if (level = 1)
		 * { batch.draw(tex[0], collider.x, collider.y; }
		 * if (level = 2)
		 * { batch.draw(tex[1], collider.x, collider.y; }
		 * if (level = 3)
		 * { batch.draw(tex[2], collider.x, collider.y; }
		 */
		batch.draw(tex, collider.x, collider.y);
		for (Bullet bullet : bullets)
			bullet.render(batch);
	}
}
