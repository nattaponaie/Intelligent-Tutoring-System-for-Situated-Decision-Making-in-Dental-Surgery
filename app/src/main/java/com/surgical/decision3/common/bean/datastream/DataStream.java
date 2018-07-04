package com.surgical.decision3.common.bean.datastream;


import com.surgical.decision3.common.bean.intraoperative.IntraOperativeCollection;
import com.surgical.decision3.common.bean.preoperative.PreoperativeDataStream;
import com.surgical.decision3.common.bean.intervention.action.InterventionStudentAction;
import com.surgical.decision3.common.bean.scene.Scene;
import com.surgical.decision3.common.bean.dateTimeStamp.DateTimeStamp;
import com.surgical.decision3.common.bean.studentaction.StudentAction;
import com.surgical.decision3.common.bean.player.PlayCase;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "dataStream")
public class DataStream
{
	public DataStream()
	{
		studentActionList = new ArrayList<StudentAction>();
	}
	
	@ElementList(required=false)
	ArrayList<StudentAction> studentActionList;

	@Element(required=false)
	Scene scene;

	@Element (required=false)
	DateTimeStamp dateTimeStamp;

	@Element (required=false)
	PlayCase playcase;

	@Element (required=false)
	InterventionStudentAction interventionStudentAction;

	@Element (required=false)
	IntraOperativeCollection intraOperativeCollection;

	@Element (required=false)
	PreoperativeDataStream preoperativeData;

	@Attribute
	boolean requireImmediateResponse = false;

	
	public ArrayList<StudentAction> getStudentActionList()
	{
		return studentActionList;
	}

	public void setStudentActionList(ArrayList<StudentAction> studentActionList)
	{
		this.studentActionList = studentActionList;
	}

	public boolean isRequireImmediateResponse()
	{
		return requireImmediateResponse;
	}
}



