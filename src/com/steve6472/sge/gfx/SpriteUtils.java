/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 25. 2. 2018
* Project: SGE
*
***********************/

package com.steve6472.sge.gfx;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SpriteUtils
{
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
	public static Sprite fill(Sprite sprite, int color)
	{
		for (int i = 0; i < sprite.getWidth(); i++)
		{
			for (int j = 0; j < sprite.getHeight(); j++)
			{
				sprite.pixels[i + j * sprite.getWidth()] = color;
			}
		}
		return sprite;
	}
	
	public Sprite insertSprite(Sprite original, Sprite toInsert, int x, int y)
	{
		if (toInsert.pixels.length > original.pixels.length)
			return original;
		
		Sprite ret = new Sprite(original);
		
		for (int i = 0; i < toInsert.getWidth(); i++)
		{
			for (int j = 0; j < toInsert.getWidth(); j++)
			{
				ret.pixels[(i + x) + (j + y) * ret.getWidth()] = toInsert.pixels[i + j * toInsert.getWidth()];
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
					ret.pixels[j + k * s.getWidth()] = Screen.getColor(Screen.getRed(p), Screen.getGreen(p), Screen.getBlue(p), newTransparency);
				}
			}
		}
		return ret;
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
				t.pixels[i + j * w] = s.pixels[(x + i) + (y + j) * s.getWidth()];
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
}
