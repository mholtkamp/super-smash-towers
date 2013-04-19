package util;


public class PressButton
{
	
	private boolean isKeyPressed, enable;
	
	public PressButton(boolean isKeyPressed, boolean enable)
	{
		this.isKeyPressed = isKeyPressed;
		this.enable = enable;
	}
	
	public boolean press_button()
	{
		if (isKeyPressed)
		{
			if (enable)
			{
				// logic goes here
				enable = false;
			}
		}
		else
			enable = true;
		return true;
	}
	
}
