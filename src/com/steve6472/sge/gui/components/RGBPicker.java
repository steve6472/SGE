package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.Panel;
import com.steve6472.sge.gui.components.Slider;
import com.steve6472.sge.gui.components.panels.Panel1;
import com.steve6472.sge.main.BaseGame;

public class RGBPicker extends Component
{
	private Slider red, green, blue;
	private Panel back;
	
	@Override
	public void init(BaseGame game)
	{
		//TODO: Extant
		int X = 0;
		int Y = 0;
		int W = 280;
		int H = 180;
		
		setSize(W, H);
		
		back = new Panel(new Panel1(game.getScreen()));
		back.setSize(W, H);
		back.setLocation(X, Y);
		addComponent(back);
		
		red = new Slider();
		red.setLocation(X + 12, Y + 12);
		red.setSize(256);
		red.setMaxValue(255);
		addComponent(red);
		
		green = new Slider();
		green.setLocation(X + 12, Y + 12 + 50);
		green.setSize(256);
		green.setMaxValue(255);
		addComponent(green);
		
		blue = new Slider();
		blue.setLocation(X + 12, Y + 12 + 100);
		blue.setSize(256);
		blue.setMaxValue(255);
		addComponent(blue);

		repaintBackground();
	}
	
	boolean location = false;
	
	@Override
	public void setLocation(int x, int y)
	{
		if (back == null)
			location = true;
		this.x = x;
		this.y = y;
		if (back == null)
			return;
		back.setLocation(x, y);
		red.setLocation(x + 12, y + 12);
		green.setLocation(x + 12, y + 12 + 50);
		blue.setLocation(x + 12, y + 112);
		repaintBackground();
	}

	@Override
	public void render(Screen screen)
	{
		renderComponents(screen);
		screen.fillRect(getX() + 6, getY() + 154, getWidth() - 12, 20, getColor());
	}
	
	public int getColor()
	{
		return Screen.getColor(red.getValue(), green.getValue(), blue.getValue());
	}

	@Override
	public void tick()
	{
		tickComponents();
		if (location)
		{
			setLocation(x, y);
			location = false;
		}
		if (co != -1)
		{
			setColor(co);
			co = -1;
		}
	}
	
	@Override
	protected int getMinWidth()
	{
		return 280;
	}
	
	@Override
	protected int getMinHeight()
	{
		return 180;
	}
	
	private int co = -1;

	public void setColor(int i)
	{
		co = i;
		if (red == null)
			return;
		red.setValue(Screen.getRed(i));
		green.setValue(Screen.getGreen(i));
		blue.setValue(Screen.getBlue(i));

		repaint();
	}

}
