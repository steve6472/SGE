/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package com.steve6472.sge.main.networking.packet;

public class DisconnectPacket extends Packet
{

	public DisconnectPacket()
	{
	}

	@Override
	public void output(DataStream output)
	{
	}

	@Override
	public void input(DataStream input)
	{
	}

	@Override
	protected int getSize()
	{
		return 0;
	}

}