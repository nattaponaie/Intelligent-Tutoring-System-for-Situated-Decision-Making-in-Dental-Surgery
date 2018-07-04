package com.surgical.decision3.common.controller;

import com.surgical.decision3.common.bean.intervention.Intervention;
import com.surgical.decision3.common.bean.intervention.InterventionData;
import com.surgical.decision3.common.bean.intervention.prompt.Prompt;
import com.surgical.decision3.common.bean.intervention.prompt.PromptMsg;
import com.surgical.decision3.common.bean.question.Choice;
import com.surgical.decision3.common.bean.question.Question;
import com.surgical.decision3.common.constant.StrategyConstants;
import com.surgical.decision3.intervention.constants.InterventionConstants;
import com.surgical.decision3.intervention.constants.InterventionTemplateKeyConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import kb.common.datastructure.graph.GraphNode;
import kb.common.datastructure.graph.StudentGraph;
import kb.common.datastructure.graph.StudentGraphNode;
import kb.common.datastructure.graph.TutorGraph;
import kb.common.datastructure.graph.TutorGraphNode;
import kb.common.datastructure.solutionpath.PlanAction;
import kb.util.InferenceEngineUtil;
import kb.util.InterventionStringUtil;
import kb.util.StudentGraphUtil;
import kb.util.TutorGraphUtil;
import kb.util.Utils;

/**
 * Created by Dell on 7/7/2017.
 */

public class InterventionGenerator
{
    public static HashMap<String, String> intervention_template_map = ApplicationController.intervention_template_map;

    //TODO: IDEA TO create the temporary object to store the selected data on graph
    //  HashMap<String, String>
    // Key = [ACTION], [ACTION_COND], [PROJECTION]
    //1. [TUTOR_ACTION_NODE]
    //2. [ACTION_NODE]
    //3. [ACTION_COND]
    //4. [LEADING_POS_FB]
    //5. [C_NODES]
    //6. [P_NODES]
    //7. [PJ_NODES]
    //8. [BLANK]

    //-----------------------------------------------------------------------
    //Strategy 9 : Tell the answer (from question)
    //-----------------------------------------------------------------------
    public static Intervention strategy_9_tellTheAnswer(Question question)
    {
        if( question == null )
        {
            return null;
        }

        Choice answerChoice = getAnswerFromQuestion(question);
        String choice_description = answerChoice.getChoiceDescription();

        if( answerChoice.isYesNoChoice() )
        {
            choice_description = InterventionStringUtil.transformExpToFinalText(answerChoice.getChoice_exp());
        }
        else
        {
            if( choice_description == null || choice_description.trim().length() == 0 )
            {
                choice_description = InterventionStringUtil.transformExpToFinalText(answerChoice.getChoice_exp());
            }

        }

        //choice_description

        //st9_tell_answer=The answer is [ANSWER_DESCRIPTION].

        String template = intervention_template_map.get(InterventionTemplateKeyConstants.ST_9_TELL_ANSWER );
        String message = template.replace("[ANSWER_DESCRIPTION]", choice_description);

        //Intervention answerIntervention = formulateIntervention(choice_description, true, null, null);
        Intervention answerIntervention = formulateIntervention(message, true, null, null);
        answerIntervention.setStrategy_name( "ST_9_TELL_ANSWER" );

        return answerIntervention;
    }

    private static Choice getAnswerFromQuestion(Question question)
    {
        Choice answer = null;
        if( question == null )
        {
            return null;
        }

        for( Choice choice : question.getChoices() )
        {
            if( choice.isAnswer() )
            {
                answer = choice;
                break;
            }
        }
        return answer;
    }

    public static Intervention strategy_8_generateYesNoQuestion( Question ref_q, Choice studentSelectedChoice )
    {
        //Choice correctChoice = ref_q.getCorrectChoice();

        if( studentSelectedChoice == null )
        {
            return null;
        }

        //st8_question=Should [CORRECT_ANSWER_EXP]?

        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_8_QUESTION);  //replace only Action
//        String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
        String q_message = q_template.replace( "[CORRECT_ANSWER_EXP]", studentSelectedChoice.getChoiceDescription() );

        Question question = generateYesNoQuestion( q_message, studentSelectedChoice.getChoice_exp(), studentSelectedChoice.isAnswer() );
        question.setReferenceQuestion( ref_q );

        Intervention intervention = formulateIntervention( null, false, question, StrategyConstants.STRATEGY_8 );
        intervention.setStrategy_name( "ST_8_GENERATE_YES_NO_QUESTION" );

        return intervention;
    }

    public static Intervention strategy_8_generateYesNoQuestion_fromCorrectChoice(Question q)
    {
        Choice correctChoice = q.getCorrectChoice();

        if( correctChoice == null )
        {
            return null;
        }

        //st8_question=Should [CORRECT_ANSWER_EXP]?

        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_8_QUESTION);  //replace only Action
//        String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
        String q_message = q_template.replace("[CORRECT_ANSWER_EXP]", correctChoice.getChoiceDescription());

        Question question = generateYesNoQuestion_forCorrectChoice(q_message, correctChoice.getChoice_exp());

        Intervention intervention = formulateIntervention(null, false, question, StrategyConstants.STRATEGY_8);
        intervention.setStrategy_name( "ST_8_GENERATE_YES_NO_QUESTION" );

        return intervention;
    }
    //-----------------------------------------------------------------------
    //Strategy 10 : provide hints  (I HAVEN'T TESTED )
    //-----------------------------------------------------------------------

    //NOTE: hint from question 7_3 (student's action is not correct)
    //Hint should be related to C and User's action
    //NOTE: For the expression that has connective AND, the process to extract the first key is limited.
    public static Intervention strategy_10_generateHints(Question q)
    {
        //1. Get correct answer
        Choice correctChoice = q.getCorrectChoice();

        if( correctChoice == null )
        {
            return null;
        }

        //Check if the answer is comprehension of userAction
        boolean isComprehension = InferenceEngineUtil.isComprehensionExp(correctChoice.getChoice_exp());
        boolean isUserAction = InferenceEngineUtil.isUserPredicateExp(correctChoice.getChoice_exp());

        String firstPredicate = null;

        //if answer is projection, get parent (action condition) node. pick by priority
        if( isComprehension )
        {
            firstPredicate = InterventionStringUtil.get1stPredicateStringFromExp(correctChoice.getChoice_exp());
        }
        else if( isUserAction )
        {
            firstPredicate = InterventionStringUtil.get1stPredicateStringFromExp(correctChoice.getChoice_exp());
        }

        if( firstPredicate == null )
        {
            return null;
        }

        //if the choice EXP connective is AND, do not give hints
        //This is for the EXP that has connective of NOT and ATOM only.

        String translateString = ApplicationController.domain_predicate_map.get(firstPredicate);      //translate EXP to English
//        String translatedHint = InterventionStringUtil.transformExpToFinalText( hintExp );
        String template = intervention_template_map.get(InterventionTemplateKeyConstants.ST_10_HINT);
        String message = template.replace("[HINT]", translateString);

        Intervention outputIntervention = formulateIntervention(message, true, null, StrategyConstants.STRATEGY_10);
        outputIntervention.setStrategy_name( "ST_10_GENERATE_HINTS" );

        return outputIntervention;

    }

    public static Intervention strategy_10_generateHints_old(Question q, Intervention mergeToIntervention)
    {
        //1. Get correct answer
        Choice correctChoice = q.getCorrectChoice();

        if( correctChoice == null )
        {
            return null;
        }

        //if the choice EXP connective is AND, do not give hints
        //This is for the EXP that has connective of NOT and ATOM only.

        PlanAction currentAction = ApplicationController.currentAction;
        TutorGraph tGraph = TutorGraphUtil.getTutorGraphByAction(currentAction);
        List<Exp> pjNodeList = tGraph.getProjectionExpList();

        Exp correctChoiceExp = correctChoice.getChoice_exp();

        Exp hintExp = null;
        String pair_predicate = null;

        boolean isProjection = InferenceEngineUtil.isProjectionExp(correctChoiceExp, pjNodeList);
        boolean isComprehension = InferenceEngineUtil.isComprehensionExp(correctChoiceExp);
        boolean isUserAction = InferenceEngineUtil.isUserPredicateExp(correctChoiceExp);

        //if answer is projection, get parent (action condition) node. pick by priority
        // 1. Comprehension, 2 user action , 3. Perception
        if( isProjection )
        {
            TutorGraphNode pjNode = tGraph.getProjectionNodeByExp(correctChoiceExp);
            hintExp = pjNode.getProjectionExp();
        }
        else if( isComprehension )
        {
            //Get Perception
            hintExp = InferenceEngineUtil.getPerceptionFromComprehensionExp(correctChoiceExp);
        }
        else if( isUserAction )
        {
            //Read ApplicationController.pair_user_action_map.get( userAction ) for getting the comparison
            String predicate = InterventionStringUtil.get1stPredicateStringFromExp(correctChoiceExp);
            if( predicate != null )
            {
                pair_predicate = ApplicationController.pair_user_action_map.get(predicate.toUpperCase());
            }
        }


        String template = intervention_template_map.get(InterventionTemplateKeyConstants.ST_10_HINT);
        String message = null;
        if( hintExp != null )
        {
            //Translate hintExp
            String translatedHint = InterventionStringUtil.transformExpToFinalText(hintExp);
            message = template.replace("[HINT]", translatedHint);
        }
        else if( pair_predicate != null && pair_predicate.trim().length() > 0 )
        {
            //Translate predicateString
            String translatedHint = InterventionStringUtil.transformExpToFinalText(hintExp);
            message = template.replace("[HINT]", translatedHint);
        }


        //-----------------------------
        // Formulate intervention
        //-----------------------------
        Intervention outputIntervention;
        if( mergeToIntervention == null )
        {
            //Create prompt
            outputIntervention = formulateIntervention(message, false, null, null);
        }
        else
        {
            //get prompt and concat String
            Prompt p = mergeToIntervention.getInterventionData().getPrompt();
            if( p != null )
            {
                String currentText = p.getPromptMsg().getText();
                currentText += message;
                p.getPromptMsg().setText(currentText);
            }

            outputIntervention = mergeToIntervention;
        }

        outputIntervention.setStrategy_name( "ST_10_GENERATE_HINTS" );

        return outputIntervention;
    }


