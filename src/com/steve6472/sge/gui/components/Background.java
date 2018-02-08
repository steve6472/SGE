/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 12. 9. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.main.BaseGame;

public class Background extends Panel
{	
	private static final long serialVersionUID = 4061909484367584836L;
	public static boolean repaint = true;
	
	public Background(int id)
	{
		setPanel(id);
	}
	
	public Background()
	{
		
	}
	
	@Override
	public void init(BaseGame game)
	{
		setSize(game.getWidth(), game.getHeight());
		
		if (getPanel() == null)
			setPanel(1);
	}
	
	@Override
	public void tick()
	{
		repaint();
	}
	
	@Override
	public void render(Screen screen)
	{
		if (repaint)
		{
			repaint = false;
			super.render(screen);
			if (parentGui != null)
				parentGui.repaintComponents();
		}
	}

}
