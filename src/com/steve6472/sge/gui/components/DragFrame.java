package com.steve6472.sge.gui.components;

import javax.swing.JFrame;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.BaseGame;

public class DragFrame extends Component
{
	protected PanelBase panel;
	private JFrame frame;
	protected String text;
	protected boolean renderTextInCenter = true;
	public int textSize = 2;
	
	protected boolean canDrag = true;
	
	@Override
	public void init(BaseGame game)
	{
		setPanel(game.panelList.getPanelById(8));
		frame = game.getMain().getJFrame();
		repaint();
	}

	@Override
	public void render(Screen screen)
	{
		panel.render(getX(), getY(), getWidth(), getHeight());
		if (text != null && text != "")
		{
			if (renderTextInCenter)
				getFont().render(getText(), screen, getX() + getWidth() / 2 - (getText().length() * (8 * textSize)) / 2,
						getHeight() / 2 - (8 * textSize) / 2 + 3 * textSize, textSize);
			else
				getFont().render(getText(), screen, getX(), getY() + getHeight() / 2 - (8 * textSize) / 2 + 3 * textSize, textSize);

		}
	}

	private int lastX = 0;
	private int lastY = 0;
	private boolean b = false;
	
	@Override
	public void tick()
	{
		if (!canDrag)
			return;
//		frame.setLocationRelativeTo(null);
		if (isCursorInComponent())
		{
			if (getMouseHandler().mouse_hold && !getMouseHandler().mouse_triggered)
			{
				lastX = getMouseHandler().pressedMouseXOnScreen;
				lastY = getMouseHandler().pressedMouseYOnScreen;
				getMouseHandler().mouse_triggered = true;
				b = true;
			}
		}
		if (getMouseHandler().mouse_hold && b)
		{
			int x = getMouseHandler().mouseXOnScreen;
			int y = getMouseHandler().mouseYOnScreen;

			if (x != lastX || y != lastY)
			{
				int nextX = frame.getLocationOnScreen().x + x - lastX;
				int nextY = frame.getLocationOnScreen().y + y - lastY;
				/*
				GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
				int width = gd[0].getDisplayMode().getWidth();
				int height = gd[0].getDisplayMode().getHeight();
				
				
				if (nextX < 0 || nextY < 0 || frame.getX() + game.getWidth() > width || frame.getY() + game.getHeight() > height)
				{
					if (nextX < 0 && nextY < 0)
					{
						frame.setLocation(0, 0);
					} else
					{
						if (nextX < 0)
						{
							frame.setLocation(0, nextY);
							lastY = mouseHandler.mouseYOnScreen;
						}
						if (nextY < 0)
						{
							frame.setLocation(nextX, 0);
							lastX = mouseHandler.mouseXOnScreen;
						}
					}
					if (frame.getX() + game.getWidth() > width && frame.getY() + game.getHeight() > height)
					{
						frame.setLocation(width - game.getWidth(), height - game.getHeight());
					} else
					{
						if (frame.getX() + game.getWidth() > width)
						{
							frame.setLocation(width - game.getWidth(), nextY);
							lastY = mouseHandler.mouseYOnScreen;
						}
						if (frame.getY() + game.getHeight() > height)
						{
							frame.setLocation(nextX, height - game.getHeight());
							lastX = mouseHandler.mouseXOnScreen;
						}
					}
				} else
				{*/
					frame.setLocation(nextX, nextY);
					
					if (frame.getLocationOnScreen().y < 0)
						frame.setLocation(frame.getLocationOnScreen().x, 0);

					lastX = getMouseHandler().mouseXOnScreen;
					lastY = getMouseHandler().mouseYOnScreen;
				//}
			}
		}
		if (!b || !getMouseHandler().mouse_hold)
		{
			b = false;
		}
	}

	public void setPanel(PanelBase panel)
	{
		this.panel = panel;
		repaint();
	}

	protected int getMinHeight() { return 2; }
	
	protected int getMinWidth() { return 2; }
	
	public void setFontSize(int textSize)
	{
		this.textSize = textSize;
		repaint();
	}

	public void setText(String text)
	{
		if (!text.equals(this.getText()))
		{
			this.text = text;
			repaint();
		}
	}

	public void renderTextInCenter(boolean renderTextInCenter)
	{
		this.renderTextInCenter = renderTextInCenter;
		repaint();
	}
	
	public boolean isTextRenderedInCenter() { return renderTextInCenter; }
	
	public String getText() { return text; }

	public void setCanDrag(boolean c)
	{
		canDrag = c;
	}
	
	public boolean isCanDrag()
	{
		return canDrag;
	}

}
