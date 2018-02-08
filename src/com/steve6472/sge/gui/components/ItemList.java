package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.events.ChangeEvent;
import com.steve6472.sge.main.BaseGame;

public class ItemList extends Component
{

	private static final long serialVersionUID = 4821451061681776222L;
	protected List<Item> items = new ArrayList<Item>();
	private List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
	protected int fontSize = 1;
	protected int visibleItems = 4;
	protected int scroll = 0;
	protected int hovered = 0;
	protected int selected = 0;

	private boolean upRepainted = false, downRepainted = false, upEnabled = true, downEnabled = true, upHovered = false, downHovered = false;

	@Override
	public void init(BaseGame game)
	{
		
	}

	@Override
	public void render(Screen screen)
	{
		for (int i = 0; i < visibleItems; i++)
		{
			if (!((i + scroll) > (items.size() - 1)))
			{
				getGame().panelList.getPanelById(0).render(getX(), getY() + i * getHeight(), getWidth() - 22, getHeight());

				if (hovered == i)
					screen.fillRect(getX(), getY() + i * getHeight(), getWidth() - 22, getHeight(), 0x80777777);

				if (selected == i + scroll)
					screen.fillRect(getX(), getY() + i * getHeight(), getWidth() - 22, getHeight(), 0x80555555);

				renderText(screen, i);

				screen.renderSprite(items.get((i + scroll)).sprite, getX() + 2,
						getY() + i * getHeight() + (getHeight() / 2 - items.get(i + scroll).sprite.getHeight() / 2));
			}
		}
		// Left "slider"
		getGame().panelList.getPanelById(8).render(getX() + getWidth() - 22, getY() + 14, 22, getHeight() * visibleItems - 14 * 2);

		// Up button
		if (upEnabled && upHovered)
		{
			getGame().panelList.getPanelById(11).render(getX() + getWidth() - 22, getY(), 22, 14);
		} else if (upEnabled && !upHovered)
		{
			getGame().panelList.getPanelById(9).render(getX() + getWidth() - 22, getY(), 22, 14);
		} else if (!upEnabled)
		{
			getGame().panelList.getPanelById(10).render(getX() + getWidth() - 22, getY(), 22, 14);
		}

		// Down button
		if (downEnabled && downHovered)
		{
			getGame().panelList.getPanelById(11).render(getX() + getWidth() - 22, getY() + getHeight() * getVisibleItems() - 14, 22, 14);
		} else if (downEnabled && !downHovered)
		{
			getGame().panelList.getPanelById(9).render(getX() + getWidth() - 22, getY() + getHeight() * getVisibleItems() - 14, 22, 14);
		} else if (!downEnabled)
		{
			getGame().panelList.getPanelById(10).render(getX() + getWidth() - 22, getY() + getHeight() * getVisibleItems() - 14, 22, 14);
		}
	}

	private int oldHover = -1;
	private boolean selectRepainted = false;

	@Override
	public void tick()
	{
		upHovered = isCursorInComponent(getX() + getWidth() - 22, getY(), 22, 14);

		downHovered = isCursorInComponent(getX() + getWidth() - 22, getY() + getHeight() * getVisibleItems() - 14, 22, 14);

		if (upHovered && !upRepainted)
		{
			upRepainted = true;
			repaint();
		} else if (!upHovered && upRepainted)
		{
			upRepainted = false;
			repaint();
		}
		
		onMouseClicked(upEnabled && upHovered, (c) ->
		{
			scroll--;
			getMouseHandler().mouse_triggered = true;
			repaint();
			if (parentComponent == null)
				repaintBackground();
			else
				parentComponent.repaint();
		});

		if (downHovered && !downRepainted)
		{
			downHovered = true;
			repaint();
		} else if (!downHovered && downRepainted)
		{
			downHovered = false;
			repaint();
		} else if (!downHovered && !downRepainted)
		{
			downHovered = false;
			repaint();
		}
		
		onMouseClicked(downHovered && downEnabled, (c) ->
		{
			scroll++;
			getMouseHandler().mouse_triggered = true;
			repaint();
			if (parentComponent == null)
				repaintBackground();
			else
				parentComponent.repaint();
		});

		upEnabled = scroll > 0;
		downEnabled = scroll < items.size() - visibleItems;

		hovered = -1;
		for (int i = 0; i < visibleItems; i++)
		{
			if (!((i + scroll) > (items.size() - 1)))
			{
				int awbud = i;
				boolean b = isCursorInComponent(getX(), getY() + i * getHeight(), getWidth() - 22, getHeight());
				if (b)
				{
					onMousePressed(b, (c) -> 
					{
						if (selected != awbud + scroll)
						{
							selected = awbud + scroll;
							for (ChangeEvent ce : changeEvents)
							{
								ce.change();
							}
						}
						if (!selectRepainted)
						{
							repaint();
							selectRepainted = true;
						}
					});
					hovered = i;
					break;
				}
			}
		}
		if (oldHover != hovered)
		{
			oldHover = hovered;
			selectRepainted = false;
			repaint();
		}
	}

	protected void renderText(Screen screen, int index)
	{
		getFont().render(Font.trim(items.get((index + scroll)).text, width - 34, fontSize), screen,
				items.get(index + scroll).sprite.getWidth() + getX() + 10, getY() + getHeight() / 2 - 8 / 2 + index * getHeight(), fontSize);
	}

	/**
	 * Set size of *ONE* slot
	 */
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
	}

	public Sprite getSprite(int index)
	{
		return items.get(index).sprite;
	}

	public List<Item> getItems()
	{
		return items;
	}

	public void addItem(String text, Sprite sprite)
	{
		items.add(new Item(text, sprite != null ? sprite : new Sprite(new int[]
		{ 0 }, 1, 0)));
		repaint();
		if (parentComponent == null)
			repaintBackground();
		else
			parentComponent.repaint();
	}

	public void addItem(String text)
	{
		addItem(text, null);
	}

	public void removeItem(int index)
	{
		if (index <= items.size())
			items.remove(index);
		repaint();
		if (parentComponent == null)
			repaintBackground();
		else
			parentComponent.repaint();
	}

	public void clear()
	{
		items.clear();
	}

	public void setSelectedItem(int index)
	{
		selected = index < items.size() ? index : items.size() - 1;
		repaint();
	}

	public void setFontSize(int size)
	{
		this.fontSize = size;
		repaint();
	}

	public void setVisibleItems(int i)
	{
		this.visibleItems = i;
		repaint();
		if (parentComponent == null)
			repaintBackground();
		else
			parentComponent.repaint();
	}

	public int getVisibleItems()
	{
		return visibleItems;
	}

	public String getSelectedItem()
	{
		return items.get(selected).text;
	}

	public int getSelectedIndex()
	{
		return selected;
	}

	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}

	public void fireChangeEvent()
	{
		for (ChangeEvent ce : changeEvents)
		{
			ce.change();
		}
	}

	public class Item
	{
		protected String text = "";
		protected Sprite sprite;

		private Item(String text, Sprite sprite)
		{
			this.text = text;
			this.sprite = sprite;
		}

		public String getText()
		{
			return text;
		}
	}

}
