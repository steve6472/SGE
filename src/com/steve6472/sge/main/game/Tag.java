package com.steve6472.sge.main.game;

public class Tag
{
	public String name = null;
	public Object value = null;
	
	public Tag(String name, Object value)
	{
		this.name = name;
		this.value = value;
	}
	
	public String getName()
	{
		return name;
	}
	
	
	protected Object get()
	{
		return value;
	}

	public boolean getBoolean()
	{
		if (get() == null)
			return false;
		return (boolean) get();
	}

	public char getChar()
	{
		if (get() == null)
			return ' ';
		return ((String) get()).charAt(0);
	}
	
	public String getString()
	{
		return (String) get();
	}

	public double getDouble()
	{
		if (get() == null)
			return 0;
		return (Double) get();
	}

	public int getInt()
	{
		if (get() == null)
			return 0;
		return new Integer((String) get());
	}
	/*
		try
		{
			return new Double((String) get());
		} catch (Exception ex)
		{
			System.err.println("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage() + "\nIn:");
			for (StackTraceElement w : ex.getStackTrace())
			{
				System.err.println("\t" + w.getClassName() + "." + w.getMethodName() + "(" + w.getFileName() + ":" + w.getLineNumber() + ")");
			}
			System.err.println("End of error log. Returning (double) 0 & Exitting level");
			return 0;
		}
		*/
}
