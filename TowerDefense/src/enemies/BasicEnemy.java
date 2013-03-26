package enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class BasicEnemy extends Enemy
{
	
	public BasicEnemy(Point[] waypoints)
	{
		super(waypoints);
		
		name = "Mushroom";
		width = 32;
		height = 32;
		health = 50;
		speed = 2.5f;
		damage = 5;
		gold_given = 10;
		animation_speed = Integer.MAX_VALUE / 2;
		
		tex = new Texture[1];
		tex[0] = new Texture("data/enemies/BasicEnemy.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
