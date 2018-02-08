package com.steve6472.sge.gui;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.steve6472.sge.gfx.Screen;
import com.steve6472.sge.gfx.Sprite;
import com.steve6472.sge.gui.components.Background;
import com.steve6472.sge.gui.components.Button;
import com.steve6472.sge.gui.components.CheckBox;
import com.steve6472.sge.gui.components.ColorIndex;
import com.steve6472.sge.gui.components.ComboBox;
import com.steve6472.sge.gui.components.DragFrame;
import com.steve6472.sge.gui.components.FileBrowser;
import com.steve6472.sge.gui.components.HexKeyboard;
import com.steve6472.sge.gui.components.ItemGridList;
import com.steve6472.sge.gui.components.ItemList;
import com.steve6472.sge.gui.components.NumberSelector;
import com.steve6472.sge.gui.components.ProgressBar;
import com.steve6472.sge.gui.components.RGBPicker;
import com.steve6472.sge.gui.components.Slider;
import com.steve6472.sge.gui.components.TextArea;
import com.steve6472.sge.gui.components.TextField;
import com.steve6472.sge.gui.components.events.ButtonEvents;
import com.steve6472.sge.gui.components.events.ChangeEvent;
import com.steve6472.sge.main.BaseGame;

public class ComponentTest extends Gui
{
	private static final long serialVersionUID = 2331300649167209910L;
	Button b;
	Background p;
	ComboBox cb;
	CheckBox chb;
	NumberSelector ns;
//	Image i;
	DragFrame df;
	Slider s;
	ProgressBar pb;
	ItemList il;
	ItemGridList igl;
	TextField t;
	RGBPicker rgb;
	HexKeyboard hex;
	FileBrowser fb;
	TextArea ta;
	
	public static Object[] variableColors = new Object[]
			// Local Var. init	keywords         {}[]<>()+;*-/.... numbers			public var			Type    		  method const.	    method call      usage of local var     Strings
			{"&0", 0xffED7F48, "&1", 0xffDD2867, "&2", 0xffE6E6FA, "&3", 0xff6897BB, "&4", 0xff66E1F8, "&5", 0xff79ABFF, "&6", 0xff1EB540, "&7", 0xffA7EC21, "&8", 0xffFFBF26, "&9", 0xff17C6A3};

	public static ColorIndex keywords = new ColorIndex("&1", "while", "true", "do", "false", "if", "else", "break", "case", "switch", "instanceof",
			"boolean", "byte", "char", "short", "int", "long", "float", "double", "catch", "final", "class", "private", "public", "static", "void",
			"protected", "abstract", "continue", "for", "enum", "super", "this", "try", "throw", "synchronized", "throws", "transient", "volatile",
			"native", "interface", "import", "implements", "goto", "finally", "default", "const", "assert", "null", "new");
	
	public static ColorIndex characters = new ColorIndex("&2", "+", "-", "*", "/", ".", "'", "<", ">", "\\[", "\\]", "{", "}", "(", ")", "=", "%", "^");
	
