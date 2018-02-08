package com.steve6472.sge.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.components.Background;
import com.steve6472.sge.gui.components.ToolTip;
import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.KeyHandler;
import com.steve6472.sge.main.MouseHandler;

public abstract class Component extends LambdaControl implements Serializable
{

	private static final long serialVersionUID = 62938822794527605L;
	private boolean isVisible = true;
	protected boolean shouldRepaint = false;
	public ToolTip toolTip;
	protected int x, y, width, height;
	List<Component> components = new ArrayList<Component>();
	protected Gui parentGui;
	protected Component parentComponent;

	private Font font;
	private Screen screen;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private BaseGame game;

//	public Component(BaseGame game)
//	{
//		this.game = game;
//		if (game != null)
//		{
//			this.font = game.font;
//			this.screen = game.getScreen();
//			this.keyHandler = game.getKeyHandler();
//			this.mouseHandler = game.getMouseHandler();
//		} else
//		{
//			this.font = null;
//			this.screen = null;
//			this.keyHandler = null;
//			this.mouseHandler = null;
//		}
//	}
	
	public final void preInit(BaseGame game)
	{
		initLambdaControl(this);
		this.game = game;
		if (game != null)
		{
			this.font = game.font;
			this.screen = game.getScreen();
			this.keyHandler = game.getKeyHandler();
			this.mouseHandler = game.getMouseHandler();
		} else
		{
			this.font = null;
			this.screen = null;
			this.keyHandler = null;
			this.mouseHandler = null;
		}
	}

	/*
	 * Abstract methods
	 */
	
	public abstract void init(BaseGame game);
	
	public abstract void render(Screen screen);
	
	public abstract void tick();

	public boolean isCursorInComponent(int x, int y, int w, int h)
	{
		if (!isVisible())
			return false;
		return (mouseHandler.getMouseX() >= x && mouseHandler.getMouseX() <= w + x) && (mouseHandler.getMouseY() >= y && mouseHandler.getMouseY() <= h + y);
	}

	public boolean isCursorInComponent()
	{
		if (!isVisible())
			return false;
		return (mouseHandler.getMouseX() >= getX() && mouseHandler.getMouseX() <= getWidth() + getX())
				&& (mouseHandler.getMouseY() >= getY() && mouseHandler.getMouseY() <= getHeight() + getY());
	}

	private short idleMouseTime = 0;
	private int mouseLastPosX = 0;
	private int mouseLastPosY = 0;
	private boolean mouseMoved = false;
	
	/*
	 * Run in tick if you want toolTip in your component
	 */
	protected void toolTipTick(MouseHandler m)
	{
		if (!isVisible() || toolTip == null || (toolTip.getText() == null || toolTip.getText().equals("")))
			return;
		
		toolTip.setVisible(isVisible());
		
		{
			boolean hovering = isCursorInComponent(x, y, width, height);

			int currentMousePosX = m.getMouseX();
			int currentMousePosY = m.getMouseY();

			
			//Check if mouse has moved
			if (currentMousePosX != mouseLastPosX || currentMousePosY != mouseLastPosY)
			{
				mouseMoved = true;
			} else
			{
				mouseMoved = false;
			}

			//If mouse moved & mouse is not over component reset timer else add to timer
			if (mouseMoved && !hovering)
			{
				idleMouseTime = 0;
			} else
			{
				idleMouseTime++;
			}

			mouseLastPosX = currentMousePosX;
			mouseLastPosY = currentMousePosY;

			if (mouseMoved && !hovering)
			{
				idleMouseTime = 0;
			} else
			{
				idleMouseTime++;
			}
			
			if (idleMouseTime >= toolTip.getDelay() && hovering)
			{
				toolTip.show();
				toolTip.setPosition(currentMousePosX + 14, currentMousePosY - 8);
			} else
			{
				toolTip.hide();
			}
		}
	}

	
	protected void renderComponents(Screen screen)
	{
		if (isVisible())
		{
			for (Component gc : components)
			{
				if (gc.isVisible() && gc.shouldRepaint)
				{
					gc.render(screen);
					gc.shouldRepaint = false;
				}
			}
		}
	}
	
	public void repaintComponents()
	{
		components.forEach((c) -> c.repaint());
	}

	/*
	 * Operators
	 */
	
	public void hide()
	{
		this.isVisible = false;
		hideAllComponents();
	}
	
	public void show()
	{
		this.isVisible = true;
		showAllComponents();
	}
	
	public void toggleVisibility()
	{
		this.isVisible = !isVisible;
		repaintComponents();
	}
	
	protected void addComponent(Component component)
	{
		if (component == null)
			throw new NullPointerException("Component can't be null");
		component.parentComponent = this;
		component.preInit(game);
		component.init(game);
		components.add(component);
		repaintComponents();
	}
	
	protected void removeComponent(Component component)
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
	
	protected void hideAllComponents()
	{
		for (Component gc : components)
		{
			gc.hide();
		}
		repaintComponents();
	}
	
	protected void showAllComponents()
	{
		for (Component gc : components)
		{
			gc.show();
		}
		repaintComponents();
	}

	protected void tickComponents()
	{
		if (isVisible())
		{
			for (Component gc : components)
			{
				if (gc.isVisible())
				{
					gc.tick();
				}
			}
		}
	}

	public void repaint()
	{
		this.shouldRepaint = true;
		repaintComponents();
	}

	public static void repaintBackground()
	{
		Background.repaint = true;
	}

	/*
	 * Setters
	 */

	public void setVisible(boolean b)
	{
		this.isVisible = b;
		
		components.forEach((c) -> c.setVisible(b));
		repaint();
		repaintBackground();
	}
	
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		for (Component gc : components)
		{
			gc.setLocation(x + gc.getX(), y + gc.getY());
		}
		repaint();
		repaintBackground();
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		if (width < getMinWidth())
			width = getMinWidth();

		if (height < getMinHeight())
			height = getMinHeight();
		
		repaint();
		repaintBackground();
	}
	
	public void setToolTipText(String text)
	{
		if(toolTip == null)
			toolTip = new ToolTip(game);
		toolTip.setText(text);
	}

	/*
	 * Getters
	 */

	public boolean isVisible()
	{
		return isVisible;
	}

	public int getHeight()
	{
		if (height < getMinHeight())
			height = getMinHeight();
		
		return height;
	}

	public int getWidth()
	{
		if (width < getMinWidth())
			width = getMinWidth();
		
		return width;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	protected int getMinWidth()
	{
		return 0;
	}
	
	protected int getMinHeight()
	{
		return 0;
	}
	
	public String getTooltipText()
	{
		if (toolTip != null)
			return toolTip.getText();
		else
			return null;
	}
	
	public KeyHandler getKeyHandler()
	{
		return keyHandler;
	}
	
	public MouseHandler getMouseHandler()
	{
		return mouseHandler;
	}
	
	public Gui getParentGui()
	{
		return parentGui;
	}
	
	public Screen getScreen()
	{
		return screen;
	}
	
	public Font getFont()
	{
		return font;
	}
	
	public BaseGame getGame()
	{
		return game;
	}
}
