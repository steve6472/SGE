package com.steve6472.sge.main.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;

public class Blur implements Serializable
{
	private static final long serialVersionUID = 210013109000097622L;
	List<Vec2> blur = new ArrayList<Vec2>();
	private final int blurSize;
	private final int[] blurLoc;
	private final int[] trans;
	private Sprite[] transparentSprites;
	
	public Blur(int blurSize, int[] blurLoc, int[] transparent, Sprite sprite)
	{
		this.blurSize = blurSize;
		this.blurLoc = blurLoc;
		
		if (blurLoc.length != transparent.length)
		{
			throw new IllegalArgumentException("blurLoc & transparent doesn't have same amount of variables");
		}
		
		transparentSprites = new Sprite[transparent.length];
		
		trans = transparent;
		
		for (int i = 0; i < transparent.length; i++)
		{
			transparentSprites[i] = Sprite.makeTransparent(sprite.safeClone(), transparent[i]);
		}
	}
	
	/**
	 * Fast setup
	 * @param sprite
	 */
	public Blur(Sprite sprite)
	{
		int[] transparent = new int[] {16, 32};
		this.blurSize = 16;
		this.blurLoc = new int[] {13, 14};
		
		trans = transparent;
		
		transparentSprites = new Sprite[transparent.length];
		
		for (int i = 0; i < transparent.length; i++)
		{
			transparentSprites[i] = Sprite.makeTransparent(sprite.safeClone(), transparent[i]);
		}
	}
	
	public void tick(Vec2 newLoc)
	{
		blur.add(new Vec2(newLoc.getX(), newLoc.getY()));
		
		if (blur.size() > blurSize)
		{
			blur.remove(0);
		}
	}

	public void renderBlur(Screen screen)
	{
		for (int j = 0; j < blurSize; j++)
		{
			for (int i = 0; i < blurLoc.length; i++)
			{
				if (blur.size() > blurLoc[i])
				{
					if (j == blurLoc[i])
					{
						screen.renderTransparentSprite(transparentSprites[i], blur.get(j).getX(), blur.get(j).getY());
					}
				}
			}
		}
	}
	
	public void setSprite(Sprite sprite)
	{
		for (int i = 0; i < trans.length; i++)
		{
			transparentSprites[i] = Sprite.makeTransparent(sprite.safeClone(), trans[i]);
		}
	}
	
}
