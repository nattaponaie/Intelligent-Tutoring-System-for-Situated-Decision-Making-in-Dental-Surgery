package com.surgical.decision3.common.bean.intervention.forcePlayback;

import org.simpleframework.xml.Element;

public class ForcePlayback2
{

//	String xmlVDO = TestXMLConstants.RESPONSE_INTERVENTION_VDO;
//	public static String RESPONSE_INTERVENTION_VDO = "<?xml version='1.0' encoding='utf-8'?>"
//			+"<dataStream xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' requireImmediateResponse='true'>"
//			+"  <scene name='intraOperativeScene' event='USER_ACTION_' />"
//			+"  <dateTimeStamp date='2015/9/22' time='0:23:31' />"
//			+"  <studentAction>"
//			+"    <action_desc>User have three time error.</action_desc>"
//			+"    <action_code>CALL</action_code>"
//			+"    <semantic_action_code>FORCE_PLAYBACK_MODE</semantic_action_code>"
//			+"  </studentAction>"
//			+"</dataStream>";
	
	@Element (required=false)
	String action_code;
	
	@Element (required=false)
	String semantic_action_code;
	
	@Element (required=false)
	String action_desc;
	
	public ForcePlayback2()
	{
		
	}

	public String getAction_code()
	{
		return action_code;
	}

	public void setAction_code(String action_code)
	{
		this.action_code = action_code;
	}

	public String getSemantic_action_code()
	{
		return semantic_action_code;
	}

	public void setSemantic_action_code(String semantic_action_code)
	{
		this.semantic_action_code = semantic_action_code;
	}

	public String getAction_desc()
	{
		return action_desc;
	}

	public void setAction_desc(String action_desc)
	{
		this.action_desc = action_desc;
	}
	
	
	
	
}
