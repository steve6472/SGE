/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 25. 1. 2018
* Project: SGE
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.panels.Panel1;
import com.steve6472.sge.main.BaseGame;

public class GroupedSliders extends Component
{
	
	private static final long serialVersionUID = -8308406769388191L;
	Slider[] sliders;
	String[] texts;
	private Panel back;
	private int textOrientation = 0;

	public GroupedSliders()
	{
	}

	@Override
	public void init(BaseGame game)
	{
		
		back = new Panel(new Panel1(game.getScreen()));
		addComponent(back);
	}

	@Override
	public void render(Screen screen)
	{
		renderComponents(screen);
		
		for (int i = 0; i < texts.length; i++)
		{
			getFont().render(texts[i], screen, x + 12, y + 8 + getHeight() / sliders.length * i, 1);
		}
	}

	@Override
	public void tick()
	{
		tickComponents();
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
		
		int index = 0;
		for (Slider s : sliders)
		{
			s.setLocation(x + 12, y + 8 + getHeight() / sliders.length * index + 22);
			index++;
		}
		
		back.setLocation(getX(), getY());
	}
	
	@Override
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		back.setSize(width, height);
		setLocation(x, y);
		
		for (Slider s : sliders)
		{
			s.setSize(getWidth() - 24);
		}
	}

	/**
	 * One Slider takes 75 pixels
	 * @param i
	 */
	public void setSliderCount(int i)
	{
		sliders = new Slider[i];
		texts = new String[i];
		for (int j = 0; j < i; j++)
		{
			texts[j] = "";
			sliders[j] = new Slider();
			addComponent(sliders[j]);
		}
	}
	
	public void setText(String text, int index)
	{
		texts[index] = text;
	}
	
	public int getValue(int index)
	{
		return sliders[index].getValue();
	}
	
	public Slider getSlider(int index)
	{
		return sliders[index];
	}

	public void setTextOrientation(int i)
	{
		textOrientation = i;
	}
	
	public int getTextOrientation()
	{
		return textOrientation;
	}
	
	

}
