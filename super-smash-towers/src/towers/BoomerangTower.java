package towers;

import bullets.Boomerang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;


public class BoomerangTower extends Tower
{
	
	public BoomerangTower()
	{
		// attributes
		name = "BoomerangTower";
		width = 40;
		height = 48;
		range = 130;
		cost = 400;
		firing_speed = 2.0f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
	}
	
	public BoomerangTower(ArrayList<Enemy> enemies, float x, float y, AssetManager manager)
	{
		super(enemies, manager);
		
		// attributes
		name = "BoomerangTower";
		width = 40;
		height = 50;
		range = 200;
		cost = 400;
		firing_speed = 1.5f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex = new Texture[3];
		tex[0] = this.manager.get("data/towers/BoomerangTower.png");
		tex[1] = this.manager.get("data/towers/BoomerangTowerUpgrade.png");
		tex[2] = this.manager.get("data/towers/BoomerangTowerUpgrade.png");
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
					bullets.add(new Boomerang(target, center_x, center_y, damagemultiplier, manager));
					time_since_last_shot = 0;
					break;
				}
			}
		}
	}
	
}
