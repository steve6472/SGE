package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.BaseGame;

public class Image extends Component
{
	private Sprite sprite;
	public int maxx, maxy, minx, miny;
	private boolean setLimitedRender;
	public boolean renderAsTransparent = false;
	
	public Image(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	@Override
	public void init(BaseGame game)
	{
	}

	@Override
	public void render(Screen screen)
	{
		if (sprite != null)
		{
			if (!setLimitedRender)
			{
				if (renderAsTransparent)
					screen.renderTransparentSprite(sprite, x, y);
				else
					screen.renderSprite(sprite, x, y);
			} else
			{
				screen.renderSprite(sprite, x, y, maxx, maxy, minx, miny);
			}
		}
	}

	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */

	public void setLimitedRender(int maxx, int maxy, int minx, int miny)
	{
		this.maxx = maxx;
		this.maxy = maxy;
		this.minx = minx;
		this.miny = miny;
		setLimitedRender = true;
	}

	public Image setImage(Sprite sprite)
	{
		this.sprite = sprite;
		return this;
	}

	@Override
	public void tick()
	{
	}
	
	/*
	 * Getters
	 */
	
}
