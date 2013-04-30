package towers;

import bullets.GalagaThermo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;


public class G4Tower extends Tower
{
	
	public G4Tower()
	{
		// attributes
		name = "G4Tower";
		width = 40;
		height = 48;
		range = 110;
		cost = 2000;
		firing_speed = 2.0f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
		max_level = 2;
	}
	
	public G4Tower(ArrayList<Enemy> enemies, float x, float y, AssetManager manager)
	{
		super(enemies, manager);
		
		// attributes
		name = "G4Tower";
		width = 40;
		height = 48;
		range = 110;
		cost = 2000;
		firing_speed = 2.0f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
		max_level = 2;
		
		damagemultiplier = 2;
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex = new Texture[3];
		tex[0] = this.manager.get("data/towers/GalagaTower4.png");
		tex[1] = this.manager.get("data/towers/GalagaTower4Upgrade.png");
		tex[2] = this.manager.get("data/towers/GalagaTower4Upgrade.png");
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
					bullets.add(new GalagaThermo(target, center_x, center_y, damagemultiplier, enemies, manager));
					time_since_last_shot = 0;
					break;
				}
			}
		}
	}
	
}
