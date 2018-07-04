package com.surgical.decision3.common.bean.question;

import com.surgical.decision3.common.controller.InterventionGenerator;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import fr.uga.pddl4j.parser.Exp;
import kb.util.InterventionStringUtil;

/**
 * Created by mess on 5/29/2017.
 */

@Root
public class Choice {
    public Choice(){
        super();
    }

    // Choice( Exp choice_exp, boolean isAnswer, boolean isYesNoChoice, boolean islevel3  )
    public Choice( Exp choice_exp, boolean isAnswer, boolean isYesNoChoice ) {
        super();
        //this.choiceDescription = null;
        this.isAnswer = isAnswer;
        this.choice_exp = choice_exp;
        this.isYesNoChoice = isYesNoChoice;

        if( isYesNoChoice )
        {
            this.choiceDescription = (isAnswer) ? "Yes" : "No";
        }
        else
        {
            //String choice_label_x = StringUtil.transformExpToFinalText( this.choiceExp );
            this.choiceDescription = InterventionStringUtil.transformExpToFinalText( choice_exp );
//            String tempDescription = InterventionGenerator.makeTheFirstStringCapital( this.choiceDescription );
//            this.choiceDescription = tempDescription + ".";
        }

    }

    public void capitalizeTheFirstString()
    {
        String tempDescription = InterventionGenerator.makeTheFirstStringCapital( this.choiceDescription );
        this.choiceDescription = tempDescription;
    }

    //For Testing only.
    public Choice( String choice_desc, boolean isAnswer, boolean isYesNoChoice ) {
        super();
        //this.choiceDescription = null;
        this.isAnswer = isAnswer;
//        this.choice_exp = choice_exp;
        this.isYesNoChoice = isYesNoChoice;

        //String choice_label_x = StringUtil.transformExpToFinalText( this.choiceExp );
        this.choiceDescription = choice_desc;
        this.isSelected = false;
    }

//    @Attribute(required=false)
//    int choiceNumber;
    Exp choice_exp;

    @Attribute(required=false)
    String choiceDescription;

    @Attribute(required=false)
    boolean isAnswer;

    @Attribute(required=false)
    boolean isYesNoChoice;

    @Attribute(required=false)
    boolean isSelected;

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public Exp getChoice_exp()
    {
        return choice_exp;
    }

    public void setChoice_exp(Exp choice_exp)
    {
        this.choice_exp = choice_exp;
    }

    public boolean isYesNoChoice()
    {
        return isYesNoChoice;
    }

    public void setYesNoChoice(boolean yesNoChoice)
    {
        isYesNoChoice = yesNoChoice;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public String getChoiceDescription() {
        return choiceDescription;
    }

    public void setChoiceDescription(String choiceDescription) {
        this.choiceDescription = choiceDescription;
    }
}
