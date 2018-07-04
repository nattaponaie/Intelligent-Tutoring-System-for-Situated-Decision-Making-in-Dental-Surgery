package com.surgical.decision3.common.bean.answer;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class StudentPerformance
{
	@Attribute(required = false)
	int id;

	@Attribute(required = false)
	int play_id;

	@Element(required = false)
	int student_action_id;

	@Attribute(required = false)
	boolean isSuccess;

	@Attribute(required = false)
	String error_code;

	@Attribute(required = false)
	String error_description;
	
	public String getError_description()
	{
		return error_description;
	}

	public void setError_description(String error_description)
	{
		this.error_description = error_description;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPlay_id()
	{
		return play_id;
	}

	public void setPlay_id(int play_id)
	{
		this.play_id = play_id;
	}

	public int getStudent_action_id()
	{
		return student_action_id;
	}

	public void setStudent_action_id(int student_action_id)
	{
		this.student_action_id = student_action_id;
	}

	public boolean isSuccess()
	{
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess)
	{
		this.isSuccess = isSuccess;
	}

	public String getError_code()
	{
		return error_code;
	}

	public void setError_code(String error_code)
	{
		this.error_code = error_code;
	}

}
