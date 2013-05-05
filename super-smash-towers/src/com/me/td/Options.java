/**
 * 
 */
package com.me.td;

/**
 * @author Classified Studios
 *
 */
public class Options {

	static boolean enemyHealthDisplay = false;

	public Options()
	{
		
	}

	public static boolean isEnemyHealthDisplay()
	{
		return enemyHealthDisplay;
	}

	public static void setEnemyHealthDisplay(boolean enemyHealthDisplay)
	{
		Options.enemyHealthDisplay = enemyHealthDisplay;
	}
	
	public static void toggleEnemyHealthDisplay()
	{
		Options.enemyHealthDisplay = !enemyHealthDisplay;
	}
	
	

}
