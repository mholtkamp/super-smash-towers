package maps;

import enemies.Bowser;
import enemies.Enemy;
import enemies.Goomba;
import enemies.Koopa;
import enemies.ShyGuy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;

import util.Database;
import util.Point;


public class MarioMap extends Map
{
	
	private final int WAVE_COUNT = 10, ENEMY_COUNT = 3;
	
	public MarioMap()
	{
		
		bg_tex = Database.mario_map;
		
//		this.width = width;
//		this.height = height;
		
//		System.out.println(this.width + " " + this.height);
		
		song = Gdx.audio.newMusic(Gdx.files.internal("sounds/mario.mp3"));
		song.setLooping(true);
		song.play();
		
		// VALID PLACEMENTS
		valid_placement = new boolean[HEIGHT/GRID_HEIGHT][WIDTH/GRID_WIDTH];	// [y][x]
		for (int i = 0; i < valid_placement.length; i++)
			Arrays.fill(valid_placement[i], false);
		
		// fill valid placements array
		// "1" means you can place a Tower down
		String[] bitmap = {	"000001111111000",
							"001000000000100",
							"011011111010111",
							"000010100000110",
							"101111101001100",
							"001000001110000",
							"001010111000001",
							"111001100000000",
							"101010101011100",
							"000000001000001" };
		
		for (int y = 0; y < valid_placement.length; y++)
		{
			for (int x = 0; x < valid_placement[y].length; x++)
			{
				if (bitmap[y].charAt(x) == '1')
					valid_placement[y][x] = true;
			}
		}
		
		// WAYPOINTS
		waypoints = new Point[12];
		waypoints[0] = new Point(-1*GRID_WIDTH, 6*GRID_HEIGHT);	// use grid values, just multiply by grid dimensions
		waypoints[1] = new Point(3*GRID_WIDTH, 6*GRID_HEIGHT);
		waypoints[2] = new Point(3*GRID_WIDTH, 8*GRID_HEIGHT);
		waypoints[3] = new Point(11*GRID_WIDTH, 8*GRID_HEIGHT);
		waypoints[4] = new Point(11*GRID_WIDTH, 6*GRID_HEIGHT);
		waypoints[5] = new Point(7*GRID_WIDTH, 6*GRID_HEIGHT);
		waypoints[6] = new Point(7*GRID_WIDTH, 4*GRID_HEIGHT);
		waypoints[7] = new Point(3*GRID_WIDTH, 4*GRID_HEIGHT);
		waypoints[8] = new Point(3*GRID_WIDTH, 0*GRID_HEIGHT);
		waypoints[9] = new Point(7*GRID_WIDTH, 0*GRID_HEIGHT);
		waypoints[10] = new Point(7*GRID_WIDTH, 2*GRID_HEIGHT);
		waypoints[11] = new Point(12*GRID_WIDTH, 2*GRID_HEIGHT);
		
		// WAVES
		waves = new LinkedList<Queue<Enemy>>();
		Queue<Enemy> q;
		for (int wave = 0; wave < WAVE_COUNT; wave++)
		{
			q = new LinkedList<Enemy>();
			if (wave / 3 < 1)
			{// for first 3 waves, spawn BasicEnemy
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new Goomba(waypoints));
			}
			else if (wave / 3 < 2)
			{// for next 3 waves, spawn Goomba
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new ShyGuy(waypoints));
			}
			else if (wave / 3 < 3)
			{// for next 3 waves, spawn Koopa
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new Koopa(waypoints));
			}
			else
			{// boss wave
				int current_enemy = 0;
				for (int j = 0; j < wave*5 + 5; j++)
				{
					switch (current_enemy)
					{
						case 0: q.add(new Goomba(waypoints)); break;
						case 1: q.add(new ShyGuy(waypoints)); break;
						case 2: q.add(new Koopa(waypoints)); break;
						default: q.add(new Goomba(waypoints)); break;
					}
					current_enemy = (current_enemy + 1) % ENEMY_COUNT;
				}
				q.add(new Bowser(waypoints));
			}
			waves.add(q);
		}
		
	}

}
