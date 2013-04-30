package enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enums.Type;

import util.Point;


public class Victree extends Enemy
{
	static int maxHealth=15000;
 	
 	public Victree(Point[] waypoints, float difficulty, AssetManager manager)
 	{
		super(waypoints,maxHealth);
		
		name = "Victree";
		width = 57;
		height = 45;
		health = (int)(maxHealth*difficulty);
		speed = 1.5f;
		damage = (int)(40*difficulty);
		gold_given = 50;
		animation_speed = 10;
		type = Type.GRASS;
		
		tex = new Texture[2];
		tex[0] = manager.get("data/enemies/Victree1.png");
		tex[1] = manager.get("data/enemies/Victree2.png");
		collider = new Rectangle((float)waypoints[0].getX(), (float)waypoints[0].getY(), width, height);
	}
	
}
