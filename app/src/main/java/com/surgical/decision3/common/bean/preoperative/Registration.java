package com.surgical.decision3.common.bean.preoperative;

public class Registration
{
	String username; // email
	String password;
	String confirmedPassword;
	String firstname;
	String lastname;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getConfirmedPassword()
	{
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword)
	{
		this.confirmedPassword = confirmedPassword;
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

}
