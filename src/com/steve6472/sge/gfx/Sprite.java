package com.steve6472.sge.gfx;

import static com.steve6472.sge.main.Util.printf;
import static com.steve6472.sge.main.Util.printfd;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.steve6472.sge.main.SGEMain;

public class Sprite implements Serializable
{
	private static final long serialVersionUID = 6642418612885176692L;
	
	@Deprecated
	public int width;
	@Deprecated
	public int height;
	public int[] pixels;
	transient protected static List<String> loadedPaths = new ArrayList<String>();
	
	public static boolean listPaths = true;
	public static boolean printPath = false;

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
		if (sprite == null)
			return;
		
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.pixels = new int[sprite.pixels.length];
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = sprite.pixels[i];
		}
	}

	public Sprite(String path)
	{
		if (path == null)
			return;
		boolean dupe = false;

		if (!path.startsWith("/"))
		{
			path = "/" + path;
		}
		
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
		
		if (printPath)
			if (dupe)
			{
				printfd("Creating new sprite from resource: " + path);
			} else
			{
				printf("Creating new sprite from resource: " + path);
			}
		
		if (listPaths) loadedPaths.add(path);
		
		BufferedImage img = /* Utility.getErrorImage() */null;
		try
		{
			img = ImageIO.read(SGEMain.class.getResourceAsStream(path));
		} catch (IOException e)
		{
			// e.printStackTrace();
			System.err.println("Setting error image");
		}

		this.width = img.getWidth();
		this.height = img.getHeight();
		
		pixels = img.getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());
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

		this.width = img.getWidth();
		this.height = img.getHeight();
		pixels = img.getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());
	}
	
	public Sprite(File f)
	{
		if (f == null)
			return;
		
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
		this.width = s.getWidth();
		this.height = s.getHeight();
	}

	public Sprite(BufferedImage img)
	{
		if (img == null)
			return;
		
		this.width = img.getWidth();
		this.height = img.getHeight();
		pixels = img.getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());
	}
	
	public Sprite clone()
	{
		return new Sprite(pixels, getWidth(), getHeight());
	}

	/*
	 * Setters
	 */
	
	public void setPixel(int x, int y, int color)
	{
		pixels[x + y * getWidth()] = color;
	}

	/*
	 * Getters
	 */
	
	public int getPixel(int x, int y)
	{
		return pixels[x + y * getWidth()];
	}
	
	public int getPixel(int i)
	{
		return pixels[i];
	}

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
}
