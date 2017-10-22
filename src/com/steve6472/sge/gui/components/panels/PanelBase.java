package com.steve6472.sge.gui.components.panels;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.BaseGame;

public abstract class PanelBase
{
	private Screen screen;
	public final int id;
	
	public PanelBase(Screen screen)
	{
		this.screen = screen;
		id = -1;
	}
	
	public PanelBase(BaseGame game)
	{
		this.screen = game.getScreen();
		id = game.totalId;
		game.totalId++;
	}
	
	public int maxx = Integer.MAX_VALUE, maxy = Integer.MAX_VALUE, minx = 0, miny = 0;
	
	public int thickness = 2;
	
	public abstract void render(int x, int y, int w, int h);
	
	public String getName()
	{
		return "Unarmed";
	}
	
	public void setMaxRender(int maxX, int maxY, int minX, int minY)
	{
		maxx = maxX;
		maxy = maxY;
		minx = minX;
		miny = minY;
	}
	
	public void fillRect(int x, int y, int w, int h, int c)
	{
		screen.fillRect(x, y, w, h, c, maxx, maxy, minx, miny);
	}
	
	public void drawRect(int x, int y, int w, int h, int c)
	{
		screen.drawRect(x, y, w, h, thickness, c);
	}
}
