package com.steve6472.sge.gui.components;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class Panel extends Component
{
	private static final long serialVersionUID = -2331423356605156534L;
	boolean set_limited_render = false;
	int maxx, maxy, minx, miny, size;
	private PanelBase panel;
	
	public Panel(PanelBase p)
	{
		panel = p;
	}
	
	public Panel()
	{
		
	}

	@Deprecated
	/**
	 * Gonna be removed
	 */
	public Panel(BaseGame game)
	{
//		super(game);
	}
	
	@Override
	public void init(BaseGame game)
	{
		panel = game.panelList.getPanelById(0);
	}

	@Override
	public void render(Screen screen)
	{
		if (isVisible())
		{
			panel.render(getX(), getY(), getWidth(), getHeight());
		}
	}

	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */

	public void setLimitedRender(int x2, int y2, int i, int j)
	{
		this.maxx = x2;
		this.maxy = y2;
		this.minx = i;
		this.miny = j;
		set_limited_render = true;
	}
	
	public void fillScreen(BaseGame game)
	{
		setSize(game.getWidth(), game.getHeight());
		setLocation(0, 0);
	}
	
	public void setPanel(PanelBase panel)
	{
		this.panel = panel;
	}
	
	public PanelBase getPanel()
	{
		return panel;
	}
	
	/**
	 * Can be used only when Panel was constructed with BaseGame
	 * @param id
	 */
	public void setPanel(int id)
	{
		if (getGame() != null)
			this.panel = getGame().panelList.getPanelById(id);
	}

	@Override
	public void tick()
	{
	}
	
	/*
	 * Getters
	 */
}
