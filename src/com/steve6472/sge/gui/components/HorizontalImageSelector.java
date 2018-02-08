package com.steve6472.sge.gui.components;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.Component;
import com.steve6472.sge.gui.components.events.ButtonEvents;
import com.steve6472.sge.gui.components.panels.Panel1;
import com.steve6472.sge.gui.components.panels.Panel2;
import com.steve6472.sge.gui.components.panels.PanelBase;
import com.steve6472.sge.main.ArrowTextures;
import com.steve6472.sge.main.BaseGame;

public class HorizontalImageSelector extends Component
{
	private static final long serialVersionUID = 5269254274983682816L;
	List<Sprite> images = new ArrayList<Sprite>();
	List<Sprite> editedImages = new ArrayList<Sprite>();
	
	protected int scroll = 0;
	protected int selected = -1;
	protected int hovered = -1;
	protected int maxImages = 4;
	PanelBase back;
	PanelBase panel;
	
	Button left, right;
	
	@Override
	public void init(BaseGame game)
	{
		back = new Panel2(game.getScreen());
		panel = new Panel1(game.getScreen());
		
		left = new Button();
		left.setEnabledImage(Sprite.multiplySize(ArrowTextures.arrowLeft, 2));
		left.setHoveredImage(Sprite.multiplySize(ArrowTextures.arrowLeftHovered, 2));
		left.setDisabledImage(Sprite.multiplySize(ArrowTextures.arrowLeftDisabled, 2));
		left.setSize(32, getHeight());
		left.setImageOffset(2, 13);
		left.disable();
		left.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				scroll--;
				if (scroll == 0)
					left.disable();
					
			}
		});
		addComponent(left);
		
		right = new Button();
		right.setEnabledImage(Sprite.multiplySize(ArrowTextures.arrowRight, 2));
		right.setHoveredImage(Sprite.multiplySize(ArrowTextures.arrowRightHovered, 2));
		right.setDisabledImage(Sprite.multiplySize(ArrowTextures.arrowRightDisabled, 2));
		right.setSize(32, getHeight());
		right.setImageOffset(2, 13);
		right.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
				scroll++;
				if (scroll > 0)
					left.enable();
			}
		});
		addComponent(right);
		
		setMaxTextureHeight(64);
		setMaxImages(4);
	}

	@Override
	public void render(Screen screen)
	{
		back.render(x + 32, y, width, height);
		
		for (int i = 0; i < maxImages; i++)
		{

			if (!((i + scroll) > (images.size() - 1)))
			{
				panel.render(x + i * 64 + 4 + 32, y + 4, getHeight() - 8, getHeight() - 8);
				int X = x + i * 64 + 4 + 32;
				screen.renderSprite(editedImages.get(i + scroll),
						X + (((getHeight() - 8) / 2) - (editedImages.get(i + scroll).getWidth() / 2)),
						(y + 4) + (((getHeight() - 8) / 2) - (editedImages.get(i + scroll).getHeight() / 2)));
			}
		}
		
		renderComponents(screen);
//		screen.drawRect(screen, x, y, width + 64, height, 0x80222222);
	}

	@Override
	public void tick()
	{
		right.setEnabled(images.size() > scroll + maxImages);
		tickComponents();
	}
	
	public Sprite getSprite(int index)
	{
		return images.get(index);
	}
	
	public void setMaxImages(int i)
	{
		this.width = i * (getHeight() - 8) + 8;
		this.maxImages = i;
		right.setLocation(getWidth() + 48 + 4, getY());
	}
	
	public void setMaxTextureHeight(int h)
	{
		this.height = h + 8;
		left.setSize(32, getHeight());
		right.setSize(32, getHeight());
	}
	
	public void addSprites(Sprite...sprites)
	{
		for (Sprite s : sprites)
		{
			addSprite(s);
		}
	}
	
	public void addSprite(Sprite sprite)
	{
		images.add(sprite);
		editedImages.add(Sprite.multiplySize(sprite, getImageMaxSizeMulti(sprite)));
	}
	
	public void addSprites(File...files)
	{
		for (File f : files)
		{
			try
			{
				addSprites(new Sprite(ImageIO.read(f)));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	protected int getImageMaxSizeMulti(Sprite s)
	{
		int lastWhatever = 1; //Multiwhatever
		int loop = 0;
		for (;;)
		{
			int w = Sprite.multiplySize(s, lastWhatever).getWidth();
			int h = Sprite.multiplySize(s, lastWhatever).getHeight();
			if (w > getHeight() - 8 - 4 || h > getHeight() - 8 - 4)
			{
				break;
			} else
			{
				lastWhatever++;
			}
			
			
			loop++;
			if (loop > getHeight() - 8)
				break;
		}
		return lastWhatever - 1;
	}

	
	protected class ImageSelectorSelected extends PanelBase
	{
		public ImageSelectorSelected(Screen screen)
		{
			super(screen);
		}

		public int borderSize = 2;

		@Override
		public void render(int x, int y, int w, int h)
		{
			//Border: 0xff3f3f3f
			//Fill: 0xffbfbfbf
			int c = 0xeebdc6ff;
			//LU - RU
			fillRect(x, y, w, borderSize, c);
			//RU-DR
			fillRect(x + w - borderSize, y + borderSize, borderSize, h - 2 * borderSize, c);
			//LD-DR
			fillRect(x, y + h - borderSize, w, borderSize, c);
//			LU-DL
			fillRect(x, y + borderSize, borderSize, h - 2 * borderSize, c);
		}

	}


	public List<Sprite> getSprites()
	{
		return images;
	}

}
