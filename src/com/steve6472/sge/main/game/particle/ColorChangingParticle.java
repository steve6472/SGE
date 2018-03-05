/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 16. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game.particle;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;

public class ColorChangingParticle extends AngledParticle
{
	private static final long serialVersionUID = -7241722656600969795L;
	int c1;
	double ratio = 100d;
	
	public ColorChangingParticle(double x, double y, double angle, int size, int life, int from, int to, double ratio)
	{
		super(x, y, angle, size, life, 0);
		this.c1 = to;
		this.ratio = ratio;
		setColor(from);
	}
	
	public ColorChangingParticle(double x, double y, double angle, int life, Sprite from, int to, double ratio)
	{
		super(x, y, angle, life, from);
		this.c1 = to;
		this.ratio = ratio;
	}

	@Override
	public void tick()
	{
		double ratio = 1d / this.ratio;
		
		for (int i = 0; i < texture.pixels.length; i++)
		{
			int c = texture.pixels[i];
			int r = (int) Math.abs((ratio * Screen.getRed(c1)) + ((1 - ratio) * Screen.getRed(c)));
			int g = (int) Math.abs((ratio * Screen.getGreen(c1)) + ((1 - ratio) * Screen.getGreen(c)));
			int b = (int) Math.abs((ratio * Screen.getBlue(c1)) + ((1 - ratio) * Screen.getBlue(c)));

			if (Screen.getAlpha(c) > 0)
				texture.pixels[i] = Screen.getColor(r, g, b, Screen.getAlpha(c));
		}

		super.tick();
	}

}