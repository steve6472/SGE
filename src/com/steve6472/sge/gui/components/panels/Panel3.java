package com.steve6472.sge.gui.components.panels;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.BaseGame;

public class Panel3 extends PanelBase
{
	
	public Panel3(Screen screen)
	{
		super(screen);
	}
	
	public Panel3(BaseGame game)
	{
		super(game);
	}

	public int bs = 2;

	@Override
	public void render(int x, int y, int w, int h)
	{

		int b = 0xff636363;
		int ib = 0xff898989;
		int f = 0xffaeaeae;
		
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
