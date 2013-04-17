package enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class BasicEnemy extends Enemy
{
	
	public BasicEnemy(Point[] waypoints, float difficulty)
	{
		super(waypoints);
		
		name = "Mushroom";
		width = 32;
		height = 32;
		float hp = 50*difficulty;
		health = (int)hp;
		speed = 2.5f*difficulty;
		damage = 5;
		gold_given = 10;
		animation_speed = Integer.MAX_VALUE / 2;
		
		tex = new Texture[1];
		tex[0] = new Texture("data/enemies/mushroom.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
