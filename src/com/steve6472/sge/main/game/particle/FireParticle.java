/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 17. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game.particle;

import com.steve6472.sge.main.Util;

public class FireParticle extends ColorChangingParticle
{

	private static final long serialVersionUID = -7344748137421315234L;


	public FireParticle(double x, double y, double angle, double step, int size, int life)
	{
		super(Util.getRandomDouble(x + 5, x - 5), y, Util.getRandomDouble(step * angle, step * (angle - 2)), size, life, 0xffEE1910, 0xffE7BA16, 30);
	}

	
	public FireParticle(double x, double y, double angle, int size, int life)
	{
		super(Util.getRandomDouble(x + 5, x - 5), y, Util.getRandomDouble(22.5d * angle, 22.5d * (angle - 2)), size, life, 0xffEE1910, 0xffE7BA16, 30);
	}
	

}
