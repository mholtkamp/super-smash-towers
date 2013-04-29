package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Voltorb extends Enemy
{
	
	public Voltorb(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints);
		
		name = "Voltorb";
		width = 29;
		height = 29;
		health = 200;
		speed = 1.5f;
		damage = 20;
		gold_given = 50;
		animation_speed = 20;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/voltorb_right_1.png");
		tex[1] = manager.get("data/enemies/voltorb_right_2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
