/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 17. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game.particle;

import com.steve6472.sge.gfx.AnimatedSprite;
import com.steve6472.sge.gfx.Screen;

public class SpriteColoredParticle extends AngledParticle
{
	private static final long serialVersionUID = 1324443855475302800L;
	public static final AnimatedSprite sprite = new AnimatedSprite("particle_01.png", 9);
	
	static
	{
		sprite.setChangeTime(-1);
	}
	
	int currentParticle = 0;
	int changeDelay = 0, maxDelay = 0;
	int currentColor = 0;
	int c1 = 0;
	
	public SpriteColoredParticle(double x, double y, double angle, int life, int from, int to)
	{
		super(x, y, angle, 0, life, 0xffffffff);
		maxDelay = life / 9;
		currentColor = from;
		c1 = to;
	}
	
	@Override
	public void tick()
	{
		changeDelay++;
		if (changeDelay >= maxDelay)
		{
			changeDelay = 0;
			currentParticle++;
		}
		texture = sprite.getFrame(currentParticle);
		
		for (int i = 0; i < texture.pixels.length; i++)
		{
			if (texture.pixels[i] == 0xffffffff || texture.pixels[i] == 0xffffff)
				texture.pixels[i] = currentColor;
		}
		
		double ratio = 1d / 5000;
		
		int oldColor = currentColor;
		
		int newColor = 0;
		
		for (int i = 0; i < texture.pixels.length; i++)
		{
			int c = currentColor;
			int r = (int) Math.abs((ratio * Screen.getRed(c1)) + ((1 - ratio) * Screen.getRed(c)));
			int g = (int) Math.abs((ratio * Screen.getGreen(c1)) + ((1 - ratio) * Screen.getGreen(c)));
			int b = (int) Math.abs((ratio * Screen.getBlue(c1)) + ((1 - ratio) * Screen.getBlue(c)));
			
			newColor = Screen.getColor(r, g, b, Screen.getAlpha(c));

			if (texture.pixels[i] == oldColor)
				texture.pixels[i] = newColor;
		}
		
		currentColor = newColor;
		
		super.tick();
	}

}
