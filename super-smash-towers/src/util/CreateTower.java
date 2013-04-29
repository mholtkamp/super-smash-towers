package util;

import towers.*;
import enums.TowerEnum;


public class CreateTower
{
	
	private Tower tower;
	
	public CreateTower(TowerEnum t)
	{
		switch (t)
		{
			case CASTLE:		this.tower = new CastleTower();	break;
			case HAMMER_BROS:	this.tower = new HammerBros();	break;
			case FLOWER:        this.tower = new FlowerTower(); break;
			case PSYCHIC:		this.tower = new PsychicTower();	break;
			case FIRE:			this.tower = new FireTower();	break;
			case GRASS:			this.tower = new GrassTower();	break;
			case WATER:			this.tower = new WaterTower();	break;
			case BOMB:			this.tower = new BombTower();	break;
			case BOOMERANG:		this.tower = new BoomerangTower();	break;
			case SLINGSHOT:		this.tower = new SlingshotTower();	break;
			case SWORD: 		this.tower = new SwordTower();	break;
			case G1:			this.tower = new G1Tower();	break;
			case G2:			this.tower = new G2Tower();	break;
			case G3:			this.tower = new G3Tower();	break;
			case G4:			this.tower = new G4Tower();	break;
		}
	}
	
	public Tower getTower()
	{
		return this.tower;
	}
	
}
