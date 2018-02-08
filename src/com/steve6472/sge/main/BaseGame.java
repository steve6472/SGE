package com.steve6472.sge.main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.steve6472.sge.gfx.Font;
import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Gui;
import com.steve6472.sge.gui.components.ToolTip;
import com.steve6472.sge.gui.components.panels.CustomPanel;
import com.steve6472.sge.gui.components.panels.Panel1;
import com.steve6472.sge.gui.components.panels.Panel2;
import com.steve6472.sge.gui.components.panels.Panel3;
import com.steve6472.sge.gui.components.panels.Panel4;
import com.steve6472.sge.gui.components.panels.Panel5;
import com.steve6472.sge.gui.components.panels.Panel6;
import com.steve6472.sge.gui.components.panels.Panel7;
import com.steve6472.sge.gui.components.panels.Panel8;
import com.steve6472.sge.gui.components.panels.Panel9;
import com.steve6472.sge.gui.components.panels.PanelList;
import com.steve6472.sge.gui.components.panels.SliderPanel;
import com.steve6472.sge.gui.components.panels.button.ButtonPanel;
import com.steve6472.sge.gui.components.panels.button.ButtonPanelDisabled;
import com.steve6472.sge.gui.components.panels.button.ButtonPanelHovered;
import com.steve6472.sge.main.KeyHandler.Key;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public abstract class BaseGame
{
	protected final SGEMain main;
	protected final Screen screen;
	protected final MouseHandler mouseHandler;
	protected final KeyHandler keyHandler;
	public final PanelList panelList;
	public final Font font;
	public final BaseGame game;
	public int totalId = 0;
	public boolean shouldMainRender = true;
	
	public List<ToolTip> toolTips = new ArrayList<ToolTip>();
	public List<Gui> guis = new ArrayList<Gui>();
//	public List<Class<? extends Gui>> registredGuis = new ArrayList<Class<? extends Gui>>();
	
	public BaseGame()
	{
		game = this;
		font = new Font(this);
		screen = new Screen(game.getWidth(), game.getHeight(), game);
		main = new SGEMain(isApplet(), game);
		mouseHandler = new MouseHandler(main);
		keyHandler = new KeyHandler(main);
		panelList = new PanelList();
		initPanels();
		main.start();
	}

	protected void initPanels()
	{
		panelList.panels.add(new Panel1(game)); // 0
		panelList.panels.add(new Panel2(game)); // 1
		panelList.panels.add(new Panel3(game)); // 2
		panelList.panels.add(new Panel4(game)); // 3
		panelList.panels.add(new Panel5(game)); // 4
		panelList.panels.add(new Panel6(game)); // 5
		panelList.panels.add(new Panel7(game)); // 6
		panelList.panels.add(new Panel8(game)); // 7
		panelList.panels.add(new Panel9(game)); // 8

		panelList.panels.add(new ButtonPanel(game)); // 9
		panelList.panels.add(new ButtonPanelDisabled(game)); // 10
		panelList.panels.add(new ButtonPanelHovered(game)); // 11
		
		panelList.panels.add(new SliderPanel(game)); // 12
		
		panelList.panels.add(new CustomPanel(game)); // 13
	}

	public void renderGui()
	{
		for (Iterator<Gui> gu = guis.iterator(); gu.hasNext();)
		{
			gu.next().renderGui(screen);
		}
	}
	
	public void tickGui()
	{
		for (Iterator<Gui> gu = guis.iterator(); gu.hasNext();)
		{
			gu.next().tick();
		}
	}
	
	protected void screenshot(Key key)
	{
		if (key.isPressed() && !key.typed)
		{
			key.typed = true;
			takeScreenshot();
		}
	}
	
	public void takeScreenshot()
	{
		File file = new File("screenshots");
		if (!file.exists())
		{
			file.mkdirs();
		}
		try
		{
			ImageIO.write(getMain().getRenderedImage(), "PNG", new File(String.format("screenshots\\" + Util.getFormatedTime() + ".png")));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Experimental method. Can cause problems in unexpected ways.
	 */
	@Deprecated
	protected final void decorateFrame()
	{
		JFrame f = getMain().getJFrame();
		f.dispose();
		f.setUndecorated(false);
		f.setVisible(true);
		f.setResizable(true);
		getMain().experimentalBlurRender = true;
	}
	
	public abstract void init(final SGEMain main);
	public abstract void tick();
	public abstract void render(final SGEMain main);
	public abstract void exit();

	public abstract int getWidth();
	public abstract int getHeight();
	
	public abstract boolean fontShadow();
	public abstract boolean smallFont();
	public abstract boolean isApplet();
	
	public abstract String getTitle();
	
	public abstract Image setIconImage();
	
	/*
	 * Methods
	 */
	
	/*
	 * Operators
	 */
	
	/*
	 * Setters
	 */
	
	/*
	 * Getters
	 */
	
	public MouseHandler getMouseHandler() { return mouseHandler; }
	
	public KeyHandler getKeyHandler() { return keyHandler; }
	
	public Screen getScreen() { return screen; }
	
	public SGEMain getMain() { return main; }
	
	public int getFPS() { return main.fps; }

	public Vec2 getCenter() { return new Vec2(getWidth() / 2 , getHeight() / 2); }

	public AABB getScreenAABB() { return new AABB(0, 0, getWidth(), getHeight()); }
}
