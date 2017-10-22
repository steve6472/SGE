package com.steve6472.sge.gui.components;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.panels.Panel9;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class TextField extends Component implements IFocusable
{
	boolean isFocused = false;
	private String text = "";
	int carretPosition = 0;
	private int fontSize = 1;
	private double carretTick = 0;
	private int maxCharacters = Short.MAX_VALUE;
	private boolean showCarret = false;
	private boolean isEnabled = true;
	private boolean centeredRender = true;
	private String editedText = "";
	PanelBase back;
	List<EnterEvent> enterEvents = new ArrayList<EnterEvent>();
	
	@Override
	public void init(BaseGame game)
	{
		getKeyHandler().addListenerEvent(getKeyHandler().new KeyListener(this)
		{
			@Override
			public void keyTyped(char c, int keyCode)
			{
				if (isEnabled())
				{
					switch (keyCode)
					{
					case KeyEvent.VK_ENTER:
						enterEvents.forEach((ee) -> ee.enter());
						break;
					case KeyEvent.VK_BACK_SPACE: // backspace I guess
						text = text.substring(0, text.length() > 0 ? text.length() - 1 : text.length());
						break;
					default:
						if (text.length() < maxCharacters)
							text += Character.toString(c);
						break;
					}
					updateText();
				}
			}
		});
		back = new Panel9(game.getScreen());
	}

	@Override
	public void render(Screen screen)
	{
		Vec2 loc = Font.stringCenter(new AABB(new Vec2(getX(), getY()), getWidth(), getHeight()), text, fontSize);

		back.render(getX(), getY(), getWidth(), getHeight());
		
		int x = 0;

		if (centeredRender)
		{
			getFont().render(text, screen, (int) loc.getX(), (int) loc.getY(), fontSize);
			x = (int) loc.getX() + text.length() * (8 * fontSize);
		} else
		{
			getFont().render(editedText, screen, getX() + 9, (int) loc.getY(), fontSize);
			x = getX() + 5 + editedText.length() * (8 * fontSize);
		}

		if (isFocused && showCarret && text.length() < maxCharacters && isEnabled())
		{
			if (centeredRender)
			{
				getFont().render("|", screen, (int) loc.getX() + text.length() * (8 * fontSize) - 2, (int) loc.getY(), fontSize);
			} else
			{
				getFont().render("|", screen, /*getX() + text.length() * (8 * fontSize) - 2 + 9*/ x, (int) loc.getY(), fontSize);
			}
		}
	}

	public void setText(String text)
	{
		this.text = text;
		updateText();
	}

	public String getText()
	{
		return text;
	}

	public void setFontSize(int fontSize) { this.fontSize = fontSize; }
	
	public int getFontSize() { return fontSize; }
	
	
	public void setMaxCharacters(int maxCharacters) { this.maxCharacters = maxCharacters; }
	
	public int getMaxCharacters() { return maxCharacters; }
	

	public boolean isEnabled() { return isEnabled; }

	public void setEnabled(boolean isEnabled) { this.isEnabled = isEnabled; }
	

	public boolean isCenteredRender() 	{ return centeredRender; }

	public void setCenteredRender(boolean centeredRender) { this.centeredRender = centeredRender; }
	
	
	public void addEnterEvent(EnterEvent ee) { enterEvents.add(ee); }

	@Override
	public void tick()
	{
		carretTick += Math.max(60d / Math.max(getGame().getFPS(), 1), 1);
		if (carretTick >= 60)
		{
			carretTick = 0;
			showCarret = !showCarret;
			repaint();
		}
		
		isFocused = isCursorInComponent();
	}

	@Override
	public boolean isFocused()
	{
		return isFocused;
	}
	
	@Override
	protected int getMinHeight()
	{
		return 10;
	}
	
	@Override
	protected int getMinWidth()
	{
		return 10;
	}
	
	private void updateText()
	{
		editedText = Font.trimFront(getText(), getWidth() <= 10 ? 10 : getWidth() / 2 - 8, fontSize > 0 ? fontSize : 1);
		repaint();
	}
	
	public abstract class EnterEvent
	{
		public abstract void enter();
	}

}
