package com.surgical.decision3.common.bean.tooth;

import com.surgical.decision3.common.bean.tooth.rootcanal.RootCanal;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class WorkingTooth extends BaseTooth
{
	public WorkingTooth()
	{
		super();
		this.rootCanals = new ArrayList<RootCanal>();
	}

	private int case_id;

	@Attribute
	private int id;

	@Attribute
	private int toothNo;

	@Attribute
	private String name;

	@Attribute
	private boolean isPermanent;

	@Attribute
	private boolean isMandibular;

	@Attribute
	private double wholelength;

	// private boolean isWorkingTooth;

	// Another type

	@Element
	Enamel enamel;

	@Element
	Dentine dentine;

	@Element
	PulpChamber pulpChamber;

	@ElementList(name = "roots")
	ArrayList<RootCanal> rootCanals;

	// public boolean isWorkingTooth()
	// {
	// return isWorkingTooth;
	// }
	//
	// public void setWorkingTooth(boolean isWorkingTooth)
	// {
	// this.isWorkingTooth = isWorkingTooth;
	// }

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getToothNumber()
	{
		return toothNo;
	}

	public void setToothNumber(int toothNumber)
	{
		this.toothNo = toothNumber;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isPermanent()
	{
		return isPermanent;
	}

	public void setPermanent(boolean isPermanent)
	{
		this.isPermanent = isPermanent;
	}

	public boolean isMandibular()
	{
		return isMandibular;
	}

	public void setMandibular(boolean isMandibular)
	{
		this.isMandibular = isMandibular;
	}

	public double getWholelength()
	{
		return wholelength;
	}

	public void setWholelength(double wholelength)
	{
		this.wholelength = wholelength;
	}

	public int getToothNo()
	{
		return toothNo;
	}

	public void setToothNo(int toothNo)
	{
		this.toothNo = toothNo;
	}

	public Enamel getEnamel()
	{
		return enamel;
	}

	public void setEnamel(Enamel enamel)
	{
		this.enamel = enamel;
	}

	public Dentine getDentine()
	{
		return dentine;
	}

	public void setDentine(Dentine dentine)
	{
		this.dentine = dentine;
	}

	public PulpChamber getPulpChamber()
	{
		return pulpChamber;
	}

	public void setPulpChamber(PulpChamber pulpChamber)
	{
		this.pulpChamber = pulpChamber;
	}

	public ArrayList<RootCanal> getRootCanals()
	{
		return rootCanals;
	}

	public void setRootCanals(ArrayList<RootCanal> rootCanals)
	{
		this.rootCanals = rootCanals;
	}

	public int getCase_id()
	{
		return case_id;
	}

	public void setCase_id(int case_id)
	{
		this.case_id = case_id;
	}
}
