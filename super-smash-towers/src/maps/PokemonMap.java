package maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import enemies.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import util.Point;


public class PokemonMap extends Map
{
	
	private final int GRID_WIDTH = 40, GRID_HEIGHT = 40;
	private final int WAVE_COUNT = 10; 
	private Texture bg_tex;
	
	public PokemonMap(float difficulty,AssetManager manager)
	{
		
		bg_tex = manager.get("data/maps/pokemon_map.png");
		
		song = manager.get("sounds/pokemon.mp3");
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
							"011111000000111", //5
							"000001000100111", //6
							"111101110100001", //7
							"111101110111101", //8
							"000001110000001", //9
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
		waypoints = new Point[18];
		waypoints[0] = new Point(-1*GRID_WIDTH, 1*GRID_HEIGHT);	// use grid values, just multiply by grid dimensions
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
		waypoints[17] = new Point(12*GRID_WIDTH, 7*GRID_HEIGHT);
		
		// WAVES
		waves = new LinkedList<Queue<Enemy>>();
		Queue<Enemy> q;
		for (int wave = 0; wave < WAVE_COUNT; wave++)
		{
			q = new LinkedList<Enemy>();
			if (wave  < 1)
			{ 
				for (int j = 0; j < wave*5 + 5; j++)
				{
					if(j % 5 == 0)
						q.add(new Tentacool(waypoints, difficulty,manager));
					else
						q.add(new Weedle(waypoints, difficulty,manager));
				}
			}
			else if (wave < 2)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					if(j % 3 == 0)
						q.add(new Voltorb(waypoints, difficulty,manager));
					else
						q.add(new Geodude(waypoints, difficulty,manager));
				}
			}
			else if (wave < 3)
			{// for next 3 waves, spawn Koopa
				for (int j = 0; j < wave*5 + 5; j++)
					q.add(new Voltorb(waypoints, difficulty,manager));
			}
			else if (wave < 5)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					if(j % 2 == 0)
						q.add(new Arcanine(waypoints, difficulty,manager));
					else
						q.add(new Geodude(waypoints, difficulty,manager));
				}
			}
			else if (wave < 7)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					if(j % 8 == 0)
						q.add(new Tentacool(waypoints, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new Geodude(waypoints, difficulty,manager));
					else
						q.add(new Arcanine(waypoints, difficulty,manager));

				}
			}
			else if (wave < 8)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					if(j % 8 == 0)
						q.add(new Arcanine(waypoints, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new Voltorb(waypoints, difficulty,manager));
					else
						q.add(new Geodude(waypoints, difficulty,manager));

				}
			}
			else if(wave == 9)
			{
					q.add(new Onix(waypoints, difficulty,manager));

			}
			else
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					if(j==0)
						q.add(new Lapras(waypoints, difficulty,manager));
					if(j % 8 == 0)
						q.add(new Arcanine(waypoints, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new Geodude(waypoints, difficulty,manager));
					else if(j==wave*5+4)
						q.add(new Victree(waypoints, difficulty,manager));
					else
						q.add(new Tentacool(waypoints, difficulty,manager));

				}
			}
			waves.add(q);
		}
		
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(bg_tex, 0, 0, WIDTH, HEIGHT);
	}

}