/*



            Exp answerExp =  tutorAcNodeToUse.getExpByNodeType();

            List<Exp> answerExpList = null;
            if( answerExp.getConnective().equals( Connective.AND ) )
            {
                answerExpList = answerExp.getChildren();
            }
            else
            {
                if( answerExpList == null )
                {
                    answerExpList = new ArrayList<>();
                }

                answerExpList.add( answerExp );
            }

            //List<String> stringList = InterventionStringUtil.getTheBaseStringListPredicateFromExp( answerExpList, null );
            List<Exp> choiceList =  tutorGraph.generateChoicesByActionCondition_noAnswer2( tutorAcNodeToUse );

            //------------------
            //4. formulate questions and return intervention
            //------------------
            // st13_question_relevant_node=What should be the correct [ACTION_COND] for [ACTION_NODE]?

            String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_7_3_QUESTION_RELEVANT_NODE);  //replace only Action
            String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
            String q_message = q_template.replace( "[ACTION_NODE]", translateAction );

            basePredicateTutorNode = InterventionStringUtil.getTheBasePredicateFromExp(answerExp );

            if( basePredicateTutorNode == null )
            {
                return null;
            }

            String translateBasePredicateAcCond = ApplicationController.domain_predicate_map.get( basePredicateTutorNode );
            q_message = q_message.replace( "[ACTION_COND]", translateBasePredicateAcCond );

            Question question = generateQuestion( q_message, choiceList, tutorAcNodeToUse.getExpByNodeType() );
            return formulateIntervention( null, false, question, StrategyConstants.STRATEGY_7_3 );  //13A
            * */

    //---------------------------------------------------------------
    // Strategy 7_3 for compound
    //Now we know that this actionConditionNode is compound sentences
    //---------------------------------------------------------------
    public static Intervention strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction_compound(StudentGraphNode studentACNode )
    {
        //Intervention intervention = null;

        //3. find the ac node from the tutor graph.
        PlanAction currentAction = ApplicationController.currentAction;
        List<TutorGraph> tutorGraphList = ApplicationController.tutorGraphList;
        TutorGraph tutorGraph = TutorGraphUtil.getTutorGraphByAction(currentAction);
        List<TutorGraphNode> tutorAcNodeList = tutorGraph.getActionConditionList();

        //List<TutorGraphNode> tutorAcNodeList = TutorGraphUtil.getAcNodeListByAction( null );           //get from current action
        //String basePredicateTutorNode = null;
        TutorGraphNode tutorAcNodeToUse = findMostMatchedTutorAcNodeStudentAcNode(tutorAcNodeList, studentACNode);

        String basePredicateTutorNode = null;
        String basePredicateStudentNode = null;

        //Both tutor and student AC node is compound.

        List<Exp> tutorACExpList = InterventionStringUtil.flattenExpList( tutorAcNodeToUse.getExpByNodeType(), null );
        List<Exp> studentACExpList = InterventionStringUtil.flattenExpList( studentACNode.getExpByNodeType(), null );

        Exp correctExp =  null;

        for( Exp studentExp : studentACExpList )
        {
            //if the exp contains in tutor AC EXP list , skip, else check the predicate.
            if( tutorACExpList.contains( studentExp ) )
            {
                continue;
            }
            else
            {
                //Check if the answer is comprehension of userAction
                boolean isComprehension = InferenceEngineUtil.isComprehensionExp( studentExp );
                boolean isUserAction = InferenceEngineUtil.isUserPredicateExp( studentExp );
                //check if it is the user action

                //Get Exp From tutorACExpList using basePredicate from studentExp
                String studentBaseExp = InterventionStringUtil.getTheBasePredicateFromExp( studentExp );

                correctExp = InterventionStringUtil.getExpFromListByKeyBaseExp( tutorACExpList, studentExp );

                break;
                /*
                if( isUserAction )
                {
                    //get the correct exp from tutor
                    correctExp = tutorACExpList.get( tutorACExpList.indexOf( correctExp ) );
                }
                else if (isComprehension)
                {
                    correctExp =  tutorACExpList.get( tutorACExpList.indexOf( correctExp ) );
                }
                else
                {
                    //This is perception
                    correctExp =  tutorACExpList.get( tutorACExpList.indexOf( correctExp ) );
                }*/
            }
        }//end for

        if( correctExp == null )
        {
            return null;
        }

        Exp answerExp = correctExp;
        List<Exp> answerExpList =  new ArrayList<>();

        List<Exp> choiceList = tutorGraph.generate_AC_Choices_forCompoundExp_noAnswer2(tutorAcNodeList, tutorAcNodeToUse, answerExp );

        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_7_3_QUESTION_RELEVANT_NODE);  //replace only Action
        String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
        String q_message = q_template.replace( "[ACTION_NODE]", translateAction );

        String baseAnswerExp = InterventionStringUtil.getTheBasePredicateFromExp(answerExp );

        if( baseAnswerExp == null )
        {
            return null;
        }

        String translateBasePredicateAcCond = ApplicationController.domain_predicate_map.get( baseAnswerExp );
        q_message = q_message.replace( "[ACTION_COND]", translateBasePredicateAcCond );

        Question question = generateQuestion( q_message, choiceList, answerExp );

        Intervention intervention = formulateIntervention( null, false, question, StrategyConstants.STRATEGY_7_3 );  //13A
        intervention.setStrategy_name( "ST_7_3_GENERATE_QUESTION_INCORRECT_INFO_TO_CORRECT_ACTION_COMPOUND" );

        return intervention;
    }

    //-----------------------------------------------------------------------
    //Strategy 13 and other questioning strategy: Response the student's answer
    // 7_3
    //-----------------------------------------------------------------------
    public static Intervention strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction()
    {
        Intervention intervention = null;

        //1. Get student's graph
        List<StudentGraph> studentGraphList = ApplicationController.studentGraphList;

        StudentGraph stGraph = studentGraphList.get(studentGraphList.size() - 1);
        StudentGraphNode stPjNodeNotDO = stGraph.getProjectionNodeByDesiredOutcome(false);
        if( stPjNodeNotDO != null )
        {
            //2. Get AC node that is not satisfy DO
            List<StudentGraphNode> stAcNodeList = stPjNodeNotDO.getParentNodes();

            //Note: there could be many actionCondition Nodes, however, usually, the parent of projection node is only one action Condition node.
            StudentGraphNode studentAcNode = stAcNodeList.get(0);

            //3. Check if the acNode is compound node
            if( InferenceEngineUtil.isCompoundPredicate( studentAcNode.getActionCondition() ))
            {
                intervention = strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction_compound( studentAcNode  );
            }
            else
            {
                //cal 7_3 normal
                intervention = strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction_nonCompound();
            }
        }
        else
        {
            //Student Pjs satisfy all desired outcomes
            intervention = strategy_7_2_generateQuestionAboutCorrectPerception();
        } //end-if if( stPjNodeNotDO != null )



        return intervention;
    }

    public static int getTotalPredicate(Exp exp)
    {
        if( exp != null )
        {
            if(exp.getConnective().equals(Connective.AND) )
            {
                return exp.getChildren().size();
            }
            else
            {
                return 1;
            }

        }
        return -1;
    }

    public static boolean isExpTotalPredicateEqual(Exp tutorExp, Exp studentExp )
    {
        int studentChildren = getTotalPredicate( studentExp );
        int tutorChildren = getTotalPredicate(tutorExp);

        return (tutorChildren == studentChildren);
    }

    /*
    public static boolean isBaseSamePredicate( Exp tutorExp, Exp studentExp )
    {
        if( ! isExpTotalPredicateEqual( tutorExp, studentExp ) )
        {
            return false;
        }

        //predicate has the same number of children.
        if( tutorExp.getChildren().equals(Connective.AND) )
        {
            for(Exp tExp: tutorExp.getChildren() )
            {
                for(Exp stExp: studentExp.getChildren() )
                {
                    if( stExp.equals(tExp) )
                    {
                        continue
                    }
                }
            }
        }
        else
        {
            String tutorBasePredicate = InterventionStringUtil.getTheBasePredicateFromExp( tutorExp );
            String studentBasePredicate = InterventionStringUtil.getTheBasePredicateFromExp( studentExp );

            return (tutorBasePredicate.trim().toLowerCase().equals( studentBasePredicate.trim().toLowerCase() ) );
        }
    }

    */

    private static boolean isPredicateFound(List<Exp> tutorExpList, Exp keyExp )
    {
        boolean isFound = false;

        String baseStudentAcPredicate = InterventionStringUtil.getTheBasePredicateFromExp( keyExp );

        if( baseStudentAcPredicate == null )
        {
            return false;
        }

        for( Exp tExp : tutorExpList )
        {
            String tExpBasePredicate = InterventionStringUtil.getTheBasePredicateFromExp( tExp );

            if( tExpBasePredicate == null )
            {
                continue;
            }

            if( baseStudentAcPredicate.trim().toLowerCase().equals( tExpBasePredicate.trim().toLowerCase() ) )
            {
                isFound = true;

                break;
            }
        }

        return isFound;
    }


    public static boolean isSimilarBasePredicate_All(List<Exp> tutorExpList, List<Exp> studentExpList)
    {
        boolean isMatchAll = true;

        for(Exp stExp: studentExpList )
        {
            if( !isPredicateFound(tutorExpList, stExp ) )
            {
                isMatchAll = false;
                break;
            }
        }

        return isMatchAll;
    }

    //boolean isSimilarBasePredicate = true;

        /*for(Exp tExp: tutorExpList )
        {

            for(Exp stExp: studentExpList )
            {
                //Compare each children by base.
                String baseTutorAcPredicate = InterventionStringUtil.getTheBasePredicateFromExp( stExp );
                String baseStudentAcPredicate = InterventionStringUtil.getTheBasePredicateFromExp( tExp );
                if( baseTutorAcPredicate.equals(baseStudentAcPredicate) )
                {
                    //Continue to check value.
                    //you can check the whold exp right awaey

                    //Break to get the new tExp
                    break;
                }
                else
                {
                    isSimilarBasePredicate = false;

                    return isSimilarBasePredicate;
                }
            }
        }*/

