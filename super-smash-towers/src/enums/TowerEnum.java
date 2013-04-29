package enums;


public enum TowerEnum
{
	
	CASTLE		(0),
	HAMMER_BROS	(1),
	FLOWER      (2),
	FIRE		(3),
	WATER		(4),
	GRASS		(5),
	PSYCHIC		(6),
	BOMB		(7),
	BOOMERANG   (8),
	SLINGSHOT   (9),
	SWORD       (10),
	G1          (11),
	G2          (12),
	G3          (13),
	G4          (14);
	
	public final int index;
	
	private TowerEnum(int index)
	{
		this.index = index;
	}
	
}
