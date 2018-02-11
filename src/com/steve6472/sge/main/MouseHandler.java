package com.steve6472.sge.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import com.steve6472.sge.main.game.Vec2;

public class MouseHandler implements Serializable
{

	private static final long serialVersionUID = 3621659503484598136L;
	@Deprecated
	public int mouseX = 0;
	@Deprecated
	public int mouseY = 0;
	@Deprecated
	public int mouseXOnScreen = 0;
	@Deprecated
	public int mouseYOnScreen = 0;
	@Deprecated
	public int pressedMouseXOnScreen = 0;
	@Deprecated
	public int pressedMouseYOnScreen = 0;
	@Deprecated
	public int pressedMouseX = 0;
	@Deprecated
	public int pressedMouseY = 0;
	@Deprecated
	/**
	 * Setted to false when released
	 */
	public boolean mouseHold = false;
	@Deprecated
	public boolean mouseTriggered = false;
	@Deprecated
	public int button = 0;
	SGEMain main;

	public MouseHandler(SGEMain game)
	{
		this.main = game;
		game.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				mouseHold = true;
				mouseX = e.getX();
				mouseY = e.getY();
				pressedMouseX = e.getX();
				pressedMouseY = e.getY();
				pressedMouseXOnScreen = e.getXOnScreen();
				pressedMouseYOnScreen = e.getYOnScreen();
				mouseXOnScreen = e.getXOnScreen();
				mouseYOnScreen = e.getYOnScreen();
				button = e.getButton();
			}
			
			@Override
			public void mouseReleased(MouseEvent e)
			{
				mouseHold = false;
				mouseTriggered = false;
				mouseX = e.getX();
				mouseY = e.getY();
				button = 0;
			}
		});
		game.addMouseMotionListener(new MouseAdapter()
		{
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
				mouseX = e.getX();
				mouseY = e.getY();
				mouseXOnScreen = e.getXOnScreen();
				mouseYOnScreen = e.getYOnScreen();
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				mouseHold = true;
				mouseX = e.getX();
				mouseY = e.getY();
				mouseXOnScreen = e.getXOnScreen();
				mouseYOnScreen = e.getYOnScreen();
			}
		});
	}
	
	
	public int getMouseX()
	{
		return mouseX;
	}
	
	public int getMouseY()
	{
		return mouseY;
	}
	
	public int getButton()
	{
		return button;
	}
	
	public int getMouseXOnScreen()
	{
		return mouseXOnScreen;
	}
	
	public int getMouseYOnScreen()
	{
		return mouseYOnScreen;
	}
	
	public int getPressedMouseX()
	{
		return pressedMouseX;
	}
	
	public int getPressedMouseY()
	{
		return pressedMouseY;
	}
	
	public SGEMain getMain()
	{
		return main;
	}
	
	public int getPressedMouseXOnScreen()
	{
		return pressedMouseXOnScreen;
	}
	
	public int getPressedMouseYOnScreen()
	{
		return pressedMouseYOnScreen;
	}
	
	public boolean isMouseHolded()
	{
		return mouseHold;
	}
	
	public boolean isMouseTriggered()
	{
		return mouseTriggered;
	}
	
	public void trigger()
	{
		mouseTriggered = false;
	}
	
	public void toggleTrigger()
	{
		mouseTriggered = !mouseTriggered;
	}
	
	public void setTrigger(boolean triggered)
	{
		mouseTriggered = triggered;
	}
	
	public boolean mousePressed(int mouseButton)
	{
		if (isMouseHolded() && !isMouseTriggered() && getButton() == mouseButton)
		{
			mouseTriggered = true;
			
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return true when user presses any mouse button. (Calls only once)
	 */
	public boolean mousePressed()
	{
		if (isMouseHolded() && !isMouseTriggered())
		{
			mouseTriggered = true;
			
			return true;
		}
		return false;
	}
	
	public Vec2 toVec()
	{
		return new Vec2(mouseX, mouseY);
	}
	
	public BaseGame getGame()
	{
		return main.game;
	}
}
