package com.steve6472.sge.gui;

import javax.swing.JFrame;

import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.Panel;
import com.steve6472.sge.gui.components.events.ButtonEvents;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.MouseHandler;

public class GuiUtils
{
	public static void createPanelBackground(Gui gui, PanelBase panel)
	{
		Panel p = new Panel(panel);
		p.setLocation(0, 0);
		p.setSize(gui.getGame().getWidth(), gui.getGame().getHeight());
		gui.addComponent(p);
	}
	
	/**
	 * 
	 * @param gui
	 * @return Exit & Minimalize buttons
	 */
	public static Button[] createOperationButtons(Gui gui)
	{
		return new Button[]
		{ createExitButton(gui), createMinimalizeButton(gui) };
	}
	
	public static Button createExitButton(Gui gui)
	{
		Button exit = new Button("X");
		exit.setLocation(gui.getGame().getWidth() - 40, 0);
		exit.setSize(40, 25);
		exit.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				gui.getGame().exit();
			}
		});
		gui.addComponent(exit);
		return exit;
	}
	
	public static Button createMinimalizeButton(Gui gui)
	{
		Button minimalize = new Button("_");
		minimalize.setLocation(gui.getGame().getWidth() - 80, 0);
		minimalize.setSize(40, 25);
		minimalize.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				gui.getGame().getMain().getJFrame().setExtendedState(JFrame.ICONIFIED);
			}
		});
		gui.addComponent(minimalize);
		return minimalize;
	}

	public static boolean isCursorInComponent(MouseHandler m, int x, int y, int w, int h)
	{
		return ( m.mouse_x >= x && m.mouse_x <= w + x)   // check if X is within range
				   && ( m.mouse_y >= y && m.mouse_y <= h + y);
	}
}
