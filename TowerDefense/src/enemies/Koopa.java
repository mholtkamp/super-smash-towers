package enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Koopa extends Enemy
{
	
	public Koopa(Point[] waypoints, float difficulty)
	{
		super(waypoints);
		
		name = "Koopa";
		width = 32;
		height = 46;
		health = (int)(200*difficulty);
		speed = 1.5f;
		speed_multiplier = difficulty;
		damage = 20;
		gold_given = 50;
		animation_speed = 7;
		
		tex = new Texture[2];
		tex[0] = new Texture("data/enemies/koopa_right_up.png");
		tex[1] = new Texture("data/enemies/koopa_right_down.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
