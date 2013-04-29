package enums;


public enum EnemyEnum
{
	//MARIO
	MUSHROOM	(0),
	GOOMBA		(1),
	KOOPA		(2),
	BOWSER		(3),
	SHYGUY		(4),
	
	//POKEMON
	ARCANINE	(0),
	GEODUDE		(1),
	WEEDLE		(2),
	VOLTORB		(3),
	TENTACOOL	(4),
	LAPRAS      (5),
	ONIX        (6),
	VICTREE     (7),
	
	//ZELDA
	CACTUS		(0),
	EYE			(1),	
	JELLYFISH	(2),
	KNIGHT		(3),
	KNIGHTB		(4),
	GANNON		(5),
	
	//GALAGA
	GALAGA1     (0),
	GALAGA2     (1),
	GALAGA3     (2),
	GALAGA4     (3);
	
	public final int index;
	public static final int NUM_MARIO_ENEMY = 5, NUM_POKEMON_ENEMY = 8, NUM_ZELDA_ENEMY = 6, NUM_GALAGA_ENEMY = 4;
	
	private EnemyEnum(int index)
	{
		this.index = index;
	}
	
}
