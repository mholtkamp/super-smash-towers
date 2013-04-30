package towers;

import bullets.GalagaThermo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;


public class G2Tower extends Tower
{
	
	public G2Tower()
	{
		// attributes
		name = "G2Tower";
		width = 40;
		height = 40;
		range = 130;
		cost = 800;
		firing_speed = 2.0f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
	}
	
	public G2Tower(ArrayList<Enemy> enemies, float x, float y, AssetManager manager)
	{
		super(enemies, manager);
		
		// attributes
		name = "G2Tower";
		width = 40;
		height = 40;
		range = 130;
		cost = 800;
		firing_speed = 2.0f;	// shoot every x seconds
		upgradecost = cost * 3 / 4;
		
		damagemultiplier = 2;
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex = new Texture[3];
		tex[0] = this.manager.get("data/towers/galagaTower2.png");
		tex[1] = this.manager.get("data/towers/galagaTower2Upgrade.png");
		tex[2] = this.manager.get("data/towers/galagaTower2Upgrade.png");
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
					bullets.add(new GalagaThermo(target, center_x, center_y, damagemultiplier,enemies, manager));
					time_since_last_shot = 0;
					break;
				}
			}
		}
	}
	
}
