package com.steve6472.sge.gui.components.panels.progressBar;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class ProgressBarBack extends PanelBase
{
	
	public ProgressBarBack(Screen screen)
	{
		super(screen);
	}
	
	public ProgressBarBack(BaseGame game)
	{
		super(game.getScreen());
	}

	public int bs = 2;

	@Override
	public void render(int x, int y, int w, int h)
	{
		//Border: 0xff333333
		//Inner Border:0xff7f7f7f
		//Fill: 0xfff2f2f2
		
		int b = 0xff000000;
		int ib = 0xffa8a8a8;
		int f = 0xff6f6f6f;
		
		/*
		 * Outter border
		 */
		
		//LU - RU
		fillRect(x, y, w, bs, b);
		//RU-RD
		fillRect(x + w - bs, y + bs, bs, h - 2 * bs, b);
		//LD-RD
		fillRect(x, y + h - bs, w, bs, b);
//		LU-DL
		fillRect(x, y + bs, bs, h - 2 * bs, b);
		
		/*
		 * Inner border
		 */
		
		//LU - RU
		fillRect(x + bs, y + bs, w - bs * 2, bs, ib);
		//RU-RD
		fillRect(x + w - bs * 2, y + bs * 2, bs, h - 4 * bs, ib);
		//LD-RD
		fillRect(x + bs, y + h - bs * 2, w - bs * 2, bs, ib);
//		LU-DL
		fillRect(x + bs, y + bs * 2, bs, h - 4 * bs, ib);
		
		//Fill
		fillRect(x + bs * 2, y + bs * 2, w - 4 * bs, h - 4 * bs, f);
	}

}
