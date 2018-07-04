package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

public class GuttaPercha
{
	int id;
	String number;
	ArrayList<Double> availableLength;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public ArrayList<Double> getAvailableLength()
	{
		return availableLength;
	}

	public void setAvailableLength(ArrayList<Double> availableLength)
	{
		this.availableLength = availableLength;
	}
}
