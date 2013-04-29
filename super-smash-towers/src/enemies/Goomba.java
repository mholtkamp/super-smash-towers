package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import util.Point;


public class Goomba extends Enemy
{
	static int maxHealth = 50;
	public Goomba(Point[] waypoints,float difficulty,AssetManager manager)
	{
		super(waypoints ,maxHealth);
		
		name = "Goomba";
		width = 32;
		height = 32;
		health = maxHealth;
		speed = 2.5f;
		damage = 5;
		gold_given = 10;
		animation_speed = 6;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/goomba1.png");
		tex[1] = manager.get("data/enemies/goomba2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
