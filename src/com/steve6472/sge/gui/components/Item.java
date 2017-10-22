package com.steve6472.sge.gui.components;

public class Item extends Button
{
	private String text;
	private boolean isSelected = false;
	
	public Item(String text)
	{
		super(text);
		this.text = text;
	}

	public void select()
	{
		isSelected = true;
		disable();
	}

	public void unselect()
	{
		isSelected = false;
		enable();
	}

	public boolean isSelected()
	{
		return isSelected;
	}

	public String getText()
	{
		return text;
	}
}