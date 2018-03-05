/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package com.steve6472.sge.main.networking.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataStream implements Serializable
{
	private static final long serialVersionUID = 6171502508214339154L;
	
	private List<Object> data;
	
	public DataStream()
	{
		data = new ArrayList<Object>();
	}
	
	/*
	 * Writing
	 */
	
	public void writeBoolean(boolean o) { data.add(o); }
	
	public void writeChar(char o) { data.add(o); }
	
	public void writeString(String o) { data.add(o); }
	
	public void writeDouble(double o) { data.add(o); }
	
	public void writeInt(int o) { data.add(o); }
	
	public void writeLong(long o) { data.add(o); }
	
	public void writeObject(Object o) { data.add(o); }
	
	/*
	 * Reading
	 */
	
	public boolean readBoolean() { return (boolean) read(Boolean.class); }
	
	public char readChar() { return (char) read(Character.class); }
	
	public String readString() { return (String) read(String.class); }
	
	public double readDouble() { return (double) read(Double.class); }
	
	public int readInt() { return (int) read(Integer.class); }
	
	public long readLong() { return (long) read(Long.class); }
	
	public Object readObject() { return (Object) read(Object.class); }
	
	/*
	 * Methods
	 */
	
	private Object read(Class<?> clazz)
	{
		for (Iterator<Object> iter = data.iterator(); iter.hasNext();)
		{
			Object o = iter.next();
			
			if (o == null)
			{
				iter.remove();
				return null;
			}
			
			if (clazz.isInstance(o))
			{
				iter.remove();
				return o;
			}
		}
		return null;
	}
	
	public void printData()
	{
		System.out.println(Arrays.toString(data.toArray()));
	}
	
	

}
