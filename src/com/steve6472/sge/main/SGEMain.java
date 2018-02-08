package com.steve6472.sge.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class SGEMain extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public int fps = 0;
	public int ticks = 0;
	public final BaseGame game;
	
	public boolean exitOnRenderError = false;

	protected BufferedImage image = null;
	protected int[] pixels = null;
	private final JFrame frame;

	public boolean running = false;
	
//	private Cursor emptyCursor, defaultCursor;

	public SGEMain(boolean isApplet, BaseGame game)
	{
		this.game = game;
		if (!isApplet)
		{
			Dimension d = new Dimension(game != null ? game.getWidth() > 0 ? game.getWidth() : 32 : 3,
					game != null ? game.getHeight() > 0 ? game.getHeight() : 32 : 32);
			setMaximumSize(d);
			setMinimumSize(d);
			setPreferredSize(d);
			frame = new JFrame(game != null ? game.getTitle() : "NULL");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());
			frame.add(this, BorderLayout.CENTER);
			frame.setIconImage(game != null ? game.setIconImage() : null);
			frame.setUndecorated(true);
			frame.pack();
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.requestFocus();
		} else
		{
			frame = null;
		}
		image = new BufferedImage(game != null ? game.getWidth() > 0 ? game.getWidth() : 32 : 32,
				game != null ? game.getHeight() > 0 ? game.getHeight() : 32 : 32, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
//		emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "empty");
//		defaultCursor = getCursor();
	}
	
	public void setNullCursor()
	{
		getJFrame().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "empty"));
//		defaultCursor = getCursor();
	}
	
	public void recreateFrame(int width, int height)
	{
		image = new BufferedImage(game != null ? game.getWidth() > 0 ? game.getWidth() : 32 : 32,
				game != null ? game.getHeight() > 0 ? game.getHeight() : 32 : 32, BufferedImage.TYPE_INT_RGB);
		
		setPixels(((DataBufferInt) image.getRaster().getDataBuffer()).getData());
		
		game.screen.width = width;
		game.screen.height = height;
		
		game.screen.pixels = pixels;
		
		frame.setSize(width, height);
	}

	public JFrame getJFrame()
	{
		return frame;
	}
	
	public void closeFrame()
	{
		frame.setVisible(false);
		recreateFrame(1, 1);
		frame.setSize(1, 1);
		frame.dispose();
	}
	
	public void setImage(BufferedImage img)
	{
		this.image = img;
	}
	
	public void setPixels(int[] pix)
	{
		this.pixels = pix;
	}
	
	public void setPixel(int color, int x, int y)
	{
		if (x + y * game.getWidth() >= 0 && x + y * game.getWidth() < pixels.length)
			pixels[x + y * game.getWidth()] = color;
	}
	
	public void setPixel(int index, int color)
	{
		pixels[index] = color;
	}

	public synchronized void start()
	{
		running = true;
		new Thread(this).start();
	}

	public synchronized void stop()
	{
		running = false;
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		// int ticks = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		game.init(this);

		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (delta >= 1)
			{
				 ticks++;
				game.tick();
				delta -= 1;
				shouldRender = true;
			}

			try
			{
				Thread.sleep(2);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			if (shouldRender)
			{
				frames++;
				try
				{
					if (frame.getState() == Frame.NORMAL)
						render();
				} catch (Exception ex)
				{
					ex.printStackTrace();
					//TODO:
					if (exitOnRenderError)
						game.exit();
				}
			}

			if (System.currentTimeMillis() - lastTimer >= 1000)
			{
				lastTimer += 1000;
				// System.out.println(ticks + " ticks, " + frames + " frames");
				fps = frames;
				frames = 0;
				 ticks = 0;
			}
		}
	}
	
	public boolean experimentalBlurRender = false;
	
	public static List<GraphicsRender> graphicsRender = new ArrayList<GraphicsRender>();

	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		game.render(this);
		
		if (experimentalBlurRender)
		{

			Graphics2D g = (Graphics2D) bs.getDrawGraphics();

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			for (GraphicsRender gr : graphicsRender)
				if (gr != null)
					gr.render(g);

			g.dispose();
			bs.show();
		} else
		{
			Graphics g = bs.getDrawGraphics();

			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			
			for (GraphicsRender gr : graphicsRender)
				if (gr != null)
					gr.render(g);

			g.dispose();
			bs.show();
		}
	}

//	public void init(BaseGame game)
//	{
//		this.game = game;
////		game = new Game();
////		Game.sheet = new Sprite("/font.png");
////		Save.load();
////		if (!GameState.isApplet)
////			new SGEMain(false).start();
//	}
	
	public BufferedImage getRenderedImage()
	{
		return image;
	}
	
	public int[] getPixels()
	{
		return pixels;
	}

	public static int[] getFilledTexture(int w, int h, int color)
	{
		int[] r = new int[w * h];
		for (int i = 0; i < w; i++)
		{
			for (int j = 0; j < h; j++)
			{
				r[i + j * w] = color;
			}
		}
		return r;

	}
}
