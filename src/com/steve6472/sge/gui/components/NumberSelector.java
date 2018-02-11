package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.events.ChangeEvent;
import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class NumberSelector extends Component
{
	private static final long serialVersionUID = 6097537167658644896L;
	int value = 0, max = 16, min = -16, panelType = 8;
	private List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
	private boolean enabled = true;
	
	private boolean addHovered = false, removeHovered = false, renderedAdd, renderedRemove;
	
	@Override
	public void init(BaseGame game)
	{
		setSize(150, 40);
	}

	@Override
	public void tick()
	{
		removeHovered = isCursorInComponent(getX(), getY(), 40, getHeight());

		addHovered = isCursorInComponent(getX() + getWidth() - 40, getY(), 40, getHeight());

		if (enabled)
		{
			if (removeHovered)
			{
				if (!renderedRemove)
				{
					repaint();
					renderedRemove = true;
				}
				if (getMouseHandler().mouseHold && !getMouseHandler().mouseTriggered)
				{
					if (getKeyHandler().alt.isPressed())
					{
						if (getMouseHandler().getButton() == 3)
							removeValue(1000);
						else
							removeValue(100);
					} else if (getKeyHandler().shift.isPressed())
					{
						removeValue(10);
					} else if (getKeyHandler().control.isPressed())
					{
						removeValue(5);
					} else
					{
						removeValue();
					}
					for (ChangeEvent ce : changeEvents)
					{
						ce.change();
					}
					getMouseHandler().mouseTriggered = true;
				}
			} else
			{
				renderedRemove = false;
				repaint();
			}

			if (addHovered)
			{
				if (!renderedAdd)
				{
					repaint();
					renderedAdd = true;
				}
				if (getMouseHandler().mouseHold && !getMouseHandler().mouseTriggered)
				{
					if (getKeyHandler().alt.isPressed())
					{
						if (getMouseHandler().getButton() == 3)
							addValue(1000);
						else
							addValue(100);
					} else if (getKeyHandler().shift.isPressed())
					{
						addValue(10);
					} else if (getKeyHandler().control.isPressed())
					{
						addValue(5);
					} else
					{
						addValue();
					}
					for (ChangeEvent ce : changeEvents)
					{
						ce.change();
					}
					getMouseHandler().mouseTriggered = true;
				}
			} else
			{
				renderedAdd = false;
				repaint();
			}
		}
	}

	@Override
	public void render(Screen screen)
	{
		
		Vec2 center = Font.stringCenter(new AABB(new Vec2(getX(), getY()), 40, getHeight()), " ", 1);

		if (!enabled)
		{
			getGame().panelList.getPanelById(12).render(getX(), getY(), 40, getHeight());
		} else
		{
			if (removeHovered)
			{
				getGame().panelList.getPanelById(11).render(getX(), getY(), 40, getHeight());
			} else
			{
				getGame().panelList.getPanelById(9).render(getX(), getY(), 40, getHeight());
			}

			getFont().render("-", screen, (int) center.getX(), (int) center.getY(), 1);

			if (addHovered)
			{
				getGame().panelList.getPanelById(11).render(getX() + getWidth() - 40, getY(), 40, getHeight());
			} else
			{
				getGame().panelList.getPanelById(9).render(getX() + getWidth() - 40, getY(), 40, getHeight());
			}

			getFont().render("+", screen, (int) center.getX() + getWidth() - 40, (int) center.getY(), 1);
		}
		
		getGame().panelList.getPanelById(getPanelType()).render(getX() + 40, getY(), getWidth() - 80, getHeight());;
		
		getFont().render("" + value, screen, x + 40 + (getWidth() - 80) / 2 - (("" + value).length() * 8) / 2, y + 16, 1);
	}

	/*
	 * Operators
	 */

	public void addValue(int i)
	{
		value += i;
		if (value > max)
			value = min;
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}

	public void removeValue(int i)
	{
		value -= i;
		if (value < min)
			value = max;
	}

	public void addValue()
	{
		addValue(1);
	}

	public void removeValue()
	{
		removeValue(1);
	}
	
	/*
	 * Setters
	 */

	public void setValue(int value)
	{
		this.value = value;
	}
	
	public void setMaxValue(int max)
	{
		this.max = max;
	}
	
	public void setMinValue(int min)
	{
		this.min = min;
	}

	public void setLocation(int x, int y)
	{
		super.setLocation(x, y);
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public void setPanelType(int type) { this.panelType = type; }
	
	/*
	 * Getters
	 */

	public int getValue()
	{
		return value;
	}
	
	public int getMaxValue()
	{
		return max;
	}
	
	public int getMinValue()
	{
		return min;
	}
	
	public int getPanelType()
	{
		return panelType;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
}
