package com.steve6472.sge.gfx;

import java.io.Serializable;

import com.steve6472.sge.main.BaseGame;
import com.steve6472.sge.main.game.AABB;
import com.steve6472.sge.main.game.Vec2;

public class Font implements Serializable
{
	private static final long serialVersionUID = 8177570202100046359L;
	BaseGame game;
	
	public Font(BaseGame game)
	{
		this.game = game;
	}
	// 32 spaces: "                                ";
	// 64 Chars per line
	public static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ&♥|█* abcdefghijklmnopqrstuvwxyz      " + "0123456789.,:;'\"!?$%()-=+/><_#§\\@                             ";
	public static final String galactic_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ><.                                   " + "0123456789";
	public static final String special = "0123456789AB&_♥C                                                ";

	/*
	 * One line text
	 */
	
	public static final int WHITE = 0xffffffff;
	public static final int SHADOW = 0xff383838;

	public void render(String msg, Screen screen, int x, int y, int scale)
	{
		if (game.fontShadow())
			renderColored(msg, screen, chars, x + (1 * scale), y + (1 * scale), scale, 2, WHITE, SHADOW);
		renderFont(chars, x, y, scale, 2, msg, screen);
	}

	public void renderColoredWithShadow(String msg, Screen screen, int x, int y, int scale, int colorToReplace, int color)
	{
		if (game.fontShadow())
			renderColored(msg, screen, chars, x + (1 * scale), y + (1 * scale), scale, 2, WHITE, darkColor(color, 0.2f));
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
	
	//TODO: Remove screen parameter from ALL methods. Use BaseGame's screen!
	
	/**
	 * 
	 * @param text
	 * @param screen
	 * @param x
	 * @param y
	 * @param scale
	 * @param colorIndexes Ex.&2, @code{0xff00ff00}, &5,@code{0xffff0000};
	 * Sets of 2 parameters - index & color
	 */
	public void renderIndexedColors(String msg, Screen screen, int x, int y, int scale, Object...colorIndexes)
	{
		if (msg == null)
			return;
		
		if (!game.smallFont())
			msg = msg.toUpperCase();
		
		int color = WHITE;
		
		int ignore = 0;
		
		int totalIgnore = 0;

		for (int i = 0; i < msg.length(); i++)
		{
			int char_index = chars.indexOf(msg.charAt(i));
			
			int colorIndex = 0;
			
			if (colorIndexes != null)
			for (Object o : colorIndexes)
			{
				if (o instanceof String)
				{
					String st = (String) o;
					if (msg.length() > i + st.length())
					{
						String indexedText = msg.substring(i, i + st.length());
						if (st.equals(indexedText))
						{
							Object obj = colorIndexes[colorIndex + 1];
							if (obj instanceof Integer)
							{
								color = (Integer) obj;
								ignore = st.length();
								totalIgnore += st.length();
							}
						}
					}
				}
				colorIndex++;
			}
			
			if (char_index >= 0)
			{
				if (ignore == 0)
				{
					if (game.smallFont())
						screen.render(x + (i * scale * 8 - (totalIgnore * 8 * scale)) + scale, y + scale, (char_index + 2 * 64), scale, WHITE, darkColor(color, 0.2f));
					screen.render(x + (i * scale * 8 - (totalIgnore * 8 * scale)), y, (char_index + 2 * 64), scale, WHITE, color);
				} else
				{
					ignore--;
				}
			}
		}
	}

	public static int darkColor(int color, float factor)
	{
		return Screen.getColor((int) (Screen.getRed(color) * factor), (int) (Screen.getGreen(color) * factor),
				(int) (Screen.getBlue(color) * factor));
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
