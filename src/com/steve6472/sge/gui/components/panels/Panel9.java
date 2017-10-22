package com.steve6472.sge.gui.components.panels;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.BaseGame;

public class Panel9 extends PanelBase
{
	
	public Panel9(Screen screen)
	{
		super(screen);
	}
	
	public Panel9(BaseGame game)
	{
		super(game);
	}

	public int borderSize = 2;

	@Override
	public void render(int x, int y, int w, int h)
	{
		
		int b = 0xff7f7f7f;
		int f = 0xff000000;
		
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

}
