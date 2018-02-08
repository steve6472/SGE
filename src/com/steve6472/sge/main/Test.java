package com.steve6472.sge.main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.ComponentTest;
import com.steve6472.sge.gui.Gui;

class Test extends BaseGame
{
	public static void main(String[] args)
	{
		new Test();
	}

	@Override
	public void init(SGEMain main)
	{
		ComponentTest t = new ComponentTest(this);
		t.showGui();
		
		getMain().exitOnRenderError = true;
		
//		Sprite s1 = new Sprite("test.png");
//		Sprite s2 = new Sprite(s1.getWidth(), s1.getHeight());
//		s2.fill(0xfff2f2f2);
//		
//		BufferedImage combined = new BufferedImage(s2.getWidth(), s2.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//		Graphics g = combined.getGraphics();
//		g.drawImage(s2.toBufferedImage(), 0, 0, null);
//		g.drawImage(s1.toBufferedImage(), 0, 0, null);
//
//		try
//		{
//			ImageIO.write(combined, "PNG", new File("combined.png"));
//		} catch (IOException e)
//		{
//			e.printStackTrace();
//		}
		

//		Cursor emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "empty");
		Cursor emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new Sprite("aeroMouse.png").toBufferedImage(), new Point(0, 0), "custom");
//		Cursor defaultCursor = getCursor();
		getMain().getJFrame().setCursor(emptyCursor);
		
		int[] is = new int[32];
		
		int totalCount = is.length;
		
		int box = getBiggestClosetsSqrt(totalCount);
		
		System.out.println(box);
		
		System.out.println("Can fit: " + box * box);
		System.out.println("Free space: " + ((box * box) - is.length));
		
		for (int i = 0; i < totalCount; i++)
		{
			int x = i % box;
			int y = i / box;
			
			System.out.print(x + "/" + y + " ");
			if (x == box - 1)
				System.out.println();
		}
	}

	public int getBiggestClosetsSqrt(int count)
	{
		int r = count;
		double temp = Math.sqrt(r);
		int iteration = 0;
		for (;;)
		{
			if (temp == Math.floor(temp))
			{
				return (int) temp;
			} else
			{
				r++;
				temp = Math.sqrt(r);
			}
			iteration++;
			if (iteration >= 256)
			{
				System.err.println("More than 256 iterations!");
				game.exit();
			}
		}
	}

	@Override
	public void tick()
	{
		tickGui();
		if (keyHandler.esc.isPressed() && !keyHandler.esc.typed)
		{
			Gui.openGui(this, ComponentTest.class);
			keyHandler.esc.typed = true;
		}
		screenshot(getKeyHandler().c);
	}

	@Override
	public void render(SGEMain main)
	{
		renderGui();
		
		screen.fillRect(0, 0, 70, 16, 0xff383838);
		font.render("Fps:" + main.fps, screen, 5, 5, 1);
	}

	@Override
	public int getWidth()
	{
		return 16 * 80;
	}

	@Override
	public int getHeight()
	{
		return 9 * 80;
	}

	@Override
	public boolean fontShadow()
	{
		return true;
	}

	@Override
	public boolean smallFont()
	{
		return ComponentTest.smallFont;
	}
	
	@Override
	public boolean isApplet()
	{
		return false;
	}

	@Override
	public String getTitle()
	{
		return "TEST";
	}

	@Override
	public Image setIconImage()
	{
		return null;
	}

	@Override
	public void exit()
	{
		System.exit(0);
	}

}
