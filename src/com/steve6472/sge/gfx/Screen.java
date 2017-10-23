package com.steve6472.sge.gfx;

import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.game.Vec2;

public class Screen
{
	public int[] pixels;
	
	public int width = 0;
	public int height = 0;
	
	public Sprite sheet;
	public BaseGame game;
	
	public Screen(int width, int height, BaseGame game)
	{
		this.width = width;
		this.height = height;
		this.sheet = new Sprite("font.png");
		this.game = game;

		pixels = new int[width * height];
	}

	public static int getColor(int r, int g, int b, int a)
	{
		int re = ((a & 0xff) << 24) | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
		return re;
	}

	public static int getColor(int r, int g, int b)
	{
		return getColor(r, g, b, 255);
	}
	
	public static int getRed(int color)
	{
		return (color >> 16) & 0xff;
	}
	
	public static int getGreen(int color)
	{
		return (color >> 8) & 0xff;
	}
	
	public static int getBlue(int color)
	{
		return color & 0xff;
	}
	
	public static int getAlpha(int color)
	{
		return (color >> 24) & 0xff;
	}

	/**
	 * Font render
	 */
	public void render(int x_pos, int y_pos, int tile, int scale)
	{
		int scale_map = scale -1;
		int x_tile = tile % 64;
		int y_tile = tile / 64;
		int tile_offset = (x_tile << 3) + (y_tile << 3) * sheet.width;
		for (int y = 0; y < 8; y++)
		{
			int y_pixel = y + y_pos + (y * scale_map) - ((scale_map << 3) / 2);
			for (int x = 0; x < 8; x++)
			{
				int x_pixel = x + x_pos + (x * scale_map) - ((scale_map << 3) / 2);
				int c = (sheet.pixels[x + y * sheet.width + tile_offset]);
				if (c < 0)
				{
					for (int y_scale = 0; y_scale < scale; y_scale++)
					{
						if (y_pixel + y_scale < 0 || y_pixel + y_scale >= height)
							continue;
						for (int x_scale = 0; x_scale < scale; x_scale++)
						{
							if (x_pixel + x_scale < 0 || x_pixel + x_scale >= width)
								continue;
								render((x_pixel + x_scale), (y_pixel + y_scale), c);
						}
					}
				}
			}
		}
	}

	/**
	 * Font test render
	 */
	public void render(int x_pos, int y_pos, int tile, int scale, int replaceColor, int color)
	{
		int scale_map = scale -1;
		int x_tile = tile % 64;
		int y_tile = tile / 64;
		int tile_offset = (x_tile << 3) + (y_tile << 3) * sheet.width;
		for (int y = 0; y < 8; y++)
		{
			int y_pixel = y + y_pos + (y * scale_map) - ((scale_map << 3) / 2);
			for (int x = 0; x < 8; x++)
			{
				int x_pixel = x + x_pos + (x * scale_map) - ((scale_map << 3) / 2);
				int c = (sheet.pixels[x + y * sheet.width + tile_offset]);
				if (c < 0)
				{
					for (int y_scale = 0; y_scale < scale; y_scale++)
					{
						if (y_pixel + y_scale < 0 || y_pixel + y_scale >= height)
							continue;
						for (int x_scale = 0; x_scale < scale; x_scale++)
						{
							if (x_pixel + x_scale < 0 || x_pixel + x_scale >= width)
								continue;
							if (c == replaceColor)
								c = color;
							game.getMain().setPixel(c, (x_pixel + x_scale), (y_pixel + y_scale));
						}
					}
				}
			}
		}
	}

	public void renderSprite(Sprite sprite, int x, int y, int maxx, int maxy, int minx, int miny)
	{
		if (sprite == null)
		{
			throw new NullPointerException();
		}
		for (int i = 0; i < sprite.height; i++)
		{
			for (int j = 0; j < sprite.width; j++)
			{
				if (i > height)
					return;
				if (j > width)
					return;
				if ((j + x) >= maxx)
					break;
				if ((i + y) >= maxy)
					break;
				if ((j + x) <= minx)
					continue;
				if ((i + y) <= miny)
					break;
				int c = sprite.pixels[j + i * sprite.width];
				{
					if (c != 0 && c != 0x00ffffff)
					{
						int pCol = game.getMain().getPixels()[(j + x) + (i + y) * width];
						int alpha = getAlpha(c);
						if (alpha == 128)
							c = blend(c, pCol);
						render(j + x, i + y, c);
					}
				}
			}
		}
	}

	public void renderTransparentSprite(Sprite sprite, double x, double y)
	{
		renderSprite(
				Sprite.combine(sprite,
						Sprite.cut((int) x, (int) y, sprite.getWidth(), sprite.getHeight(), new Sprite(game.getMain().getPixels(), width, height))),
				x, y);
	}
	/**
	 * 
	 * Method adapted from
	 * <p>
	 * See <a href=
	 * "http://www.java2s.com/Code/Java/2D-Graphics-GUI/Blendtwocolors.htm">http://www.java2s.com/Code/Java/2D-Graphics-GUI/Blendtwocolors.htm</a>
	 * 
	 * @author Cameron Behar
	 * 
	 */
	public static int blend(int c0, int c1)
	{
		double r = 0.5d * getRed(c0) + 0.5d * getRed(c1);
		double g = 0.5d * getGreen(c0) + 0.5d * getGreen(c1);
		double b = 0.5d * getBlue(c0) + 0.5d * getBlue(c1);
		double a = getMax(getAlpha(c0), getAlpha(c1));

		return getColor((int) r, (int) g, (int) b, (int) a);
	}
	
