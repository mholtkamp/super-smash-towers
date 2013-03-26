package maps;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import enemies.Enemy;

import java.util.List;
import java.util.Queue;

import util.Point;


public abstract class Map
{
	protected final int WIDTH = 600, HEIGHT = 400, GRID_WIDTH = 40, GRID_HEIGHT = 40;
	
	protected Point[] waypoints;
	protected List<Queue<Enemy>> waves;
	protected boolean[][] valid_placement;
	public Music song;
	protected Texture bg_tex;
	
	public int getWidth() {return WIDTH;}
	
	public int getHeight() {return HEIGHT;}
	
	public Point[] getWayPoints() {return waypoints;}
	
	public int getWayPointCount() {return waypoints.length;}
	
	public Queue<Enemy> getWave(int i) {return waves.get(i);}
	
	public int numWaves() {return waves.size();}
	
	public boolean check_indices(int x, int y)
	{
		if (y < 0 || x < 0 || y >= valid_placement.length || x >= valid_placement[y].length)
			return false;
		return true;
	}
	
	public boolean placement_valid(int x, int y)
	{// returns true if (x,y) is a valid cell to place a Tower
		return check_indices(x, y) ? valid_placement[y][x] : false;
	}
	
	public void set_placement(int x, int y, boolean value)
	{
		if (check_indices(x, y))
			valid_placement[y][x] = value;
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(bg_tex, 0, 0, WIDTH, HEIGHT);
	}

}
