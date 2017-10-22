/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 9. 9. 2017
* Project: SGE
*
***********************/

package com.steve6472.sge.gui.components.panels;

import java.util.ArrayList;
import java.util.List;

public class PanelList
{
	public List<PanelBase> panels = new ArrayList<PanelBase>();
	
	public PanelBase getPanelById(int id)
	{
		for (PanelBase pb : panels)
		{
			if (pb.id == id)
			{
				return pb;
			}
		}
		return panels.get(0);
	}
	
	public PanelBase getPanelByName(String name)
	{
		for (PanelBase pb : panels)
		{
			if (pb.getName().equals(name))
			{
				return pb;
			}
		}
		return panels.get(0);
	}
	
}
