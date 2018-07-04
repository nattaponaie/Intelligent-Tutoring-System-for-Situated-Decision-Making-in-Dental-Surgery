package com.surgical.decision3.common.controller;

import com.surgical.decision3.common.bean.intervention.Intervention;
import com.surgical.decision3.common.bean.intervention.prompt.Prompt;
import com.surgical.decision3.common.bean.question.Choice;
import com.surgical.decision3.common.bean.question.Question;
import com.surgical.decision3.common.constant.StrategyConstants;

import java.util.List;

import fr.uga.pddl4j.parser.Exp;
import kb.common.controller.ParserController;
import kb.common.datastructure.graph.StudentGraph;
import kb.common.datastructure.solutionpath.Plan;
import kb.common.datastructure.solutionpath.PlanAction;
import kb.common.tutorpointer.TutorPointer;
import kb.util.InterventionStringUtil;

/**
 * Created by Dell on 7/6/2017.
 *
 * NOTE: Refine the teaching strategy
 * Strategy 12 --> strategy 6.2
 * Strategy 13 and 13a --> strategy 7.2, 7.3
 */

public class StrategyController
{
    public static PlanAction actionToDiscuss = null;

    public static Intervention receiveStudentsResponse(Intervention inIntervention )
    {
        Intervention responseIntervention = null;
        String responseToStrategy = null;

        //Get response Intervention
        if( inIntervention != null )
        {
            responseToStrategy = inIntervention.getInterventionData().getResponseToStrategy();
            if( responseToStrategy  != null )
            {
                if( responseToStrategy.equals(StrategyConstants.STRATEGY_6_1 ) ||
                        responseToStrategy.equals(StrategyConstants.STRATEGY_6_1_PARENT_PLAN ))
                {
                    if( inIntervention.getInterventionData().getStudentAnswer().isAnswer() )
                    {
                        responseIntervention =  InterventionGenerator.generateLeadingPositiveFeedback();
                    }
                    else
                    {
                        //student answer is not correct
                        Intervention inv1 =  InterventionGenerator.generateLeadingNegativeFeedback();
                        Question q = inIntervention.getInterventionData().getQuestion();


                        Intervention inv2 = null;

                        if( responseToStrategy.equals(StrategyConstants.STRATEGY_6_1 ) )
                        {
                            inv2 = InterventionGenerator.strategy_7_1_generateQuestionAboutCorrectAction( q ); //Reuse the question from 6.1 to 7.1
                        }
                        else if( responseToStrategy.equals(StrategyConstants.STRATEGY_6_1_PARENT_PLAN ) )
                        {
                            inv2 = InterventionGenerator.strategy_7_1_generateQuestionAboutCorrectAction_PARENT_PLAN( q ); //Reuse the question from 6.1 to 7.1


                        }

                        responseIntervention = mergeTwoInterventions( inv1, inv2 );
                    }
                }
                else if( responseToStrategy.equals(StrategyConstants.STRATEGY_6_2 ) )
                {
//                    responseIntervention = InterventionGenerator.strategy_6_2_responseFeedbackToStudentAnswer( inIntervention );
                    if( inIntervention.getInterventionData().getStudentAnswer().isAnswer() )
                    {
                        //Student answer is correct
                        //1. Feedback
                        //responseIntervention = InterventionGenerator.strategy_6_2_responseFeedbackToStudentAnswer( inIntervention );
                        responseIntervention =  InterventionGenerator.generateLeadingPositiveFeedback();
                    }
                    else
                    {
                        //student answer is not correct
                        //1. Tell the answer
                        //responseIntervention  = InterventionGenerator.strategy_9_tellTheAnswer( inIntervention.getInterventionData().getQuestion() );
                        Intervention inv1 =  InterventionGenerator.generateLeadingNegativeFeedback();
                        //Intervention inv2 = InterventionGenerator.strategy_7_2_generateQuestionAboutCorrectPerception();  //Change

                        Question q = inIntervention.getInterventionData().getQuestion();
                        Intervention inv2 = InterventionGenerator.strategy_7_2_generateQuestionAboutCorrectAction( q );  //Reuse the question from 6.2 to 7.2

                        responseIntervention = mergeTwoInterventions( inv1, inv2 );
                    }

                }
                else if( responseToStrategy.equals(StrategyConstants.STRATEGY_7_1 ) ||
                         responseToStrategy.equals(StrategyConstants.STRATEGY_7_1_PARENT_PLAN ))
                {
                    if( inIntervention.getInterventionData().getStudentAnswer().isAnswer() )
                    {
                        //FB+
                        responseIntervention = InterventionGenerator.generateLeadingPositiveFeedback();
                    }
                    else
                    {
                        //FB-
                        // Tell the answer
                        Intervention fbIntervention = InterventionGenerator.generateLeadingNegativeFeedback();
                        Intervention inv2  = InterventionGenerator.strategy_9_tellTheAnswer( inIntervention.getInterventionData().getQuestion() );
                        responseIntervention = mergeTwoInterventions( fbIntervention, inv2 );
                    }
                }
                else if( responseToStrategy.equals(StrategyConstants.STRATEGY_7_2 ) )
                {
                    if( inIntervention.getInterventionData().getStudentAnswer().isAnswer() )
                    {
                        //correct
                        //FB+
                        responseIntervention = InterventionGenerator.generateLeadingPositiveFeedback();
                    }
                    else
                    {
                        //FB-
                        // Tell the answer
                        Intervention fbIntervention = InterventionGenerator.generateLeadingNegativeFeedback();
                        Intervention inv2  = InterventionGenerator.strategy_9_tellTheAnswer( inIntervention.getInterventionData().getQuestion() );
                        responseIntervention = mergeTwoInterventions( fbIntervention, inv2 );
                    }

                }
                else if( responseToStrategy.equals(StrategyConstants.STRATEGY_7_3 ) )
                {
                    //responseIntervention = InterventionGenerator.strategy13_responseToStudentAnswer( inIntervention );

                    //If student's answer correctly,
                    if( inIntervention.getInterventionData().getStudentAnswer().isAnswer() )
                    {
                        //Positive feedback
                        Intervention fbIntervention = InterventionGenerator.generateLeadingPositiveFeedback();

                        //------------------
                        //Test //TEST Strategy 8 and 10
                        //------------------
                        Exp correctChoice = inIntervention.getInterventionData().getQuestion().getCorrectChoice().getChoice_exp();
                        Intervention inv2  = InterventionGenerator.strategy_11_generateReconfirmTheAction( correctChoice );
                        responseIntervention = mergeTwoInterventions( fbIntervention, inv2 );

//                        responseIntervention = InterventionGenerator.strategy_8_generateYesNoQuestion( inIntervention.getInterventionData().getQuestion( ) );

                        /*
                        //------------------
                        //RESERVE FOR DEMO (skip 8 and 10)
                        //------------------
                        //If student's answer is not correct.
                        //generate hints and ask the same question.
//                        responseIntervention = InterventionGenerator.strategy_10_generateHints( q, responseIntervention );

                        //1. Feedback
                        Intervention inv1  = InterventionGenerator.generateLeadingPositiveFeedback();   //strategy_7_2_responseFeedbackToStudentAnswer( inIntervention );

                        //2. Reconfirm Action
                        Intervention inv2  = InterventionGenerator.strategy_11_generateReconfirmTheAction( null );

                        //Finalize intervention
                        responseIntervention = mergeTwoInterventions( inv1, inv2 );

                        */
                    }
                    else
                    {
                        Intervention fbIntervention = InterventionGenerator.generateLeadingNegativeFeedback();

                        Choice studentSelectedChoice = inIntervention.getInterventionData().getStudentAnswer();
                        Question ref_q = inIntervention.getInterventionData().getQuestion( );

                        //------------------
                        //TEST Strategy 8 and 10
                        //------------------
                        Intervention hintIntervention = InterventionGenerator.strategy_10_generateHints( inIntervention.getInterventionData().getQuestion( ) );

                        Intervention inv1 = mergeTwoInterventions(fbIntervention, hintIntervention );

                        //strategy_8_generateYesNoQuestion_fromCorrectChoice
                        Intervention inv2 = InterventionGenerator.strategy_8_generateYesNoQuestion( ref_q, studentSelectedChoice );
                        //Finalize intervention
                        responseIntervention = mergeTwoInterventions( inv1, inv2 );

                        /*
                        //------------------
                        //RESERVE FOR DEMO (Skip 8 and 10)
                        //------------------
                        //if student does not answer correctly.
                        //1. Feedback
                        Intervention inv1  = InterventionGenerator.generateLeadingNegativeFeedback();  //InterventionGenerator.//strategy_7_2_responseFeedbackToStudentAnswer( inIntervention );

                        //2. Tell the answer
                        Intervention inv2  = InterventionGenerator.strategy_9_tellTheAnswer( inIntervention.getInterventionData().getQuestion() );

                        //3. Reconfirm Action
                        Intervention inv3 = InterventionGenerator.strategy_11_generateReconfirmTheAction( null );

                        Intervention tempInv = mergeTwoInterventions( inv1, inv2 );
                        responseIntervention = mergeTwoInterventions( tempInv, inv3 );
                        */
                    }
                } //else if( responseToStrategy.equals(StrategyConstants.STRATEGY_7_3 ) )
                else if( responseToStrategy.equals(StrategyConstants.STRATEGY_8 ) )
                {
                    if( inIntervention.getInterventionData().getStudentAnswer().isAnswer() )
                    {
                        //1. Feedback
                        Intervention inv1  = InterventionGenerator.generateLeadingPositiveFeedback();   //strategy_7_2_responseFeedbackToStudentAnswer( inIntervention );

                        //2. Reconfirm Action (if student answers correctly, use correct choice from the reference question
                        Question current_q = inIntervention.getInterventionData().getQuestion();
                        Intervention inv2 = null;

                        if( current_q.getReferenceQuestion() != null )
                        {
                            Exp correctChoice_fromRefQuestion = current_q.getReferenceQuestion().getCorrectChoice().getChoice_exp();
                            inv2  = InterventionGenerator.strategy_11_generateReconfirmTheAction( correctChoice_fromRefQuestion );
                        }
                        else
                        {
                            Exp correctChoice = current_q.getCorrectChoice().getChoice_exp();
                            inv2  = InterventionGenerator.strategy_11_generateReconfirmTheAction( correctChoice );
                        }

                        //Finalize intervention
                        responseIntervention = mergeTwoInterventions( inv1, inv2 );
                    }
                    else
                    {
                        //RESERVE FOR DEMO
                        //if student does not answer correctly.
                        //1. Feedback
                        Intervention inv1  = InterventionGenerator.generateLeadingNegativeFeedback();  //InterventionGenerator.//strategy_7_2_responseFeedbackToStudentAnswer( inIntervention );

                        //2. Tell the answer
                        Intervention inv2  = InterventionGenerator.strategy_9_tellTheAnswer( inIntervention.getInterventionData().getQuestion() );

                        //3. Reconfirm Action
                        Question current_q = inIntervention.getInterventionData().getQuestion();
                        Intervention inv3 = null;

                        if( current_q.getReferenceQuestion() != null )
                        {
                            Exp correctChoice_fromRefQuestion = current_q.getReferenceQuestion().getCorrectChoice().getChoice_exp();
                            inv3  = InterventionGenerator.strategy_11_generateReconfirmTheAction( correctChoice_fromRefQuestion );
                        }
                        else
                        {
                            Exp correctChoice = current_q.getCorrectChoice().getChoice_exp();
                            inv3  = InterventionGenerator.strategy_11_generateReconfirmTheAction( correctChoice );
                        }

                        Intervention tempInv = mergeTwoInterventions( inv1, inv2 );
                        responseIntervention = mergeTwoInterventions( tempInv, inv3 );

                    }
                } //end else if( responseToStrategy.equals(StrategyConstants.STRATEGY_8 ) )
                else
                {
                    //other strategy
                }
            }
        }

        if( responseIntervention != null )
        {
            responseIntervention.printOut();
        }

        return responseIntervention;
    }

