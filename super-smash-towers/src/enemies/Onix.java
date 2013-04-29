package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Onix extends Enemy
{
	
	public Onix(Point[] waypoints, float difficulty, AssetManager manager)
	{
		super(waypoints);
		
		name = "Onix";
		width = 64;
		height = 64;
		health = 50000;
		speed = 1.5f;
		damage = 100;
		gold_given = 50;
		animation_speed = 10;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/Onix1.png");
		tex[1] = manager.get("data/enemies/Onix2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}