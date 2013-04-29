package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Knight extends Enemy
{
	
	public Knight(Point[] waypoints, float difficulty, AssetManager manager)
	{
		super(waypoints);
		
		name = "Knight";
		width = 28;
		height = 42;
		health = 1000;
		speed = 2.0f;
		damage = 30;
		gold_given = 50;
		animation_speed = 10;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/zknight1.png");
		tex[1] = manager.get("data/enemies/zknight2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}