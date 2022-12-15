package mastermind;

import java.awt.Graphics;
import java.awt.Image;

import gui.HotSpot;

class MasterMindColor
{  
	private Image guess_color_img;
	private HotSpot guess_hot_spot;

	public MasterMindColor(Image color_img, HotSpot hot_spot)  
	{
		guess_color_img = color_img;
		guess_hot_spot = hot_spot;
	}
	
	public int getGuessColorID()
	{
		return guess_hot_spot.getHotSpotID();
	}

	public boolean isColorSelected( int x, int y)
	{
		return guess_hot_spot.isSelected(x, y);
	}

	public void draw(Graphics g, int x, int y)
	{
		x = x - 31;
		y = y - 31;
		g.drawImage(guess_color_img, x, y, null);
	}
	
	public String toString()
	{
		return "" + guess_hot_spot.getHotSpotID();
	}
}