//    public static boolean isCompoundPredicate(Exp exp)
//    {
//        boolean isCompoundPredicate = false;
//        if( exp != null )
//        {
//            if( exp.getConnective().equals( Connective.AND ))
//            {
//                isCompoundPredicate = true;
//                return true;
//            }
//
//        }
//        return false;
//    }
    /**
     * For each criteria to check matching
     * 1. the number of children must be equal
     *
     * */
    public static TutorGraphNode findMostMatchedTutorAcNodeStudentAcNode(List<TutorGraphNode> tutorAcNodeList, StudentGraphNode studentAcNode)
    {
        TutorGraphNode tutorAcNodeToUse = null;

        //for each tutorAcNode from the tutorGraph
        for( TutorGraphNode tutorAcNode: tutorAcNodeList )
        {
            //1. if number  equal
            if( isExpTotalPredicateEqual ( tutorAcNode.getActionCondition(), studentAcNode.getActionCondition() ) )
            {
                //if total predicates == 1
                //check if the predicate is the same, else, continue
                if( getTotalPredicate( studentAcNode.getActionCondition() ) == 1)
                {
                   String basePredicate_tutor = InterventionStringUtil.getTheBasePredicateFromExp( tutorAcNode.getActionCondition() );
                   String basePredicate_student = InterventionStringUtil.getTheBasePredicateFromExp( studentAcNode.getActionCondition() );

                   if( basePredicate_student!= null
                           && basePredicate_tutor != null
                           && basePredicate_student.toLowerCase().equals( basePredicate_tutor.toLowerCase() ) )
                   {
                       return tutorAcNode;
                   }
                   else
                   {
                       continue;
                   }
                }
                else if( getTotalPredicate( studentAcNode.getActionCondition() ) > 1 )
                {
                    //Compound predicate
                    // if the all predicate pairs are the same predicate?
                    List<Exp> tExpList = tutorAcNode.getActionCondition().getChildren();
                    List<Exp> stExpList = studentAcNode.getActionCondition().getChildren();

                    boolean isMatchAll = isSimilarBasePredicate_All( tExpList, stExpList );

                    if( isMatchAll )
                    {
                        //find the predicate that makes
                        tutorAcNodeToUse = tutorAcNode;
                        break;
                    }
                    else
                    {
                        continue;
                    }
                }
                else
                {
                    //DO NOTHING.
                }
            }
            else
            {
                continue;
            }

        }

        return tutorAcNodeToUse;
    }
    //-----------------------------------------------------------------------
    //Strategy 13 : Generate a question about the correct perception relevant to the correct action
    //Trigger, when student's action is not correct
    //Algorithm:
    //   1. get one pjNode from student's graph that does not satisfy the desired outcome.
    //   2. get parent of pjNode, which is actionCondition node.
    //   3. use the action condition node (only the 1st predicate) from the student graph as a key.
    //      Get the list of actionCondition from the tutor graph, select the actionConditionNode as the answer node
    //      Get the other actionConditionNode that does not satisfy the key,
    //      (Get ACNode from the tutor graph that its child satisfy the desired outcome.)
    //   4. Generate the question related to this action condition. with parameters.
    //-----------------------------------------------------------------------
    //NOTE: this is does not cover the compound predicate
    // ----------------------------------------------------------
    public static Intervention strategy_7_3_generateQuestionAboutIncorrectInfoToCorrectAction_nonCompound()
    {
        List<StudentGraph> studentGraphList = ApplicationController.studentGraphList;

        //1. get one pjNode from student's graph that does not satisfy the desired outcome.

        //NOTE: On 21 July, it's found that the student graph Lists keep the array of student action by time.
        //if student tries again, the latest student's action would be at the last index of the studentGraphList.
        StudentGraph stGraph = studentGraphList.get( studentGraphList.size() -1 );
        StudentGraphNode stPjNodeNotDO = stGraph.getProjectionNodeByDesiredOutcome( false );

        if( stPjNodeNotDO != null )
        {
            //2. get parent of pjNode, which is actionCondition node
            List<StudentGraphNode> stAcNodeList = stPjNodeNotDO.getParentNodes();

            //Note: there could be many actionCondition Nodes, however, usually, the parent of projection node is only one action Condition node.
            StudentGraphNode studentAcNode = stAcNodeList.get( 0 );

            //Get base predicate as a key, to find the acNode in tutor graph and check if their child satisfy DO
            String basePredicateStudentNode = InterventionStringUtil.getTheBasePredicateFromExp( studentAcNode.getExpByNodeType() );

            if( basePredicateStudentNode == null )
            {
                return null;
            }

            //Check tutor
            PlanAction currentAction = ApplicationController.currentAction;
            List<TutorGraph> tutorGraphList = ApplicationController.tutorGraphList;
            TutorGraph tutorGraph = TutorGraphUtil.getTutorGraphByAction( currentAction );
            List<TutorGraphNode> tutorAcNodeList = tutorGraph.getActionConditionList();

            //List<TutorGraphNode> tutorAcNodeList = TutorGraphUtil.getAcNodeListByAction( null );           //get from current action
            String basePredicateTutorNode = null;
            TutorGraphNode tutorAcNodeToUse = null;

            for( TutorGraphNode tutorAcNode: tutorAcNodeList )
            {
                basePredicateTutorNode = InterventionStringUtil.getTheBasePredicateFromExp( tutorAcNode.getExpByNodeType() );
                if(  basePredicateStudentNode != null && basePredicateTutorNode != null  )
                {
                    if(basePredicateStudentNode.trim().toLowerCase().equals( basePredicateTutorNode.trim().toLowerCase() ) )
                    {
                        //Matched, now check if tutor pj has DO
                        List<TutorGraphNode> tutorPjNodeList = tutorAcNode.getChildNodes();
                        boolean isPjSatisfyDO = TutorGraphUtil.isAllPjSatisfyDO( tutorPjNodeList );
                        if( isPjSatisfyDO )
                        {
                            tutorAcNodeToUse = tutorAcNode;
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    }
                }
                else
                {
                    return null;
                }
            }

            //Check th

            if( tutorAcNodeToUse == null )
            {
                return null;
            }

            //Generate question  //st13_question2=What should be the correct [1ST_P_NODES] for [ACTION_NODE]?
            //Create AnswerChoices --> acNodes
            Exp answerExp =  tutorAcNodeToUse.getExpByNodeType();

            List<Exp> answerExpList = null;
            if( answerExp.getConnective().equals( Connective.AND ) )
            {
                answerExpList = answerExp.getChildren();
            }
            else
            {
                if( answerExpList == null )
                {
                    answerExpList = new ArrayList<>();
                }

                answerExpList.add( answerExp );
            }

            //List<String> stringList = InterventionStringUtil.getTheBaseStringListPredicateFromExp( answerExpList, null );  //NOT USE
            //List<Exp> choiceList =  tutorGraph.generateChoicesByActionCondition_noAnswer2( tutorAcNodeToUse );   //CHOICE for any AC NODE

            List<Exp> choiceList = tutorGraph.generate_AC_Choices_forNonCompoundExp_noAnswer2( tutorAcNodeList, answerExp );
            //TODO: this method must be combined with the compound actionCondition node.

            //------------------
            //4. formulate questions and return intervention
            //------------------
            // st13_question_relevant_node=What should be the correct [ACTION_COND] for [ACTION_NODE]?

            String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_7_3_QUESTION_RELEVANT_NODE);  //replace only Action
            String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
            String q_message = q_template.replace( "[ACTION_NODE]", translateAction );

            basePredicateTutorNode = InterventionStringUtil.getTheBasePredicateFromExp(answerExp );

            if( basePredicateTutorNode == null )
            {
                return null;
            }

            String translateBasePredicateAcCond = ApplicationController.domain_predicate_map.get( basePredicateTutorNode );
            q_message = q_message.replace( "[ACTION_COND]", translateBasePredicateAcCond );

            Question question = generateQuestion( q_message, choiceList, tutorAcNodeToUse.getExpByNodeType() );

            Intervention intervention = formulateIntervention( null, false, question, StrategyConstants.STRATEGY_7_3 );  //13A
            intervention.setStrategy_name( "ST_7_3_GENERATE_QUESTION_INCORRECT_INFO_TO_CORRECT_ACTION_NON_COMPOUND" );

            return intervention;
        }
        else
        {
            //Student Pjs satisfy all desired outcomes
            Intervention intervention = strategy_7_2_generateQuestionAboutCorrectPerception();
        }
        return null;
    }

    public static Intervention strategy_7_1_generateQuestionAboutCorrectAction_PARENT_PLAN( Question q )
    {
        if( q == null )
        {
            return null;
        }

        Exp refExp = q.getReferenceExp();
        if( refExp == null )
        {
            return null;
        }

        String basePredicate = InterventionStringUtil.getTheBasePredicateFromExp( refExp );
        String key = basePredicate.concat("_base");

        //st7_1_question_from_6_1_parent_plan=What is [BASE_PREDICATE]?
        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_7_1_QUESTION_FROM_6_1_PARENT_PLAN);  //replace only Action
        //String translatePj = InterventionStringUtil.transformExpToFinalText( );
        String translatedBasePredicate = ApplicationController.domain_predicate_map.get( key );
        String q_message = q_template.replace( "[BASE_PREDICATE]", translatedBasePredicate );

        q = generateQuestion_answerInChoiceList( q_message, q.getChoices()  );

        Intervention intervention =  formulateIntervention( null, false, q, StrategyConstants.STRATEGY_7_1 );
        intervention.setStrategy_name( "ST_7_1_GENERATE_QUESTION_CORRECT_ACTION_PARENT_PLAN" );

        return intervention;
    }

    public static Intervention strategy_7_1_generateQuestionAboutCorrectAction( Question q )
    {
        if( q == null )
        {
            return null;
        }

        //Use choice from question to generate the new question
        PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ? StrategyController.actionToDiscuss : ApplicationController.currentAction;
        TutorGraph tGraph = TutorGraphUtil.getTutorGraphByAction( currentAction );
        Exp desiredProjectionExp = InterventionStringUtil.extractMainProjectionExp( currentAction.getDesired_outcomes() );
        TutorGraphNode pjDesiredOutcomeExpNode = tGraph.getProjectionNodeByMainDesireOutcomeExp( desiredProjectionExp );

        //st7_1_question_from_6_1=What do you have to consider when you [PROJECTION_NODE]?
        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_7_1_QUESTION_FROM_6_1);  //replace only Action
        String translatePj = InterventionStringUtil.transformExpToFinalText( pjDesiredOutcomeExpNode.getExpByNodeType() );
        String q_message = q_template.replace( "[PROJECTION_NODE]", translatePj );

        q = generateQuestion_answerInChoiceList( q_message, q.getChoices()  );

        Intervention intervention =  formulateIntervention( null, false, q, StrategyConstants.STRATEGY_7_1 );
        intervention.setStrategy_name( "ST_7_1_GENERATE_QUESTION_CORRECT_ACTION" );

        return intervention;
    }

    //THis method do not use student's graph as a key.
    //It use the tutor graphs only .
    public static Intervention strategy_7_2_generateQuestionAboutCorrectAction( Question q )
    {
        if( q == null )
        {
            return null;
        }

        //Use choice from question to generate the new question
        PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ? StrategyController.actionToDiscuss : ApplicationController.currentAction;

        //st7_2_question_from_6_2=What is the objective of [ACTION_NODE]?
        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_7_2_QUESTION_FROM_6_2);  //replace only Action
        String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
        String q_message = q_template.replace( "[ACTION_NODE]", translateAction );

        q = generateQuestion_answerInChoiceList( q_message, q.getChoices()  );

        Intervention intervention =  formulateIntervention( null, false, q, StrategyConstants.STRATEGY_7_2);
        intervention.setStrategy_name( "ST_7_2_GENERATE_QUESTION_CORRECT_ACTION" );

        return intervention;
    }

    public static Intervention strategy_7_2_generateQuestionAboutCorrectPerception()
    {
        //------------------
        //st13_question=What should be the correct [1ST_P_NODES]?
        //1. Get Action, use tutor graph, get list of  actionCondition
        //------------------
        //PlanAction currentAction = ApplicationController.currentAction;
        PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ? StrategyController.actionToDiscuss : ApplicationController.currentAction;

        List<TutorGraph> tutorGraph = ApplicationController.tutorGraphList;
        List<StudentGraph> studentGraph = ApplicationController.studentGraphList;
        TutorGraph tGraph = TutorGraphUtil.getTutorGraphByAction( currentAction );
        List<TutorGraphNode> ActionConditionList = tGraph.getActionConditionList();

        //------------------
        //2. from the projection node (DO = true), get correct perception from ActionCondition Node.
        //------------------
        Exp answerExp = currentAction.getDesired_outcomes();
        List<Exp> answerExpList = null;
        if( answerExp.getConnective().equals(Connective.AND ) )
        {
            answerExpList = answerExp.getChildren();
        }
        else
        {
            if( answerExpList == null )
            {
                answerExpList = new ArrayList<>();
            }

            answerExpList.add( answerExp );
        }

        //------------------
        //3. Create the choice using the other perceptions
        //------------------
        //List<Exp> choiceList =  tGraph.generatePerceptionChoicesFromActionCondition_noAnswer();  //Do not deal with AND nested EXP
        List<Exp> choiceList =  tGraph.generatePerceptionChoicesFromActionCondition_noAnswer2();
        Exp answerChoice = tGraph.generatePerceptionChoicesFromActionCondition_onlyAnswer();

        //------------------
        //4. formulate questions and return intervention
        //------------------
        // ORIGINAL: st13_question=What should be the correct [1ST_P_NODES] for [ACTION_NODE]?
        // NEW: st13_question=What factor makes [ACTION_NODE] successfully?

        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_7_2_QUESTION);  //replace only Action
        String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
        String q_message = q_template.replace( "[ACTION_NODE]", translateAction );

        //String first_predicate = InterventionStringUtil.get1stPredicateStringFromExp( );
        //TODO: In case that there is a predicate with value, this should be changed.

        //TODO: Add user actionCondition
        //If there is action Parameter, there should be added.

        Question question = generateQuestion( q_message, choiceList, answerChoice );
        Intervention intervention =  formulateIntervention( null, false, question, StrategyConstants.STRATEGY_7_2 );  //13
        intervention.setStrategy_name( "ST_7_2_GENERATE_QUESTION_CORRECT_PERCEPTION" );

        return intervention;
    }

    public static Intervention strategy_7_2_responseFeedbackToStudentAnswer(Intervention answerIntervention )
    {
        Intervention feedbackIntervention = null;

        //Get the answer of the question for intervention.
        //check if the selected choice is the answer.
        Choice studentChoice = answerIntervention.getInterventionData().getStudentAnswer();
        if( studentChoice.isAnswer() )
        {
            //FB+
            feedbackIntervention = generateLeadingPositiveFeedback();
        }
        else
        {
            //FB-
            //Additional Strategy?
            feedbackIntervention = generateLeadingNegativeFeedback();
        }
        return feedbackIntervention;
    }

    //==================================================================================================
    //-----------------------------------------------------------------------
    //Strategy 12 : Generate a question to reconfirm the consequence of the action
    // Algorithm: Use tutor graph, get action, get projection that is the answer (DO = true), get choices from the other pjNodes
    //-----------------------------------------------------------------------
    public static Intervention generateQuestionToReconfirm()
    {
        //Idea:
        //1. from projection that desired outcome is satisfied
        //2. Consider the action condition,
        // PRIORITY:
        //  User action >> Comprehension >> Perception
        //3. if the Useraction is correct, --> ask 6.2 for the projection
        //   if it is the perception --> ask 6.1 for projection
        //

        //1. Get Action
        PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ?
                                    StrategyController.actionToDiscuss :
                                    ApplicationController.currentAction;

        //2. use tutor graph, get the tutor graph by the correct pointed action (current action)
        List<TutorGraph> tutorGraph = ApplicationController.tutorGraphList;
        TutorGraph tGraph = TutorGraphUtil.getTutorGraphByAction( currentAction );

        //Get the choice that is desired outcome
        //get the answer choice
        //create the most similar choices

        return null;
    }

    //TODO:
    public static Intervention strategy_6_1_generateQuestionReconfirmPerception_referToParentPlan( Exp optionalExp )
    {
        PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ?
                StrategyController.actionToDiscuss :
                ApplicationController.currentAction;

        //Get parent Plan./

        //Template: st6_1_question_parent_plan=Why do you [PARENT_PLAN]?
        //st7_1_question_from_6_1_parent_plan=What is [BASE_PREDICATE]?
        String namePlan = currentAction.getParentPlan().getName().getImage();
        String parentPlanAction = ApplicationController.domain_predicate_map.get( namePlan );

        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_6_1_QUESTION_PARENT_PLAN);  //replace only Action
        String q_message = q_template.replace( "[PARENT_PLAN]", parentPlanAction );

        //NOTE. if optionalExp != null, that means the current executed step is not required.
        //So, the choice of DIAGNOSIS pulp necrosis is optional, it is the choice.
        //The answer is the another constants
        if( optionalExp != null )
        {
            Exp answerExp = null;
            List<Exp> choiceList = new ArrayList<>();
            choiceList.add( optionalExp );

            //Find the answer:
            TutorGraph tGraph = TutorGraphUtil.getTutorGraphByAction( currentAction );
            List<Exp> answerList = tGraph.generateSimilarChoice( optionalExp );

            //Randomly get answer
            answerExp = answerList.get( Utils.getOneRandomNumber( answerList.size() ) -1 );

            //String basePredicate = InterventionStringUtil.getTheBasePredicateFromExp( optionalExp );
            Question question = generateQuestion( q_message, choiceList, answerExp );
            //question.setReferenceExp( optionalExp );  //optional exp is not the answer
            question.setReferenceExp( answerExp );

            Intervention intervention = formulateIntervention( null, false, question, StrategyConstants.STRATEGY_6_1_PARENT_PLAN );
            intervention.setStrategy_name( "ST_6_1_QUESTION_RECONFIRM_ACTION_PERCEPTION_PARENT_PLAN" );

            return intervention;
        }
        else
        {
            //Call normal 6.1
            Intervention intervention = InterventionGenerator.strategy_6_1_generateQuestionReconfirmPerception();
            return intervention;
        }
    }

    //-----------------------------------------------------
    // Sample:
    // WHy do you select la as lidocaine (pj)?
    // Because [P] patient does not have underlying disease
    //-----------------------------------------------------
    // NOTE: THE STUDENT GRAPH IS NOT CORRECT. WE TEMPORARY USE STUDENT GRAPH
    // IT IS NEEDED TO FIX WHEN STUDENT GRAPH IS CORRECT
    //-----------------------------------------------------
    public static Intervention strategy_6_1_generateQuestionReconfirmPerception()
    {
        //1. Get Action
        //correct action makes the tutor points to the next action.
        //But the conversation process continues; so we need to keep the value of action to discuss
        //PlanAction currentAction = ApplicationController.currentAction;
        PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ?
                StrategyController.actionToDiscuss :
                ApplicationController.currentAction;

        //2. use tutor graph, get the tutor graph by the correct pointed action (current action)
        List<TutorGraph> tutorGraph = ApplicationController.tutorGraphList;
        TutorGraph tGraph = TutorGraphUtil.getTutorGraphByAction( currentAction );

        Exp desiredProjectionExp = InterventionStringUtil.extractMainProjectionExp( currentAction.getDesired_outcomes() );

        //TutorGraphNode pjAnswerExpNode = tGraph.getProjectionNodeByExp( desiredProjectionExp );
        TutorGraphNode pjDesiredOutcomeExpNode = tGraph.getProjectionNodeByMainDesireOutcomeExp( desiredProjectionExp );

        Exp acExp = null;

        //-------------------------------------------------
        //NOTE: THIS IS TEMPORARY CODE FOR INCORRECT STUDENT'S GRAPH
        StudentGraph stGraph = StudentGraphUtil.getCurrentStudentGraph();
        for( StudentGraphNode sgNode : stGraph.getProjectionList() )
        {
            if(sgNode.getProjectionExp().equals( desiredProjectionExp ) )
            {
                acExp = sgNode.getParentNodes().get(0).getExpByNodeType();
                break;
            }
        }

        //---------------------------------------

        //Get projection node from tutor graph by desired outcome.
        if( pjDesiredOutcomeExpNode == null )
        {
            return null;
        }


        TutorGraphNode parentDesiredOutcomeExpNode = pjDesiredOutcomeExpNode.getParentNodes().get( 0 );

        /*
        Exp acExp = null;

        if( parentPjAnswerExpNode.getNodeType().equals( GraphNodeConstants.NODE_TYPE_ACTION_CONDITION ) )
        {
            acExp = parentPjAnswerExpNode.getExpByNodeType();
        }
        */

        Exp answerExp = null;
        List<Exp> choiceList = null;

        if( acExp != null )
        {
            //TutorGraphNode pNode = null;
            Exp pExp = null;

            boolean isCompound = InferenceEngineUtil.isCompoundPredicate( acExp );
            if( isCompound )
            {
                //if it is compound, find the predicate that is P or C
                //Find perception
                for( Exp e : acExp.getChildren() )
                {
                    boolean isComprehension = InferenceEngineUtil.isComprehensionExp( e );
                    boolean isUserAction = InferenceEngineUtil.isUserPredicateExp( e );

                    if( isComprehension )
                    {
                        //Get Comprehension node by exp
                        /*TutorGraphNode cNode = tGraph.getComprehensionNodeByExp( e );
                        if( cNode != null )
                        {
                            pNode = cNode.getCurrentParentNode();
                            break;
                        }*/

                        //Get pExp
                        pExp = InferenceEngineUtil.getPerceptionFromComprehensionExp( e );
                        break;
                    }
                    else if( isUserAction )
                    {
                        //do nothing
                    }
                    else
                    {
                        //This is pNode, get pNode
                        //pNode = tGraph.getPerceptionNodeByExp( e );
                        pExp = e;
                        break;
                    }
                }
            }
            else
            {
                //Check if the predicate is Perception.
                boolean isPerception = InferenceEngineUtil.isPerceptionPredicateExp( acExp );
                if( !isPerception)
                {
                    return null;
                }
                else
                {
                    //pNode = tGraph.getPerceptionNodeByExp( acExp );
                    pExp = acExp;
                }
            }

            //Generate intervention
            //if( pNode != null )
            if( pExp != null )
            {
                answerExp = pExp;   // pNode.getExpByNodeType();

                //Get base predicate from the answer Exp
                //String basePerceptionPredicate = InterventionStringUtil.getTheBasePredicateFromExp( answerExp );

                //Get available choices of the base predicates
                choiceList = tGraph.generateSimilarChoice( answerExp );

                //Create strategy  Why do you select anesthesia lidocaine?
                /*
                String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_6_1_QUESTION );  //replace only Action
                String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
                String q_message = q_template.replace( "[ACTION_NODE]", translateAction );
                */

//                String q_message = q_template.replace( "[PERCEPTION]", basePerceptionPredicate );

                /*
                st6_1_question=Why do you [ACTION_NODE]
                st6_1_question_PJ=such that [PROJECTION_NODE]
                st6_1_question_mark=?
                * */


                String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_6_1_QUESTION );  //replace only Action
                //String translatePj = InterventionStringUtil.transformExpToFinalText( parentPjAnswerExpNode.getExpByNodeType() );
                String translatePj = InterventionStringUtil.transformExpToFinalText( pjDesiredOutcomeExpNode.getExpByNodeType() );
                String q_message = q_template.replace( "[PROJECTION_NODE]", translatePj );

                Question question = generateQuestion( q_message, choiceList, answerExp );

                Intervention intervention = formulateIntervention( null, false, question, StrategyConstants.STRATEGY_6_1);
                intervention.setStrategy_name( "ST_6_1_QUESTION_RECONFIRM_ACTION_PERCEPTION" );

                return intervention;
            }

            return null;  //TODO: Implement something
        }

        return null;
    }

    public static Intervention strategy_6_2_generateQuestionReconfirmConsequence()
    {
        //1. Get Action

        //correct action makes the tutor points to the next action.
        //But the conversation process continues; so we need to keep the value of action to discuss
        //PlanAction currentAction = ApplicationController.currentAction;
        PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ? StrategyController.actionToDiscuss : ApplicationController.currentAction;

        //2. use tutor graph, get the tutor graph by the correct pointed action (current action)
        List<TutorGraph> tutorGraph = ApplicationController.tutorGraphList;
        TutorGraph tGraph = TutorGraphUtil.getTutorGraphByAction( currentAction );

        //3. Get projection list (including the projection node that is not satisfied the desired outcome.
        //3.1 Get the answer projection nodes
        //Exp answerExp = currentAction.getDesired_outcomes();
        Exp answerExp = InterventionStringUtil.extractMainProjectionExp( currentAction.getDesired_outcomes() );

        //3.2 Get the choices projection nodes.
        //List<Exp> choiceList =  tGraph.generateProjectionChoices_noAnswer();
        List<Exp> choiceList = tGraph.generateSimilarChoice( answerExp );

        //Generate the choices
//        st12_question = Why do you [ACTION_NODE]?
//        st12_question_reconfirm_consequence = Why do you [ACTION_NODE] with [USER_ACTION_PARAMS]?
        String q_template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_6_2_QUESTION);  //replace only Action
        String translateAction = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );
        String q_message = q_template.replace( "[ACTION_NODE]", translateAction );

        //TODO: Add user actionCondition
        //If there is action Parameter, there should be added.

        Question question = generateQuestion( q_message, choiceList, answerExp );

        Intervention intervention = formulateIntervention( null, false, question, StrategyConstants.STRATEGY_6_2);
        intervention.setStrategy_name( "ST_6_2_GENERATE_QUESTION_RECONFIRM_CONSEQUENCE" );

        return intervention;
    }

    //-----------------------------------------------------------------------
    //Strategy 12 and other questioning strategy: Response the student's answer
    //-----------------------------------------------------------------------
    public static Intervention strategy_6_2_responseFeedbackToStudentAnswer(Intervention answerIntervention )
    {
        Intervention feedbackIntervention = null;

        //Get the answer of the question for intervention.
        //check if the selected choice is the answer.
        Choice studentChoice = answerIntervention.getInterventionData().getStudentAnswer();
        if( studentChoice.isAnswer() )
        {
            //FB+
            feedbackIntervention = generateLeadingPositiveFeedback();
        }
        else
        {
            //FB-
            //Additional Strategy?
            feedbackIntervention = generateLeadingNegativeFeedback();
        }
        return feedbackIntervention;
    }
    //==================================================================================================

    //This is for single predicates.
    private static Exp createNonAnswerChoice(Exp answerExp)
    {
        //negate the answerExp
        Exp non_answerExp = null;

        boolean isCompoundPredicate = InferenceEngineUtil.isCompoundPredicate( answerExp );

        if( isCompoundPredicate )
        {
            //Get value
            //( answerExp.getConnective().equals( Connective.AND ) )
            {
                //TODO: enhance each node
                //idea:
                //For each children which is atom or negated atom,
                //Generate the negation add to new expressin with And
                //Return the new expression
                //TODO: Test the following logic
            /*
            non_answerExp = new Exp(Connective.AND);
            Exp tempExp = null;
            for( Exp e: answerExp.getChildren() )
            {
                tempExp = createNonAnswerChoice( e );
                if( tempExp != null )
                {
                    non_answerExp.addChild( tempExp );
                }
            }*/
            }
        }
        else
        {
            //If the answerExp is not compound, set negated.
            if( answerExp.getConnective().equals( Connective.NOT ) )
            {
                non_answerExp = answerExp.getChildren().get( 0 );
            }
            else if( answerExp.getConnective().equals( Connective.ATOM ) )
            {
                non_answerExp = new Exp(Connective.NOT);
                non_answerExp.addChild( answerExp );
            }
        }

        return non_answerExp;
    }

    public static Question generateYesNoQuestion(String questionString, Exp exp, boolean isAnswer )
    {
        Set<Choice> choice_set = new HashSet<>();

        if( isAnswer )
        {
            //exp is the answer
            //Create choice for answer
            choice_set.add(new Choice( exp, true, true ) );

            //Create choice for negated
            Exp non_answerExp = createNonAnswerChoice( exp );
            choice_set.add(new Choice( non_answerExp, false, true ) );
        }
        else
        {
            //exp is NOT the answer
            //Create choice for answer
            Choice choice = new Choice( exp, (isAnswer) , true );
            choice.setChoiceDescription( "Yes" );
            choice_set.add( choice );

            //Create choice for negated
            Exp non_answerExp = createNonAnswerChoice( exp );
            Choice non_answer_choice = new Choice( non_answerExp, (!isAnswer), true );
            non_answer_choice.setChoiceDescription( "No" );

            choice_set.add( non_answer_choice );
        }

        //Create questions
        Question q = new Question();
        q.setQuestion(questionString);
        q.setMultipleChoices( true );
        q.setChoices( new ArrayList<>( choice_set ) );

        return q;
    }


    public static Question generateYesNoQuestion_forCorrectChoice(String questionString, Exp answerExp )
    {
        //Create choices from AnswerExp
        Set<Choice> choice_set = new HashSet<>();
        choice_set.add(new Choice( answerExp, true, true ) );

        //Create choice for non_answerExp
        Exp non_answerExp = createNonAnswerChoice( answerExp );
        choice_set.add(new Choice( non_answerExp, false, true ) );

        //Create questions
        Question q = new Question();
        q.setQuestion(questionString);
        q.setMultipleChoices( true );
        q.setChoices( new ArrayList<>( choice_set ) );

        return q;
    }

    public static Question generateQuestion_answerInChoiceList(String questionString, ArrayList<Choice> choiceList )
    {
        // questionString is about the message that replace value in [ACTION_NODE]
        //1. Create teh question and choices object.
        //2. Random the order of the choices. the choices

        int total_choices = getNumberOfTotalChoices( choiceList.size() + 1 );  //this is non-answer choices

        Question q = new Question();
        q.setQuestion(questionString);
        q.setMultipleChoices( true );

        q.setChoices( choiceList );

        return q;
    }


    public static Question generateQuestion(String questionString, List<Exp> choiceList, Exp answerExp )
    {
        // questionString is about the message that replace value in [ACTION_NODE]
        //1. Create teh question and choices object.
        //2. Random the order of the choices. the choices

        int total_choices = getNumberOfTotalChoices( choiceList.size() + 1 );  //this is non-answer choices

        Question q = new Question();
        q.setQuestion(questionString);
        q.setMultipleChoices( true );

        Set<Choice> choice_set = new HashSet<>();

        if( answerExp !=  null )
        {
            choice_set.add(new Choice( answerExp, true, false ) );
        }

        for( Exp exp : choiceList)
        {
            if( choice_set.size() < total_choices )        //limited choices
                    //&& !choice_set.contains( exp ) )           //error
            {
                choice_set.add(new Choice(exp, false, false));
            }
        }

        //InterventionConstants.MAXIMUM_CHOICES;
        //Transform set into arraylist
        q.setChoices( new ArrayList<>( choice_set ) );

        return q;
    }
    private static int getNumberOfTotalChoices( int available_choices )
    {
        return ( available_choices > InterventionConstants.MAXIMUM_CHOICES ) ?  //if total choices is more than max choices
                InterventionConstants.MAXIMUM_CHOICES : available_choices;
    }

    //-----------------------------------------------------------------------
    //Strategy 6 : Ask a question to reconfirm
    //-----------------------------------------------------------------------
