package enums;


public enum Level
{
	
	NONE	(-1),
	MARIO	(0),
	POKEMON	(1);
	
	public final int index;
	
	private Level(int index)
	{
		this.index = index;
	}
	
}
