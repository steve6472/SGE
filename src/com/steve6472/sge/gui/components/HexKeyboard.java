package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.Panel;
import com.steve6472.sge.gui.components.events.ButtonEvents;
import com.steve6472.sge.gui.components.panels.Panel1;
import com.steve6472.sge.main.BaseGame;

public class HexKeyboard extends Component
{
	String s[] = {"1", "4", "7", "2", "5", "8", "3", "6", "9", "a", "c", "e", "b", "d", "f"};
	Button zero, clear;
	Panel back;
	TextField hex;
	String text = "ffffff";
	
	@Override
	public void init(BaseGame game)
	{
		super.setSize(240, 186);
		
		back = new Panel(new Panel1(game.getScreen()));
		back.setSize(250, 164);
		back.setLocation(656, 41);
		addComponent(back);
		
		int x = 665;
		int y = 50;
		int w = 40;
		int h = 25;
		int sp = 8;
		int p = 0;
		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				Button b = new Button(s[p]);
				b.setSize(w, h);
				b.setLocation(x + i * w + i * sp, y + j * 30);
				b.addEvent(new ButtonEvents()
				{
					@Override
					public void click()
					{
						if (text.length() < 6)
							text += b.getText();
						HexKeyboard.this.repaint();
					}
				});
				addComponent(b);
				p++;
			}
		}
		
		zero = new Button("0");
		zero.setLocation(665, 140);
		zero.setSize(136, 25);
		zero.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				if (text.length() < 6)
					text += zero.getText();
				HexKeyboard.this.repaint();
			}
		});
		addComponent(zero);
		
		clear = new Button("<-");
		clear.setLocation(665 + 136 + 8, 140);
		clear.setSize(88, 25);
		clear.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				if (text.length() == 0)
					text = "";
				else
					text = text.substring(0, text.length() - 1);
				HexKeyboard.this.repaint();
			}
		});
		addComponent(clear);
		
		hex = new TextField();
		hex.setLocation(665, 140 + 30);
		hex.setSize(136 + 88 + 8, 25);
		hex.setFontSize(2);
		hex.setEnabled(false);
//		hex.setYOffset(8);
//		hex.setPanel(new Panel9(game.getScreen()));
		addComponent(hex);
	}

	@Override
	public void render(Screen screen)
	{
		renderComponents(screen);
	}
	
	public String getHexString()
	{
		return text;
	}
	
	public int getHex()
	{
		return Integer.decode("0x" + text);
	}
	
	public int getRed()
	{
		return Screen.getRed(getHex());
	}
	
	public int getGreen()
	{
		return Screen.getGreen(getHex());
	}
	
	public int getBlue()
	{
		return Screen.getBlue(getHex());
	}

	@Override
	public void tick()
	{
		hex.setText(text);
//		hex.setXOffset(8 + hex.getWidth() / 2 - (hex.getText().length() * (hex.getText().length() * 8)) / 2);
		tickComponents();
	}
	
	@Override
	@Deprecated
	/**
	 * You can't change size of this component
	 */
	public void setSize(int width, int height)
	{
		
	}
	
	@Override
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		int p = 1;
		int sp = 8;
		int w = 40;

		for (int i = 0; i < 5; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				getComponent(p).setLocation(x + i * w + i * sp + 8, y + j * 30 + 8);
				p++;
			}
		}
		
		getComponent(0).setLocation(x, y);
		getComponent(5 * 3 + 1).setLocation(x + 8, y + 100);
		getComponent(5 * 3 + 2).setLocation(x + 8 + 144, y + 100);
		getComponent(5 * 3 + 3).setLocation(x + 8, y + 130);
		
		repaint();
		repaintBackground();
	}
	
	@Override
	protected int getMinWidth()
	{
		return 240;
	}
	
	@Override
	protected int getMinHeight()
	{
		return 186;
	}
}
