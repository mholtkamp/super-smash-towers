package enums;


public enum EnemyEnum
{
	
	MUSHROOM	(0),
	GOOMBA		(1),
	KOOPA		(2),
	BOWSER		(3),
	SHYGUY		(4),
	ARCANINE	(5),
	GEODUDE		(6),
	WEEDLE		(7),
	VOLTORB		(8),
	TENTACOOL	(9);
	
	public final int index;
	
	private EnemyEnum(int index)
	{
		this.index = index;
	}
	
}
