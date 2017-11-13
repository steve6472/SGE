/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 17. 9. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.gui.components.panels;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class CustomPanel extends PanelBase
{

	public int border = 0xff333333;
	public int innerBorder = 0xff7f7f7f;
	public int fill = 0xfff2f2f2;

	public CustomPanel(Screen screen)
	{
		super(screen);
		thickness = 2;
	}

	public CustomPanel(BaseGame game)
	{
		super(game);
		thickness = 2;
	}

	@Override
	public void render(int x, int y, int w, int h)
	{

		// Outter border
		drawRect(x, y, w, h, border);

		// Inner border
		drawRect(x + thickness, y + thickness, w - thickness * 2, h - thickness * 2, innerBorder);

		// Fill
		fillRect(x + thickness * 2, y + thickness * 2, w - 4 * thickness, h - 4 * thickness, fill);
	}

}
