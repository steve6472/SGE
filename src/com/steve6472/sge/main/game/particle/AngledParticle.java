/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 16. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game.particle;

import com.steve6472.sge.gfx.Sprite;

public class AngledParticle extends Particle
{
	private static final long serialVersionUID = -4195764672446043110L;
	double angle;
	
	public AngledParticle(double x, double y, double angle, int size, int life, int c)
	{
		super(x, y, 0, 0, size, life, c);
		this.angle = angle;
	}
	
	public AngledParticle(double x, double y, double angle, int life, Sprite sprite)
	{
		super(x, y, 0, 0, life, sprite);
		this.angle = angle;
	}

	protected void move()
	{
		if (!isDead())
		{
			pos.move2(angle, speed);
			life--;
			if (life <= 0)
				setDead();
			checkPos();
		}
	}

}