/*    public static Intervention strategy_6_1_generateQuestionReConfirmAction()
    {
        //Get Current Action
        //Get Multiple Choices related to Action
        //Get 1. Tutor's action
        PlanAction currentAction = ApplicationController.currentAction;

        //TODO: YOU NEED TUTOR GRAPH TO GENERATE INTERVENTION
        //Get 2. Trace the perception to generate the question.

        //Get 3. Set the answer of the choices generated.

        Question q = new Question();
        q.setQuestion("Can you imagine why the root canal sealing is maximized?");
        q.setMultipleChoices( true );

        Choice choices = new Choice();
        ArrayList<Choice> choiceSet = new ArrayList<Choice>();
        choiceSet.add(new Choice("the root canal surface is not slipped", true, false));
        choiceSet.add(new Choice("the main cone size equals to the MAF size", false, false));
        choiceSet.add(new Choice("the main cone size is greater than the MAF size", false, false));
        choiceSet.add(new Choice("the main cone size is less than the MAF size", false, false));

        q.setChoices( choiceSet );

        //Generate Intervention object
        return formulateIntervention( null, false, q, StrategyConstants.STRATEGY_6_1 );
    }
*/
    //-------------------------------------------
    //Strategy 14: Prompt for remind student if student repeated mistakes.
    //-------------------------------------------
    public static Intervention strategy_14_generateHintForRepeatedMistakes()
    {
        String template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_14_PROMPT_REPEATED_INCORRECT_ACTION);
