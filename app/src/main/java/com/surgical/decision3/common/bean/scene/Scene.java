package com.surgical.decision3.common.bean.scene;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root 
public class Scene
{
	@Attribute (required=false)
	private int id;
	
	@Attribute
	private String name;
	
	@Attribute
	private String event;
	
	public Scene()
	{
		super();
	}

	public Scene(int id, String name, String event)
	{
		super();
		this.id = id;
		this.name = name;
		this.event = event;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEvent()
	{
		return event;
	}

	public void setEvent(String event)
	{
		this.event = event;
	}

}
