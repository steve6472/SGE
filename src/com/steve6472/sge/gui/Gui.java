package com.steve6472.sge.gui;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.components.IFocusable;
import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.KeyHandler.KeyListener;
import com.steve6472.sge.main.MouseHandler;

public abstract class Gui
{
	private List<Component> components = new ArrayList<Component>();
	private boolean isVisible = true;
	protected final BaseGame game;
	protected final Font font;
	/**
	 * If true it will render components last
	 */
	private boolean switchedRender = false;

	public Gui(BaseGame game)
	{
		this.game = game;
		game.guis.add(this);
		font = game.font;
		hideGui();
//		game.registredGuis.add(this.getClass());
		createGui();
	}
	
	public abstract void createGui();

	public abstract void guiTick();

	public abstract void render(Screen screen);

	protected void renderComponents(Screen screen)
	{
		try
		{
			if (isVisible())
			{
				for (Component gc : components)
				{
					gc.components.forEach((c) ->
					{
						if (c.shouldRepaint && c.isVisible())
						{
							c.render(screen);
							c.shouldRepaint = false;
						}
							
					});
					if (gc.isVisible() && gc.shouldRepaint)
					{
						gc.render(screen);
						gc.shouldRepaint = false;
					}
				}
			}
		} catch (ConcurrentModificationException ex)
		{
			
		}
	}

	public final boolean isCursorInComponent(MouseHandler m, int x, int y, int w, int h)
	{
		if (!isVisible())
			return false;
		return ( m.mouse_x >= x && m.mouse_x <= w + x)   // check if X is within range
				   && ( m.mouse_y >= y && m.mouse_y <= h + y);
	}

	public final void setVisible(boolean b)
	{
		this.isVisible = b;
	}

	/**
	 * Opens & recreates the GUI (Best for debugging)
	 * Cleares components list!
	 * 
	 * @param game
	 * @param gui
	 *            Class of the GUI (I suppose that you don't have 2 same guis
	 *            but if so it will open the last registrated);
	 */
	public static final void openGui(BaseGame game, Class<? extends Gui> gui)
	{
		int index = 0;
		int i = 0;
		for (Gui g : game.guis)
		{
			if (g.getClass().getName().equals(gui.getName()))
			{
				index = i;
			}
			i++;
		}
		// Removing KeyListener from any IFocusables (Without this code the textfield
		// would have more & more keyListeners resulting in typing multiple keys
		// at once)
		for (Component c : game.guis.get(index).components)
		{
			if (c instanceof IFocusable)
			{
				for (Iterator<KeyListener> li = game.getKeyHandler().getListeners().iterator(); li.hasNext();)
				{
					KeyListener l = li.next();
					if (l.ifocusable == c)
					{
						li.remove();
					}
				}
			}
		}
		game.guis.get(index).components.clear();
		game.guis.get(index).createGui();
		game.guis.get(index).showGui();
	}
	
	/**
	 * Calls hideAllComponents();
	 */
	public void hideGui()
	{
		isVisible = false;
		for (Component gc : components)
		{
			gc.hide();
		}
	}
	
	public void showGui()
	{
		isVisible = true;
		for (Component gc : components)
		{
			gc.show();
			gc.repaint();
		}
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}

	public void renderGui(Screen screen)
	{
		if (isVisible())
		{
			if (switchedRender)
			{
				render(screen);
				renderComponents(screen);
			} else
			{
				renderComponents(screen);
				render(screen);
			}
		}
	}

	public void tick()
	{
		if (isVisible())
		{
			tickComponents();
			guiTick();
		}
	}

	public void tickComponents()
	{
		if (isVisible())
		{
			for (Iterator<Component> co = components.iterator(); co.hasNext();)
			{
				Component c = co.next();
				if (c.isVisible())
				{
					c.tick();
				}
			}
		}
	}

	public void addComponent(Component component)
	{
		if (component == null)
			throw new NullPointerException("Component can't be null");
		component.parentGui = this;
		component.preInit(game);
		component.init(game);
		components.add(component);
		repaintComponents();
	}
	
	public void repaintComponents()
	{
		components.forEach((c) -> c.repaint());
	}

	public void removeComponent(Component component)
	{
		components.remove(component);
		repaintComponents();
	}
	
	public void removeComponent(int index)
	{
		components.remove(index);
		repaintComponents();
	}
	
	public Component getComponent(int index)
	{
		return components.get(index);
	}
	
	public BaseGame getGame()
	{
		return game;
	}
	
	/**
	 * Makes components render over screen
	 */
	public void switchRender()
	{
		this.switchedRender = true;
		components.forEach((c) -> c.repaint());
	}
	
	/**
	 * Renders 1 pixel at mouse location for render testing
	 */
	public void drawTestPixel(Screen screen)
	{
		screen.render(game.getMouseHandler().mouse_x, game.getMouseHandler().mouse_y, 0xff259154);
	}
}
