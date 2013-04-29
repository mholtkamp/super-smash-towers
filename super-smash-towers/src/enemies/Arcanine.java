package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Arcanine extends Enemy
{
	
	public Arcanine(Point[] waypoints, float difficulty, AssetManager manager)
	{
		super(waypoints);
		
		name = "Arcanine";
		width = 35;
		height = 34;
		health = 200;
		speed = 1.5f;
		damage = 20;
		gold_given = 50;
		animation_speed = 10;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/arcanine_right_1.png");
		tex[1] = manager.get("data/enemies/arcanine_right_2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
