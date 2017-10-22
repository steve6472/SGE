package com.steve6472.sge.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gui.components.IFocusable;

public class KeyHandler
{
	
	private List<KeyListener> listeners = new ArrayList<KeyListener>();
	private int keyCode = 0;

	public KeyHandler(SGEMain game)
	{
		game.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				any.toggleKey(true);
				any.key_code = e.getKeyCode();
				toggleKey(e.getKeyCode(), true);
				keyCode = e.getKeyCode();
				for (KeyListener kl : listeners)
				{
//					System.out.println(e.getKeyCode());
					if (kl.ifocusable.isFocused())
						kl.keyPressed(e.getKeyChar(), e.getKeyCode());
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				any.toggleKey(false);
				any.key_code = e.getKeyCode();
				toggleKey(e.getKeyCode(), false);
				keyCode = e.getKeyCode();
				for (KeyListener kl : listeners)
				{
					if (kl.ifocusable.isFocused())
						kl.keyReleased(e.getKeyChar(), e.getKeyCode());
				}
			}

			@Override
			public void keyTyped(KeyEvent e)
			{
				for (KeyListener kl : listeners)
				{
					if (kl.ifocusable.isFocused())
						kl.keyTyped(e.getKeyChar(), keyCode);
				}
			}
		});
	}

	public void addListenerEvent(KeyListener event)
	{
		this.listeners.add(event);
	}
	
	public List<KeyListener> getListeners()
	{
		return listeners;
	}

	public static List<Key> keys = new ArrayList<Key>();

	//Arrows
	public Key up = new Key(KeyEvent.VK_UP);
	public Key down = new Key(KeyEvent.VK_DOWN);
	public Key left = new Key(KeyEvent.VK_LEFT);
	public Key right = new Key(KeyEvent.VK_RIGHT);
	
	//Latters
	public Key a = new Key(KeyEvent.VK_A); //Hex
	public Key b = new Key(KeyEvent.VK_B); //Hex
	public Key c = new Key(KeyEvent.VK_C); //Hex
	public Key d = new Key(KeyEvent.VK_D); //Hex
	public Key e = new Key(KeyEvent.VK_E); //Hex
	public Key f = new Key(KeyEvent.VK_F); //Hex
	public Key g = new Key(KeyEvent.VK_G);
	public Key h = new Key(KeyEvent.VK_H);
	public Key i = new Key(KeyEvent.VK_I);
	public Key j = new Key(KeyEvent.VK_J);
	public Key k = new Key(KeyEvent.VK_K);
	public Key l = new Key(KeyEvent.VK_L);
	public Key m = new Key(KeyEvent.VK_M);
	public Key n = new Key(KeyEvent.VK_N);
	public Key o = new Key(KeyEvent.VK_O);
	public Key p = new Key(KeyEvent.VK_P);
	public Key q = new Key(KeyEvent.VK_Q);
	public Key r = new Key(KeyEvent.VK_R);
	public Key s = new Key(KeyEvent.VK_S);
	public Key t = new Key(KeyEvent.VK_T);
	public Key u = new Key(KeyEvent.VK_U);
	public Key v = new Key(KeyEvent.VK_V);
	public Key w = new Key(KeyEvent.VK_W);
	public Key x = new Key(KeyEvent.VK_X);
	public Key y = new Key(KeyEvent.VK_Y);
	public Key z = new Key(KeyEvent.VK_Z);
	
	//Numbers
	public Key zero = new Key(KeyEvent.VK_NUMPAD0);
	public Key one = new Key(KeyEvent.VK_NUMPAD1);
	public Key two = new Key(KeyEvent.VK_NUMPAD2);
	public Key three = new Key(KeyEvent.VK_NUMPAD3);
	public Key four = new Key(KeyEvent.VK_NUMPAD4);
	public Key five = new Key(KeyEvent.VK_NUMPAD5);
	public Key six = new Key(KeyEvent.VK_NUMPAD6);
	public Key seven = new Key(KeyEvent.VK_NUMPAD7);
	public Key eight = new Key(KeyEvent.VK_NUMPAD8);
	public Key nine = new Key(KeyEvent.VK_NUMPAD9);
	
	//The numbers
	public Key one_ = new Key(KeyEvent.VK_1);
	public Key two_ = new Key(KeyEvent.VK_2);
	public Key three_ = new Key(KeyEvent.VK_3);
	public Key four_ = new Key(KeyEvent.VK_4);
	public Key five_ = new Key(KeyEvent.VK_5);
	public Key six_ = new Key(KeyEvent.VK_6);
	public Key seven_ = new Key(KeyEvent.VK_7);
	public Key eight_ = new Key(KeyEvent.VK_8);
	public Key nine_ = new Key(KeyEvent.VK_9);
	public Key zero_ = new Key(KeyEvent.VK_0);
	
	//misc
	public Key decimal = new Key(KeyEvent.VK_DECIMAL);
	public Key any = new Key();
	public Key shift = new Key(KeyEvent.VK_SHIFT);
	public Key tab = new Key(KeyEvent.VK_TAB);
	public Key alt = new Key(KeyEvent.VK_ALT);
	public Key control = new Key(KeyEvent.VK_CONTROL);
	public Key esc = new Key(KeyEvent.VK_ESCAPE);
	public Key space = new Key(KeyEvent.VK_SPACE);
	public Key plus = new Key(KeyEvent.VK_PLUS);
	public Key backspace = new Key(KeyEvent.VK_BACK_SPACE);

	public void toggleKey(int key_code, boolean is_pressed)
	{
		for (Key key : keys)
		{
			if (key.getKeyCode() == key_code)
			{
				key.toggleKey(is_pressed);
			}
		}
	}

	public class Key
	{
		
		public Key(int key_code)
		{
			KeyHandler.keys.add(this);
			this.key_code = key_code;
		}
		
		public Key()
		{
			
		}
		
		private int num_times_pressed = 0;
		private boolean pressed = false;
		public boolean typed = false;
		private int key_code = 0;
		
		public int getKeyCode()
		{
			return key_code;
		}

		public boolean isPressed()
		{
			return pressed;
		}

		public int getNumTimes()
		{
			return num_times_pressed;
		}

		public void setNumTimes(int times)
		{
			num_times_pressed = times;
		}

		public void toggleKey(boolean is_pressed)
		{
			pressed = is_pressed;
			if (pressed)
			{
				num_times_pressed++;
			} else
			{
				typed = false;
			}
		}
	}

	public abstract class KeyListener
	{
		public final IFocusable ifocusable;
		
		public KeyListener(IFocusable ifocusable)
		{
			this.ifocusable = ifocusable;
		}

		public void keyPressed(char c, int keyCode)
		{
			
		}

		public void keyReleased(char c, int keyCode)
		{

		}

		public void keyTyped(char c, int keyCode)
		{

		}
	}
}
