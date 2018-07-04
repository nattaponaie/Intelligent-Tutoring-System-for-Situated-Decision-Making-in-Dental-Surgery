package com.surgical.decision3.common.bean.response;

import com.surgical.decision3.common.bean.answer.StudentSummary;
import com.surgical.decision3.common.bean.intervention.Intervention;
import com.surgical.decision3.common.bean.intraoperative.IntraOperativeCollection;
import com.surgical.decision3.common.bean.player.PlayCase;
import com.surgical.decision3.common.bean.preoperative.PreoperativeResponse;
import com.surgical.decision3.common.bean.question.Question;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root (name="response")
public class Response
{
	public Response()
	{
		super();
	}

	public Response(Intervention in)
	{
		super();
		this.intervention = in;
	}

	public Response(IntraOperativeCollection in)
	{
		super();
		this.intraOperativeCollection = in;
	}

	public Response(Question in)
	{
		super();
		this.question = in;
	}

	@Element (required=false)
	Question question;

	@Element (required=false)
	PreoperativeResponse preoperativeResponse;

	@Element (required=false)
	Intervention intervention;

	@Element (required=false)
	IntraOperativeCollection intraOperativeCollection;

	@Element (required=false)
	PlayCase playcase;

	@Element (required=false)
	StudentSummary studentSummary;

	@Attribute (required=false)
	String dateTime;

	public void setQuestion(Question question){ this.question = question; }

	public Question getQuestion(){ return this.question; }

	public String getDateTime()
	{
		return dateTime;
	}

	public void setDateTime(String dateTime)
	{
		this.dateTime = dateTime;
	}

	public StudentSummary getStudentSummary()
	{
		return studentSummary;
	}

	public void setStudentSummary(StudentSummary studentSummary)
	{
		this.studentSummary = studentSummary;
	}

	public PreoperativeResponse getPreoperativeResponse()
	{
		return preoperativeResponse;
	}

	public void setPreoperativeResponse(PreoperativeResponse preoperativeResponse)
	{
		this.preoperativeResponse = preoperativeResponse;
	}


	public Intervention getIntervention()
	{
		return intervention;
	}

	public void setIntervention(Intervention intervention)
	{
		this.intervention = intervention;
	}

	public IntraOperativeCollection getIntraOperativeCollection()
	{
		return intraOperativeCollection;
	}

	public void setIntraOperativeCollection(
			IntraOperativeCollection intraOperativeCollection)
	{
		this.intraOperativeCollection = intraOperativeCollection;
	}

	public PlayCase getPlaycase()
	{
		return playcase;
	}

	public void setPlaycase(PlayCase playcase)
	{
		this.playcase = playcase;
	}
}
