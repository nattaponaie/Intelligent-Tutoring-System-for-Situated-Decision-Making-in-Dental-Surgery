package com.surgical.decision3.common.bean.radiograph;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class Radiograph
{
	public Radiograph()
	{
		super();
	}
	
	//id	patient_case_id	patient_id	dateTaken	imagePath
	public Radiograph( int id, String name, String dateString, String imgUrl )
	{
		super();
		this.id = id;
		this.name = name;
		this.dateTaken = dateString;
		this.imgUrl = imgUrl;
	}
	
//<radiograph id='' name='' dateTaken='' imgUrl='' />
	
	//id	patient_case_id	patient_id	dateTaken	imagePath
	
	@Attribute
	private int id;
	
	@Attribute (required=false)
	private int patient_id;
	
	@Attribute (required=false)
	private int patient_case_id;
	
	@Attribute 
	private String name;
	
	@Attribute
	private String dateTaken;
	
	@Attribute
	private String imgUrl;		//imageFilename

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

	public String getDateTaken()
	{
		return dateTaken;
	}

	public void setDateTaken(String dateTaken)
	{
		this.dateTaken = dateTaken;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}
	
	public int getPatient_id()
	{
		return patient_id;
	}

	public void setPatient_id(int patient_id)
	{
		this.patient_id = patient_id;
	}

	public int getPatient_case_id()
	{
		return patient_case_id;
	}

	public void setPatient_case_id(int patient_case_id)
	{
		this.patient_case_id = patient_case_id;
	}
	
	
}