//        boolean isRepatedAction = isStudentRepeatTheSameAction();

        PlanAction action = ApplicationController.currentAction;
        String translateActionName =  ApplicationController.domain_predicate_map.get( action.getActionName() );
        String finalActionName = (translateActionName != null) ? translateActionName : action.getActionName();
        template = template.replace( "[ACTION_NODE]", finalActionName );

        Intervention intervention = formulateIntervention( template, true, null, null );
        intervention.setStrategy_name( "ST_14_GENERATE_HINT_REPEATED_MISTAKES" );

        return intervention;
    }

    //-------------------------------------------
    //Strategy 15: Hint for allowing_explore, Just a word,but the control to allow student to solve a problem it is still not tested..
    // Trigger, student's DO is not satisfy
    //
    // Algorithm:
    // 1. get the latest student's action, and get the DO from plan.
    // 2. check if there exist allow explore projection
    // 3. check if all non-allow-explore projection are DO satisfied
    ///4. check if allow-explore projection is DO not satisfy
    // 5. Generate the hint
    //         2.1 if it is not allow_explored, it must satisfy DO
    //         2.2 if it is allow_explored, the, Generate the message.
    //-------------------------------------------
//    st14_prompt_repeated_action="You try [ACTION_NODE] repeatedly."
//    st15_hint_allow_explore="Look [ALLOW_EXPLORE_PJ_NODE]"



    public static Intervention strategy_15_generateHintForAllowExplore()
    {
        //1. get Template
        String template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_15_ALLOW_EXPLORE_HINT);
