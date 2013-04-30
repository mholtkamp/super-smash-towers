package enemies;
import com.me.td.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Gannon extends Enemy
{
	
	static float maxHealth=50000;
 	
 	public Gannon(Point[] waypoints, float difficulty, AssetManager manager)
 	{
		super(waypoints,maxHealth*difficulty);
		
		name = "Gannon";
		width = 35;
		height = 34;
		health = (int)(maxHealth*difficulty);
		speed = 1.0f;
		damage = 100;
		gold_given = 50;
		animation_speed = 10;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/gannon1.png");
		tex[1] = manager.get("data/enemies/gannon2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
