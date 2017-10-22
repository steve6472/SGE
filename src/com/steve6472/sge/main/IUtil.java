/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 30. 9. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.main;

import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public interface IUtil
{
	default String getFormatedTime()
	{
		return Util.getFormatedTime();
	}
	
	default void printf(String s, Object...objects)
	{
		Util.printf(s, objects);
	}
	
	default void printfd(String s, Object...objects)
	{
		Util.printfd(s, objects);
	}
	
	default double getDistance(AABB from, AABB to)
	{
		return Util.getDistance(from, to);
	}
	
	default double getDistance(Vec2 from, Vec2 to)
	{
		return Util.getDistance(from, to);
	}
	
	default int getRandomInt(int max, int min)
	{
		return Util.getRandomInt(max, min);
	}
	
	default int getRandomInt(int max, int min, long seed)
	{
		return Util.getRandomInt(max, min, seed);
	}
	
	default double getRandomDouble(double max, double min)
	{
		return Util.getRandomDouble(max, min);
	}
	
	default double getRandomDouble(double max, double min, long seed)
	{
		return Util.getRandomDouble(max, min, seed);
	}
	
	default double countAngle(double fromx1, double fromy1, double fromx2, double fromy2)
	{
		return Util.countAngle(fromx1, fromy1, fromx2, fromy2);
	}
	
	default double countAngle(Vec2 from, Vec2 to)
	{
		return Util.countAngle(from, to);
	}
	
	default boolean isInCircle(Vec2 circle, int circleRadius, Vec2 obj)
	{
		return Util.isInCircle(circle, circleRadius, obj);
	}
}
