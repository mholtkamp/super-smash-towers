package maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import enemies.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import util.Point;


public class ZeldaMap extends Map
{
	
	private final int GRID_WIDTH = 40, GRID_HEIGHT = 40;
	private final int WAVE_COUNT = 10; 
	private Texture bg_tex;
	protected Point[] path;
	
	public ZeldaMap(float difficulty,AssetManager manager)
	{
		
		bg_tex = manager.get("data/maps/zelda_map.png");
		song = manager.get("sounds/zeldaOverworld.mp3");
		song.setLooping(true);
		song.play();
		
		// VALID PLACEMENTS
		valid_placement = new boolean[HEIGHT/GRID_HEIGHT][WIDTH/GRID_WIDTH];	// [y][x]
		for (int i = 0; i < valid_placement.length; i++)
			Arrays.fill(valid_placement[i], false);
		
		// fill valid placements array
		// "1" means you can place a Tower down
		String[] bitmap = {	"000000000000000", //1
							"110000000000000", //2
							"110100000011000", //3
							"000100000011011", //4
							"011000000011000", //5
							"010000000000110", //6
							"000000000000010", //7
							"110111001111110", //8
							"010000000000000", //9
							"001100011100011" }; //10
		
		for (int y = 0; y < valid_placement.length; y++)
		{
			for (int x = 0; x < valid_placement[y].length; x++)
			{
				if (bitmap[y].charAt(x) == '1')
					valid_placement[y][x] = true;
			}
		}
		
		
		
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
					   path = leftRight(j);
					
						if(j % 5 == 0)
							q.add(new Cactus(path, difficulty,manager));
						else
							q.add(new Eye(path, difficulty,manager));
					
				}
			}
			else if (wave < 2)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight(j);
					if(j % 3 == 0)
						q.add(new Jellyfish(path, difficulty,manager));
					else
						q.add(new Cactus(path, difficulty,manager));
				}
			}
			else if (wave < 3)
			{// for next 3 waves, spawn Koopa
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight(j);
					q.add(new Knight(path, difficulty,manager));
				}
			}
			else if (wave < 5)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight(j);
					if(j % 2 == 0)
						q.add(new Voltorb(path, difficulty,manager));
					else
						q.add(new Eye(path, difficulty,manager));
				}
			}
			else if (wave < 7)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight(j);
					if(j % 8 == 0)
						q.add(new KnightB(path, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new Jellyfish(path, difficulty,manager));
					else
						q.add(new Eye(path, difficulty,manager));

				}
			}
			else if (wave < 8)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight(j);
					if(j % 8 == 0)
						q.add(new Cactus(path, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new Knight(path, difficulty,manager));
					else
						q.add(new Jellyfish(path, difficulty,manager));

				}
			}
			else if(wave == 9)
			{q.add(new Gannon(waypoints, difficulty,manager));}
			else
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight(j);
					if(j % 8 == 0)
						q.add(new KnightB(path, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new Jellyfish(path, difficulty,manager));
					else
						q.add(new Eye(path, difficulty,manager));

				}
			}
			waves.add(q);
		}
		
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(bg_tex, 0, 0, WIDTH, HEIGHT);
	}

	public Point[] leftRight(int j)
	{
		// WAYPOINTS
		waypoints = new Point[9];
		
		if(j%2 == 0)
		{
			//start from left
			waypoints[0] = new Point(-1*GRID_WIDTH, 9*GRID_HEIGHT);	// use grid values, just multiply by grid dimensions		
			waypoints[1] = new Point(2*GRID_WIDTH, 9*GRID_HEIGHT); //L		
			waypoints[2] = new Point(2*GRID_WIDTH, 6*GRID_HEIGHT); //L		
			waypoints[3] = new Point(0*GRID_WIDTH, 6*GRID_HEIGHT); //L		
			waypoints[4] = new Point(0*GRID_WIDTH, 3*GRID_HEIGHT); //L		
			waypoints[5] = new Point(2*GRID_WIDTH, 3*GRID_HEIGHT); //L		
			waypoints[6] = new Point(2*GRID_WIDTH, 1*GRID_HEIGHT); //L		
			waypoints[7] = new Point(6*GRID_WIDTH, 1*GRID_HEIGHT);  //L	
			waypoints[8] = new Point(6*GRID_WIDTH, 8*GRID_HEIGHT); //L	
		}
		else
		{
			//start from right
			waypoints[0] = new Point(14*GRID_WIDTH, 10*GRID_HEIGHT); //R
			waypoints[1] = new Point(14*GRID_WIDTH, 9*GRID_HEIGHT); //R
			waypoints[2] = new Point(12*GRID_WIDTH, 9*GRID_HEIGHT); //R
			//waypoints[3] = new Point(12*GRID_WIDTH, 9*GRID_HEIGHT); //R
			waypoints[3] = new Point(12*GRID_WIDTH, 5*GRID_HEIGHT); //R
			waypoints[4] = new Point(14*GRID_WIDTH, 5*GRID_HEIGHT); //R
			waypoints[5] = new Point(14*GRID_WIDTH, 1*GRID_HEIGHT);  //R
			waypoints[6] = new Point(7*GRID_WIDTH, 1*GRID_HEIGHT); //R
			waypoints[7] = new Point(7*GRID_WIDTH, 7*GRID_HEIGHT); //R
			waypoints[8] = new Point(7*GRID_WIDTH, 8*GRID_HEIGHT); //R
			/*waypoints[16] = new Point(10*GRID_WIDTH, 7*GRID_HEIGHT);
			waypoints[17] = new Point(12*GRID_WIDTH, 7*GRID_HEIGHT);*/
		}
		
		return waypoints;
	}
}


