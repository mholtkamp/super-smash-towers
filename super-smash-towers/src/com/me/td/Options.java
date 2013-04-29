/**
 * 
 */
package com.me.td;

/**
 * @author Classified Studios
 *
 */
public class Options {

	static boolean enemyHealthDisplay = true;

	public Options()
	{
		
	}

	public static boolean isEnemyHealthDisplay()
	{
		return enemyHealthDisplay;
	}

	public void setEnemyHealthDisplay(boolean enemyHealthDisplay)
	{
		Options.enemyHealthDisplay = enemyHealthDisplay;
	}
	
	
	

}
