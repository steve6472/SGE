package com.steve6472.sge.gfx;

import static com.steve6472.sge.main.Util.printf;
import static com.steve6472.sge.main.Util.printfd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.steve6472.sge.main.SGEMain;

public class Sprite implements Cloneable, Serializable
{
	private static final long serialVersionUID = 6642418612885176692L;
	
	public String path;
	public int width;
	public int height;
	public int[] pixels;
	public String stringId = "";
	static List<String> loadedPaths = new ArrayList<String>();
	
	public static boolean loadFromExternal = false;
	public static boolean listPaths = true;

	public Sprite(int w, int h)
	{
		this.width = w;
		this.height = h;
		pixels = new int[w * h];
	}

	public Sprite(int[] pixels, int w, int h)
	{
		this.width = w;
		this.height = h;
		this.pixels = pixels;
	}
	
	/**
	 * Returns copy of sprite
	 * @param sprite
	 */
	public Sprite(Sprite sprite)
	{
		this.width = sprite.width;
		this.height = sprite.height;
		this.pixels = new int[sprite.pixels.length];
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = sprite.pixels[i];
		}
	}

	public Sprite(String path)
	{
		boolean dupe = false;

		if (!path.startsWith("/"))
		{
			path = "/" + path;
		}
		if (loadFromExternal)
			path = "res/textures" + path;
		else
			path = "/textures" + path;

		if (listPaths)
		{
			for (String s : loadedPaths)
			{
				if (s.equals(path))
				{
					dupe = true;
					break;
				}
			}
		}
		if (dupe)
		{
			printfd("Creating new sprite from resource: " + path);
		} else
		{
			printf("Creating new sprite from resource: " + path);
		}
		if (listPaths) loadedPaths.add(path);
		
		if (loadFromExternal)
		{
			Sprite s = new Sprite(new File(path));
			this.pixels = s.pixels;
			this.width = s.width;
			this.height = s.height;
			return;
		}

		// if (!path.endsWith(".png"))
		// path = path + ".png";
		BufferedImage img = /* Utility.getErrorImage() */null;
		try
		{
			img = ImageIO.read(SGEMain.class.getResourceAsStream(path));
		} catch (IOException e)
		{
			// e.printStackTrace();
			System.err.println("Setting error image");
		}

		this.path = path;
		this.width = img.getWidth();
		this.height = img.getHeight();
//		pixels = new int[width * height];
//		for (int i = 0; i < width; i++)
//		{
//			for (int j = 0; j < height; j++)
//			{
//				pixels[i + j * width] = new Color(img.getRGB(i, j), true).getRGB();
//			}
//		}
		 pixels = img.getRGB(0, 0, width, height, null, 0, width);
	}

	public Sprite(Class<?> clazz, String path)
	{
		printf("Creating new sprite from resource: " + path);
		BufferedImage img = /* Utility.getErrorImage() */null;
		try
		{
			img = ImageIO.read(clazz.getResourceAsStream(path));
		} catch (IOException e)
		{
			// e.printStackTrace();
			printfd("Setting error image");
		}

		this.path = path;
		this.width = img.getWidth();
		this.height = img.getHeight();
		pixels = img.getRGB(0, 0, width, height, null, 0, width);
	}
	
	public static boolean printPath = false;
	
	public Sprite(File f)
	{
		Sprite s = new Sprite(new int[] {0}, 1, 1);
		try
		{
			s = new Sprite(ImageIO.read(f));
			if (printPath)
				printf("Creating new sprite from resource: " + f.getPath());
		} catch (IOException e)
		{
			System.err.println("Can't load file from " + f.getPath());
		}
		this.pixels = s.pixels;
		this.width = s.width;
		this.height = s.height;
	}
	
	public void setPixel(int x, int y, int color)
	{
		pixels[x + y * width] = color;
	}

	public Sprite(BufferedImage img)
	{
		this.width = img.getWidth();
		this.height = img.getHeight();
		pixels = img.getRGB(0, 0, width, height, null, 0, width);
	}
	
	/**
	 * 
	 * @param s1 - Overlay
	 * @param s2 - Background
	 * @return
	 */
	public static Sprite combine(Sprite s1, Sprite s2)
	{
		BufferedImage combined = new BufferedImage(s2.getWidth(), s2.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics g = combined.getGraphics();
		g.drawImage(s2.toBufferedImage(), 0, 0, null);
		g.drawImage(s1.toBufferedImage(), 0, 0, null);
		g.dispose();
		return new Sprite(combined);
	}

	public static Sprite multiplySize(Sprite s, int m)
	{
		Sprite t = new Sprite(s.getWidth() * m, s.getHeight() * m);

		for (int i = 0; i < t.getWidth(); i++)
		{
			for (int j = 0; j < t.getHeight(); j++)
			{
				t.pixels[i + j * t.getWidth()] = s.pixels[(i / m) + (j / m) * s.getWidth()];
			}
		}
		
		return t;
	}

	/**
	 * Funny name.. I know... but Iam too bad to create normal name for the method
	 * @param s Sprite to make smaller
	 * @param m How many times smaller the sprite should be
	 * @return new Sprite (Takes every m pixel)
	 */
	public static Sprite makeTheSpriteSmaller(Sprite s, int m)
	{
		Sprite t = new Sprite(s.getWidth() / m, s.getHeight() / m);

		for (int i = 0; i < t.getWidth(); i++)
		{
			for (int j = 0; j < t.getHeight(); j++)
			{
				t.pixels[i + j * t.getWidth()] = s.pixels[(i * m) + (j * m) * s.getWidth()];
			}
		}

		return t;
	}

	/**
	 * Overrides existing pixel data with new color
	 * @param color
	 * @return 
	 */
	public Sprite fill(int color)
	{
		for (int i = 0; i < getWidth(); i++)
		{
			for (int j = 0; j < getHeight(); j++)
			{
				pixels[i + j * getWidth()] = color;
			}
		}
		return this;
	}

	/**
	 * Cut form the sprite
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param s
	 * @return new Sprite
	 */
	public static Sprite cut(int x, int y, int w, int h, Sprite s)
	{
		Sprite t = new Sprite(w, h);
		for (int i = 0; i < w; i++)
		{
			for (int j = 0; j < h; j++)
			{
				t.pixels[i + j * w] = s.pixels[(x + i) + (y + j) * s.width];
			}
		}
		return t;
	}
	
	/**
	 * Insert One sprite to second sprite
	 * @param sprite
	 * @param spriteToInsert
	 * @param x
	 * @param y
	 * @return
	 */
	public static Sprite insert(Sprite sprite, Sprite spriteToInsert, int x, int y)
	{
		Sprite s = new Sprite(sprite.pixels, sprite.getWidth(), sprite.getHeight());
		//If out of bounds - return original sprite (background)
		if (spriteToInsert.getWidth() + x > s.getWidth())
			return sprite;
		if (spriteToInsert.getHeight() + y > s. getHeight())
			return sprite;
		
		//Else "draw" the sprite
		for (int i = 0; i < spriteToInsert.getWidth(); i++)
		{
			for (int j = 0; j < spriteToInsert.getHeight(); j++)
			{
				s.pixels[(i + x) + (j + y) * s.getWidth()] = spriteToInsert.pixels[i + j * spriteToInsert.getWidth()];
			}
		}
		return s;
	}
	
	public void insert(Sprite sprite, int x, int y)
	{
		//If out of bounds - do nothing
		if (sprite.getWidth() + x > getWidth())
			return;
		if (sprite.getHeight() + y > getHeight())
			return;
		
		//Else "draw" the sprite
		for (int i = 0; i < sprite.getWidth(); i++)
		{
			for (int j = 0; j < sprite.getHeight(); j++)
			{
				pixels[(i + x) + (j + y) * getWidth()] = sprite.pixels[i + j * sprite.getWidth()];
			}
		}
	}

	@Override
	protected Sprite clone() throws CloneNotSupportedException
	{
		return (Sprite) super.clone();
	}

	public Sprite safeClone()
	{
		Sprite as = null;
		try
		{
			as = clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return as;
	}

	/*
	 * Setters
	 */

	/*
	 * Getters
	 */

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public BufferedImage toBufferedImage()
	{
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < getWidth(); i++)
		{
			for (int j = 0; j < getHeight(); j++)
			{
				img.setRGB(i, j, pixels[i + j * getWidth()]);
			}
		}
		return img;
	}
	
	/**
	 * <b>Creates only square sprites!!!</b>
	 * 
	 * @param sheet 
	 * @param indexX x coordinate of the sprite
	 * @param indexY y coordinate of the sprite
	 * @param size site of invidual sprite
	 * @param angle angle
	 * @return rotated sprite from sprite sheet
	 */
	public static Sprite rotate(Sprite sheet, int indexX, int indexY, int size, double angle)
	{
		return rotate(cut(indexX * size, indexY * size, size, size, sheet), angle);
	}
	
	/**
	 * May not be that effective but it's the only thing that works
	 * @param s Sprite to ratate
	 * @param angle
	 */
	public static Sprite rotate(Sprite s, double angle)
	{
		BufferedImage img = s.toBufferedImage();

		return new Sprite(rotateImage(img, angle));
	}

	private static BufferedImage rotateImage(BufferedImage originalImage, double degree)
	{
		int w = originalImage.getWidth();
		int h = originalImage.getHeight();
		double toRad = Math.toRadians(degree);
		int hPrime = (int) (w * Math.abs(Math.sin(toRad)) + h * Math.abs(Math.cos(toRad)));
		int wPrime = (int) (h * Math.abs(Math.sin(toRad)) + w * Math.abs(Math.cos(toRad)));

		BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = rotatedImage.createGraphics();
		g.translate(wPrime / 2, hPrime / 2);
		g.rotate(toRad);
		g.translate(-w / 2, -h / 2);
//		AffineTransform ag = g.getTransform();
//		AffineTransformOp op = new AffineTransformOp(ag, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(originalImage, 0, 0, null);
//		g.drawImage(op.filter(originalImage, null), 0, 0, null);
		g.dispose(); // release used resources before g is garbage-collected
		return rotatedImage;
	}
	
	public Sprite insertSprite(Sprite original, Sprite toInsert, int x, int y)
	{
		if (toInsert.pixels.length > original.pixels.length)
			return original;
		
		Sprite ret = new Sprite(original);
		
		for (int i = 0; i < toInsert.width; i++)
		{
			for (int j = 0; j < toInsert.width; j++)
			{
				ret.pixels[(i + x) + (j + y) * ret.width] = toInsert.pixels[i + j * toInsert.width];
			}
		}
		
		return ret;
	}
	
	/**
	 * 
	 * @param s Sprite to make transparent
	 * @param newTransparency new Transparent value (0-255)
	 * @return new Transparent sprite (Ignored hex: 0 & 0x00ffffff)
	 */
	public static Sprite makeTransparent(Sprite s, int newTransparency)
	{
		Sprite ret = new Sprite(s.getWidth(), s.getHeight());
		for (int j = 0; j < s.getWidth(); j++)
		{
			for (int k = 0; k < s.getHeight(); k++)
			{
				int p = s.pixels[j + k * s.getWidth()];
				if (p != 0 && p != 0x00ffffff)
				{
					ret.pixels[j + k * s.getWidth()] = new Color(new Color(p).getRed(), new Color(p).getGreen(), new Color(p).getBlue(),
							newTransparency).getRGB();
				}
			}
		}
		return ret;
	}
}
