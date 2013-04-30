package towers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import enemies.Enemy;
import bullets.*;

public class BulletBill extends Tower {
	private Texture tex;
	private int orientation;
	
	public BulletBill()
	{
		name = "BulletBill";
		width = 40;
		height = 40;
		range = 200;
		cost = 500;
		firing_speed = 1.75f;		
		upgradecost = 0;
	}
	public BulletBill(ArrayList<Enemy> enemies, float x, float y, AssetManager manager)
	{
		super(enemies,manager);
		tex = manager.get("data/towers/bill.png");
		orientation = 0;
		name = "BulletBill";
		width = 40;
		height = 40;
		range = 200;
		cost = 500;
		firing_speed = 1.75f;		
		upgradecost = 0;
		
		damagemultiplier = 1;
		level = 1;
		time_since_last_shot = 0;
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
	}
	
	public void update()
	{
		time_since_last_shot += Gdx.graphics.getDeltaTime();

		if(time_since_last_shot > firing_speed)
		{
			bullets.add(new BillBullet(enemies,orientation,center_x,center_y,damagemultiplier, manager));
			time_since_last_shot = 0;
		}
		for(int i = 0; i < bullets.size(); i++)
		{
			bullets.get(i).update();
			if (!bullets.get(i).isActive())
				bullets.remove(i--);
		}
	}

	public boolean levelUp()
	{
		orientation++;
		if(orientation > 3)
			orientation = 0;
		return true;
	}
	
	public void render(SpriteBatch batch)
	{
		if(orientation == 0)
			batch.draw(tex,collider.x,collider.y,0,0,collider.width,collider.height,1,1,0,0,0,tex.getWidth(),tex.getHeight(),false,false);
		else if(orientation == 1)
			batch.draw(tex,collider.x+collider.width,collider.y,0,0,collider.width,collider.height,1,1,90,0,0,tex.getWidth(),tex.getHeight(),false,false);
		else if(orientation == 2)
			batch.draw(tex,collider.x + collider.width,collider.y + collider.height,0,0,collider.width,collider.height,1,1,180,0,0,tex.getWidth(),tex.getHeight(),false,false);
		else
			batch.draw(tex,collider.x,collider.y + collider.height,0,0,collider.width,collider.height,1,1,270,0,0,tex.getWidth(),tex.getHeight(),false,false);
		
		for (Bullet bullet : bullets)
			bullet.render(batch);
	}
}
