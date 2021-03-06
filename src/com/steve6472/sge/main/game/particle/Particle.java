package com.steve6472.sge.main.game.particle;

import java.io.Serializable;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gfx.SpriteUtils;
import com.steve6472.sge.main.game.IObject;
import com.steve6472.sge.main.game.Killable;
import com.steve6472.sge.main.game.Vec2;

public class Particle extends Killable implements IObject, Cloneable, Serializable
{
	private static final long serialVersionUID = -2616195696649782107L;
	protected Vec2 pos;
	protected double dx;
	protected double dy;
	protected int life;
	protected Sprite texture = null;
	protected int size = 0;
	protected double speed = 1;

	public Particle(double x, double y, double dx, double dy, int size, int life, int c)
	{
		this.pos = new Vec2(x, y);
		this.dx = dx;
		this.dy = dy;
		this.life = life;
		this.size = size;
		texture = SpriteUtils.fill(new Sprite(size, size), c);
	}

	public Particle setSpeed(double speed)
	{
		this.speed = speed;
		return this;
	}

	public Particle setColor(int c)
	{
		SpriteUtils.fill(texture, c);
		return this;
	}

	public Particle setTexture(Sprite sprite)
	{
		this.texture = sprite;
		return this;
	}

	public Particle(double x, double y, double dx, double dy, int life, Sprite sprite)
	{
		this.pos = new Vec2(x, y);
		this.dx = dx;
		this.dy = dy;
		this.life = life;
		texture = new Sprite(sprite.pixels, sprite.getWidth(), sprite.getHeight());
	}

	public void tick()
	{
		move();
	}

	protected void move()
	{
		if (!isDead())
		{
			pos.setLocation(pos.getX() + dx * speed, pos.getY() + dy * speed);
			life--;
			if (life <= 0)
				setDead();
			checkPos();
		}
	}

	protected void checkPos()
	{
		if (pos.getX() <= 5)
		{
			setDead();
			return;
		}

		if (pos.getY() <= 5)
		{
			setDead();
			return;
		}

//		if (y > 668 - 20)
//		{
//			should_despawn = true;
//			return;
//		}
	}

	public void render(Screen screen)
	{
		screen.renderSprite(texture, pos.getX(), pos.getY());
	}

	@Override
	protected Particle clone() throws CloneNotSupportedException
	{
		return (Particle) super.clone();
	}
	
	public Particle safeClone()
	{
		try
		{
			return clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
