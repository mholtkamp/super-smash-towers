package maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


import enemies.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


import util.Point;
import java.util.Random;


public class GalagaMap extends Map
{
	
	private final int GRID_WIDTH = 40, GRID_HEIGHT = 40;
	private final int WAVE_COUNT = 10; 
	private Texture bg_tex, flagship, field;
	protected Point[] path;
	protected Rectangle base;
	
	public GalagaMap(float difficulty,AssetManager manager)
	{
		
		bg_tex = manager.get("data/maps/galaga_map.png");
		flagship = manager.get("data/towers/Galaga_Flagship.png");
		//towerPlace = manager.get("data/towers/ship.png");
		field = manager.get("data/towers/forceField.png");
		
		song = manager.get("sounds/pokemon.mp3");
		song.setLooping(true);
		song.play();
		
		base = new Rectangle(6*GRID_WIDTH, 5*GRID_HEIGHT, 40,40);
		
		// VALID PLACEMENTS
		valid_placement = new boolean[HEIGHT/GRID_HEIGHT][WIDTH/GRID_WIDTH];	// [y][x]
		for (int i = 0; i < valid_placement.length; i++)
			Arrays.fill(valid_placement[i], false);
		
		// fill valid placements array
		// "1" means you can place a Tower down
		String[] bitmap = {	/*"100000000000001", //1
							"001000000001000", //2
							"000000010000000", //3
							"010000000000010", //4
							"000000001000000", //5
							"000000000000100", //6
							"001000000010000", //7
							"000000000000000", //8
							"000001000001000", //9
							"100000001000001" */
							"111111111111111", //1
							"111111111111111", //2
							"111111111111111", //3
							"111100000111111", //4
							"111100000111111", //5
							"111100000111111", //6
							"111111111111111", //7
							"111111111111111", //8
							"111111111111111", //9
							"111111111111111"}; // 10
		
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
					path = leftRight();
					if(j % 5 == 0)
						q.add(new galagaEnemy1(path, difficulty,manager));
					else
						q.add(new galagaEnemy2(path, difficulty,manager));
				}
			}
			else if (wave < 2)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight();
					if(j % 3 == 0)
						q.add(new galagaEnemy3(path, difficulty,manager));
					else
						q.add(new galagaEnemy4(path, difficulty,manager));
				}
			}
			else if (wave < 3)
			{// for next 3 waves, spawn Koopa
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight();
					q.add(new galagaEnemy1(path, difficulty,manager));
				}
			}
			else if (wave < 5)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight();
					if(j % 2 == 0)
						q.add(new galagaEnemy2(path, difficulty,manager));
					else
						q.add(new galagaEnemy3(path, difficulty,manager));
				}
			}
			else if (wave < 7)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight();
					if(j % 8 == 0)
						q.add(new galagaEnemy1(path, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new galagaEnemy2(path, difficulty,manager));
					else
						q.add(new galagaEnemy3(path, difficulty,manager));

				}
			}
			else if (wave < 8)
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight();
					if(j % 8 == 0)
						q.add(new galagaEnemy2(path, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new galagaEnemy3(path, difficulty,manager));
					else
						q.add(new galagaEnemy4(path, difficulty,manager));

				}
			}
			else
			{	
				for (int j = 0; j < wave*5 + 5; j++)
				{
					path = leftRight();
					if(j % 8 == 0)
						q.add(new galagaEnemy1(path, difficulty,manager));
					else if(j % 3 == 0)
						q.add(new galagaEnemy3(path, difficulty,manager));
					else
						q.add(new galagaEnemy4(path, difficulty,manager));

				}
			}
			waves.add(q);
		}
		
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(bg_tex, 0, 0, WIDTH, HEIGHT);
		batch.draw(field,4*GRID_WIDTH, 4*GRID_HEIGHT, 200,120 );
		batch.draw(flagship,6*GRID_WIDTH, 5*GRID_HEIGHT, 40,40 );
	}
	
	public Point[] leftRight()
	{
		// WAYPOINTS
		waypoints = new Point[5];
		Random generator = new Random(); 
		int randx, randy, waypoint;
		
		//create random starting position
		if(generator.nextInt(2) > 0)
			randx = 0;
		else
			randx = 15;
		
		randy = generator.nextInt(9) + 1;
		
		waypoints[0] = new Point(randx*GRID_WIDTH, randy*GRID_HEIGHT);
		
		//create random path
		for(waypoint = 1; waypoint < 4; waypoint++)
		{
				randx = generator.nextInt(15);
				randy = generator.nextInt(10);
				//rand2x = generator.nextInt(15);
				//rand2y = generator.nextInt(10);
				
				waypoints[waypoint] = new Point(randx*GRID_WIDTH, randy*GRID_HEIGHT);
				//if(waypoint < 7)
				//	waypoints[waypoint+1] = new Point(rand2x*GRID_WIDTH, rand2y*GRID_HEIGHT);
			
		}
		
		//end path at base
		waypoints[4] = new Point(6*GRID_WIDTH, 5*GRID_HEIGHT);
		
		
		
		return waypoints;
	}

}
