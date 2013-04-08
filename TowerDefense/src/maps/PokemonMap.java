package maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import enemies.BasicEnemy;
import enemies.Bowser;
import enemies.Enemy;
import enemies.Goomba;
import enemies.Koopa;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import util.Database;
import util.Point;


public class PokemonMap extends Map
{
	
	private final int GRID_WIDTH = 40, GRID_HEIGHT = 40;
	private final int WAVE_COUNT = 10, ENEMY_COUNT = 3; 
	private Texture bg_tex;
	
	public PokemonMap()
	{
		
		bg_tex = Database.pokemon_map;
		
		song = Gdx.audio.newMusic(Gdx.files.internal("sounds/pokemon.mp3"));
		song.setLooping(true);
		song.play();
		
		// VALID PLACEMENTS
		valid_placement = new boolean[HEIGHT/GRID_HEIGHT][WIDTH/GRID_WIDTH];	// [y][x]
		for (int i = 0; i < valid_placement.length; i++)
			Arrays.fill(valid_placement[i], false);
		
		// fill valid placements array
		// "1" means you can place a Tower down
		String[] bitmap = {	"000100000010000", //1
							"000101111010000", //2
							"101101111000000", //3
							"000001111000111", //4
							"011111000000111",// 5
							"000001000100111",//6
							"111101110100001",//7
							"111101110111101",//8
							"000001110000001",//9
							"111111111100000" }; //10
		
		for (int y = 0; y < valid_placement.length; y++)
		{
			for (int x = 0; x < valid_placement[y].length; x++)
			{
				if (bitmap[y].charAt(x) == '1')
					valid_placement[y][x] = true;
			}
		}
		
		// WAYPOINTS
		// waypoints for mario_map_3.png
		waypoints = new Point[18];
		waypoints[0] = new Point(0*GRID_WIDTH, 1*GRID_HEIGHT);	// use grid values, just multiply by grid dimensions
		waypoints[1] = new Point(4*GRID_WIDTH, 1*GRID_HEIGHT);
		waypoints[2] = new Point(4*GRID_WIDTH, 4*GRID_HEIGHT);
		waypoints[3] = new Point(0*GRID_WIDTH, 4*GRID_HEIGHT);
		waypoints[4] = new Point(0*GRID_WIDTH, 6*GRID_HEIGHT);
		waypoints[5] = new Point(4*GRID_WIDTH, 6*GRID_HEIGHT);
		waypoints[6] = new Point(4*GRID_WIDTH, 9*GRID_HEIGHT);
		waypoints[7] = new Point(9*GRID_WIDTH, 9*GRID_HEIGHT);
		waypoints[8] = new Point(9*GRID_WIDTH, 5*GRID_HEIGHT);
		waypoints[9] = new Point(6*GRID_WIDTH, 5*GRID_HEIGHT);
		waypoints[10] = new Point(6*GRID_WIDTH, 4*GRID_HEIGHT);
		waypoints[11] = new Point(8*GRID_WIDTH, 4*GRID_HEIGHT);
		waypoints[12] = new Point(8*GRID_WIDTH, 1*GRID_HEIGHT);
		waypoints[13] = new Point(13*GRID_WIDTH, 1*GRID_HEIGHT);
		waypoints[14] = new Point(13*GRID_WIDTH, 3*GRID_HEIGHT);
		waypoints[15] = new Point(10*GRID_WIDTH, 3*GRID_HEIGHT);
		waypoints[16] = new Point(10*GRID_WIDTH, 7*GRID_HEIGHT);
		waypoints[17] = new Point(13*GRID_WIDTH, 7*GRID_HEIGHT);
		
	
//		// waypoints for mario_map.png
//		waypoints = new Point[10];
//		waypoints[0] = new Point.Float(0*40, 1*40);	
//		waypoints[1] = new Point.Float(3*40, 1*40);
//		waypoints[2] = new Point.Float(120, 280);
//		waypoints[3] = new Point.Float(320, 280);
//		waypoints[4] = new Point.Float(320, 120);
//		waypoints[5] = new Point.Float(440, 120);
//		waypoints[6] = new Point.Float(440, 280);
//		waypoints[7] = new Point.Float(640, 280);
//		waypoints[8] = new Point.Float(640, 40);
//		waypoints[9] = new Point.Float(800, 40);
		
		// WAVES
		waves = new LinkedList<Queue<Enemy>>();
		Queue<Enemy> q;
		for (int wave = 0; wave < WAVE_COUNT; wave++)
		{
			q = new LinkedList<Enemy>();
			if (wave / 3 < 1)
			{// for first 3 waves, spawn BasicEnemy
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new BasicEnemy(waypoints));
			}
			else if (wave / 3 < 2)
			{// for next 3 waves, spawn Goomba
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new Goomba(waypoints));
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
						case 0: q.add(new BasicEnemy(waypoints)); break;
						case 1: q.add(new Goomba(waypoints)); break;
						case 2: q.add(new Koopa(waypoints)); break;
						default: q.add(new BasicEnemy(waypoints)); break;
					}
					current_enemy = (current_enemy + 1) % ENEMY_COUNT;
				}
				q.add(new Bowser(waypoints));
			}
			waves.add(q);
		}
		
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(bg_tex, 0, 0, WIDTH, HEIGHT);
	}

}