	private static double getMax(double i, double j)
	{
		if (i == j)
			return j;
		if (i > j)
			return i;
		else
			return j;
	}
	
//	private static double getMin(double i, double j)
//	{
//		if (i == j)
//			return j;
//		if (i < j)
//			return i;
//		else
//			return j;
//	}

	public void fillRect(double x, double y, double xx, double yy, int c, double maxx, double maxy, double minx, double miny)
	{
		for (int i = 0; i < xx; i++)
		{
			for (int j = 0; j < yy; j++)
			{
				if (j >= height)
					break;
				if (i >= width)
					break;
				if ((i + x) >= maxx)
					break;
				if ((j + y) >= maxy)
					break;
				if ((i + x) < minx)
					continue;
				if ((j + y) < miny)
					continue;
				render(i + x, j + y, c);
			}
		}
	}

	public void fillRect(double x, double y, double xx, double yy, int c)
	{
		fillRect(x, y, xx, yy, c, width, height, 0, 0);
	}
	
	public void drawRect(double x, double y, double w, double h, double thickness, int c)
	{
		if (thickness == 0)
			return;

		// LU - RU
		fillRect(x, y, w, thickness, c);
		// RU-DR
		fillRect(x + w - thickness, y + thickness, thickness, h - 2 * thickness, c);
		// LD-DR
		fillRect(x, y + h - thickness, w, thickness, c);
		// LU-DL
		fillRect(x, y + thickness, thickness, h - 2 * thickness, c);
	}

	public void drawCircle(double x, double y, double radius, int color)
	{
		for (int i = 0; i < 360; i += 1)
		{
			double angle = i;
			int x1 = (int) (radius * Math.cos(angle * Math.PI / 180));
			int y1 = (int) (radius * Math.sin(angle * Math.PI / 180));
			render(x + x1, y + y1, color);
			
		}
	}

	/**
	 * @author https://stackoverflow.com/questions/8113629/simplified-bresenhams-line-algorithm-what-does-it-exactly-do
	 * @param screen
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param color
	 */
	public void drawLine(double x1, double y1, double x2, double y2, int color)
	{
		int dx = (int) Math.abs(x2 - x1);
		int dy = (int) Math.abs(y2 - y1);

		int sx = (x1 < x2) ? 1 : -1;
		int sy = (y1 < y2) ? 1 : -1;

		int err = dx - dy;

		while (true)
		{
			render(x1, y1, color);

			if (x1 == x2 && y1 == y2)
			{
				break;
			}

			int e2 = 2 * err;

			if (e2 > -dy)
			{
				err = err - dy;
				x1 = x1 + sx;
			}

			if (e2 < dx)
			{
				err = err + dx;
				y1 = y1 + sy;
			}
		}
	}

	public void renderSprite(Sprite sprite, double x, double y)
	{
		renderSprite(sprite, (int) x, (int) y, game.getWidth(), game.getHeight(), -1, -1);
	}

	/**
	 * 
	 * @param screen
	 * @param sprite - Sprite sheet for textures
	 * @param x Where on screen should sprite render on X coordinate
	 * @param y Where on screen should sprite render on Y coordinate
	 * @param size Size of invidiual sprites
	 * @param indexX
	 * @param indexY
	 */
	public void renderSprite(Sprite sprite, double x, double y, int size, int indexX, int indexY)
	{
		//FIXME:Rendering was throwing ArrayIndexOutOfBoundsException... Temp fixed with try catch... Not good... lagging real bad
		int x_tile = indexX % size;
		int y_tile = indexY;
		
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{/*
				if ((x + i) >= screen.width)
					return;
				if ((y) >= screen.height)
					return;
				if ((x + i) < 0)
					return;
				if ((y) < 0)
					return;*/
				if ((int) (j + y) >= height || ((x_tile * size + i) + (y_tile * size + j) * sprite.width) >= width * height)
					break;
				try
				{
					render((int) (x + i), (int) (y + j), sprite.pixels[(x_tile * size + i) + (y_tile * size + j) * sprite.width]);
				} catch (ArrayIndexOutOfBoundsException ex)
				{
//					continue;
//					ex.printStackTrace();
				}
			}
		}
//		game.font.render("" + x_tile, screen, (int) x + 8, (int) y + 22, 1);
//		game.font.render("" + y_tile, screen, (int) x + 8, (int) y + 10, 1);
	}
	
	public void renderSprite(Sprite sprite, Vec2 loc)
	{
		renderSprite(sprite, loc.getX(), loc.getY());
	}
	
	public void renderSpriteCentered(Sprite sprite, Vec2 loc)
	{
		renderSprite(sprite, loc.getX() - sprite.getWidth() / 2, loc.getY() - sprite.getHeight() / 2);
	}

	public void render(double lx, double ly, int c)
	{
		if (c != 0 && c != 0x00ffffff)
		{
			int x = (int) lx;
			int y = (int) ly;
			if (x < 0)
				return;
			if (y < 0)
				return;
			if (x >= width)
				return;
			if (y >= height)
				return;
			if ((x + y * width) > width * height)
				return;
			int pCol = game.getMain().getPixels()[x + y * width];
			int alpha = (c >> 24) & 0xff;
			if (alpha == 128)
				c = blend(c, pCol);
			game.getMain().setPixel(c, x, y);
		}
	}
}
