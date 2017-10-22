package com.steve6472.sge.main.game;

import com.steve6472.sge.main.Util;

public class Vec2
{
	private double x, y;

	public Vec2(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vec2 move(double xx, double yy)
	{
		x += xx;
		y += yy;
		return this;
	}
	
	public Vec2 move(double xx, double yy, double speed)
	{
		x += xx * speed;
		y += yy * speed;
		return this;
	}
	
	/**
	 * 
	 * @param angle
	 * @return
	 */
	public Vec2 move(double angle)
	{
		double xa = ((Math.cos(Math.toRadians(angle - 90))) * 1);
		double ya = ((Math.sin(Math.toRadians(angle - 90))) * 1);
		move(xa, ya);
		return this;
	}
	
	public Vec2 move2(double angle, double speed)
	{
		double xa = ((Math.cos(Math.toRadians(angle - 90))) * 1);
		double ya = ((Math.sin(Math.toRadians(angle - 90))) * 1);
		move(xa, ya, speed);
		return this;
	}
	
	public double getX() { return x; }
	
	public double getY() { return y; }
	
	public Vec2 setX(double x) { this.x = x; return this; }
	
	public Vec2 setY(double y) { this.y = y; return this; }

	public Vec2 up() { this.y--; return this; }

	public Vec2 down() { this.y++; return this; }

	public Vec2 left() { this.x--; return this; }

	public Vec2 right() { this.x++; return this; }

	public Vec2 up(double d) { this.y -= d; return this; }

	public Vec2 down(double d) { this.y += d; return this; }

	public Vec2 left(double d) { this.x -= d; return this; }

	public Vec2 right(double d) { this.x += d; return this; }
	
	public void invert() { this.x = -x; this.y = -y; }

	/**
	 * 
	 * @return inverted clone (x = -x; y = -y;)
	 */
	public Vec2 getInverted() { Vec2 cloned = clone(); cloned.invert(); return cloned; }

	public Vec2 clone() { return new Vec2(x, y); }

	public Vec2 add(Vec2 add)
	{
		this.x += add.getX();
		this.y += add.getY();
		return this;
	}

	public Vec2 setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public static Vec2 getVec2InRange(double xmin, double xmax, double ymin, double ymax)
	{
		return new Vec2(Util.getRandomDouble(xmax, xmin), Util.getRandomDouble(ymax, ymin));
	}
	
	public void printLocation()
	{
		System.out.println("X: " + getX() + " / Y: " + getY());
	}
	
	@Override
	public String toString()
	{
		return "Vec2 [x=" + x + ", y=" + y + "]";
	}

	public boolean equals(Object vec2)
	{
		if (vec2 instanceof Vec2)
		{
			Vec2 vec = (Vec2) vec2;
			return vec.getX() == getX() && vec.getY() == getY();
		}
		return false;
	}
}
