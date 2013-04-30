package towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;
import enums.Type;

import java.util.ArrayList;


public class GrassTower extends Tower
{
	
	private int LEAF_DAMAGE;
	
	private Texture leafTex;
	private Rectangle leaf1;
	private Rectangle leaf2;
	private Rectangle leaf3;
	private Rectangle leaf4;	
	
	private float leafAngularVelocity;
	private float leafAngle;
	private float leafRotationAngle;
	private float leafRotationAngularVelocity;
	
	private final float MAX_RANGE = 110;
	private final float MIN_RANGE = 90;
	private boolean expanding;
	private final float RANGE_RATE = 20;
	
	public GrassTower()
	{
		// attributes
		name = "GrassTower";
		width = 40;
		height = 48;
		range = 100;
		cost = 1000;
		firing_speed = 2.0f;
		upgradecost = cost * 3 / 4;
		LEAF_DAMAGE = 2;
		max_level = 3;
	}
	
	public GrassTower(ArrayList<Enemy> enemies, float x, float y, AssetManager manager)
	{
		super(enemies, manager);
		
		// attributes
		name = "GrassTower";
		width = 40;
		height = 48;
		range = 100;
		cost = 1000;
		firing_speed = 2.0f;
		upgradecost = cost * 3 / 4;
		LEAF_DAMAGE = 2;
		max_level = 3;
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex = new Texture[3];
		tex[0] = this.manager.get("data/towers/bulba.png");
		tex[1] = this.manager.get("data/towers/ivy.png");
		tex[2] = this.manager.get("data/towers/venus.png");
		
		leafTex = this.manager.get("data/bullets/leaf.png");
		
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
	
	public boolean levelUp()
	{
		if (level < max_level)
		{
			level++;
			LEAF_DAMAGE++;
			leafAngularVelocity += 2;
			current_tex++;
			return true;
		}
		return false;
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
		
		for(int i = 0; i < enemies.size(); i++)
		{
			float type_multiplier;
			if (enemies.get(i).getType() == Type.GRASS || enemies.get(i).getType() == Type.FIRE)
				type_multiplier = 0.5f;
			else if (enemies.get(i).getType() == Type.WATER || enemies.get(i).getType() == Type.ROCK)
				type_multiplier = 2.0f;
			else 
				type_multiplier = 1.0f;
			
			if (leaf1.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth((int)(LEAF_DAMAGE*damagemultiplier*type_multiplier));
			else if (leaf2.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth((int)(LEAF_DAMAGE*damagemultiplier*type_multiplier));
			else if (leaf3.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth((int)(LEAF_DAMAGE*damagemultiplier*type_multiplier));
			else if (leaf4.overlaps(enemies.get(i).getCollider()))
				enemies.get(i).subHealth((int)(LEAF_DAMAGE*damagemultiplier*type_multiplier));
		}
		
		leafRotationAngle += leafRotationAngularVelocity*Gdx.graphics.getDeltaTime();
		if(leafRotationAngle >= 360f)
			leafRotationAngle = 0f;
	}
	
	public void render(SpriteBatch batch)
	{
		// face left?
		right = face_left() ? false : true;

		// render Tower
		batch.draw(tex[current_tex], collider.x, collider.y, (float)width, (float)height, 0, 0, width, height, right, false);

		// render Bullets
		batch.draw(leafTex, leaf1.x, leaf1.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf2.x, leaf2.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf3.x, leaf3.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
		batch.draw(leafTex, leaf4.x, leaf4.y, 0, 0, leafTex.getWidth(), leafTex.getHeight(), 1, 1, leafRotationAngle, 0, 0, leafTex.getWidth(), leafTex.getHeight(), false, false);
	}
	
}