    //TODO: You should follow the flow of application
    public static Intervention evaluateStudentAction()
    {
        boolean isActionCorrect = ApplicationController.isActionCorrect;
        boolean isSatisfyDO = ApplicationController.isEffectSatisfyDesiredOutcomes;

        actionToDiscuss = ApplicationController.currentAction;

        Intervention intervention = null;

        //NOTE: if it is the first step of operation, which is usually LA, ask for Perception.
        //(if student skip the step of LA, ask Why don't LA?

        PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ? StrategyController.actionToDiscuss : ApplicationController.currentAction;

        if(isActionCorrect)
        {
            if(isSatisfyDO)
            {
                // Action is correct and D.O. is satisfied
                //Generate the FB+
                //intervention = InterventionGenerator.generateLeadingPositiveFeedback();

                //6.2
                //NUI: Temp Comment for Test 6.1
                //intervention = InterventionGenerator.strategy_6_2_generateQuestionReconfirmConsequence();

                //Test
                //----------------
                // Nui add Trigger
                //----------------
                //If the current action is the first step of the operation, ask P. it is usually important.
                //NOTE: Test 6.1 //NOTE
                //6.1
                if( TutorPointer.isFirstActionOfPlan( currentAction ) )
                {
                    //Check if the parent plan has optional
                    Plan parentPlan = currentAction.getParentPlan();
                    Exp optionalExp = parentPlan.getOptionalCondition();

                    String basePredicate_optional = InterventionStringUtil.getTheBasePredicateFromExp( optionalExp );

                    //getCurrent Situation
//                    StudentGraph stGraph = StudentGraphUtil.getCurrentStudentGraph();
//                    StudentGraphNode stGraphStateNode = stGraph.getStateNode();
                    //NOTE: Perception List EXP keeps only the base predicate --> this is not correct.

                    Exp currentFactExp = ParserController.problem.getInitialFactsByBasePredicate( basePredicate_optional );

//                  HashMap pcHashMap = stGraphStateNode.getPerceptionWithComprehensionHashMap();
                    if( optionalExp != null
                            && !optionalExp.equals( currentFactExp) )
                    {
                        //there are some conditions that allow to skip the parent plan.
                        intervention = InterventionGenerator.strategy_6_1_generateQuestionReconfirmPerception_referToParentPlan( optionalExp );
                    }
                    else
                    {
                        //Parent plan is required,
                        //Ask normal 6.1
                        intervention = InterventionGenerator.strategy_6_1_generateQuestionReconfirmPerception();
                        if( intervention == null )
                        {
                            intervention = InterventionGenerator.strategy_6_2_generateQuestionReconfirmConsequence();
                        }
                    }
                }
            }
            else
            {
                // Action is correct but D.O. is not satisfied
                System.err.println( "--> NUI: Action is correct but D.O. is not satisfied" );

                Intervention inv0 = null;

                if( isStudentRepeatTheSameAction() )
                {
                    inv0 = InterventionGenerator.strategy_14_generateHintForRepeatedMistakes();
                    Intervention inv1 = InterventionGenerator.generateNegativeFeedback();  //This is correct intervention
                    Intervention temp = mergeTwoInterventions( inv0 , inv1  );

                    //Intervention inv2 = InterventionGenerator.strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction_nonCompound();
                    Intervention inv2 = InterventionGenerator.strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction();
                    intervention = mergeTwoInterventions( temp , inv2  );
                }
                else
                {
                    Intervention inv1 = InterventionGenerator.generateNegativeFeedback();  //This is correct intervention
                    //intervention = InterventionGenerator.strategy_7_2_generateQuestionAboutCorrectPerception();  //Test only

                    //Intervention inv2 = InterventionGenerator.strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction_nonCompound();
                    Intervention inv2 = InterventionGenerator.strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction();

                    //Merge
                    intervention = mergeTwoInterventions( inv1 , inv2  );
                }
            }
        }
        else //action is not correct
        {
            System.err.println( "--> NUI: Action is not correct" );
            //Test only
            //intervention = InterventionGenerator.strategy_6_2_generateQuestionReconfirmConsequence(); //Test for reconfirm actoin, but trigger for reconfirm action is not this.
            //Give the FB -, and a hint - what kind of action to [Pj Node of the current correct action ]

            //TODO: Generate the hint about the correct action
            //Think about the action that makes [PJ_NODES]
            intervention = InterventionGenerator.strategy_x_generateHintForIncorrectAction();
        }

        if( intervention != null )
        {
            intervention.printOut();
        }

        return intervention;
    }

