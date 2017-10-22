/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 11. 10. 2017
* Project: SRT
*
***********************/

package com.steve6472.sge.main.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.steve6472.sge.gfx.Screen;

public class IObjectManipulator<T extends IObject>
{
	private final List<T> eAdd;
	protected final List<T> enti;
	
	public IObjectManipulator()
	{
		eAdd = new ArrayList<T>();
		enti = new ArrayList<T>();
	}
	
	public void add(T type)
	{
		eAdd.add(type);
	}
	
	public List<T> getAll()
	{
		return enti;
	}
	
	public T get(int index)
	{
		return enti.get(index);
	}
	
	public void clear()
	{
		eAdd.clear();
		enti.clear();
	}
	
	public void tick(boolean tickKillable)
	{
		enti.addAll(eAdd);
		eAdd.clear();
		
		for (Iterator<T> t = enti.iterator(); t.hasNext();)
		{
			T io = (T) t.next();
			if (io instanceof BaseEntity)
			{
				((BaseEntity) io).tickTasks();
			}
			if (tickKillable && io instanceof Killable)
			{
				Killable k = (Killable) io;
				if (k.isDead())
				{
					t.remove();
				} else
				{
					io.tick();
				}
			} else
			{
				io.tick();
			}
		}
	}
	
	public void render(Screen screen)
	{
		enti.forEach((c) -> c.render(screen));
	}
}
