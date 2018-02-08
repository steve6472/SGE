package com.steve6472.sge.gui.components;

import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.events.ButtonEvents;
import com.steve6472.sge.gui.components.panels.ComboBoxPanel;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.ArrowTextures;
import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.MouseHandler;

public class ComboBox extends Component
{
	private static final long serialVersionUID = -5916694577318452150L;
	int x, y, width, height, maxItems = 4, slider = 0;
	Button up, down;
	ComboBoxButton b;
	List<Item> items = new ArrayList<Item>();
	boolean isExpanded = false;
	
	@Override
	public void init(BaseGame game)
	{
		b = new ComboBoxButton(0, 0, 120 - 24, 40);
		b.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				isExpanded = !isExpanded;
				b.expanded_ = isExpanded;
				if (!isExpanded)
					repaintBackground();
				repaint();
			}
		});
		up = new Button();
		up.setEnabledImage(ArrowTextures.arrowUp);
		up.setHoveredImage(ArrowTextures.arrowUpHovered);
		up.setDisabledImage(ArrowTextures.arrowUpDisabled);
		up.setLocation(x + width - 22, y + 2);
		up.setSize(22, 14);
		up.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				if (slider != 0)
				{
					slider--;
					for (Item i : items)
					{
						i.setLocation(i.getX(), i.getY() + 40);
					}
					repaint();
				}
			}
		});

		down = new Button();
		down.setEnabledImage(ArrowTextures.arrowDown);
		down.setHoveredImage(ArrowTextures.arrowDownHovered);
		down.setDisabledImage(ArrowTextures.arrowDownDisabled);
		down.setLocation(x + width - 22, y + 20);
		down.setSize(22, 14);
		down.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				if (slider + maxItems < items.size())
				{
					slider++;
					for (Item i : items)
					{
						i.setLocation(i.getX(), i.getY() - 40);
					}
					repaint();
				}
			}
		});
	}

	public void addItem(Item item)
	{
		item.setLocation(x, y + items.size() * 40 + 40);
		item.setSize(width, height);
		item.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				for (Item i : items)
				{
					i.unselect();
				}
				item.select();
				repaint();
			}
		});
		this.items.add(item);
		repaint();
	}

	public void addItems(MouseHandler mh, String... strings)
	{
		for (String s : strings)
		{
			Item item = new Item(s);
			item.setLocation(x, y + items.size() * 40 + 40);
			item.setSize(width, height);
			item.addEvent(new ButtonEvents()
			{
				@Override
				public void click()
				{
					for (Item i : items)
					{
						i.unselect();
					}
					item.select();
					repaint();
				}
			});
			this.items.add(item);
			repaint();
		}
	}

	public void addItem(Item item, ButtonEvents customClickEvent)
	{
		item.setLocation(x, y + items.size() * 40 + 40);
		item.setSize(width, height);
		item.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				for (Item i : items)
				{
					i.unselect();
				}
				item.select();
				repaint();
			}
		});
		item.addEvent(customClickEvent);
		this.items.add(item);
		repaint();
	}

	public void removeItem(String name)
	{
		int ii = 0;
		int remove = 0;
		for (Item i : items)
		{
			if (i.getText().equals(name))
			{
				remove = ii;
				break;
			}
			ii++;
		}
		items.remove(remove);
		int j = 0;
		for (Item i : items)
		{
			i.setLocation(x, y + j * 40 + 40);
			j++;
		}
		repaint();
	}

	public List<Item> getItems()
	{
		return items;
	}

	public String getSelectedItem()
	{
		for (Item i : items)
		{
			if (i.isSelected())
			{
				return i.getText();
			}
		}
		return null;
	}
	
	public int getSelectedItemIndex()
	{
		int r = 0;
		int ii = 0;
		for (Item i : items)
		{
			if (i.isSelected())
			{
				r = ii;
				ii++;
				return r;
			}
		}
		return 0;
	}
	
	public void setSelectedItem(int index)
	{
		for (Item i : items)
		{
			i.unselect();
		}
		items.get(index).select();
		repaint();
	}
	
	public void removeAllItems()
	{
		items.removeAll(items);
		repaint();
	}

	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		b.setLocation(x, y);
		up.setLocation(x + width - 22, y + 2);
		down.setLocation(x + width - 22, y + 24);
//		repaintBackground();
		repaint();
	}

	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		b.setSize(width - 24, height);
		up.setLocation(x + width - 22, y + 2);
		down.setLocation(x + width - 22, y + 24);
//		repaintBackground();
		repaint();
	}

	@Override
	public void tick()
	{
		if (isVisible())
		{
			if (slider == 0)
			{
				up.disable();
				repaint();
			} else
			{
				up.enable();
				repaint();
			}
			b.tick();
			up.tick();
			down.tick();
			b.setText(getSelectedItem());
			if (isExpanded)
			{
				if (items.size() != 0)
				{
					if (items.size() < maxItems)
					{
						int maxItems = items.size();
						for (int i = 0; i < maxItems; i++)
						{
							items.get(i + slider).tick();
						}
					} else
					{
						for (int i = 0; i < maxItems; i++)
						{
							items.get(i + slider).tick();
						}
					}
				}
			} else
			{}
		}
	}

	public void setMaxItems(int i)
	{
		this.maxItems = i;
		repaint();
	}

	@Override
	public void render(Screen screen)
	{
		if (isVisible())
		{
			b.render(screen);
			up.render(screen);
			down.render(screen);
			if (items.size() != 0)
			{
				if (isExpanded)
				{
					if (items.size() < maxItems)
					{
						int maxItems = items.size();
						for (int i = 0; i < maxItems; i++)
						{
							items.get(i + slider).render(screen);
						}
					} else
					{
						for (int i = 0; i < maxItems; i++)
						{
							items.get(i + slider).render(screen);
						}
					}
				}
			}
		}
	}
}

class ComboBoxButton extends Button
{
	private static final long serialVersionUID = 8367163866261346256L;
	private PanelBase expanded = null;
	public boolean expanded_ = false;

	public ComboBoxButton(int x, int y, int width, int height)
	{
		super();
		setLocation(x, y);
		setSize(width, height);
	}
	
	@Override
	public void init(BaseGame game)
	{
		super.init(game);
		expanded = new ComboBoxPanel(getGame().getScreen());
		renderFont = false;
	}

	@Override
	public void tick()
	{
		if (isVisible() && enabled_)
		{
			if (isCursorInComponent(x, y, width, height))
			{
				if (getMouseHandler().mouse_hold && !getMouseHandler().mouse_triggered)
				{
					for (ButtonEvents e : events)
					{
						e.click();
					}
					getMouseHandler().mouse_triggered = true;
				}
				hovered_ = true;
				return;
			} else
			{
				hovered_ = false;
			}
		}
	}

	@Override
	public void render(Screen screen)
	{
		super.render(screen);
		
		if (isVisible())
		{
			if (enabled_ && expanded_ && !hovered_)
			{
				if (expanded != null)
				{
					expanded.render(x + image_offset_x, y + image_offset_y, getWidth(), getHeight());
				}
			}

			if (getText() != null)
				getFont().render(getText(), screen, x + (width / 2) - (getText().length() * 8) / 2, y + (height / 2) - 3, fontScale);
		}
	}
}
