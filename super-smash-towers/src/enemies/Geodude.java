package enemies;
import com.me.td.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enums.Type;

import util.Point;


public class Geodude extends Enemy
{
	static float maxHealth = 350;
	public Geodude(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints, maxHealth*difficulty);
		
		name = "Geodude";
		width = 49;
		height = 27;
		health = (int)(maxHealth*difficulty);
		speed = 1.5f;
		damage = (int)(20*difficulty);
		gold_given = 50;
		animation_speed = 7;
		type = Type.ROCK;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/geodude_right_1.png");
		tex[1] = manager.get("data/enemies/geodude_right_2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
