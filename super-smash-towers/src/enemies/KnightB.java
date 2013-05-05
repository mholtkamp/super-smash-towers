package enemies;
import com.me.td.World;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class KnightB extends Enemy
{
	static float maxHealth=2000;
 	
 	public KnightB(Point[] waypoints, float difficulty, AssetManager manager)
 	{
		super(waypoints,maxHealth*difficulty);
		
		name = "KnightB";
		width = 28;
		height = 42;
		health = (int)(maxHealth*difficulty);
		speed = 2.0f;
		damage = (int)(40*difficulty);
		gold_given = 50;
		animation_speed = 10;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/zknightb1.png");
		tex[1] = manager.get("data/enemies/zknightb2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
