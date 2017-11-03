package com.steve6472.sge.main;

import java.util.Calendar;
import java.util.Random;

import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class Util
{

	public static final int HOVERED_OVERLAY = 0x807f87be;
	public static final int SELECTED_OVERLAY = 0x806d76ad;
	
	public static String getFormatedTime()
	{
		return String.format("%tY.%tm.%te-%tH.%tM.%tS", Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance());
	}
	
	/**
	 * I just like the method from C++ :D
	 * @param s
	 * @param objects
	 */
	public static void printf(String s, Object... objects)
	{
		System.out.println(String.format(s, objects));
	}
	/**
	 * I just like the method from C++ :D
	 * @param s
	 * @param objects
	 */
	public static void printfd(String s, Object... objects)
	{
		System.err.println(String.format(s, objects));
	}
	
//	/**
//	 * Prob not gonna use it but I can change it for the Magical Key to work with AABB
//	 * @param r_1
//	 * @param r_2
//	 * @return
//	 */
//	public static int getDistance(Rectangle r_1, Rectangle r_2)
//	{
//
//		int a = -((r_1.height / 2 + r_1.y) - (r_2.height / 2 + r_2.y));
//		int b = -((r_1.width / 2 + r_1.x) - (r_2.width / 2 + r_2.x));
//		
//		return (int) Math.sqrt((a * a) + (b * b));
//	}
	
	/**
	 * @param r_1
	 * @param r_2
	 * @return
	 */
	public static double getDistance(AABB from, AABB to)
	{

		double a = -((from.getCenterY()) - (to.getCenterY()));
		double b = -((from.getCenterX()) - (to.getCenterX()));
		
		return Math.sqrt((a * a) + (b * b));
	}
	
	/**
	 * @return
	 */
	public static double getDistance(Vec2 from, Vec2 to)
	{

		double a = -((from.getY()) - (to.getY()));
		double b = -((from.getX()) - (to.getX()));
		
		return Math.sqrt((a * a) + (b * b));
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static int getRandomInt(int max, int min)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		Random ra = new Random(new Random().nextLong());
		return ra.nextInt((max - min) + 1) + min;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static int getRandomInt(int max, int min, long seed)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		Random ra = new Random(seed);
		return ra.nextInt((max - min) + 1) + min;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static double getRandomDouble(double max, double min)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		Random ra = new Random();
		double r = min + (max - min) * ra.nextDouble();
		return r;
	}

	/**
	 * 
	 * @param max
	 * @param min
	 * @return if max == min returns max, if max > min returns random number
	 */
	public static double getRandomDouble(double max, double min, long seed)
	{
		if (max == min)
		{
			return max;
		}
		if (max < min)
		{
			return 0;
		}
		Random ra = new Random(seed);
		double r = min + (max - min) * ra.nextDouble();
		return r;
	}

	/**
	 * 
	 * @param fromx1 From X
	 * @param fromy1 From Y
	 * @param fromx2 To X
	 * @param fromy2 To Y
	 * @return angle
	 */
	public static double countAngle(double fromx1, double fromy1, double fromx2, double fromy2)
	{
		return -Math.toDegrees(Math.atan2(fromx1 - fromx2, fromy1 - fromy2));
	}
	
	public static double countAngle(Vec2 from, Vec2 to)
	{
		return -Math.toDegrees(Math.atan2(from.getX() - to.getX(), from.getY() - to.getY()));
	}

	public static boolean isInCircle(Vec2 circle, int circleRadius, Vec2 obj)
	{
		if (obj == null)
			return false;
		if (circle == null)
			return false;
		if (circleRadius == 0)
			return false;
		
		double i = (obj.getX() - circle.getX()) * (obj.getX() - circle.getX());
		double j = (obj.getY() - circle.getY()) * (obj.getY() - circle.getY());
		double k = Math.sqrt(i + j);
		if (k <= circleRadius)
		{
			return true;
		}
		return false;
	}
	
	public static String getLastClassName(Class<?> clazz) { return clazz.getName().split("\\.")[clazz.getName().split("\\.").length - 1]; }

	public static int getBiggestClosetsSqrt(int count)
	{
		int r = count;
		double temp = Math.sqrt(r);
		int iteration = 0;
		while (true)
		{
			if (temp == Math.floor(temp))
			{
				return (int) temp;
			} else
			{
				r++;
				temp = Math.sqrt(r);
			}
			iteration++;
			if (iteration >= 256)
			{
				System.err.println("More than 256 iterations!");
				return count;
			}
		}
	}
	
	public static boolean isNumber(String l)
	{
		try
		{
			new Long(l);
			return true;
		} catch (NumberFormatException ex)
		{
			return false;
		}
	}
	
/*
	public static double getRandomCircleX(double radius)
	{
//		double angle = Math.toRadians(Math.random()*Math.PI*2);
//		return (radius * Math.cos(angle * Math.PI / 180d));
		double angle = Math.toRadians(getRandomDouble(360, 0));
		double r = radius * new Random().nextDouble();
		return r * Math.cos(angle);
	}

	public static double getRandomCircleY(double radius)
	{
//		double angle = Math.toRadians(Math.random()*Math.PI*2);
//		return (radius * Math.sin(angle * Math.PI / 180d));
		double angle = Math.toRadians(getRandomDouble(360, 0));
		double r = radius * new Random().nextDouble();
		return r * Math.sin(angle);
	}*/
	
	/**
	 * TODO: Create regex escape method .$|()[{^?*+\\
	 */
}
