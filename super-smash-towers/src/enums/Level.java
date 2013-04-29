package enums;


public enum Level
{
	
	NONE	(-1),
	MARIO	(0),
	POKEMON	(1),
	ZELDA   (2),
	GALAGA  (3);
	
	public final int index;
	
	private Level(int index)
	{
		this.index = index;
	}
	
}
