package towers;

import bullets.BasicBullet;
import bullets.Bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.me.td.World;

import enemies.Enemy;

import java.util.ArrayList;


public class BasicTower extends Tower
{
	
	private Texture tex;
	private Sound shoot;
	
	public BasicTower(ArrayList<Enemy> enemies, float x, float y)
	{
		super(enemies, x, y);
		
		// attributes - change for each new Tower
		name = "Castle";
		width = 40;
		height = 50;
		range = 100;
		cost = 250;
		firing_speed = 1.5f;	// shoot every x seconds
		
		center_x = x + width/2;
		center_y = y + height/2;
		collider = new Rectangle(x, y, width, height);
		
		tex = new Texture("data/towers/tower.png");
		shoot = Gdx.audio.newSound(Gdx.files.internal("sounds/firework.mp3"));
	}
	
	public void update()
	{
		for (int i = 0; i < bullets.size(); i++)
		{
			bullets.get(i).update();
			if (!bullets.get(i).isActive())
				bullets.remove(i--);
		}
		time_since_last_shot += Gdx.graphics.getDeltaTime();
		if (time_since_last_shot > firing_speed)
		{
			for (int i = 0; i < enemies.size(); i++)
			{
				float xE = (center_x - enemies.get(i).getX());
				float yE = (center_y - enemies.get(i).getY());
				if (Math.sqrt(xE*xE + yE*yE) < range)
				{
					shoot.play(World.volume);
					bullets.add(new BasicBullet(enemies.get(i), center_x, center_y));
					time_since_last_shot = 0;
					break;
				}
			}
		}
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(tex, collider.x, collider.y);
		for (Bullet bullet : bullets)
			bullet.render(batch);
	}
}
