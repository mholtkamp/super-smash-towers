package enemies;
import com.me.td.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Koopa extends Enemy
{
	static float maxHealth = 1000;
	public Koopa(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints, maxHealth*difficulty);
		
		name = "Koopa";
		width = 32;
		height = 46;
		health = (int)(maxHealth*difficulty);
		speed = 1.5f;
		damage = (int)(20*difficulty);
		gold_given = 55;
		animation_speed = 7;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/koopa_right_up.png");
		tex[1] = manager.get("data/enemies/koopa_right_down.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
