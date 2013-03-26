package enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Koopa extends Enemy
{
	
	public Koopa(Point[] waypoints)
	{
		super(waypoints);
		
		name = "Koopa";
		width = 32;
		height = 46;
		health = 200;
		speed = 1.5f;
		damage = 20;
		gold_given = 50;
		animation_speed = 7;
		
		tex = new Texture[2];
		tex[0] = new Texture("data/enemies/koopa_right_up.png");
		tex[1] = new Texture("data/enemies/koopa_right_down.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
