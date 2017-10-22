package com.steve6472.sge.gui.components.panels.button;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class ButtonPanelDisabled extends PanelBase
{
	public ButtonPanelDisabled(Screen screen)
	{
		super(screen);
	}

	public ButtonPanelDisabled(BaseGame game)
	{
		super(game);
	}

	public int borderSize = 2;

	@Override
	public void render(int x, int y, int w, int h)
	{
		//Border: 0xff3f3f3f
		//Fill: 0xffbfbfbf
		
		int b = 0xff000000;
		int f = 0xff2b2b2b;
		
		//LU - RU
		fillRect(x, y, w, borderSize, b);
		//RU-DR
		fillRect(x + w - borderSize, y + borderSize, borderSize, h - 2 * borderSize, b);
		//LD-DR
		fillRect(x, y + h - borderSize, w, borderSize, b);
//		LU-DL
		fillRect(x, y + borderSize, borderSize, h - 2 * borderSize, b);
		
		//Fill
		fillRect(x + borderSize, y + borderSize, w - 2 * borderSize, h - 2 * borderSize, f);
	}
	
	@Override
	public String getName()
	{
		return "ButtonPanelDisabled";
	}

}
