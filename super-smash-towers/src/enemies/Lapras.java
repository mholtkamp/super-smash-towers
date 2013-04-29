package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Lapras extends Enemy
{
	static int maxHealth=95000;
 	
 	public Lapras(Point[] waypoints, float difficulty, AssetManager manager)
 	{
		super(waypoints,maxHealth);
		
		name = "Lapras";
		width = 35;
		height = 52;
		health = 95000;
		speed = 1.5f;
		damage = 50;
		gold_given = 80;
		animation_speed = 20;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/Lapras1.png");
		tex[1] = manager.get("data/enemies/Lapras2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
