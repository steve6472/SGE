package com.steve6472.sge.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.steve6472.sge.main.game.Vec2;

public class MouseHandler
{

	public int mouse_x = 0;
	public int mouse_y = 0;
	public int mouseXOnScreen = 0;
	public int mouseYOnScreen = 0;
	public int pressedMouseXOnScreen = 0;
	public int pressedMouseYOnScreen = 0;
	public int pressed_mouse_x = 0;
	public int pressed_mouse_y = 0;
	public boolean mouse_hold = false;
	public boolean mouse_triggered = false;
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
				mouse_hold = true;
				mouse_x = e.getX();
				mouse_y = e.getY();
				pressed_mouse_x = e.getX();
				pressed_mouse_y = e.getY();
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
				mouse_x = e.getX();
				mouse_y = e.getY();
				button = 0;
			}
		});
		game.addMouseMotionListener(new MouseAdapter()
		{
			
			@Override
			public void mouseMoved(MouseEvent e)
			{
				mouse_x = e.getX();
				mouse_y = e.getY();
				mouseXOnScreen = e.getXOnScreen();
				mouseYOnScreen = e.getYOnScreen();
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
				mouse_hold = true;
				mouse_x = e.getX();
				mouse_y = e.getY();
				mouseXOnScreen = e.getXOnScreen();
				mouseYOnScreen = e.getYOnScreen();
				button = e.getButton();
			}
		});
	}
	
	public Vec2 toVec()
	{
		return new Vec2(mouse_x, mouse_y);
	}
	
	public BaseGame getGame()
	{
		return main.game;
	}
}
