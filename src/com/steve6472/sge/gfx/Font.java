package com.steve6472.sge.gfx;

import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class Font
{
	BaseGame game;
	
	public Font(BaseGame game)
	{
		this.game = game;
	}
	// 32 spaces: "                                ";
	// 64 Chars per line
	public static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ&♥|█  abcdefghijklmnopqrstuvwxyz      " + "0123456789.,:;'\"!?$%()-=+/><_#§\\                              ";
	public static final String galactic_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ><.                                   " + "0123456789";
	public static final String special = "0123456789AB&_♥C                                                ";

	/*
	 * One line text
	 */
	
	static int WHITE = 0xffffffff;
	static int SHADOW = 0xff383838;

	public void render(String msg, Screen screen, int x, int y, int scale)
	{
		if (game.fontShadow())
			renderColored(msg, screen, chars, x + (1 * scale), y + (1 * scale), scale, 2, WHITE, SHADOW);
		renderFont(chars, x, y, scale, 2, msg, screen);
	}

	public void renderColoredWithShadow(String msg, Screen screen, int x, int y, int scale, int colorToReplace, int color)
	{
		if (game.fontShadow())
			renderColored(msg, screen, chars, x + (1 * scale), y + (1 * scale), scale, 2, WHITE, SHADOW);
		renderColoredFont(chars, x, y, scale, 2, colorToReplace, color, msg, screen);
	}

	public void renderColored(String msg, Screen screen, String chars, int x, int y, int scale, int pos, int colorToReplace, int color)
	{
		renderColoredFont(chars, x, y, scale, pos, colorToReplace, color, msg, screen);
	}

	public void renderColored(String msg, Screen screen, int x, int y, int scale, int colorToReplace, int color)
	{
		renderColoredFont(chars, x, y, scale, 2, colorToReplace, color, msg, screen);
	}

	public void renderGalactic(String msg, Screen screen, int x, int y, int scale)
	{
		renderFont(galactic_chars, x, y, scale, 0, msg, screen);
	}

	public void renderSpecial(String msg, Screen screen, int x, int y, int scale)
	{
		renderFont(special, x, y, scale, 4, msg, screen);
	}

	public void renderDark(String msg, Screen screen, int x, int y, int scale)
	{
		renderFont(chars, x, y, scale, 5, msg, screen);
	}

	/*
	 * Multilined text
	 */

	public void renderArray(Screen screen, int x, int y, int scale, String... msg)
	{
		if (game.fontShadow())
			renderColoredFontArray(chars, x + (1 * scale), y + (1 * scale), scale, WHITE, SHADOW, screen, 2, msg);
		renderFontArray(chars, x, y, scale, screen, 2, msg);
	}

	public void renderColoredArray(Screen screen, int x, int y, int scale, int colorToReplace, int color, String... msg)
	{
		if (game.fontShadow())
			renderColoredFontArray(chars, x + (1 * scale), y + (1 * scale), scale, WHITE, SHADOW, screen, 2, msg);
		renderColoredFontArray(chars, x, y, scale, colorToReplace, color, screen, 2, msg);
	}

	public void renderGalacticArray(Screen screen, int x, int y, int scale, String... msg)
	{
		if (game.fontShadow())
			renderColoredFontArray(galactic_chars, x + (1 * scale), y + (1 * scale), scale, WHITE, SHADOW, screen, 0, msg);
		renderFontArray(galactic_chars, x, y, scale, screen, 0, msg);
	}

	public void renderSpecialArray(Screen screen, int x, int y, int scale, String... msg)
	{
		renderFontArray(special, x, y, scale, screen, 4, msg);
	}

	public void renderDarkArray(Screen screen, int x, int y, int scale, String... msg)
	{
		renderFontArray(chars, x, y, scale, screen, 5, msg);
	}

	/*
	 * Render Methods
	 */

	protected void renderFont(String arr, int x, int y, int scale, int line, String msg, Screen screen)
	{
		if (msg == null)
			return;
		if (!game.smallFont())
			msg = msg.toUpperCase();

		for (int i = 0; i < msg.length(); i++)
		{
			int char_index = arr.indexOf(msg.charAt(i));
			if (char_index >= 0)
				screen.render(x + (i * scale * 8), y, (char_index + line * 64), scale);
		}
	}

	protected void renderColoredFont(String arr, int x, int y, int scale, int line, int colorToReplace, int color, String msg, Screen screen)
	{
		if (msg == null)
			return;
		if (!game.smallFont())
			msg = msg.toUpperCase();

		for (int i = 0; i < msg.length(); i++)
		{
			int char_index = arr.indexOf(msg.charAt(i));
			if (char_index >= 0)
				screen.render(x + (i * scale * 8), y, (char_index + line * 64), scale, colorToReplace, color);
		}
	}

	protected void renderFontArray(String arr, int x, int y, int scale, Screen screen, int line, String... msg)
	{
		if (msg == null)
			return;
		for (int ii = 0; ii < msg.length; ii++)
		{
			if (msg[ii] == null)
				return;
			String m = msg[ii].toUpperCase();

			for (int i = 0; i < m.length(); i++)
			{
				int char_index = arr.indexOf(m.charAt(i));
				if (char_index >= 0)
					screen.render(x + (i * scale * 8), y + ii * scale * 8, (char_index + line * 64), scale);
			}
		}
	}

	protected void renderColoredFontArray(String arr, int x, int y, int scale, int colorToReplace, int color, Screen screen, int line,
			String... msg)
	{
		if (msg == null)
			return;
		for (int ii = 0; ii < msg.length; ii++)
		{
			if (msg[ii] == null)
				return;
			String m = msg[ii].toUpperCase();

			for (int i = 0; i < m.length(); i++)
			{
				int char_index = arr.indexOf(m.charAt(i));
				if (char_index >= 0)
					screen.render(x + (i * scale * 8), y + ii * scale * 8, (char_index + line * 64), scale, colorToReplace, color);
			}
		}
	}
	
	public static Vec2 stringCenter(AABB recSize, String text, int fontSize)
	{
		return new Vec2(recSize.from.getX() + (recSize.getWidth() / 2) - ((text.length() * (8 * fontSize)) / 2), recSize.from.getY() + (recSize.getHeight() / 2) - 3);
	}
	
	public static String trim(String text, int width, int fontSize)
	{
		String r = "";
		
		int textWidth = text.length() * (8 * fontSize);
		
		int trim = (textWidth - (textWidth - width)) / (8 * fontSize);
		
		r = text.substring(0, Math.min(text.length(), trim > 0 ? trim : text.length()));
		
		return r;
	}
	
	/**
	 * Trimming String form front
	 * @param text
	 * @param width
	 * @param fontSize
	 * @return
	 */
	public static String trimFront(String text, int width, int fontSize)
	{
		String r = text;
		
		int textWidth = text.length() * (8 * fontSize);
		
		int spaceForText = width / (4 * fontSize);
		
		int trim = ((textWidth - spaceForText) / (8 * fontSize));
		
		r = text.substring(Math.max(trim - spaceForText + 1, 0), text.length());
		
		return r;
	}
}
