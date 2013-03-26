package towers;

import bullets.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;

import java.util.ArrayList;

public class GrassTower extends Tower
{

	private final int WIDTH = 40, HEIGHT = 48;
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
		center_x = x + WIDTH/2;
		center_y = y + HEIGHT/2;
		time_since_last_shot = 0;
		collider = new Rectangle(x, y, WIDTH, HEIGHT);
		this.enemies = enemies;
		bullets = new ArrayList<Bullet>();
		target = null;
		
		// attributes - change these for each new Tower
		tex = new Texture[2];
		tex[0] = new Texture(Gdx.files.internal("data/towers/grass_tower_1.png"));
		tex[1] = new Texture(Gdx.files.internal("data/towers/grass_tower_2.png"));
		
		leafTex = new Texture(Gdx.files.internal("data/bullets/leaf.png"));
		current_tex = 0;
		toggle_count = CALLS_BETWEEN_TOGGLE;
		
		name = "GrassTower";
		range = 100;
		cost = 500;
		firing_speed = 2.0f;	// shoot every x seconds
		
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
				enemies.get(i).subHealth(2);
			else if (leaf2.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth(2);
			else if (leaf3.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth(2);
			else if (leaf4.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth(2);
		}
		
		leafRotationAngle += leafRotationAngularVelocity*Gdx.graphics.getDeltaTime();
		if(leafRotationAngle >= 360f)
			leafRotationAngle = 0f;
	}
	
	public void render(SpriteBatch batch)
	{
		// render Tower
		batch.draw(tex[current_tex], collider.x, collider.y);
		
		// toggle textures
		if (--toggle_count < 0)
		{
			current_tex = (current_tex + 1) % tex.length;
			toggle_count = CALLS_BETWEEN_TOGGLE;
		}
		
		// render Bullets
		batch.draw(leafTex, leaf1.x, leaf1.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf2.x, leaf2.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf3.x, leaf3.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf4.x, leaf4.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);

	}
}