    public static Intervention mergeTwoInterventions(Intervention inv1, Intervention inv2 )
    {
        //inv 1 comes before inv2
        Intervention mergedIntervention = null;

        if( inv1 == null )
        {
            return inv2;
        }
        else if( inv2 == null )
        {
           return  inv1;
        }

        //Case 1, Prompt and prompt, use prompt1 as main priority
        if( inv1.getInterventionData().getPrompt() != null && inv2.getInterventionData().getPrompt() != null )
        {
           Prompt inv1Prompt = inv1.getInterventionData().getPrompt();
           Prompt inv2Prompt = inv2.getInterventionData().getPrompt();

           //inv1Prompt.getPromptMsg().getText().concat( "\n\n" + inv2Prompt.getPromptMsg().getText() );
            String mergePrompt = inv1Prompt.getPromptMsg().getText() + "\n\n" + inv2Prompt.getPromptMsg().getText() + "\n\n";
            inv1.getInterventionData().getPrompt().getPromptMsg().setText( mergePrompt );

            //Add merge strategy name for printing out
            String mergeStrategy = inv1.getStrategy_name() + ", " + inv2.getStrategy_name();
            inv1.setStrategy_name( mergeStrategy );

            mergedIntervention = inv1;
        }
        //Case 1, Prompt and question, use question as main priority
        else if( inv1.getInterventionData().getPrompt() != null && inv2.getInterventionData().getQuestion() != null )
        {
            Prompt inv1Prompt = inv1.getInterventionData().getPrompt();
            Question inv2Question = inv2.getInterventionData().getQuestion();

            String inv2QuestionString = inv1Prompt.getPromptMsg().getText() + "\n\n" + inv2Question.getQuestion();
            inv2Question.setQuestion( inv2QuestionString );

            //Add merge strategy name for printing out
            // Add for print out
            String mergeStrategy = inv1.getStrategy_name() + ", " + inv2.getStrategy_name();
            inv2.setStrategy_name( mergeStrategy );

            mergedIntervention = inv2;
        }
        else if( inv1.getInterventionData().getQuestion() != null && inv2.getInterventionData().getPrompt() != null )
        {

            //If the first intervention is question, but second is prompt. question has higher priority, so, return question.
            mergedIntervention = inv1;
        }
        else if( inv1.getInterventionData().getQuestion() != null && inv2.getInterventionData().getQuestion()  != null )
        {
            //if both are questions, impossible
            mergedIntervention = null;
        }

        return mergedIntervention;
    }

    private static boolean isStudentRepeatTheSameAction()
    {
        boolean isRepeatedAction = false;

        List<StudentGraph> studentGraphList = ApplicationController.studentGraphList;
//        StudentGraph stGraph = studentGraphList.get( studentGraphList.size() -1 );
//        StudentGraphNode stPjNodeNotDO = stGraph.getProjectionNodeByDesiredOutcome( false );

        if( studentGraphList.size() > 1 )
        {
            //Get the last action
            StudentGraph stGraph = studentGraphList.get( studentGraphList.size() -1 );
            PlanAction theCurrentAction = stGraph.getActionNode().getActionNode();

            StudentGraph stGraph2 = studentGraphList.get( studentGraphList.size() -2 );
            PlanAction thePreviousAction = stGraph2.getActionNode().getActionNode();

            if( theCurrentAction.getActionName().equals( thePreviousAction.getActionName() ) )
            {
                isRepeatedAction = true;
            }
        }

        return isRepeatedAction;
    }

}
