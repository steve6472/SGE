/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package com.steve6472.sge.main.networking.packet;

import java.util.HashMap;
import java.util.Map;

public abstract class Packet
{
	static Map<Integer, Class<? extends Packet>> packets0;
	static Map<Class<? extends Packet>, Integer> packets1;
	
	static
	{
		packets0 = new HashMap<Integer, Class<? extends Packet>>();
		packets1 = new HashMap<Class<? extends Packet>, Integer>();

		addPacket(0, ConnectPacket.class);
		addPacket(1, DisconnectPacket.class);
	}
	
	public Packet()
	{
	}
	
	public static int getPacketId(Packet packet)
	{
		return packets1.get(packet.getClass());
	}
	
	private static void addPacket(int id, Class<? extends Packet> clazz)
	{
        if (packets0.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate packet id:" + id);
        }
        if (packets1.containsKey(clazz)) {
            throw new IllegalArgumentException("Duplicate packet class:" + clazz);
        }
        packets0.put(id, clazz);
        packets1.put(clazz, id);
	}

	public static Packet getPacket(int id)
	{
		try
		{
			Class<? extends Packet> clazz = packets0.get(id);
			if (clazz == null)
				return null;
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
            System.out.println("Skipping packet with id " + id);
			return null;
		}
	}
	
	public static String getPacketIdHex(int id)
	{
		String hexId = Integer.toHexString(id);
		
		//Hex id must have 4 characters!
		if (hexId.length() == 1)
			hexId = "000" + hexId;
		else if (hexId.length() == 2)
			hexId = "00" + hexId;
		else if (hexId.length() == 3)
			hexId = "0" + hexId;
		
		return hexId;
	}
	
	public abstract void output(DataStream output);
	
	public abstract void input(DataStream input);
	
	protected abstract int getSize();
	
//	public final byte[] getData0()
//	{
//		return (getPacketIdHex() + getData()).getBytes();
//	}
}
