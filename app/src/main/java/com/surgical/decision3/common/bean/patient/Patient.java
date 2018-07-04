package com.surgical.decision3.common.bean.patient;

import java.sql.Date;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Patient
{
	public Patient()
	{
		super();
	}

	public Patient(int id, String fn, String ln, int age, String sex)
	{
		super();
		this.id = id;
		this.firstname = fn;
		this.lastname = ln;
		this.age = age;
		this.sex = sex;
	}

	@Attribute
	private int id;

	@Attribute
	private String firstname;

	@Attribute
	private String lastname;

	@Attribute
	private int age;

	@Attribute
	private String sex;
	
	private Date DOB;
	
	
	public Date getDOB()
	{
		return DOB;
	}

	public void setDOB(Date dOB)
	{
		DOB = dOB;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

}
