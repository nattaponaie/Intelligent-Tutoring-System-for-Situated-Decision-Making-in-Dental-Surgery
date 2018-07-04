package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

public class KFile
{
	int id;
	
	String number;
	
	String color;
	
	double diameter_d0;
	
	double diameter_d1;
	
	ArrayList<Double> available_lengths;

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

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public double getDiameter_d0()
	{
		return diameter_d0;
	}

	public void setDiameter_d0(double diameter_d0)
	{
		this.diameter_d0 = diameter_d0;
	}

	public double getDiameter_d1()
	{
		return diameter_d1;
	}

	public void setDiameter_d1(double diamter_d1)
	{
		this.diameter_d1 = diamter_d1;
	}

	public ArrayList<Double> getAvailable_lengths()
	{
		return available_lengths;
	}

	public void setAvailable_lengths(ArrayList<Double> available_lengths)
	{
		this.available_lengths = available_lengths;
	}
	
	
}