	public static ColorIndex numbers = new ColorIndex("&3", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
	
	public static ColorIndex basicTypes = new ColorIndex("&3", "Boolean", "Byte", "Char", "Short", "Integer", "Long", "Float", "Double", "Class", "File", "String", "Object");

	public static boolean smallFont = false;

	{
		
	}
	
	int mouseCount = 8;
	
	public ComponentTest(BaseGame game)
	{
		super(game);
	}


	@Override
	public void createGui()
	{
		Sprite.listPaths = false;
		//You want render background first right ?
		//WORKS
//		p = new Panel(new Panel2(game.getScreen()));
//		p.setLocation(0, 0);
//		p.setSize(game.getWidth(), game.getHeight());
//		addComponent(p);
		p = new Background();
		addComponent(p);
		p.setPanel(1);

		//WORKS
		b = new Button(20, game.getHeight() - 60, game.getWidth() - 40, 40, "Exit", game);
		b.addEvent(new ButtonEvents()
		{
			@Override
			public void click()
			{
//				hideGui();
//				game.getMenu().setVisible(true);
				game.exit();
			}
		});
		addComponent(b);

		//WORKS
		chb = new CheckBox();
//		chb.addToolTip("Just normal CheckBox");
		chb.setLocation(10, 60);
		chb.check();
		addComponent(chb);
		
		//WORKS
		ns = new NumberSelector();
		ns.setLocation(10, 110);
		addComponent(ns);
		
		t = new TextField();
		t.setSize(150, 40);
		t.setLocation(10, 160);
		addComponent(t);
		
//		try
//		{
//			ImageIO.write(new Sprite("test2.png").toBufferedImage(), "PNG", new File("debug.png"));
//		} catch (IOException e)
//		{
		
//			e.printStackTrace();
//		}

		//WORKS
		s = new Slider();
		s.setLocation(30, 230);
		s.setSize(370);
		s.setValue(50);
		addComponent(s);

		//WORKS
//		i = new Image(new Sprite("debug.png"));
//		i = new Image(new Sprite("test.png"));
//		i = new Image(new Sprite("test2.png"));
//		i.renderAsTransparent = true;
//		i.setLocation(180, 120);
//		addComponent(i);

		//WORKS
		pb = new ProgressBar();
		pb.setLocation(30, 280);
		pb.setSize(370, 20);
		addComponent(pb);

		//WORKS
		il = new ItemList();
		il.setLocation(426, 130);
		il.setSize(172, 40);
		il.setFontSize(1);
//		il.addItem("Hello world", new Sprite("darkGrass.png"));
//		il.addItem("Hi", new Sprite("darkGrassGrassTrans.png"));
//		il.addItem("No", new Sprite("darkGrassSandTrans.png"));
//		il.addItem("Yes", new Sprite("grass.png"));
//		il.addItem("Why ?!", new Sprite("grassSandTrans.png"));
//		il.addItem("Why not ?", new Sprite("stone.png"));
//		il.addItem("OMG", new Sprite("stone2.png"));
//		il.addItem("So funny!", new Sprite("stoneSandTrans.png"));
		il.setVisibleItems(6);
		il.addChangeEvent(new ChangeEvent()
		{
			@Override
			public void change()
			{
//				System.out.println(il.getSelectedItem() + " " + il.getSelectedIndex());
			}
		});
		addComponent(il);
		
		igl = new ItemGridList();
		igl.setLocation(426 + 172 + 10, 130);
		igl.setSize(40, 40);
		igl.setVisibleItems(4, 6);
		for (int i = 0; i < 6; i++)
		{
//			igl.addItem(new Sprite("darkGrass.png"));
//			igl.addItem(new Sprite("darkGrassGrassTrans.png"));
//			igl.addItem(new Sprite("darkGrassSandTrans.png"));
//			igl.addItem(new Sprite("grass.png"));
//			igl.addItem(new Sprite("grassSandTrans.png"));
//			igl.addItem(new Sprite("stone.png"));
//			igl.addItem(new Sprite("stone2.png"));
//			igl.addItem(new Sprite("stoneSandTrans.png"));
		}
		addComponent(igl);
		
		rgb = new RGBPicker();
		addComponent(rgb);
		rgb.setLocation(800, 130);
		
		hex = new HexKeyboard();
		addComponent(hex);
		hex.setLocation(800, 330);
		
		fb = new FileBrowser();
		addComponent(fb);
		fb.setLocation(30, 320);
		
		df = new DragFrame();
		df.setLocation(200, 2);
		df.setSize(game.getWidth() - 202, 25);
		addComponent(df);
		
		//365 390
		ta = new TextArea(64);
		ta.setLocation(365, 390);
		ta.setSize(300, 200);
		ta.setColors(variableColors, new ColorIndex[] { keywords, characters, numbers, new ColorIndex("&5", "String", "Integer", "Double")});
		// 
		addComponent(ta);

		//You want see the items over other components right ? ( So this component *should* be last )
//		cb = new ComboBox();
//		addComponent(cb);
//		cb.setLocation(320, 60);
//		cb.setSize(game.getWidth() - 380, 40);
//		cb.addItems(game.getMouseHandler(), "Hello", "I <3 You", "You're my favourite", "#Magic Is The Key");
//		cb.setMaxItems(3);
//		cb.setSelectedItem(1);

		Sprite aeroMouse = new Sprite("aeroMouse.png");

		for (int i = 0; i < mouseCount; i++)
		{
			Sprite s = new Sprite("aeroMouse.png");
			for (int j = 0; j < aeroMouse.getWidth(); j++)
			{
				for (int k = 0; k < aeroMouse.getHeight(); k++)
				{
					int p = s.pixels[j + k * s.getWidth()];
					if (p != 0 && p != 0x00ffffff)
					{
						s.pixels[j + k * s.getWidth()] = new Color(new Color(p).getGreen(), new Color(p).getBlue(), new Color(p).getRed(),
								(int) (i / ((mouseCount / 256d)))).getRGB();
					}
				}
			}
			sprites.add(s);
		}
		Sprite.listPaths = true;
		System.out.println("Started component test");
	}
	
	List<Point> points = new ArrayList<Point>();
	List<Sprite> sprites = new ArrayList<Sprite>();
	
	@Override
	public void guiTick()
	{
//		s.setValue_(20);
//		System.out.println(s.getValue());
		if (ns.getValue() < 0)
			chb.setType(0);
		
		if (ns.getValue() > 0)
			chb.setType(1);
		
		/*
		 * Progress bar & slider
		 */
		
		pb.setValue(s.getValue());
//		i.setLocation(120 + s.getValue(), 120);

		if (chb.isChecked())
		{
			points.add(new Point(game.getMouseHandler().getMouseX(), game.getMouseHandler().getMouseY()));
		}
		if (points.size() == mouseCount)
			points.remove(0);

	}
	
	float hue = 0;
	

	@Override
	public void render(Screen screen)
	{
		if (chb.isChecked())
		{
			for (int i = 0; i < points.size(); i++)
			{
//FIXME				screen.renderTransparentSprite(screen, sprites.get(i), points.get(i).getX(), points.get(i).getY());
			}
		}
		int x = 50;
		screen.fillRect(8 + x, 8, 128, 48, 0xff000000);
		font.render("MouseX: " + game.getMouseHandler().getMouseX(), screen, 12 + x, 12, 1);
		font.render("MouseY: " + game.getMouseHandler().getMouseY(), screen, 12 + x, 22, 1);
		screen.fillRect(8 + x, 30, 300, 11, 0xff000000);
//		font.renderIndexedColors("&1for &2(&1int&0 i&2 = &30&2; &8i &2< &38&2; &8i&2++)", screen, x + 12, 32, 1, variableColors);

		smallFont = chb.isChecked();
//		int X = 500, Y = 500;
//		double radius = 128;
//			drawTestPixel(screen);
		{/*
			for (double i = 0; i < 360; i += 1 / 64d)
			{
				double angle = i;
				double x1 = radius * Math.cos(angle * Math.PI / 180);
				double y1 = radius * Math.sin(angle * Math.PI / 180);
				screen.drawLine(X, Y, (int) x1 + X, (int) y1 + Y, Color.HSBtoRGB((float)i / 360, 1, 1));
//				screen.drawLine(X, Y, i, Y, Color.HSBtoRGB((float)i / 360, 1, 1));
//				screen.render(X + x1, Y + y1, Color.HSBtoRGB((float)i / 360, 1, 1));
			}*//*
			for (int i = 0; i < radius; i++)
			{
				for (double j = 0; j < 360; j += 1 / 16d)
				{
					double angle = j;
					double x1 = i * Math.cos(angle * Math.PI / 180);
					double y1 = i * Math.sin(angle * Math.PI / 180);
					screen.render((int) x1 + X, (int) y1 + Y, Color.HSBtoRGB((float) j / 360, (float) (i / radius), 1));
				}
			}*/
		}
	}
}
