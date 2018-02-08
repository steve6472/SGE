package com.steve6472.sge.gui.components.panels;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.BaseGame;

public class Panel1 extends PanelBase
{
	public Panel1(Screen screen)
	{
		super(screen);
	}
	
	public Panel1(BaseGame game)
	{
		super(game);
	}

	public int borderSize = 2;
	public static int fill = 0xffbfbfbf;

	@Override
	public void render(int x, int y, int w, int h)
	{
		//Border: 0xff3f3f3f
		//Fill: 0xffbfbfbf
		
		//LU - RU
		fillRect(x, y, w, borderSize, 0xff3f3f3f);
		//RU-DR
		fillRect(x + w - borderSize, y + borderSize, borderSize, h - 2 * borderSize, 0xff3f3f3f);
		//LD-DR
		fillRect(x, y + h - borderSize, w, borderSize, 0xff3f3f3f);
//		LU-DL
		fillRect(x, y + borderSize, borderSize, h - 2 * borderSize, 0xff3f3f3f);
		
		//Fill
		fillRect(x + borderSize, y + borderSize, w - 2 * borderSize, h - 2 * borderSize, fill);
	}

}
