package enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Tentacool extends Enemy
{
	
	public Tentacool(Point[] waypoints)
	{
		super(waypoints);
		
		name = "Tentacool";
		width = 37;
		height = 45;
		health = 200;
		speed = 1.5f;
		damage = 20;
		gold_given = 50;
		animation_speed = 20;
		
		tex = new Texture[2];
		tex[0] = new Texture("data/enemies/tentacool_right_1.png");
		tex[1] = new Texture("data/enemies/tentacool_right_2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
