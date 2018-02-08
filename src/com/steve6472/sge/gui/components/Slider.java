package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.events.ChangeEvent;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class Slider extends Component
{
	private static final long serialVersionUID = 4164297008969991704L;

	//TODO: Mouse Wheel scroll!
	PanelBase sliderBack;
	
	private int value = 0, maxValue = 100, minValue = 0, privateX;

	protected List<ChangeEvent> changeEvent = new ArrayList<ChangeEvent>();
	
	@Override
	public void init(BaseGame game)
	{
		sliderBack = game.panelList.getPanelById(12);
	}

	@Override
	public void render(Screen screen)
	{
		sliderBack.render(getX(), getY(), getWidth(), getHeight());

		if (!hovered)
			getGame().panelList.getPanelById(9).render(moveX, getY() - 8, 32, 48); //render at x (mouse) and y (slight offset just cuz it look really good)
		else
			//FIXME: Render of slider button is kinda broken
			getGame().panelList.getPanelById(11).render(moveX, getY() - 8, 32, 48);
		
	}

	protected void sliderChange()
	{
		privateX = getX() - getMouseHandler().getMouseX(); //mouse pos relativly to component
		
		changeEvent.forEach((ce) -> ce.change());
	}
	
	private boolean setted = false;
	private int oldValue = 0;
	private int moveX = 0;
	private boolean hovered = false;
	private boolean lastHovered = false;
	
	@Override
	public void tick()
	{
		tickComponents();

		if (!setted)
		{
			onMousePressed(c -> privateX = getX() - getMouseHandler().getMouseX());
		} else
		{
			setted = false;
		}
		
		//Over the slider thing
		hovered = isCursorInComponent(moveX, getY() - 8, 32, 48);
		
		if (hovered != lastHovered)
		{
			lastHovered = hovered;
			repaint();
		}

		float max = (float) getMaxValue();

		int valueBuffer = -((int) ((privateX * max) / getWidth())); //Some weird shit
		
		int oldValue = getValue();
		
		if (valueBuffer > getMaxValue())
		{
			value = getMaxValue();
		} else if (valueBuffer < 0)
		{
			value = 0;
		} else
		{
			value = valueBuffer;
		}
		
		if (oldValue != getValue())
		{
//			slider.setToolTipText("Value:" + getValue());
		}
		
		moveX = getX() - 16 - privateX; //Center to the slider

		if (privateX + 8 > 0)
			moveX = getX() - 8;

		if (privateX - 8 < (-getWidth()))
			moveX = getX() + getWidth() - 24;

		//Cuz of background repaint delay the slider button have little "ghost"
		
		if (this.oldValue != value)
		{
			repaint();
			if (parentComponent == null && parentGui != null)
				repaintBackground();
			else
				parentComponent.repaint();
			this.oldValue = value;
			changeEvent.forEach(ce -> ce.change());
		}
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
		setted = true;
		privateX = -((int) ((value * getWidth()) / getMaxValue()));
	}
	
	public void setMaxValue(int maxValue)
	{
		this.maxValue = maxValue;
	}
	
	public void setMinValue(int minValue)
	{
		this.minValue = minValue;
	}
	
	public void setSize(int width)
	{
		if (width < 64)
			width = 64;
		super.setSize(width, 32);
	}
	
	@Override
	public void setSize(int width, int height)
	{
		setSize(width);
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvent.add(ce);
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
	protected int getMinWidth()
	{
		return 64;
	}
	
	@Override
	protected int getMinHeight()
	{
		return 32;
	}
}
