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
	public boolean mouse_hold = false;
	public boolean mouse_triggered = false;
	@Deprecated
	public int button = 0;
	private boolean triggeredPress = false;
	SGEMain main;

	public MouseHandler(SGEMain game)
	{
		this.main = game;
		game.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				mouse_hold = true;
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
				mouse_hold = false;
				mouse_triggered = false;
				triggeredPress = false;
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
				mouse_hold = true;
				mouseX = e.getX();
				mouseY = e.getY();
				mouseXOnScreen = e.getXOnScreen();
				mouseYOnScreen = e.getYOnScreen();
			}
		});
	}
	
	/**
	 * 
	 * @return true when user presses any mouse button. (Calls only once)
	 */
	public boolean mouseClicked()
	{
		if (!triggeredPress)
		{
			triggeredPress = true;
			return true;
		}
		return false;
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
	
	public Vec2 toVec()
	{
		return new Vec2(mouseX, mouseY);
	}
	
	public BaseGame getGame()
	{
		return main.game;
	}
}
