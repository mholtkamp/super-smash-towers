package towers;

import bullets.Bullet;
import bullets.FireBall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;


public class FlowerTower extends Tower
{

	private final int CALLS_BETWEEN_TOGGLE = 15;
	private int tex_count;
	private Texture[] texDown, texUp;
	private boolean down;
	
	public FlowerTower()
	{
		// attributes
		name = "FlowerTower";
		width = 40;
		height = 50;
		range = 200;
		cost = 200;
		firing_speed = 1.5f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
	}
	
	public FlowerTower(ArrayList<Enemy> enemies, float x, float y, AssetManager manager)
	{
		super(enemies, manager);
		
		// attributes
		name = "FlowerTower";
		width = 40;
		height = 50;
		range = 200;
		cost = 200;
		firing_speed = 1.5f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		texDown = new Texture[3];
		texUp = new Texture[3];
		texDown[0] = this.manager.get("data/towers/firetower.png");
		texDown[1] = this.manager.get("data/towers/fireUpgrade.png");
		texDown[2] = this.manager.get("data/towers/fireUpgrade.png");
		texUp[0] = this.manager.get("data/towers/firetower2.png");
		texUp[1] = this.manager.get("data/towers/fireUpgrade2.png");
		texUp[2] = this.manager.get("data/towers/fireUpgrade2.png");
		
		down = true;
		tex_count = 0;
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
					bullets.add(new FireBall(target, center_x, center_y, damagemultiplier, manager));
					time_since_last_shot = 0;
					break;
				}
			}
		}
	}
	
	public void render(SpriteBatch batch)
	{
		// render Tower
		tex_count++;

		if (tex_count == CALLS_BETWEEN_TOGGLE)
		{
			down = false;
		}
		if (tex_count == 2*CALLS_BETWEEN_TOGGLE)
		{
			down = true;
			tex_count = 0;
		}

		if(down)
			batch.draw(texDown[current_tex], collider.x, collider.y);
		else
			batch.draw(texUp[current_tex], collider.x, collider.y);

		// render Bullets
		for (Bullet bullet : bullets)
			bullet.render(batch);
	}
	
}
