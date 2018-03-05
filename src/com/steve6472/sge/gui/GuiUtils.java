package com.steve6472.sge.gui;

import javax.swing.JFrame;

import com.steve6472.sge.gui.components.Background;
import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.DragFrame;
import com.steve6472.sge.gui.components.Panel;
import com.steve6472.sge.gui.components.events.ButtonEvents;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.MouseHandler;
import com.steve6472.sge.main.game.Vec2;

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
		minimalize.setLocation(gui.getGame().getWidth() - 82, 0);
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
	
	public static void createBasicLayout(Gui gui)
	{
		gui.addComponent(new Background(1));
		
		DragFrame drag = new DragFrame();
		drag.setLocation(2, 2);
		drag.setSize(gui.game.getWidth() - 84, 25);
		drag.setText(gui.game.getTitle());
		
		gui.addComponent(drag);
		
		Button exit = GuiUtils.createExitButton(gui);
		exit.setLocation(gui.game.getWidth() - 40 - 2, 2);
		gui.addComponent(exit);
		
		Button minimalise = GuiUtils.createMinimalizeButton(gui);
		minimalise.setLocation(gui.game.getWidth() - 82, 2);
		gui.addComponent(minimalise);
	}
	
	public static Vec2 centerComponent(Gui gui, Component comp)
	{
		BaseGame game = gui.getGame();
		int compWidth = comp.getWidth();
		int compHeight = comp.getHeight();
		return new Vec2((game.getWidth() - compWidth) / 2, (game.getHeight() - compHeight) / 2);
	}

	public static boolean isCursorInRectangle(MouseHandler m, int x, int y, int w, int h)
	{
		return ( m.getMouseX() >= x && m.getMouseX() <= w + x)   // check if X is within range
				   && ( m.getMouseY() >= y && m.getMouseY()<= h + y);
	}
}
