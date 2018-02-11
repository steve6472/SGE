/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 13. 12. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.gui.components;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.TextField.EnterEvent;
import com.steve6472.sge.main.BaseGame;

public class TextArea extends Component implements IFocusable
{
	private static final long serialVersionUID = 7783560380863267944L;
	boolean isFocused = false;
	private boolean showCarret = false;
	int carretPositionX = 0, carretPositionY = 0;
//	private int fontSize = 1;
	private double carretTick = 0;
	private boolean isEnabled = true, initRender = true;
	List<String> text;
	List<EnterEvent> enterEvents = new ArrayList<EnterEvent>();
	public int xLimit = Integer.MAX_VALUE;

	PipedOutputStream pos;
	public PrintStream output;
	PipedInputStream pis;
	BufferedReader reader;

	Object[] colors;
	ColorIndex[] words;

	public TextArea(int maxY)
	{
		super();
		
		text = new ArrayList<String>();
		for (int i = 0; i < maxY; i++)
			text.add("");
	}

	@Override
	public void init(BaseGame game)
	{
		getKeyHandler().addListenerEvent(getKeyHandler().new KeyListener(this)
		{
			@Override
			public void keyTyped(char c, int keyCode)
			{
				if (isEnabled())
				{
					switch (keyCode)
					{
					case KeyEvent.VK_ENTER:
						enterEvents.forEach((ee) -> ee.enter());
						moveCarretDown();
						carretPositionX = text.get(carretPositionY).length();
						break;
					case KeyEvent.VK_BACK_SPACE:
						//Delete whole line
						if (game.getKeyHandler().control.isPressed())
						{
							text.set(carretPositionY, "");
							carretPositionX = 0;
							repaint();
							break;
						}
						String t = text.get(carretPositionY);
						if (t.equals(""))
						{
							moveCarretUp();
							carretPositionX = text.get(carretPositionY).length();
							break;
						}
						moveCarretLeft();
						
						if (text.get(carretPositionY).length() == carretPositionX + 1)
							text.set(carretPositionY, t.substring(0, t.length() > 0 ? t.length() - 1 : t.length()));
						else
							text.set(carretPositionY, text.get(carretPositionY).substring(0, Math.min(carretPositionX, text.get(carretPositionY).length())) + text.get(carretPositionY).substring(Math.min(carretPositionX + 1, text.get(carretPositionY).length())));
						
						break;
					case 38: //Arrow Up
						moveCarretUp();
						break;
					case 40: //Arrow Down
						moveCarretDown();
						break;
					case KeyEvent.VK_LEFT:
						moveCarretLeft();
						break;
					case KeyEvent.VK_RIGHT:
						moveCarretRight();
						break;
					case 65: //Control + A
					default:
						if (text.get(carretPositionY).length() >= xLimit)
							moveCarretDown();
						text.set(carretPositionY, text.get(carretPositionY).substring(0, Math.min(carretPositionX, text.get(carretPositionY).length())) + Character.toString(c) + text.get(carretPositionY).substring(Math.min(carretPositionX, text.get(carretPositionY).length())));
						moveCarretRight();
						if (text.get(text.size() - 1).length() >= xLimit)
						{
							text.set(text.size() - 1, text.get(text.size() - 1).substring(0, xLimit));
							moveCarretLeft();
						}
						break;
					}
					updateText();
				}
			}
		});
	}

	@Override
	public void render(Screen screen)
	{
		if (initRender)
		{
			screen.drawRect(x, y, getWidth(), getHeight(), 2, 0xff020202);
			screen.fillRect(x + 2, y + 2, getWidth() - 4, getHeight() - 4, 0xff414041);
			initRender = false;
		} else
		{
			screen.fillRect(x + 2, Math.max(carretPositionY - 1, 0) * 9 + y + 2, getWidth() - 4, 13, 0xff414041);
			screen.fillRect(x + 2, Math.min(carretPositionY + 1, text.size()) * 9 + y + 2, getWidth() - 4, 13, 0xff414041);
			screen.fillRect(x + 2, carretPositionY * 9 + y + 2, getWidth() - 4, 13, 0xff414041);
		}

		for (int i = 0; i < text.size(); i++)
		{
			if (words != null)
			{
				String[] text = separateWords(this.text.get(i));

				int totalIndex = 0;

				for (int j = 0; j < text.length; j++)
				{
					getFont().renderIndexedColors(text[j], screen, x + 3 + totalIndex * 8, y + 3 + i * 9, 1, colors);
					totalIndex += text[j].length();
					for (ColorIndex ci : words)
					{
						for (String word : ci.words)
						{
							if (text[j].contains(ci.index + word))
							{
								totalIndex -= ci.index.length();
							}
						}
					}
				}
			} else
			{
				getFont().render(text.get(i), screen, x + 3, y + 3 + i * 9, 1);
			}

			if (isEnabled())
				if (i == carretPositionY)
					if (showCarret && isFocused)
						screen.fillRect(carretPositionX * 8 + x + 3, carretPositionY * 9 + y + 10, 8, 2, 0xffa9a8aa);
		}
	}
	
