package com.surgical.decision3.common.bean.tooth;

public class BaseTooth
{
	public BaseTooth()
	{
		super();
	}

	int toothNumber;
	String toothName;
	String toothPosition;
	String arch1;
	String arch2;
	String outerSurfaceName;
	String innerSurfaceName;
	String bitingSurfaceName;

	boolean isPermanent;

	public int getToothNumber()
	{
		return toothNumber;
	}

	public void setToothNumber(int toothNo)
	{
		this.toothNumber = toothNo;
	}

	public String getToothName()
	{
		return toothName;
	}

	public void setToothName(String toothName)
	{
		this.toothName = toothName;
	}

	public String getToothPosition()
	{
		return toothPosition;
	}

	public void setToothPosition(String toothPosition)
	{
		this.toothPosition = toothPosition;
	}

	public String getArch1()
	{
		return arch1;
	}

	public void setArch1(String arch1)
	{
		this.arch1 = arch1;
	}

	public String getArch2()
	{
		return arch2;
	}

	public void setArch2(String arch2)
	{
		this.arch2 = arch2;
	}

	public String getOuterSurfaceName()
	{
		return outerSurfaceName;
	}

	public void setOuterSurfaceName(String outerSurfaceName)
	{
		this.outerSurfaceName = outerSurfaceName;
	}

	public String getInnerSurfaceName()
	{
		return innerSurfaceName;
	}

	public void setInnerSurfaceName(String innerSurfaceName)
	{
		this.innerSurfaceName = innerSurfaceName;
	}

	public String getBitingSurfaceName()
	{
		return bitingSurfaceName;
	}

	public void setBitingSurfaceName(String bitingSurfaceName)
	{
		this.bitingSurfaceName = bitingSurfaceName;
	}

}