//        //Get pjNode that is allowed Explore.
//        Exp desired ApplicationController.currentAction.getDesired_outcomes();
//
//        PlanAction action = ApplicationController.currentAction;
//        String translateActionName =  ApplicationController.domain_predicate_map.get( action.getActionName() );
//        String finalActionName = (translateActionName != null) ? translateActionName : action.getActionName();
//        template = template.replace( "[ALLOW_EXPLORE_PJ_NODE]", finalActionName );
//
//        String message = generateInterventionContent( template, null  );
//

        Intervention intervention = formulateIntervention( template, true, null, null );
        intervention.setStrategy_name( "ST_15_GENERATE_HINT_ALLOW_EXPLORE" );

        return intervention;
    }

    //-------------------------------------------
    //Strategy 11:
    //-------------------------------------------
    public static Intervention strategy_x_generateHintForIncorrectAction( )
    {
        //1. get Template
        //stx_hint_incorrect_action=Think about the action that makes [PJ_NODES]
        String template = ApplicationController.intervention_template_map.get( InterventionTemplateKeyConstants.ST_X_HINT_INCORRECT_ACTION );

        Exp desire_outcomes = ApplicationController.currentAction.getDesired_outcomes();

        Exp selected_desired_outcomes = randomlySelectDO( desire_outcomes, true );

        String translatedActionName = InterventionStringUtil.transformExpToFinalText( selected_desired_outcomes );
        template = template.replace( "[PJ_NODES]", translatedActionName );

        Intervention intervention = formulateIntervention( template, true, null, null );
        intervention.setStrategy_name( "ST_X_GENERATE_HINT_FOR_INCORRECT_ACTION" );

        return intervention;
    }

    private static Exp randomlySelectDO(Exp e, boolean isMainRequire )
    {
        //Extract the main
        Exp selectedExp = null;
        if( e.getConnective().equals(Connective.AND) )
        {
            List<Exp> children = null;

            //Select main exp only.
            if( isMainRequire )
            {
                children = new ArrayList<>();

                //Get only main children
                for( Exp exp_child : e.getChildren() )
                {
                    if( exp_child.isMain() )
                    {
                        children.add(exp_child);
                    }
                }

                //check if there is no main set
                if( children.size() == 0 )
                {
                    //If there is no setting of main. set
                    children = e.getChildren();
                }
            }
            else
            {
                //Get all children
                children = e.getChildren();
            }

            //----------------
            //Select Randomly.
            //----------------
            int random_number = Utils.getOneRandomNumber( children.size() );

            selectedExp = children.get( random_number -1 );
        }
        else
        {
            selectedExp = e;
        }
        return selectedExp;
    }
    //-------------------------------------------
    //Strategy 11:
    //-------------------------------------------
    public static Intervention strategy_11_generateReconfirmTheAction(Exp correctActionExp )
    {
        if( correctActionExp == null )
        {
            //1. get Template
            String template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_11_RECONFIRM_ACTION);
            System.out.println( "=== TEMPLATE ===" );
            System.out.println( template );
            System.out.println( "----------------" );

            String message = generateInterventionContent( template, null  );
            return formulateIntervention( message, true, null, null );
        }
        else
        {
            //1. get Template
            String template = ApplicationController.intervention_template_map.get(InterventionTemplateKeyConstants.ST_11_RECONFIRM_CORRECT_ACTION );
            System.out.println( "=== TEMPLATE ===" );
            System.out.println( template );
            System.out.println( "----------------" );

            //Main Action

            //st11_reconfirm_correct_action=You should [ACTION_NODE] with [ACTION_COND].
            PlanAction currentAction = ( StrategyController.actionToDiscuss != null) ?
                                         StrategyController.actionToDiscuss : ApplicationController.currentAction;
//            String actionName = ApplicationController.currentAction.getActionName();    //insert_rubber_dam
            String translatedActionName = ApplicationController.domain_predicate_map.get( currentAction.getActionName() );

            //Action Details
            String translateActionDetails = InterventionStringUtil.transformExpToFinalText( correctActionExp );
            String message = template.replace( "[ACTION_NODE]", translatedActionName );
            message = message.replace( "[ACTION_COND]", translateActionDetails );
            //message = changeToSentenceFormat( message );

            Intervention intervention = formulateIntervention( message, true, null, null );
            intervention.setStrategy_name( "ST_11_GENERATE_RECONFIRM_ACTION" );

            return intervention;
        }

       //return null;
    }

    private static String changeToSentenceFormat( String translatedString)
    {
        if( translatedString == null || translatedString.trim().length() == 0 )
            return null;

        String tempDescription = InterventionGenerator.makeTheFirstStringCapital( translatedString );
        tempDescription = tempDescription + ".";

        return tempDescription;

    }

    public static Intervention generateLeadingNegativeFeedback()
    {
        String message = getLeadingNegativeFB();

        Intervention intervention = formulateIntervention( message, true, null, null );
        intervention.setStrategy_name( "ST_3_LEADING_FB-" );

        return intervention;
    }

    public static String getLeadingNegativeFB()
    {
        int random_number = Utils.getOneRandomNumber(3);
        String key = InterventionTemplateKeyConstants.LEADING_NEGATIVE_FB_Main + random_number;
        return intervention_template_map.get( key );
    }

    public static Intervention generateLeadingPositiveFeedback()
    {
        String message = getLeadingPositiveFB();
        Intervention intervention = formulateIntervention( message, true, null, null  );
        intervention.setStrategy_name( "ST_5_LEADING_FB+" );

        return intervention;
    }

    public static String getLeadingPositiveFB()
    {
        int random_number = Utils.getOneRandomNumber(3);
        String key = InterventionTemplateKeyConstants.LEADING_POSITIVE_FB_Main + random_number;
        return intervention_template_map.get( key );
    }

    public static Intervention formulateIntervention(String message,
                                                     boolean isPrompt,
                                                     Question question,
                                                     String strategyName )
    {
        if(isPrompt)
        {
            if(message == null || message.trim().length() == 0 )
            {
                return null;
            }

            //Formulate message
            Prompt prompt = new Prompt();
            prompt.setPromptMsg( new PromptMsg( message ) );

            Intervention i = new Intervention();
            InterventionData invData = new InterventionData();

            invData.setQuestionAnswerSession( false );

            invData.setPrompt( prompt );
            i.setInterventionData(invData);

            return i;
        }
        else
        {
            //is question
            if( question == null )
            { return null;}

            Intervention i = new Intervention();
            InterventionData invData = new InterventionData();

            invData.setQuestionAnswerSession( true );
            invData.setAnswerQuestion( false );
            invData.setStudentAnswer( null );

            if( strategyName != null &&  strategyName.trim().length() > 0 )
            {
                invData.setResponseToStrategy( strategyName );
            }

            invData.setQuestion( question );
            i.setInterventionData(invData);
            return i;
        }
    }

    public static GraphNode getLatestActionFromStateNode(GraphNode stateNode )
    {
        GraphNode latestActionNode = null;
        if(stateNode != null )
        {
            List<GraphNode> actionList = stateNode.getChildNodes();
            latestActionNode = actionList.get( actionList.size() - 1 );

        }
        return latestActionNode;
    }

