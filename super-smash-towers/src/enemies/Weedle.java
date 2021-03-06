package enemies;
import com.me.td.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.me.td.World;

import enums.Type;

import util.Point;


public class Weedle extends Enemy
{
	static float maxHealth = 225;
	public Weedle(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints, maxHealth*difficulty);
		
		name = "Weedle";
		width = 40;
		height = 28;
		health = (int)(maxHealth*difficulty);
		speed = 1.5f;
		damage = (int)(10);
		gold_given = 25;
		animation_speed = 7;
		type = Type.GRASS;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/weedle_right_1.png");
		tex[1] = manager.get("data/enemies/weedle_right_2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
