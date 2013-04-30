package enemies;
import com.me.td.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class ShyGuy extends Enemy
{
	static float maxHealth = 650;
	public ShyGuy(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints, maxHealth*difficulty);
		
		name = "ShyGuy";
		width = 32;
		height = 32;
		health = (int)(maxHealth*difficulty);
		speed = 2.0f;
		damage = (int)(10*difficulty);
		gold_given = 30;
		animation_speed = 6;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/shy_guy_right_1.png");
		tex[1] = manager.get("data/enemies/shy_guy_right_2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
