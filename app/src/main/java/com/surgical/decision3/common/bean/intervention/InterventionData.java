package com.surgical.decision3.common.bean.intervention;

import com.surgical.decision3.common.bean.intervention.forcePlayback.ForcePlayback;
import com.surgical.decision3.common.bean.intervention.forcePlayback.ForcePlayback2;
import com.surgical.decision3.common.bean.question.Choice;
import com.surgical.decision3.common.bean.question.Question;
import com.surgical.decision3.common.bean.tool.PrepareSet;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.surgical.decision3.common.bean.intervention.prompt.Prompt;

@Root
public class InterventionData
{

	public InterventionData()
	{
		super();
	}

	public InterventionData(InterventionType interventionType,
			InterventionModel interventionModel, Prompt prompt,
			ForcePlayback forcePlayback)
	{
		super();
		this.interventionType = interventionType;
		this.interventionModel = interventionModel;
		this.prompt = prompt;
		this.forcePlayback = forcePlayback;
	}

	@Element (required=false)
	private InterventionType interventionType;

	@Element (required=false)
	private InterventionModel interventionModel;

	@Element (required=false)
	private Prompt prompt;

	@Element (required=false)
	private ForcePlayback forcePlayback;

	@Element (required=false)
	private ForcePlayback2 forcePlayback2;
	
	@Element (required=false)
	private PrepareSet prepareSet;

	@Element (required=false)
	Question question;

	@Element (required=false)
	private boolean isQuestionAnswerSession;			//always true when tutor sends question to student and student answer

	@Element (required=false)
	private boolean isAnswerQuestion;					//When student send the answer back to the tutor, this is true

	@Element (required =  false )
	private Choice studentAnswer;

	@Element (required =  false )
	private String responseToStrategy;

	public String getResponseToStrategy()
	{
		return responseToStrategy;
	}

	public void setResponseToStrategy(String responseToStrategy)
	{
		this.responseToStrategy = responseToStrategy;
	}

	public boolean isQuestionAnswerSession()
	{
		return isQuestionAnswerSession;
	}

	public void setQuestionAnswerSession(boolean questionAnswerSession)
	{
		isQuestionAnswerSession = questionAnswerSession;
	}

	public boolean isAnswerQuestion()
	{
		return isAnswerQuestion;
	}

	public void setAnswerQuestion(boolean answerQuestion)
	{
		isAnswerQuestion = answerQuestion;
	}

	public Choice getStudentAnswer()
	{
		return studentAnswer;
	}

	public void setStudentAnswer(Choice studentAnswer)
	{
		this.studentAnswer = studentAnswer;
	}

	public Question getQuestion()
	{
		return question;
	}

	public Choice getChoiceByIndex(int index )
	{
		if(index < this.question.getChoices().size() )
		{
			//If index is less than size,

			return this.question.getChoices().get( index );
		}

		return null;
	}

	public void setQuestion(Question question)
	{
		this.question = question;
	}
	
	public PrepareSet getPrepareSet()
	{
		return prepareSet;
	}

	public void setPrepareSet(PrepareSet prepareSet)
	{
		this.prepareSet = prepareSet;
	}

	public InterventionType getInterventionType()
	{
		return interventionType;
	}

	public void setInterventionType(InterventionType interventionType)
	{
		this.interventionType = interventionType;
	}

	public InterventionModel getInterventionModel()
	{
		return interventionModel;
	}

	public void setInterventionModel(InterventionModel interventionModel)
	{
		this.interventionModel = interventionModel;
	}

	public Prompt getPrompt()
	{
		return prompt;
	}

	public void setPrompt(Prompt prompt)
	{
		this.prompt = prompt;
	}

	public ForcePlayback getForcePlayback()
	{
		return forcePlayback;
	}

	public void setForcePlayback(ForcePlayback forcePlayback)
	{
		this.forcePlayback = forcePlayback;
	}

	public ForcePlayback2 getForcePlayback2()
	{
		return forcePlayback2;
	}

	public void setForcePlayback2(ForcePlayback2 forcePlayback2)
	{
		this.forcePlayback2 = forcePlayback2;
	}

}
