package com.surgical.decision3.common.bean.tool;

import java.util.ArrayList;

public class Lentulo
{
	int id;
	String number;
	String number2;
	String color;
	
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

	public String getNumber2()
	{
		return number2;
	}

	public void setNumber2(String number2)
	{
		this.number2 = number2;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
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
// id no no2 length color
// 1 1 25 12 red
