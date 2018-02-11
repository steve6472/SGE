package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.events.ChangeEvent;
import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.Util;

public class ItemGridList extends Component
{
	
	private static final long serialVersionUID = 3003404793344194226L;
	List<Item> items = new ArrayList<Item>();
	private List<ChangeEvent> changeEvents = new ArrayList<ChangeEvent>();
	int fontSize = 1;
	int visibleItemsX = 4;
	int visibleItemsY = 4;
	int scroll = 0;
	int hovered = 0;
	int selected = 0;
	
	private boolean upRepainted = false, downRepainted = false, upEnabled = true, downEnabled = true, upHovered = false, downHovered = false;
	
	@Override
	public void init(BaseGame game)
	{
	}
	
	@Override
	public void render(Screen screen)
	{
		for (int i = 0; i < visibleItemsX; i++)
		{
			for (int j = 0; j < visibleItemsY; j++)
			{
				int y = i + j * visibleItemsX;
				
				if (!((y + scroll) > (items.size() - 1)))
				{
					getGame().panelList.getPanelById(0).render(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight());

					screen.renderSprite(items.get((y + scroll)).sprite, getX() + 2 + i * getWidth(),
							getY() + j * getHeight() + (getHeight() / 2 - items.get(y + scroll).sprite.getHeight() / 2));

					if (hovered == y)
						screen.fillRect(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight(), Util.HOVERED_OVERLAY);

					if (selected == y + scroll)
						screen.fillRect(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight(), Util.SELECTED_OVERLAY);
				}
			}
		}

		//Left "slider"
		getGame().panelList.getPanelById(8).render(getX() + getWidth() - 40 + visibleItemsX * getWidth() + 4, getY() + 14, 22, getHeight() * visibleItemsY - 14 * 2);

		// Up button
		if (upEnabled && upHovered)
		{
			getGame().panelList.getPanelById(11).render(getX() + getWidth() * getVisibleItemsX(), getY(), 22, 14);
		} else if (upEnabled && !upHovered)
		{
			getGame().panelList.getPanelById(9).render(getX() + getWidth() * getVisibleItemsX(), getY(), 22, 14);
		} else if (!upEnabled)
		{
			getGame().panelList.getPanelById(10).render(getX() + getWidth() * getVisibleItemsX(), getY(), 22, 14);
		}

		//Down button
		if (downEnabled && downHovered)
		{
			getGame().panelList.getPanelById(11).render(getX() + getWidth() * getVisibleItemsX(), getY() + getHeight() * getVisibleItemsY() - 14, 22, 14);
		} else if (downEnabled && !downHovered)
		{
			getGame().panelList.getPanelById(9).render(getX() + getWidth() * getVisibleItemsX(), getY() + getHeight() * getVisibleItemsY() - 14, 22, 14);
		} else if (!downEnabled)
		{
			getGame().panelList.getPanelById(10).render(getX() + getWidth() * getVisibleItemsX(), getY() + getHeight() * getVisibleItemsY() - 14, 22, 14);
		}
	}
	
	private int oldHover = -1;
	private boolean selectRepainted = false;

	@Override
	public void tick()
	{
		upHovered = isCursorInComponent(getX() + getWidth() * getVisibleItemsX(), getY(), 22, 14);
		
		downHovered = isCursorInComponent(getX() + getWidth() * getVisibleItemsX(), getY() + getHeight() * getVisibleItemsY() - 14, 22, 14);
		
		if (upHovered && !upRepainted)
		{
			upRepainted = true;
			repaint();
		} else if (!upHovered && upRepainted)
		{
			upRepainted = false;
			repaint();
		}
		
		onMouseClicked(upEnabled && upHovered, c ->
		{
			scroll -= visibleItemsX;
			getMouseHandler().mouseTriggered = true;
			repaint();
			for (ChangeEvent ce : changeEvents)
			{
				ce.change();
			}
			repaintBackground();
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
		
		onMouseClicked(downHovered && downEnabled, c ->
		{
			scroll += visibleItemsX;
			getMouseHandler().mouseTriggered = true;
			repaint();
			for (ChangeEvent ce : changeEvents)
			{
				ce.change();
			}
			repaintBackground();
		});

		upEnabled = scroll > 0;
		downEnabled = scroll < items.size() - visibleItemsY * visibleItemsX;

		hovered = -1;
		for (int i = 0; i < visibleItemsX; i++)
		{
			for (int j = 0; j < visibleItemsY; j++)
			{
				int y = i + j * visibleItemsX;

				if (!((y + scroll) > (items.size() - 1)))
				{
					boolean b = isCursorInComponent(getX() + i * getWidth(), getY() + j * getHeight(), getWidth(), getHeight());
					if (b)
					{
						onMousePressed(b, (c) ->
						{
							if (selected != y + scroll)
							{
								selected = y + scroll;
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
						hovered = y;
						break;
					}
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
	
	public Item getItem(int index)
	{
		return items.get(index);
	}
	
	public Item getSelectedItem()
	{
		return items.get(selected);
	}
	
	public void setSelected(int selected)
	{
		this.selected = selected;
	}
	
	public void setScroll(int scroll)
	{
		this.scroll = scroll;
	}
	
	public int getScroll()
	{
		return scroll;
	}
	
	public void addItem(Sprite sprite)
	{
		items.add(new Item(sprite != null ? sprite : new Sprite(new int[] {0}, 1, 0)));
		repaint();
		repaintBackground();
	}
	
	public void addItem(Sprite sprite, String name)
	{
		items.add(new Item(sprite != null ? sprite : new Sprite(new int[] {0}, 1, 0), name));
		repaint();
		repaintBackground();
	}
	
	public void removeItem(int index)
	{
		if (index < items.size())
			items.remove(index);
		repaint();
		repaintBackground();
	}
	
	public void removeAllItems()
	{
		items.clear();
	}
	
	public void setFontSize(int size)
	{
		this.fontSize = size;
		repaint();
	}
	
	public void setVisibleItems(int x, int y)
	{
		this.visibleItemsX = x;
		this.visibleItemsY = y;
		repaint();
		repaintBackground();
	}
	
	public int getVisibleItemsX()
	{
		return visibleItemsX;
	}
	
	public int getVisibleItemsY()
	{
		return visibleItemsY;
	}
	
	public int getSelectedIndex()
	{
		return selected;
	}
	
	public void addChangeEvent(ChangeEvent ce)
	{
		changeEvents.add(ce);
	}
	
	public List<Item> getItems()
	{
		return items;
	}

	public class Item
	{
		Sprite sprite;
		String name;
		
		private Item(Sprite sprite)
		{
			this.sprite = sprite;
		}
		
		private Item(Sprite sprite, String name)
		{
			this.sprite = sprite;
			this.name = name;
		}
		
		public Sprite getSprite()
		{
			return sprite;
		}
		
		public String getString()
		{
			return name;
		}
	}
}
