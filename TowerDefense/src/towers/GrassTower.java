package towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;

public class GrassTower extends Tower
{
	
	private int DAMAGE;

	
	private final int CALLS_BETWEEN_TOGGLE = 15;
	private int current_tex, toggle_count;
	private Texture[] tex;
	private Texture leafTex;
	private Rectangle leaf1;
	private Rectangle leaf2;
	private Rectangle leaf3;
	private Rectangle leaf4;	
	
	private float leafAngularVelocity;
	private float leafAngle;
	private float leafRotationAngle;
	private float leafRotationAngularVelocity;

	
	private final float MAX_RANGE = 150;
	private final float MIN_RANGE = 50;
	private boolean expanding;
	private final float RANGE_RATE = 20;
	
	public GrassTower(ArrayList<Enemy> enemies, float x, float y)
	{
		super(enemies, x, y);
		
		name = "GrassTower";
		width = 40;
		height = 48;
		range = 100;
		cost = 500;
		firing_speed = 2.0f;
		level = 1;
		DAMAGE = 10;
		damagemultiplier = 1;
		upgradecost = 400;
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex = new Texture[3];
		tex[0] = new Texture("data/towers/bulba.png");
		tex[1] = new Texture("data/towers/ivy.png");
		tex[2] = new Texture("data/towers/venus.png");
		//tex[0] = new Texture(Gdx.files.internal("data/towers/grass_tower_1.png"));
		//tex[1] = new Texture(Gdx.files.internal("data/towers/grass_tower_2.png"));
		//add tex 2-5 for lvls 2 and 3
		current_tex = 0;
		toggle_count = CALLS_BETWEEN_TOGGLE;
		
		leafTex = new Texture(Gdx.files.internal("data/bullets/leaf.png"));
		
		leaf1 = new Rectangle(center_x,center_y,leafTex.getWidth(),leafTex.getHeight());
		leaf2 = new Rectangle(center_x,center_y,leafTex.getWidth(),leafTex.getHeight());
		leaf3 = new Rectangle(center_x,center_y,leafTex.getWidth(),leafTex.getHeight());
		leaf4 = new Rectangle(center_x,center_y,leafTex.getWidth(),leafTex.getHeight());
		leafAngle = 0f;
		leafAngularVelocity = 2f;
		leafRotationAngle = 0f;
		leafRotationAngularVelocity = 1000f;
		
		expanding = true;

	}
	
	public void levelUp()
	{
		level++;
		damagemultiplier++;
		firing_speed = firing_speed*0.75f;
		current_tex++;
	}
	
	public void update()
	{

		leafAngle += leafAngularVelocity*Gdx.graphics.getDeltaTime();
		if(leafAngle >= (Math.PI * 2))
				leafAngle = 0f;
		
		if(expanding)
		{
			range += RANGE_RATE*Gdx.graphics.getDeltaTime();
			if(range > MAX_RANGE)
				expanding = false;
		}
		else
		{
			range -= RANGE_RATE*Gdx.graphics.getDeltaTime();
			if(range < MIN_RANGE)
				expanding = true;
		}

		leaf1.x = ((float) Math.cos(leafAngle))*range + center_x;
		leaf1.y = ((float) Math.sin(leafAngle))*range + center_y;
		leaf2.x = ((float) Math.cos(leafAngle + Math.PI/2))*range + center_x;
		leaf2.y = ((float) Math.sin(leafAngle + Math.PI/2))*range + center_y;
		leaf3.x = ((float) Math.cos(leafAngle + Math.PI))*range + center_x;
		leaf3.y = ((float) Math.sin(leafAngle + Math.PI))*range + center_y;
		leaf4.x = ((float) Math.cos(leafAngle + 3*Math.PI/2))*range + center_x;
		leaf4.y = ((float) Math.sin(leafAngle + 3*Math.PI/2))*range + center_y;
		
		for(int i = 0; i < enemies.size();i++)
		{
			if(leaf1.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth(DAMAGE*damagemultiplier);
			else if (leaf2.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth(DAMAGE*damagemultiplier);
			else if (leaf3.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth(DAMAGE*damagemultiplier);
			else if (leaf4.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth(DAMAGE*damagemultiplier);
		}
		
		leafRotationAngle += leafRotationAngularVelocity*Gdx.graphics.getDeltaTime();
		if(leafRotationAngle >= 360f)
			leafRotationAngle = 0f;
	}
	
	public void render(SpriteBatch batch)
	{
		// render Tower
		/* 2 different textures for each level of tower
		 * if (level = 1)
		 * { batch.draw(tex[current_tex], collider.x, collider.y; }
		 * else
		 * { if (level = 2)
		 * { batch.draw(tex[current_tex+2], collider.x, collider.y; }}
		 * else 
		 * {if (level = 3)
		 * { batch.draw(tex[current_tex+4], collider.x, collider.y; }}
		 */
		batch.draw(tex[current_tex], collider.x, collider.y);
		
		// toggle textures
		//if (--toggle_count < 0)
		//{
			//current_tex = (current_tex + 1) % tex.length;
			//toggle_count = CALLS_BETWEEN_TOGGLE;
		//}
		
		// render Bullets
		batch.draw(leafTex, leaf1.x, leaf1.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf2.x, leaf2.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf3.x, leaf3.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf4.x, leaf4.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);

	}
}
