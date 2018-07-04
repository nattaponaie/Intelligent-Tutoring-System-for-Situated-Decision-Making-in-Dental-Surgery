package com.surgical.decision3.common.bean.intervention;

import com.surgical.decision3.common.bean.intervention.prompt.Prompt;
import com.surgical.decision3.common.bean.procedure.Domain;
import com.surgical.decision3.common.bean.procedure.PatientCase;
import com.surgical.decision3.common.bean.dateTimeStamp.DateTimeStamp;
import com.surgical.decision3.common.bean.procedure.Procedure;
import com.surgical.decision3.common.bean.procedure.SubDomain;
import com.surgical.decision3.common.bean.procedure.SubProcedure;
import com.surgical.decision3.common.bean.procedure.SubProcedureStep;
import com.surgical.decision3.common.bean.question.Question;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Intervention
{
	public Intervention()
	{
		super();
	}

	public Intervention(PatientCase pCase, Domain domain, SubDomain subDomain,
						Procedure procedure, SubProcedure subProcedure,
						SubProcedureStep subProcedureStep, DateTimeStamp dateTimeStamp)
	{
		super();
		this.patientCase = pCase;
		this.domain = domain;
		this.subDomain = subDomain;
		this.procedure = procedure;
		this.subProcedure = subProcedure;
		this.subProcedureStep = subProcedureStep;
		this.dateTimeStamp = dateTimeStamp;
	}





	@Element(required = false)
	InterventionData interventionData;

	@Element(name = "case", required = false)
	PatientCase patientCase;

	@Element(required = false)
	Domain domain;

	@Element(required = false)
	SubDomain subDomain;

	@Element(required = false)
	Procedure procedure;

	@Element(required = false)
	SubProcedure subProcedure;

	@Element(required = false)
	SubProcedureStep subProcedureStep;

	@Element(required = false)
	DateTimeStamp dateTimeStamp;

	public SubDomain getSubDomain()
	{
		return subDomain;
	}

	public void setSubDomain(SubDomain subDomain)
	{
		this.subDomain = subDomain;
	}

	public PatientCase getPatientCase()
	{
		return patientCase;
	}

	public void setPatientCase(PatientCase pCase)
	{
		this.patientCase = pCase;
	}

	public Domain getDomain()
	{
		return domain;
	}

	public void setDomain(Domain domain)
	{
		this.domain = domain;
	}

	public Procedure getProcedure()
	{
		return procedure;
	}

	public void setProcedure(Procedure procedure)
	{
		this.procedure = procedure;
	}

	public SubProcedure getSubProcedure()
	{
		return subProcedure;
	}

	public void setSubProcedure(SubProcedure subProcedure)
	{
		this.subProcedure = subProcedure;
	}

	public SubProcedureStep getSubProcedureStep()
	{
		return subProcedureStep;
	}

	public void setSubProcedureStep(SubProcedureStep subProcedureStep)
	{
		this.subProcedureStep = subProcedureStep;
	}

	public DateTimeStamp getDateTimeStamp()
	{
		return dateTimeStamp;
	}

	public void setDateTimeStamp(DateTimeStamp dateTimeStamp)
	{
		this.dateTimeStamp = dateTimeStamp;
	}

	public InterventionData getInterventionData()
	{
		return interventionData;
	}

	public void setInterventionData(InterventionData interventionData)
	{
		this.interventionData = interventionData;
	}

	String strategy_name;

	public String getStrategy_name()
	{
		return strategy_name;
	}

	public void setStrategy_name(String strategy_name)
	{
		this.strategy_name = strategy_name;
	}

	public void printOut()
	{
		System.out.println( "============================================================================" );
		System.out.println( "=============== PRINT OUT INTERVENTION, Strategy : " + this.getStrategy_name() + " ===============" );


		Question q = this.getInterventionData().getQuestion();
		Prompt p = this.getInterventionData().getPrompt();
		renderResults( q, p );

		System.out.println( "================ END PRINT OUT INTERVENTION ================================" );
		System.out.println( "============================================================================" );
	}

    private void renderResults(Question q, Prompt p )
    {
        if( q != null )
        {
            //There is a question , and might have prompt
            printOutQuestionAndPrompt( q, p );
        }
        else
        {
            //Prompt P
            if( p!= null )
            {
				printOutPrompt( p );
            }
        }
    }

    private void printOutPrompt( Prompt p )
    {
        System.out.println( "===============================" );
        System.out.println( "---- INTERVENTION : PROMPT ----" );
        System.out.println( p.getPromptMsg().getText() );
        System.out.println( "===============================" );
    }

    private void printOutQuestionAndPrompt(Question q, Prompt p )
    {
		if( p != null )
		{
			printOutPrompt( p );
		}

        String tutorIntString = q.populateQuestionChoicesString();

        System.out.println( "=================================" );
        System.out.println( "---- INTERVENTION : QUESTION ----" );
        System.out.println( tutorIntString );
        System.out.println( "=================================" );
    }
}