	private String[] separateWords(String text)
	{
		String[] r = text.split(" ");
		
		for (int i = 0; i < r.length; i++)
		{
			for (ColorIndex ci : words)
			{
				for (String word : ci.words)
				{
					if (r[i].equals(word))
						r[i] = r[i].replace(word, ci.index + word);
				}
			}
			r[i] += " ";
		}
		
		return r;
	}

	@Override
	public void tick()
	{
		if (!isVisible())
			return;
		if (isCursorInComponent() && getMouseHandler().mouseHold)
			isFocused = true;
		
		if (!isCursorInComponent() && getMouseHandler().mouseHold)
			isFocused = false;

		carretTick += Math.max(60d / Math.max(getGame().getFPS(), 1), 1);
		if (carretTick >= 60)
		{
			carretTick = 0;
			showCarret = !showCarret;
			repaint();
		}
	}
	
	public void addText(String msg)
	{
		for (int i = 0; i < text.size(); i++)
		{
			if (text.get(i).isEmpty())
			{
				text.set(i, msg);
				fullRepaint();
				return;
			}
		}
	}

	public void setAsOutput()
	{
		pos = new PipedOutputStream();
		output = new PrintStream(pos);
		try
		{
			pis = new PipedInputStream(pos);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		reader = new BufferedReader(new InputStreamReader(pis));
		System.setOut(output);
		new CustomOutput().start();
	}
	
	class CustomOutput extends Thread
	{
		@Override
		public void run()
		{
			while (true)
			{
				try
				{
					addText(reader.readLine());
					output.flush();
					pos.flush();
				} catch (IOException e)
				{
					e.printStackTrace();
				}

				try
				{
					Thread.sleep(2);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	protected int getMinHeight()
	{
		return 10;
	}
	
	@Override
	protected int getMinWidth()
	{
		return 10;
	}
	
	/*
	 * Operators
	 */
	
	public void fullRepaint()
	{
		initRender = true;
		repaint();
	}
	
	public void clearTextArea()
	{
		int size = text.size();
		
		text.clear();
		
		for (int i = 0; i < size; i++)
			text.add("");
		
		carretPositionX = 0;
		carretPositionY = 0;
		
		fullRepaint();
	}
	
	public void clearLine(int line)
	{
		text.remove(line);
		text.add("");
		
		fullRepaint();
	}
	
	public void moveCarretLeft() { carretPositionX = Math.max(0, carretPositionX - 1); carretTick = 0; showCarret = true; repaint(); };
	
	public void moveCarretRight() { carretPositionX = Math.min(text.get(carretPositionY).length(), carretPositionX + 1); carretTick = 0; showCarret = true; repaint(); };
	
	public void moveCarretUp() { carretPositionY = Math.max(0, carretPositionY - 1); carretTick = 0; showCarret = true; repaint(); };
	
	public void moveCarretDown() { carretPositionY = Math.min(text.size() - 1, carretPositionY + 1); carretTick = 0; showCarret = true; repaint(); };
	
	public void addEnterEvent(EnterEvent ee) { enterEvents.add(ee); }
	
	private void updateText()
	{
		
	}
	
	/*
	 * Setters
	 */
	
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}
	
	/**
	 * 
	 * @param colorIndexes index, color
	 * @param words index, word
	 */
	public void setColors(Object[] colorIndexes, ColorIndex[] words)
	{
		this.colors = colorIndexes;
		this.words = words;
	}
	
	/*
	 * Getters
	 */

	@Override
	public boolean isFocused()
	{
		return isFocused;
	}
	
	public List<String> getText()
	{
		return text;
	}
	
	public boolean isEnabled()
	{
		return isEnabled;
	}

}
