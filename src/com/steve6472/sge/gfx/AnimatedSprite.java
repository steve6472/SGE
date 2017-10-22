package com.steve6472.sge.gfx;

import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements Cloneable
{
	private static final long serialVersionUID = 1L;
	
	private int frames = 0;
	/**
	 * Current tick
	 * Resets if tick == changeTime
	 */
	private int tick = 0;
	
	/**
	 * Adds when tick == changeTime
	 * if (currentFrame > frames) -> sets to 0
	 */
	private int currentFrame = 0;
	/**
	 * In Ticks (Tick should be 60 times per 1 sec)
	 */
	private int changeTime = 60;

	/**
	 * Width = frames * height
	 * @param height
	 * @param frames
	 */
	public AnimatedSprite(int height, int frames)
	{
		super(frames * height, height);
		this.frames = frames;
	}

	public AnimatedSprite(int[] pixels, int height, int frames)
	{
		super(pixels, frames * height, height);
		this.frames = frames;
	}

	public AnimatedSprite(String path, int frames)
	{
		super(path);
		this.frames = frames;
	}

	public AnimatedSprite(BufferedImage img, int frames)
	{
		super(img);
		this.frames = frames;
	}

	public void multiplySize(int m)
	{
		Sprite.multiplySize(this, m);
	}

	public Sprite getFrame(int frame)
	{
		if (frame >= getFrames())
			return Sprite.cut(this.height * (getFrames() - 1), 0, this.height, this.height, this);
		return Sprite.cut(this.height * frame, 0, this.height, this.height, this);
	}
	
	public Sprite getCurrentFrame()
	{
		return getFrame(currentFrame);
	}
	
	public void render(Screen screen, double x, double y, int frame)
	{
		screen.renderSprite(this, x, y, this.height, frame, 0);
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
	
	@Override
	public AnimatedSprite clone() throws CloneNotSupportedException
	{
		return (AnimatedSprite) super.clone();
	}
	
	public AnimatedSprite safeClone()
	{
		AnimatedSprite as = null;
		try
		{
			as = clone();
		} catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return as;
	}

}
