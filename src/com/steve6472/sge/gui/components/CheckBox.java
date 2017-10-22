package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.events.ChangeEvent;
import com.steve6472.sge.main.BaseGame;

public class CheckBox extends Component
{
	private boolean isChecked = false, isEnabled = true, isHovered = false;
	private int type = 0;
//	private short idleMouseTime = 0;

	private static final Sprite[] TYPES =
	{ new Sprite("components/checkBox/checkTypes/type_0.png"), new Sprite("components/checkBox/checkTypes/type_1.png") };

	//TODO: Remove and replace with panels
	private static final Sprite N = new Sprite("components/checkBox/check_n.png");
	private static final Sprite H = new Sprite("components/checkBox/check_h.png");
	private static final Sprite D = new Sprite("components/checkBox/check_d.png");

	protected List<ChangeEvent> changeEvent = new ArrayList<ChangeEvent>();
	
	@Override
	public void init(BaseGame game)
	{
		setSize(40, 40);
	}
	
//	public void addToolTip(String text)
//	{
//		toolTip = new ToolTip(getGame());
//		toolTip.setText(text);
//	}

//	int mouseLastPosX = 0;
//	int mouseLastPosY = 0;
//	boolean mouseMoved = false;
	
	private boolean hoverRepainted = false;

	@Override
	public void tick()
	{
		isHovered = isCursorInComponent(x, y, 40, 40);
		if (isHovered && !hoverRepainted)
		{
			repaint();
			hoverRepainted = true;
		} else if (!isHovered && hoverRepainted)
		{
			repaint();
			hoverRepainted = false;
		}
		if (isHovered && getMouseHandler().mouse_hold && !getMouseHandler().mouse_triggered)
		{
			toggle();
			getMouseHandler().mouse_triggered = true;
			repaint();
		}

		toolTipTick(getMouseHandler());
	}

	@Override
	public void render(Screen screen)
	{
		if (isEnabled)
		{
			if (isHovered)
			{
				screen.renderSprite(H, x, y);
			} else
			{
				screen.renderSprite(N, x, y);
			}
		} else
		{
			screen.renderSprite(D, x, y);
		}

		if (isChecked)
		{
			screen.renderSprite(TYPES[getType()], x, y);
		}
	}

	/*
	 * Operators
	 */

	public void enable()
	{
		isEnabled = true;
		repaint();
	}

	public void disable()
	{
		isEnabled = false;
		repaint();
	}

	public void check()
	{
		isChecked = true;
		repaint();
	}

	public void uncheck()
	{
		isChecked = false;
		repaint();
	}

	public void toggle()
	{
		isChecked = !isChecked;
		changeEvent.forEach((e) -> e.change());
		repaint();
		//TODO: changeEvent.forEach((e) -> e.change());
	}
	
	public void addChangeEvent(ChangeEvent event)
	{
		this.changeEvent.add(event);
	}
	
	/*
	 * Setters
	 */

	public void setChecked(boolean b)
	{
		isChecked = b;
		repaint();
	}

	public void setEnabled(boolean b)
	{
		this.isEnabled = b;
		repaint();
	}

	public void setType(int b)
	{
		this.type = b;
		repaint();
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		repaint();
//		repaintBackground();
	}
	
	/*
	 * Getters
	 */

	public int getType()
	{
		return type;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public boolean isChecked()
	{
		return isChecked;
	}
}
