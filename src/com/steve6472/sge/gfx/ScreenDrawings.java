/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 13. 2. 2018
* Project: SGE
*
***********************/

package com.steve6472.sge.gfx;

import com.steve6472.sge.main.Util;

public class ScreenDrawings
{
	public static void drawSquareOutline(Screen screen, double x, double y, double angle, int color, double size)
	{
		double rads0 = Math.toRadians(angle + 45);
		double rads1 = Math.toRadians(angle - 45);
		double rads2 = Math.toRadians(angle + 135);
		double rads3 = Math.toRadians(angle - 135);
		
		double xp11 = Math.cos(rads0) * size * Util.PYThAGORASRATIO;
		double yp11 = Math.sin(rads0) * size * Util.PYThAGORASRATIO;
//		screen.drawCircle(IValues.WIDTH / 2 + xp11, IValues.HEIGHT / 2 + yp11, 3, 0xff00ffff);
		
		double xp10 = Math.cos(rads1) * size * Util.PYThAGORASRATIO;
		double yp10 = Math.sin(rads1) * size * Util.PYThAGORASRATIO;
//		screen.drawCircle(IValues.WIDTH / 2 + xp10, IValues.HEIGHT / 2 + yp10, 3, 0xff00ffff);
		
		double xp01 = Math.cos(rads2) * size * Util.PYThAGORASRATIO;
		double yp01 = Math.sin(rads2) * size * Util.PYThAGORASRATIO;
//		screen.drawCircle(IValues.WIDTH / 2 + xp01, IValues.HEIGHT / 2 + yp01, 3, 0xff00ffff);
		
		double xp00 = Math.cos(rads3) * size * Util.PYThAGORASRATIO;
		double yp00 = Math.sin(rads3) * size * Util.PYThAGORASRATIO;
//		screen.drawCircle(IValues.WIDTH / 2 + xp00, IValues.HEIGHT / 2 + yp00, 3, 0xff00ffff);

		screen.drawLine(x + xp11, y + yp11, x + xp10, y + yp10, color);
		screen.drawLine(x + xp10, y + yp10, x + xp00, y + yp00, color);
		screen.drawLine(x + xp00, y + yp00, x + xp01, y + yp01, color);
		screen.drawLine(x + xp01, y + yp01, x + xp11, y + yp11, color);
	}

}
