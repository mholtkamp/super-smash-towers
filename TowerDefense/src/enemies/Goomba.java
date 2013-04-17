package enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Goomba extends Enemy
{
	
	public Goomba(Point[] waypoints, float difficulty)
	{
		super(waypoints);
		
		name = "Goomba";
		width = 32;
		height = 32;
		float hp = 50*difficulty;
		health = (int)hp;
		speed = 2.5f*difficulty;
		damage = 5;
		gold_given = 10;
		animation_speed = 6;
		
		tex = new Texture[2];
		tex[0] = new Texture("data/enemies/goomba1.png");
		tex[1] = new Texture("data/enemies/goomba2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
