package com.steve6472.sge.gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimatedSpriteV2 extends Sprite implements Cloneable
{
	private static final long serialVersionUID = 1L;
	private int frames = 0;
	/**
	 * Current tick Resets if tick == changeTime
	 */
	private int tick = 0;

	/**
	 * Adds when tick == changeTime if (currentFrame > frames) -> sets to 0
	 */
	private int currentFrame = 0;
	/**
	 * In Ticks (Tick should be 60 times per 1 sec)
	 */
	private int changeTime = 60;

	private Sprite[] sprites;

	// List<String> s = new ArrayList<String>();
	// s.forEach(i ->
	// {
	//
	// });

	/**
	 * Width = frames * height
	 * 
	 * @param height
	 * @param frames
	 */
	public AnimatedSpriteV2(int frameHeight, int frameWidth, int frames)
	{
		super(frames * frameWidth, frameHeight);
		init(frames);
	}

	public AnimatedSpriteV2(int[] pixels, int frameHeight, int frameWidth, int frames)
	{
		super(pixels, frames * frameHeight, frameWidth);
		init(frames);
	}

	public AnimatedSpriteV2(String path, int frames)
	{
		super(path);
		init(frames);
	}

	public AnimatedSpriteV2(BufferedImage img, int frames)
	{
		super(img);
		init(frames);
	}

	protected void init(int frames)
	{
		this.frames = frames;

		sprites = new Sprite[frames];

		int w = getWidth() / frames;

		for (int f = 0; f < frames; f++)
		{
			sprites[f] = new Sprite(w, getHeight());
			for (int i = 0; i < getHeight(); i++)
			{
				for (int j = 0; j < w; j++)
				{
					sprites[f].pixels[j + i * w] = pixels[(j + (w * f)) + (i * frames) * (w)];
				}
			}
		}

	}

	public void generateDebug(boolean exitAfterFinish)
	{
		int w = getWidth() / frames;
		
		BufferedImage img = new BufferedImage(w, getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int f = 0; f < frames; f++)
		{
			for (int i = 0; i < getHeight(); i++)
			{
				for (int j = 0; j < w; j++)
				{
					img.setRGB(j, i, sprites[f].pixels[j + i * w]);
				}
			}
			try
			{
				ImageIO.write(img, "PNG", new File("D:\\Java-Eclipse-Sync\\MagicalKey\\res\\debug\\" + f + ".png"));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if (exitAfterFinish)
			System.exit(0);
	}

	public Sprite getFrame(int frame)
	{
		return sprites[frame];
	}

	public Sprite getCurrentFrame()
	{
		return getFrame(currentFrame);
	}

	public int getCurrentFrameIndex()
	{
		return currentFrame;
	}

	/**
	 * Automatic changing of sprites
	 */
	public void tick()
	{
		tick++;
		if (tick == changeTime)
		{
			tick = 0;
			currentFrame++;
			if (currentFrame >= frames)
			{
				currentFrame = 0;
			}
		}
	}

	public void setChangeTime(int changeTime)
	{
		this.changeTime = changeTime;
	}

	public int getChangeTime()
	{
		return changeTime;
	}

	public int getFrames()
	{
		return frames;
	}

}
