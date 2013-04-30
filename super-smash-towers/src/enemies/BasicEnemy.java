package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class BasicEnemy extends Enemy
{
	static int maxHealth = 50;
	public BasicEnemy(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints,  maxHealth);
		
		name = "Mushroom";
		width = 32;
		height = 32;
		health = (int)(maxHealth*difficulty);
		speed = 2.5f;
		damage = (int)(5*difficulty);
		gold_given = 10;
		animation_speed = Integer.MAX_VALUE / 2;
		
		tex = new Texture[1];
		tex[0] = manager.get("data/enemies/mushroom.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
