package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.gui.components.panels.progressBar.ProgressBarBack;
import com.steve6472.sge.gui.components.panels.progressBar.ProgressBarType1;
import com.steve6472.sge.main.BaseGame;

public class ProgressBar extends Component
{
	private int value = 0, maxValue = 100, minValue = 0;
	PanelBase normal;
	PanelBase filled;
	
	@Override
	public void init(BaseGame game)
	{
		normal = new ProgressBarBack(getScreen());
		filled = new ProgressBarType1(getScreen());
	}

	@Override
	public void render(Screen screen)
	{
		normal.render(x, y, width, height);
		filled.render(x, y, width, height);
	}

	@Override
	public void tick()
	{
		
	}
	
	int oldValue = 0;

	protected void update()
	{
		if (oldValue == value)
			return;
		oldValue = value;

		double max = getMaxValue();

		int percent = (int) ((getValue() * max) / getMaxValue());

		filled.setMaxRender(getX() + (int) ((percent / max) * getWidth()), getY() + getWidth(), getX(), getY());
		
		repaint();
	}
	
	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */
	
	public void setValue(int value)
	{
		this.value = value;
		update();
	}
	
	public void setMaxValue(int maxValue)
	{
		this.maxValue = maxValue;
		update();
	}
	
	public void setMinValue(int minValue)
	{
		this.minValue = minValue;
		update();
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		update();
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		update();
	}
	
	/*
	 * Getters
	 */
	
	public int getValue()
	{
		return value;
	}
	
	public int getMaxValue()
	{
		return maxValue;
	}
	
	public int getMinValue()
	{
		return minValue;
	}
	
	@Override
	protected int getMinHeight()
	{
		return 10;
	}
	
	@Override
	protected int getMinWidth()
	{
		return 10;
	}

}
