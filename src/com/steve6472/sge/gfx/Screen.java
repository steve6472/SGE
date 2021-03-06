package com.steve6472.sge.gfx;

import java.io.Serializable;

import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.Util;
import com.steve6472.sge.main.game.Vec2;

public class Screen implements Serializable
{
	private static final long serialVersionUID = -5208705383851347246L;

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
	
	public static boolean isIgnoredColor(int c)
	{
		return c != 0 && c != 0x00ffffff;
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
	
	public static int getColor(int gray)
	{
		return getColor(gray, gray, gray, 255);
	}
	
	public static int getColor(int gray, float red, float green, float blue)
	{
		return getColor((int) (gray * red), (int) (gray * green), (int) (gray * blue));
	}
	
	public static int getColor(int gray, float red, float green, float blue, boolean halfTransparent)
	{
		return getColor((int) (gray * red), (int) (gray * green), (int) (gray * blue), halfTransparent ? 0x80 : 0xff);
	}
	
	public static int getColor(double gray)
	{
		return getColor((int) gray);
	}
	
	public static float[] getColors(int color)
	{
		return new float[] { (float) getRed(color) / 255f, (float) getGreen(color) / 255f, (float) getBlue(color) / 255f, (float) getAlpha(color) / 255f };
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
		int tile_offset = (x_tile << 3) + (y_tile << 3) * sheet.getWidth();
		for (int y = 0; y < 8; y++)
		{
			int y_pixel = y + y_pos + (y * scale_map) - ((scale_map << 3) / 2);
			for (int x = 0; x < 8; x++)
			{
				int x_pixel = x + x_pos + (x * scale_map) - ((scale_map << 3) / 2);
				int c = (sheet.pixels[x + y * sheet.getWidth() + tile_offset]);
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
		int tile_offset = (x_tile << 3) + (y_tile << 3) * sheet.getWidth();
		for (int y = 0; y < 8; y++)
		{
			int y_pixel = y + y_pos + (y * scale_map) - ((scale_map << 3) / 2);
			for (int x = 0; x < 8; x++)
			{
				int x_pixel = x + x_pos + (x * scale_map) - ((scale_map << 3) / 2);
				int c = (sheet.pixels[x + y * sheet.getWidth() + tile_offset]);
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

	public void renderSprite(Sprite sprite, int x, int y, int maxX, int maxY, int minX, int minY)
	{
		if (sprite == null)
		{
			throw new NullPointerException();
		}
		
		Util.fillRect(0, 0, sprite.getWidth(), sprite.getHeight(), maxX, maxY, minX, minY, (i, j) -> 
		{
			int c = sprite.pixels[i + j * sprite.getWidth()];
			int pCol = game.getMain().getPixels()[i + j * width];
			int alpha = getAlpha(c);
			if (alpha == 128) c = blend(c, pCol);
			render(i + x, j + y, c);
		});
	}

	public void renderTransparentSprite(Sprite sprite, double x, double y)
	{
		renderSprite(
				SpriteUtils.combine(sprite,
						SpriteUtils.cut((int) x, (int) y, sprite.getWidth(), sprite.getHeight(), new Sprite(game.getMain().getPixels(), width, height))),
				x, y);
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
		Util.fillRect(indexX * size, indexY * size, size, size, width, height, 0, 0, (i, j) ->
		{
			int c = sprite.pixels[i + j * sprite.getWidth()];
			int pCol = game.getMain().getPixels()[i + j * width];
			int alpha = getAlpha(c);
			if (alpha == 128) c = blend(c, pCol);
			render(i - indexX * size + x, j - indexY * size + y, c);
		});
	}
	
	public void renderSprite(Sprite sprite, Vec2 loc)
	{
		renderSprite(sprite, loc.getX(), loc.getY());
	}
	
	/**
	 * 
	 * @param sprite
	 * @param loc
	 */
	public void renderSpriteCentered(Sprite sprite, Vec2 loc)
	{
		renderSprite(sprite, loc.getX() - sprite.getWidth() / 2, loc.getY() - sprite.getHeight() / 2);
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
		double a = Math.max(getAlpha(c0), getAlpha(c1));

		return getColor((int) r, (int) g, (int) b, (int) a);
	}
	
/*
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
	}*/
	
	
	/*
	 * Int variations
	 */

	public void fillRect(int x, int y, int width, int height, int color, int maxX, int maxY, int minX, int minY)
	{
		Util.fillRect(x, y, width, height, maxX, maxY, minX, minY, (X, Y) -> render(X, Y, color));
	}

	public void fillRect(int x, int y, int width, int height, int color)
	{
		fillRect(x, y, width, height, color, this.width, this.height, 0, 0);
	}

	/*
	 * Double variations
	 */

	public void fillRect(double x, double y, double width, double height, int color, double maxX, double maxY, double minX, double minY)
	{
		fillRect((int) x, (int) y, (int) width, (int) height, color, (int) maxX, (int) maxY, (int) minX, (int) minY);
	}

	public void fillRect(double x, double y, double width, double height, int color)
	{
		fillRect(x, y, width, height, color, this.width, this.height, 0, 0);
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
	
	public void drawRect(double x, double y, double w, double h, double thickness, int c, double maxX, double maxY, double minX, double minY)
	{
		if (thickness == 0)
			return;

		// LU - RU
		fillRect(x, y, w, thickness, c, maxX, maxY, minX, minY);
		// RU-DR
		fillRect(x + w - thickness, y + thickness, thickness, h - 2 * thickness, c, maxX, maxY, minX, minY);
		// LD-DR
		fillRect(x, y + h - thickness, w, thickness, c, maxX, maxY, minX, minY);
		// LU-DL
		fillRect(x, y + thickness, thickness, h - 2 * thickness, c, maxX, maxY, minX, minY);
	}

	public void drawCircle(double x, double y, double radius, int color)
	{
		drawCircle(x, y, radius, 0, 360, 1, color);
	}

	public void drawCircle(double x, double y, double radius, double from, double to, double add, int color)
	{
		for (double i = from; i < to; i += add)
		{
			double angle = i;
			double x1 = radius * Math.cos(angle * Math.PI / 180);
			double y1 = radius * Math.sin(angle * Math.PI / 180);
			render(x + x1, y + y1, color);
			
		}
	}

	public void drawCircle(double x, double y, double radius, double from, double to, double angleOffset, double add, int color)
	{
		for (double i = from; i < to; i += add)
		{
			double angle = i + angleOffset;
			double x1 = radius * Math.cos(angle * Math.PI / 180);
			double y1 = radius * Math.sin(angle * Math.PI / 180);
			render(x + x1, y + y1, color);
			
		}
	}
	
	/**
	 * Custom created line draw method using sin and cos
	 * (Prob more laggy than the disabled one)
	 * Doesn't have clear lines
	 * 
	 * @param x1 - starting point X
	 * @param y1 - starting point Y
	 * @param x2 - ending point X
	 * @param y2 - ending point Y
	 * @param color - color of the line
	 * @param maxx - limit max X
	 * @param maxy - limit max Y
	 * @param minx - limit min X
	 * @param miny - limit min Y
	 */
	public void drawLine(double x1, double y1, double x2, double y2, int color, int maxx, int maxy, int minx, int miny)
	{
		int a = (int) (y2 - y1);
		int b = (int) (x2 - x1);
		int c = (int) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
		
		double ang = Math.toRadians(Util.countAngle(x1, y1, x2, y2) - 90);
		
		double cos = Math.cos(ang);
		double sin = Math.sin(ang);
		
		for (int i = 0; i < c; i++)
		{
			double X = x1 + cos * i;
			double Y = y1 + sin * i;
			if (!(X < minx || X >= maxx || Y < miny || Y >= maxy))
				render(X, Y, color);
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
		drawLine(x1, y1, x2, y2, color, width, height, 0, 0);
		
		/*
		int dx = (int) Math.abs(x2 - x1);
		int dy = (int) Math.abs(y2 - y1);

		int sx = (x1 < x2) ? 1 : -1;
		int sy = (y1 < y2) ? 1 : -1;

		int err = dx - dy;
		
		int iterations = 0;

		while (true)
		{
			iterations++;
			if (iterations >= width * height)
				return;
			
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
		}*/
	}
	/*
	public void drawLine(double x1, double y1, double x2, double y2, int color, int maxx, int maxy, int minx, int miny)
	{
		int X1 = (int) x1;
		int Y1 = (int) y1;
		int X2 = (int) x2;
		int Y2 = (int) y2;
		
		int dx = (int) Math.abs(X2 - X1);
		int dy = (int) Math.abs(Y2 - Y1);

		int sx = (X1 < X2) ? 1 : -1;
		int sy = (Y1 < Y2) ? 1 : -1;

		int err = dx - dy;

		while (true)
		{
			if ((x1 < minx || x1 >= maxx || y1 < miny || y1 >= maxy))
				return;
			
			render(X1, Y1, color);

			if (X1 == X2 && Y1 == Y2)
			{
				break;
			}

			int e2 = 2 * err;

			if (e2 > -dy)
			{
				err = err - dy;
				X1 = X1 + sx;
			}

			if (e2 < dx)
			{
				err = err + dx;
				Y1 = Y1 + sy;
			}
		}
	}*/

	
	/**
	 * 
	 * @param rot {@code 0} = 0
	 * @param rot {@code 1} = 90 degree
	 * @param rot {@code 2} = 180 degree
	 * @param rot {@code 3} = 270 degree
	 * @param rot {@code 4} = Flip 0
	 * @param rot {@code 5} = Flip 90 degree
	 * @param rot {@code 6} = Flip 180 degree
	 * @param rot {@code 7} = Flip 270 degree
	 * 
	 * 
	 * @param sprite - Sprite to render
	 * @param x - Sprite render location X
	 * @param y - Sprite render location Y
	 */
	public void renderRot(Sprite sprite, int x, int y, int rot)
	{
		switch (rot)
		{

		// Normal render
		default:
			for (int i = 0; i < sprite.getWidth(); i++)
			{
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					render(x + i, y + j, sprite.pixels[i + j * sprite.getWidth()]);
				}
			}
			break;

		case 1:
			// 90 Rot
			for (int i = 0; i < sprite.getWidth(); i++)
			{
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					render(x + (sprite.getWidth() - i - 1), y + j, sprite.pixels[j + i * sprite.getWidth()]);
				}
			}
			break;

		case 2:
			// 180 Rot
			for (int i = 0; i < sprite.getWidth(); i++)
			{
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					render(x + (sprite.getWidth() - i - 1), y + (sprite.getHeight() - j - 1), sprite.pixels[i + j * sprite.getWidth()]);
				}
			}
			break;

		case 3:
			// rot 270
			for (int i = 0; i < sprite.getWidth(); i++)
			{
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					render(x + i, y + (sprite.getHeight() - j - 1), sprite.pixels[j + i * sprite.getWidth()]);
				}
			}
			break;

		case 4:
			// Flip Horizontal
			for (int i = 0; i < sprite.getWidth(); i++)
			{
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					render(x + i, y + (sprite.getHeight() - j - 1), sprite.pixels[i + j * sprite.getWidth()]);
				}
			}
			break;

		case 5:
			// 90 + Flip horizontal
			for (int i = 0; i < sprite.getWidth(); i++)
			{
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					render(x + i, y + j, sprite.pixels[j + i * sprite.getWidth()]);
				}
			}
			break;

		case 6:
			// Flip Vertical (180 rot)
			for (int i = 0; i < sprite.getWidth(); i++)
			{
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					render(x + (sprite.getWidth() - i - 1), y + j, sprite.pixels[i + j * sprite.getWidth()]);
				}
			}
			break;

		case 7:
			// rot 270 (90 + flip)
			for (int i = 0; i < sprite.getWidth(); i++)
			{
				for (int j = 0; j < sprite.getHeight(); j++)
				{
					render(x + (sprite.getWidth() - i - 1), y + (sprite.getHeight() - j - 1), sprite.pixels[j + i * sprite.getWidth()]);
				}
			}
			break;
		}

	}
	
	public void render(Vec2 location, int c)
	{
		render(location.getX(), location.getY(), c);
	}

	public void render(double lx, double ly, int c)
	{
		if (isIgnoredColor(c))
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
			if (alpha == 0)
				return;
			if (alpha == 128)
				c = blend(c, pCol);
			game.getMain().setPixel(c, x, y);
		}
	}
}
