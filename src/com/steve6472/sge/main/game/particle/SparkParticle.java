/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 16. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game.particle;

import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.main.Util;

public class SparkParticle extends ColorChangingParticle
{
	
	static final Sprite spark = new Sprite("spark.png");

	public SparkParticle(double x, double y, int life)
	{
		super(x, y, getAng(), life, Sprite.rotate(spark, (int) getAng()), 0xff895F15, life - 5);
		a = -1;
		setSpeed(1.5);
	}
	
	private static double a = -1;
	public static double getAng()
	{
		if (a == -1)
		{
			a = Util.getRandomDouble(360, 0);
		}
		
		return a;
	}

}
