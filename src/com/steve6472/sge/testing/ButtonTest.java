/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 12. 9. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.testing;

import java.awt.Image;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.gui.components.Background;
import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.DragFrame;
import com.steve6472.sge.gui.components.events.ButtonEvents;
import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.SGEMain;

public class ButtonTest extends BaseGame
{
	
	public static void main(String[] args)
	{
		new ButtonTest();
	}

	@Override
	public void init(SGEMain main)
	{
		new Gui(this)
		{
			private static final long serialVersionUID = 5819368211027584975L;
			DragFrame drag;
			
			@Override
			public void createGui()
			{
				showGui();
				Background back = new Background(1);
				addComponent(back);

				for (int i = 0; i < 13; i++)
				{
					for (int j = 0; j < 14; j++)
					{
						Button exit = new Button("EXIT");
						exit.setLocation(i * 100 + 8, j * 48 + 32);
						exit.setSize(100, 48);
						exit.setFontScale(2);
						exit.addEvent(new ButtonEvents()
						{
							@Override
							public void click()
							{
								game.exit();
							}
						});
						addComponent(exit);
					}
				}

				drag = new DragFrame();
				drag.setLocation(2, 2);
				drag.setSize(game.getWidth() - 4, 25);
				drag.setText("" + game.getFPS());
				addComponent(drag);
			}
			
			@Override
			public void render(Screen screen)
			{
				
			}
			
			int oldFrames = 0;
			
			@Override
			public void guiTick()
			{
				if (oldFrames != game.getFPS())
				{
					oldFrames = game.getFPS();
					drag.setText("" + game.getFPS());
				}
			}
		};
	}

	@Override
	public void tick()
	{
		tickGui();
	}

	@Override
	public void render(SGEMain main)
	{
		renderGui();
	}

	@Override
	public void exit()
	{
		System.exit(0);
	}

	@Override
	public int getWidth()
	{
		return 16 * 83;
	}

	@Override
	public int getHeight()
	{
		return 9 * 80;
	}

	@Override
	public boolean fontShadow()
	{
		return true;
	}

	@Override
	public boolean smallFont()
	{
		return true;
	}

	@Override
	public boolean isApplet()
	{
		return false;
	}

	@Override
	public String getTitle()
	{
		return null;
	}

	@Override
	public Image setIconImage()
	{
		return null;
	}

}
