package maps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import enemies.BasicEnemy;
import enemies.Bowser;
import enemies.Enemy;
import enemies.Goomba;
import enemies.Koopa;

import java.util.LinkedList;
import java.util.Queue;

import util.Point;


public class BasicMap extends Map
{
	
	private final int WIDTH = 800, HEIGHT = 480;
	private final int WAYPOINT_COUNT = 4, WAVE_COUNT = 10, ENEMY_COUNT = 3; 
	private Texture bg_tex;
	
	public BasicMap(float difficulty)
	{
		
		bg_tex = new Texture("data/maps/basic_map.png");
		
		// WAYPOINTS
		waypoints = new Point[WAYPOINT_COUNT];
		waypoints[0] = new Point(0, 50);
		waypoints[1] = new Point(393, 50);
		waypoints[2] = new Point(393, 300);
		waypoints[3] = new Point(800, 300);
		
		// WAVES
		waves = new LinkedList<Queue<Enemy>>();
		Queue<Enemy> q;
		for (int wave = 0; wave < WAVE_COUNT; wave++)
		{
			q = new LinkedList<Enemy>();
			if (wave / 3 < 1)
			{// for first 3 waves, spawn BasicEnemy
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new BasicEnemy(waypoints, difficulty));
			}
			else if (wave / 3 < 2)
			{// for next 3 waves, spawn Goomba
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new Goomba(waypoints, difficulty));
			}
			else if (wave / 3 < 3)
			{// for next 3 waves, spawn Koopa
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new Koopa(waypoints, difficulty));
			}
			else
			{// boss wave
				int current_enemy = 0;
				for (int j = 0; j < wave*5 + 5; j++)
				{
					switch (current_enemy)
					{
						case 0: q.add(new BasicEnemy(waypoints, difficulty)); break;
						case 1: q.add(new Goomba(waypoints, difficulty)); break;
						case 2: q.add(new Koopa(waypoints, difficulty)); break;
						default: q.add(new BasicEnemy(waypoints, difficulty)); break;
					}
					current_enemy = (current_enemy + 1) % ENEMY_COUNT;
				}
				q.add(new Bowser(waypoints, difficulty));
			}
			waves.add(q);
		}
		
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(bg_tex, 0, 0, WIDTH, HEIGHT);
	}

}
