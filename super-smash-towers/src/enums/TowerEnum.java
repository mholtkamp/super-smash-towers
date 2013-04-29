package enums;


public enum TowerEnum
{
	
	CASTLE		(0),
	HAMMER_BROS	(1),
	FLOWER      (2),
	
	FIRE		(0),
	WATER		(1),
	GRASS		(2),
	PSYCHIC		(3),
	
	BOMB		(0),
	BOOMERANG   (1),
	SLINGSHOT   (2),
	SWORD       (3),
	
	G1          (0),
	G2          (1),
	G3          (2),
	G4          (3);
	
	public final int index;
	public static final int NUM_MARIO_TOWERS = 3, NUM_POKEMON_TOWERS = 4, NUM_ZELDA_TOWERS = 4, NUM_GALAGA_TOWERS = 4;
	
	private TowerEnum(int index)
	{
		this.index = index;
	}
	
}
