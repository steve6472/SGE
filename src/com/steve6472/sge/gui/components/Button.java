package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.events.ButtonEvents;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class Button extends Component implements IFocusable
{
	private static final long serialVersionUID = -4734082970298391201L;
	int fontScale = 1, image_offset_x, image_offset_y;
	protected Sprite enabled = null, disabled = null, hovered = null;

	protected boolean enabled_ = true, hovered_ = false;

	public boolean renderFont = true, debug = false;

	private String text = "";
	
	protected PanelBase panelNormal;
	protected PanelBase panelDisabled;
	protected PanelBase panelHovered;

	/*
	 * Events
	 */
	protected List<ButtonEvents> events = new ArrayList<ButtonEvents>();
	
	public Button(String text)
	{
		this.text = text;
	}
	
	public Button()
	{
		
	}

	public Button(int x, int y, int width, int height, String text, BaseGame game)
	{
//		super(game);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	@Override
	public void init(BaseGame game)
	{
		initPanels();
	}
	
	protected void initPanels()
	{
		panelNormal = getGame().panelList.getPanelByName("ButtonPanel");
		panelDisabled = getGame().panelList.getPanelByName("ButtonPanelDisabled");
		panelHovered = getGame().panelList.getPanelByName("ButtonPanelHovered");
		
		repaint();
	}
	
	private boolean hoverRepainted = false;
	
	@Override
	public void tick()
	{
		if (isVisible() && enabled_)
		{
			boolean isHovered = isCursorInComponent(x, y, width, height);
			toolTipTick(getMouseHandler());
			
			if (isHovered)
			{
				if (getMouseHandler().isMouseHolded())
				{
					for (ButtonEvents e : events)
					{
						e.hold();
					}
				}
				if (!hoverRepainted)
				{
					repaint();

					hoverRepainted = true;
				}
				onMouseClicked(b ->
				{
					for (ButtonEvents e : events)
					{
						e.click();

						repaint();
					}
				});
				hovered_ = true;
				return;
			} else
			{
				hovered_ = false;
				
				if (hoverRepainted)
				{
					repaint();
				}
				
				hoverRepainted = false;
			}
		}
	}
	
	@Override
	public boolean isFocused()
	{
		return isHovered();
	}
	
	public void doClick()
	{
		for (ButtonEvents e : events)
		{
			e.click();
		}
	}

	public void addEvent(ButtonEvents e)
	{
		events.add(e);
	}

	@Override
	public void render(Screen screen)
	{
		if (debug)
		{
			screen.fillRect(x, y, width, height, 0xffff22ff, getGame().getWidth(), getGame().getHeight(), 0, 0);
			screen.fillRect(x, y, 1, height, 0xffffff22, getGame().getWidth(), getGame().getHeight(), 0, 0);
		}

		if (enabled_)
		{
			if (enabled != null)
			{
				screen.renderSprite(enabled, x + image_offset_x, y + image_offset_y);
			} else
			{
				panelNormal.render(x, y, width, height);
			}
		}

		if (!enabled_)
		{
			if (disabled != null)
			{
				screen.renderSprite(disabled, x + image_offset_x, y + image_offset_y);
			} else
			{
				panelDisabled.render(x, y, width, height);
			}
		}

		if (enabled_ && hovered_)
		{
			if (hovered != null)
			{
				screen.renderSprite(hovered, x + image_offset_x, y + image_offset_y);
			} else
			{
				panelHovered.render(x, y, width, height);
			}
		}

		if (renderFont)
		{
			if (text != null)
			{
				getFont().render(text, screen, x + (width / 2) - ((text.length() * (8 * fontScale)) / 2), y + (height / 2) - 3, fontScale);
			}
		}
	}
	
	/*
	 * Operators
	 */

	public void enable()
	{
		this.enabled_ = true;
	}

	public void disable()
	{
		this.enabled_ = false;
	}
	
	public void removeEvents()
	{
		events.clear();
	}
	
	/*
	 * Setters
	 */

	public void setText(String text)
	{
		this.text = text;
		
		repaint();
	}

	public void setEnabled(boolean b)
	{
		this.enabled_ = b;
		
		repaint();
	}

	public void setSize(int w, int h)
	{
		this.width = w;
		this.height = h;
		
		repaint();
	}

	public void setImageOffset(int x, int y)
	{
		this.image_offset_x = x;
		this.image_offset_y = y;
		
		repaint();
	}

	public void setHoveredImage(Sprite image)
	{
		hovered = image;
		
		repaint();
	}

	public void setEnabledImage(Sprite image)
	{
		enabled = image;
		
		repaint();
	}

	public void setDisabledImage(Sprite image)
	{
		disabled = image;
		
		repaint();
	}

	public void setFontScale(int s)
	{
		fontScale = Math.max(1, s);
		
		repaint();
	}
	
	/*
	 * Getters
	 */

	public String getText()
	{
		return text;
	}

	public boolean isEnabled()
	{
		return enabled_;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getFontScale()
	{
		return fontScale;
	}

	public boolean isHovered()
	{
		return hovered_;
	}
}
