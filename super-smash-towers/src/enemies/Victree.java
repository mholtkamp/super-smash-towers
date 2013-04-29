package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Victree extends Enemy
{
	
	public Victree(Point[] waypoints, float difficulty, AssetManager manager)
	{
		super(waypoints);
		
		name = "Victree";
		width = 64;
		height = 64;
		health = 9500;
		speed = 1.5f;
		damage = 100;
		gold_given = 50;
		animation_speed = 10;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/Victree1.png");
		tex[1] = manager.get("data/enemies/Victree2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
