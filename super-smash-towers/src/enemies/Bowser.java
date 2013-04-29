package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Bowser extends Enemy
{
	
	public Bowser(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints);
		
		name = "Bowser";
		width = 64;
		height = 64;
		health = 9500;
		speed = 1.0f;
		damage = 100;
		gold_given = 2500;
		animation_speed = 10;
		
		tex = new Texture[2];
//		tex[0] = new Texture("data/enemies/bowser_right_left.png");
//		tex[1] = new Texture("data/enemies/bowser_right_right.png");
		tex[0] = manager.get("data/enemies/bowserR1.png");
		tex[1] = manager.get("data/enemies/bowserR2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
