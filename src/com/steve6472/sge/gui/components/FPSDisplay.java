/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 15. 12. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.gui.components;

import java.awt.Color;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.main.BaseGame;

public class FPSDisplay extends Component
{
	
	private static final long serialVersionUID = 1508542347738773160L;
	private int fontSize = 1;
	private int lastFPS = 0;

	public FPSDisplay()
	{
	}

	@Override
	public void init(BaseGame game)
	{
		lastFPS = game.getFPS();
	}

	@Override
	public void render(Screen screen)
	{
		int width = fontSize * 8 * 3 + 5 * fontSize;
		
		screen.drawRect(getX(), getY(), width, fontSize * 8 + 5 * fontSize, 2, 0xff808080);
		screen.fillRect(getX() + 2, getY() + 2, width - 4, fontSize * 8 + 5 * fontSize - 4, 0xff000000);
		
		
		int add = 0;
		if (lastFPS <= 9)
			add = fontSize * 11 + fontSize;
		else if (lastFPS >= 10 && lastFPS <= 99)
			add = fontSize * 7 + fontSize;
		else
			add = fontSize * 3 + fontSize;
		
		getFont().renderColoredWithShadow("" + lastFPS, screen, getX() + add, getY() + 5 * fontSize, fontSize, Font.WHITE, Color.HSBtoRGB((float) ((float) Math.min(60, lastFPS) / 60f * 0.3333333333f), 1f, 1));
	}

	@Override
	public void tick()
	{
		if (lastFPS != getGame().getFPS())
		{
			lastFPS = Math.min(getGame().getFPS(), 999);
			repaint();
		}
	}
	
	public void setFontSize(int fontSize) { this.fontSize = Math.max(1, fontSize); }
	
	public int getFontSize() { return fontSize; }
	
	public int getFPS() { return getGame().getFPS(); }

	/**
	 * Size can be changed only with {@code setFontSize()}
	 */
	@Override
	@Deprecated
	public void setSize(int width, int height)
	{
		
	}

}
