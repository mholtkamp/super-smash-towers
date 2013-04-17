package enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Weedle extends Enemy
{
	
	public Weedle(Point[] waypoints, float difficulty)
	{
		super(waypoints);
		
		name = "Weedle";
		width = 40;
		height = 28;
		health = (int)(200*difficulty);
		speed = 1.5f;
		speed_multiplier = difficulty;
		damage = 20;
		gold_given = 50;
		animation_speed = 7;
		
		tex = new Texture[2];
		tex[0] = new Texture("data/enemies/weedle_right_1.png");
		tex[1] = new Texture("data/enemies/weedle_right_2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