//    public static Intervention generateNegativeFeedback2()
//    {
//
//    }

    public static Intervention generateNegativeFeedback()
    {
        //Get the latest acction of student's graph
        List<StudentGraph> studentGraphList = ApplicationController.studentGraphList;
        StudentGraph studentGraph = studentGraphList.get( studentGraphList.size() - 1 );

        //Get data into EXP
//        Exp pjExp = dataObject.getProjectionNode().getExpByNodeType();
//        Exp acExp = dataObject.getActionConditionNode().getExpByNodeType();
//        PlanAction planAction = dataObject.getActionNode().getActionNode();

        StudentGraphNode pjNode = studentGraph.getProjectNodeThatDoesNotSatisfyDO();
        if( pjNode == null )
        {
            return null;
        }

        Exp pjExp = pjNode.getExpByNodeType();
        Exp acExp = pjNode.getParentNodes().get(0).getExpByNodeType();
        PlanAction planAction = studentGraph.getActionNode().getActionNode();

        //Exp pjExp = studentGraph.getProjectionList().
        //-------------
        // Message 1:
        //-------------
        //1 Get template
        //NEGATIVE_FEEDBACK_1ST_1
        String template = intervention_template_map.get( InterventionTemplateKeyConstants.NEGATIVE_FEEDBACK_1ST2_1 );

        //2. Replate with predicate (without transform)
        //   negative_fb_1st_1=You [ACTION_NODE] such that [ACTION_COND]. [PJ_NODES].
        //   negative_fb_1st_2=Look at the [P_NODES].

        /*
        String firstPredicate = InterventionStringUtil.get1stPredicateStringFromExp( userExp );    //StringUtil.subStringExp( userExp );
            String template3 = intervention_template_map.get( InterventionTemplateKeyConstants.NEGATIVE_FEEDBACK_1ST_3 );
            //String translateString = InterventionStringUtil.transformExpToFinalText( userExp );         //translate EXP to English
            String translateString = ApplicationController.domain_predicate_map.get( firstPredicate );      //translate EXP to English
            message2 = template3.replace( "[ACTION_PARAMS]", translateString  );   //Get the first predicates

        * */

        //2.1 Replace pjNOde
        String translatePj = InterventionStringUtil.transformExpToFinalText( pjExp );
        String translateAc = InterventionStringUtil.transformExpToFinalText( acExp );
        String translateAction = ApplicationController.domain_predicate_map.get( planAction.getActionName() );

//        String message1 = template.replace( "[PJ_NODES]", pjExp.toString() );
//        message1 = message1.replace( "[ACTION_COND]", acExp.toString() );
//        message1 = message1.replace( "[ACTION_NODE]", planAction.getActionName() );

        String message1 = template.replace( "[PJ_NODES]", translatePj );
        message1 = message1.replace( "[ACTION_COND]", translateAc );
        message1 = message1.replace( "[ACTION_NODE]", translateAction );

        System.out.println( message1 );

        //-------------
        //Message 2  Get P Node.
        //-------------
        //Check if there is pNode in actionConditionNode
        //1. Use acExp as a key, check if it is the perception or comprehension.
        // It is not both of them, it is user action.
        //------------------------------
        // FROM: Intervention.txt
        //------------------------------
        //negative_fb_1st_2=Look at the [P_NODES].
        //negative_fb_1st_3=Look at the [ACTION_PARAMS].
        //-------------
//        String template2 = intervention_template_map.get( InterventionTemplateKeyConstants.NEGATIVE_FEEDBACK_1ST_2 );
//        String template3 = intervention_template_map.get( InterventionTemplateKeyConstants.NEGATIVE_FEEDBACK_1ST_3 );

        //Check if acExp is comprehension, user action parameters, and perception
        Exp pExp = null;
        Exp cExp = null;
        Exp userExp = null;
        Exp toUseExp = null;

        boolean isComprehension = InferenceEngineUtil.isComprehensionExp( acExp );
        if( isComprehension )
        {
            cExp = acExp;
            pExp = InferenceEngineUtil.getPerceptionFromComprehensionExp( cExp );
            toUseExp = pExp;   //if it is node inside action condition is C, and get P, then use P
        }
        else
        {
            //If the node inside the action condition is not C,
            //Check if it is the user's action, else it is p.
            //REMOVE:  pExp = acExp; this is wrong. you must check if it is user's action first.

            boolean isUserPredicateExp = InferenceEngineUtil.isUserPredicateExp( acExp );
            if( isUserPredicateExp )
            {
                userExp = acExp;
                toUseExp = userExp;
            }
            else
            {
                pExp = acExp;
                toUseExp = pExp;
            }
        }


        //-----------------
        //Generate message.
        //-----------------
        String message2 = null;
        if( userExp != null )
        {
            String firstPredicate = InterventionStringUtil.get1stPredicateStringFromExp( userExp );    //StringUtil.subStringExp( userExp );
            if( firstPredicate != null )
            {
                //NEGATIVE_FEEDBACK_1ST_3
                String template3 = intervention_template_map.get( InterventionTemplateKeyConstants.NEGATIVE_FEEDBACK_1ST2_3 );
                //String translateString = InterventionStringUtil.transformExpToFinalText( userExp );         //translate EXP to English
                String translateString = ApplicationController.domain_predicate_map.get( firstPredicate );      //translate EXP to English
                message2 = template3.replace( "[ACTION_PARAMS]", translateString  );   //Get the first predicates
            }
        }
        else if( pExp != null )
        {
            String firstPredicate = InterventionStringUtil.get1stPredicateStringFromExp( pExp );
            if( firstPredicate != null )
            {
                //NEGATIVE_FEEDBACK_1ST_2
                String template2 = intervention_template_map.get( InterventionTemplateKeyConstants.NEGATIVE_FEEDBACK_1ST2_2 );
//            String translateString = InterventionStringUtil.transformExpToFinalText( pExp );         //translate EXP to English
                String translateString = ApplicationController.domain_predicate_map.get( firstPredicate );      //translate EXP to English
                message2 = template2.replace( "[P_NODES]", translateString );
            }

            //makeTheFirstStringCapital
        }

        Intervention intervention;

        if( message2 != null && message2.trim().length() > 0 )
        {
            intervention = formulateIntervention( message1+message2, true, null, null );
        }
        else
        {
            intervention = formulateIntervention( message1, true, null, null );
        }

        intervention.setStrategy_name( "ST_3_FB-" );
        return intervention;
    }

    public static String generateInterventionContent( String template, StudentGraphNode stGraphNode )
    {
        if( template == null || template.trim().length() == 0 )
        {
            return null;
        }

        /*
         String message1 = template.replace( "[PJ_NODES]", translatePj );
        message1 = message1.replace( "[ACTION_COND]", translateAc );
        message1 = message1.replace( "[ACTION_NODE]", translateAction );
        * */


        //1. [TUTOR_ACTION_NODE]
        if( template.contains("[TUTOR_ACTION_NODE]") )
        {
            String actionName = ApplicationController.currentAction.getActionName();    //insert_rubber_dam

            //2. replace action
            String translatedActionName = ApplicationController.domain_predicate_map.get( actionName );
            String final_action_name = (translatedActionName != null) ? translatedActionName : actionName;
            template = template.replace( "[TUTOR_ACTION_NODE]", final_action_name );
        }

        /*
        String firstPredicate = InterventionStringUtil.get1stPredicateStringFromExp( userExp );    //StringUtil.subStringExp( userExp );
            String template3 = intervention_template_map.get( InterventionTemplateKeyConstants.NEGATIVE_FEEDBACK_1ST_3 );
            //String translateString = InterventionStringUtil.transformExpToFinalText( userExp );         //translate EXP to English
            String translateString = ApplicationController.domain_predicate_map.get( firstPredicate );      //translate EXP to English
            message2 = template3.replace( "[ACTION_PARAMS]", translateString  );   //Get the first predicates
        * */

        //TODO: TEST every block (except action name)
        if(stGraphNode != null )
        {
            if( template.contains("[ACTION_NODE]") )
            {
                //2. [ACTION_NODE]
                if( stGraphNode.getActionNode() != null )
                {
                    PlanAction action = stGraphNode.getActionNode();
                    String translateActionName =  ApplicationController.domain_predicate_map.get( action.getActionName() );
                    String finalActionName = (translateActionName != null) ? translateActionName : action.getActionName();
                    template = template.replace( "[ACTION_NODE]", finalActionName );
                }
            }

            if( template.contains("[ACTION_COND]") )
            {
                //3. [ACTION_COND]
                if( stGraphNode.getActionCondition() != null )
                {
                    String translateString = InterventionStringUtil.transformExpToFinalText( stGraphNode.getActionCondition() );
                    template = template.replace( "[ACTION_COND]", translateString );
                }
            }

            //4. [LEADING_POS_FB]
            //5. [C_NODES]
            //6. [P_NODES]
            if( template.contains("[P_NODES]") )
            {

            }
            //7. [PJ_NODES]
            if( template.contains("[PJ_NODES]") )
            {
                if( stGraphNode.getProjectionExp()  != null )
                {
                    String translateString = InterventionStringUtil.transformExpToFinalText( stGraphNode.getProjectionExp() );
                    template = template.replace( "[PJ_NODES]", translateString );
                }
            }
            //8. [BLANK]
        }

        return template;
    }

    public static String makeTheFirstStringCapital( String stringIn )
    {
        //make the first letter capital for pjNode
        if( stringIn != null && stringIn.length() > 2 )
        {
            stringIn  = stringIn.substring(0, 1).toUpperCase() + stringIn.substring( 1 );
        }
        return stringIn;
    }

}
