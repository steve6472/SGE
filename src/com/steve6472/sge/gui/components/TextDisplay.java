package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.panels.Panel1;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class TextDisplay extends Component
{
	
	private static final long serialVersionUID = 4156953330410801607L;
	private List<String> rawText = new ArrayList<String>();
//	private int lineWarp = 32;
	private int textSize = 1;
//	private boolean autoWarp = true;
//	private boolean warp = true;
	private int textOffsetX = 12, textOffsetY = 14;
	private PanelBase back;
	
	public TextDisplay()
	{
	}
	
	public TextDisplay(PanelBase panel)
	{
		back = panel;
	}
	
	@Override
	public void init(BaseGame game)
	{
		if (back == null)
			back = new Panel1(game.getScreen());
	}

	@Override
	public void render(Screen screen)
	{
		if (isVisible())
		{
			back.render(getX(), getY(), getWidth(), getHeight());
			
//			List<String> text = new ArrayList<String>();
			int i = -1;
			for (String s : rawText)
			{
				i++;
//				if (warp)
//				{
//					if (s.length() > lineWarp)
//					{
//						
//					}
//				} else
				{
					getFont().render(s, screen, textOffsetX + x, textOffsetY + y + (i * (textSize * 8)) + i * 2, textSize);
				}
			}
		}
	}

	@Override
	public void tick()
	{
//		if (autoWarp)
//			lineWarp = (getWidth() * (textSize * 8) - 16);
	}
	
	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */
	
	public void addTextLine(String text)
	{
		this.rawText.add(text);
	}
	
	public void setText(List<String> text)
	{
		this.rawText = text;
	}
	
	public void setText(String...strings)
	{
		rawText.removeAll(rawText);
		for (String s : strings)
		{
			rawText.add(s);
		}
	}
	
	public void setTextSize(int textSize)
	{
		this.textSize = textSize;
	}
	
	public void setPanel(PanelBase panel)
	{
		this.back = panel;
	}
	
	public void setXOffset(int offset)
	{
		this.textOffsetX = offset;
	}
	
	public void setYOffset(int offset)
	{
		this.textOffsetY = offset;
	}
	
	/*
	 * Getters
	 */
	
	public String getTextFromLine(int line)
	{
		if (rawText.size() < line)
			return null;
		return rawText.get(line);
	}
	
	public List<String> getText()
	{
		return rawText;
	}
	
	public int getTextSize()
	{
		return textSize;
	}

}
