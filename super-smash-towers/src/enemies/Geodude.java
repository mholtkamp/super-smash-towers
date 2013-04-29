package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Geodude extends Enemy
{
	static int maxHealth = 200;
	public Geodude(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints, maxHealth);
		
		name = "Geodude";
		width = 49;
		height = 27;
		health = maxHealth;
		speed = 1.5f;
		damage = 20;
		gold_given = 50;
		animation_speed = 7;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/geodude_right_1.png");
		tex[1] = manager.get("data/enemies/geodude_right_2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
