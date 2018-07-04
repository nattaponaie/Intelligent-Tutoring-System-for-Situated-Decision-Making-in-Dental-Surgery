package com.surgical.decision3.common.bean.question;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;

import fr.uga.pddl4j.parser.Exp;

/**
 * Created by mess on 5/24/2017.
 */

public class Question
{
    public Question()
    {
        super();
    }

    @Attribute(required=false)
    String question;

    @Attribute(required=false)
    Boolean isMultipleChoices = false;

    @ElementList(required=false)
    ArrayList<Choice> choices;

    //Nui Reference question (in the case that the question is generated from the previous question, e.g. strategy 8, the reference question is not null;
    @ElementList(required=false)
    Question referenceQuestion;

    public Question getReferenceQuestion()
    {
        return referenceQuestion;
    }

    public void setReferenceQuestion(Question referenceQuestion)
    {
        this.referenceQuestion = referenceQuestion;
    }

    @ElementList(required=false)
    Exp referenceExp;

    public Exp getReferenceExp()
    {
        return referenceExp;
    }

    public void setReferenceExp(Exp referenceExp)
    {
        this.referenceExp = referenceExp;
    }

    public void setQuestion(String question){ this.question = question; }

    public String getQuestion(){ return this.question; }

    public void setChoices(ArrayList<Choice> choice){ this.choices = choice; }

    public ArrayList<Choice> getChoices(){ return this.choices; }

    public Boolean getMultipleChoices() {
        return isMultipleChoices;
    }

    public void setMultipleChoices(Boolean multipleChoices) {
        isMultipleChoices = multipleChoices;
    }

    public String populateQuestionChoicesString()
    {
        String questionChoicesString = "";
        questionChoicesString += this.getQuestion();
        if(isMultipleChoices)
        {
            for( Choice c : this.getChoices() )
            {
                questionChoicesString += "\n" +
                                         ( this.getChoices().indexOf( c ) + 1 ) +
                                         " " +
                                         c.getChoiceDescription();
            }
        }

        System.out.println( "========== Question ==============" );
        System.out.println( questionChoicesString );
        System.out.println( "==================================" );

        return questionChoicesString;
    }

    public Choice getCorrectChoice()
    {
        for(Choice c: choices )
        {
            if( c.isAnswer() )
            {
                return c;
            }
        }

        return null;
    }
}
