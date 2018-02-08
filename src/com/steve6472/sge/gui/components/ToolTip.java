package com.steve6472.sge.gui.components;

import java.io.Serializable;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.components.panels.Panel8;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class ToolTip implements Serializable
{
	private static final long serialVersionUID = -5973825427874173864L;
	int x, y, delay = 200;
	private String text;
	Panel background;
	Font font;
	boolean isVisible = false;

	public ToolTip(BaseGame game)
	{
		background = new Panel(new Panel8(game.getScreen()));
		background.setLocation(x, y);
		background.setSize(100, 40);
		font = game.font;
		game.toolTips.add(this);
	}

	public void render(Screen screen)
	{
		if (isVisible())
		{
			background.render(screen);
			font.render(text, screen, x + 6, y + 16, 1);
		}
	}
	
	/*
	 * Operators
	 */

	public void show()
	{
		isVisible = true;
	}

	public void hide()
	{
		isVisible = false;
	}
	
	/*
	 * Setters
	 */

	public void setText(String text)
	{
		this.text = text;
		background.setSize(text.length() * 8 + 16, 40);
	}

	public void setBackground(PanelBase panel)
	{
		this.background = new Panel(panel);
	}

	public void setVisible(boolean b)
	{
		isVisible = b;
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
		background.setLocation(x, y);
	}
	
	public void setDelay(int delay)
	{
		this.delay = delay;
	}
	
	/*
	 * Getters
	 */
	
	public String getText()
	{
		return text;
	}

	public boolean isVisible()
	{
		return isVisible;
	}
	
	public int getDelay()
	{
		return delay;
	}
}
