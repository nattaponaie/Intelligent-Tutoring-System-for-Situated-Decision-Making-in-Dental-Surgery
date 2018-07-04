package kb.common.datastructure.graph;

import com.surgical.decision3.intervention.constants.InterventionConstants;

import java.util.ArrayList;
import java.util.List;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.NamedTypedList;
import fr.uga.pddl4j.parser.Symbol;
import fr.uga.pddl4j.parser.TypedSymbol;
import kb.common.constants.GraphNodeConstants;
import kb.common.controller.ParserController;
import kb.util.InferenceEngineUtil;
import kb.util.InterventionStringUtil;

/**
 * Created by Nattapon on 16/7/2560.
 */

public class TutorGraph
{

    TutorGraphNode PerceptionAndComprehensionNode = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PERCEPANDCOMP);
    List<TutorGraphNode> PerceptionList = new ArrayList<>();
    List<TutorGraphNode> ComprehensionList = new ArrayList<>();
    List<TutorGraphNode> ActionConditionList = new ArrayList<>();
    List<TutorGraphNode> ProjectionList = new ArrayList<>();

    TutorGraphNode actionNode = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_ACTION);

    public List<TutorGraphNode> getPerceptionList()
    {
        return PerceptionList;
    }

    public void setPerceptionList(List<TutorGraphNode> perceptionList)
    {
        PerceptionList = perceptionList;
    }

    public List<TutorGraphNode> getComprehensionList()
    {
        return ComprehensionList;
    }

    public void setComprehensionList(List<TutorGraphNode> comprehensionList)
    {
        ComprehensionList = comprehensionList;
    }

    public TutorGraphNode getActionNode()
    {
        return actionNode;
    }

    public void setActionNode(TutorGraphNode actionNode)
    {
        this.actionNode = actionNode;
    }

    public TutorGraphNode getPerceptionAndComprehensionNode()
    {
        return PerceptionAndComprehensionNode;
    }

    public void setPerceptionAndComprehensionNode(TutorGraphNode perceptionAndComprehensionList)
    {
        this.PerceptionAndComprehensionNode = perceptionAndComprehensionList;
    }

    public List<TutorGraphNode> getActionConditionList()
    {
        return ActionConditionList;
    }

    public void setActionConditionList(List<TutorGraphNode> actionConditionList)
    {
        this.ActionConditionList = actionConditionList;
    }

    public List<TutorGraphNode> getProjectionList()
    {
        return ProjectionList;
    }

    public List<Exp> getProjectionExpList()
    {
        List<Exp> projectionList = new ArrayList<>();
        for( TutorGraphNode tgNode : this.getProjectionList() )
        {
            Exp tgNodeExp = tgNode.getProjectionExp();
            projectionList.add(tgNodeExp);
        }

        return projectionList;
    }

    public TutorGraphNode getPerceptionNodeByExp(Exp pExp)
    {
        TutorGraphNode pNode = null;

        List<Exp> comprehensionList = new ArrayList<>();
        for( TutorGraphNode tgNode : this.getComprehensionList() )
        {
            if( tgNode.getComprehensionExp().equals(pExp) )
            {
                pNode = tgNode;

            }
        }
        return pNode;
    }

    public TutorGraphNode getComprehensionNodeByExp(Exp cExp)
    {
        TutorGraphNode cNode = null;

        List<Exp> comprehensionList = new ArrayList<>();
        for( TutorGraphNode tgNode : this.getComprehensionList() )
        {
            if( tgNode.getComprehensionExp().equals(cExp) )
            {
                cNode = tgNode;

            }
        }
        return cNode;
    }

    public TutorGraphNode getProjectionNodeByExp(Exp pjExp)
    {
        TutorGraphNode pjNode = null;

        List<Exp> projectionList = new ArrayList<>();
        for( TutorGraphNode tgNode : this.getProjectionList() )
        {
            if( tgNode.getProjectionExp().equals(pjExp) )
            {
                pjNode = tgNode;

            }
        }
        return pjNode;
    }

    public TutorGraphNode getProjectionNodeByMainDesireOutcomeExp(Exp doMainExp)
    {
        TutorGraphNode pjNode = null;

        if( doMainExp == null )
        {
            return null;
        }

        boolean isCompound = InferenceEngineUtil.isCompoundPredicate(doMainExp);

        Exp exp = null;

        if( isCompound )
        {
            //random one of them
//            int random_number = Utils.getOneRandomNumber(doMainExp.getChildren().size());
//            exp = doMainExp.getChildren().get(random_number - 1);

            //make sure that projection
        }
        else
        {
            exp = doMainExp;
        }

        List<Exp> projectionList = new ArrayList<>();
        for( TutorGraphNode tgNode : this.getProjectionList() )
        {
            if( tgNode.isCompoundExp(tgNode.getProjectionExp()) )
            {
                //TODO: if it is compound PJ node, get the PJ node that has this exp as member.
                for( Exp e : tgNode.getProjectionExp().getChildren() )
                {
                    if( e.contains(exp) )
                    {
                        pjNode = tgNode;
                    }
                }
            }
            else
            {
                //This is not compound
                if( tgNode.getProjectionExp().equals(exp) )
                {
                    pjNode = tgNode;
                }
            }
        }

        return pjNode;
    }

    public void setProjectionList(List<TutorGraphNode> projectionList)
    {
        this.ProjectionList = projectionList;
    }

    /**
     * Method to get the pjNode by the property 'isDesiredOutCome'
     * param:
     * projectionNodeList : list of available projection nodes
     * isSatisfyDO : a property of the target node
     * excludeList: the list of unwanted pjNode.
     */
    public TutorGraphNode getProjectionNodeByDesiredOutcome(boolean isSatisfyDO,
                                                            List<Exp> excludeList)
    {
        for( TutorGraphNode pjNode : this.getProjectionList() )
        {
            if( pjNode.isDesiredOutCome() == isSatisfyDO )  //isDesiredOutcome == false
            {
                if( excludeList == null )   //Get always get the first when it is found
                {
                    return pjNode;
                }
                else
                {
                    //When getting the node, if it is in the exclude list, get the new one.
                    //If it is not in the exclude list, use this node.
                    if( excludeList.contains(pjNode.getProjectionExp()) )
                    {
                        continue;
                    }
                    else
                    {
                        return pjNode;
                    }
                }
            }
        }
        return null;
    }

    public List<Exp> generatePerceptionAnswer()
    {

        return null;
    }

    public List<Exp> generatePerceptionChoices_fromActionCondition_noAnswer(Exp answerExp)
    {
        List<Exp> choiceList = new ArrayList<>();
        Exp choice = null;

        //1. Get ActionConditionlist and PJ satisfy desired outcome.

//
//        int totalChoices = InterventionConstants.MAXIMUM_CHOICES -1 ;
//
//        for( int i = 0; i < totalChoices; i++ )
//        {
//            //From Pj, get parent that is ActionCondition
//            //Explore in action condition if there is C or P
//            // If it is C, get P as choices
//            TutorGraphNode tgNode = getProjectionNodeByDesiredOutcome( true, choiceList );
//
//            if( ! choiceList.contains( tgNode.getProjectionExp() ) )
//            {
//                choiceList.add( tgNode.getProjectionExp() );
//            }
//        }

        return choiceList;
    }

    /*
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

                non_answerExp = new Exp(Connective.AND);
                Exp tempExp = null;
                for( Exp e: answerExp.getChildren() )
                {
                    tempExp = createNonAnswerChoice( e );
                    if( tempExp != null )
                    {
                        non_answerExp.addChild( tempExp );
                    }
                }
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
    * */
    public List<Exp> generateSimilarChoice(Exp answerExp)
    {
        List<Exp> choiceList = new ArrayList<>();
        Exp choice = null;

        // from AnswerExp
        // Check if it is compound

        boolean isCompoundPredicate = InferenceEngineUtil.isCompoundPredicate(answerExp);
        if( isCompoundPredicate )
        {
            //not at this moment.
            //Not Implemented yet.
        }
        else
        {
            boolean isPredicateHasPairValue = isPredicateHasPairConstantValue(answerExp);
            if( isPredicateHasPairValue )
            {
                //If answerExp has both base predicate and its value, then use the available value as choices.
                //1. Get base predicate
                String basePredicate = InterventionStringUtil.getTheBasePredicateFromExp(answerExp);
                String basePredicateValue = InterventionStringUtil.getTheBasePredicateValueFromExp(answerExp);

                //2. get available value of the prediate
                NamedTypedList predicate = ParserController.getPredicateByBaseString(basePredicate);

                //3. Get type of the constant
                Symbol typeOfConstant = ParserController.getTypeOfConstantByPredicate(predicate);

                //4. Get the value of constants from domain by the type of constant
                List<TypedSymbol> constantsByTypeList = ParserController.getConstantsByType(typeOfConstant);

                //5. Transform into ChoiceList List<Exp>
                for( TypedSymbol ts : constantsByTypeList )
                {
                    Exp tempExp = new Exp(Connective.ATOM);
                    //Create basePredicate
                    Symbol s1 = new Symbol(Symbol.Kind.PREDICATE, basePredicate);            //predicate
                    Symbol s2 = new Symbol(Symbol.Kind.CONSTANT, ts.getImage());            //value

                    //if the constants value duplicated with answer
                    if( s2.getImage().equals(basePredicateValue) )
                    {
                        continue;
                    }

                    List<Symbol> atomList = new ArrayList<>();
                    atomList.add(s1);
                    atomList.add(s2);

                    tempExp.setAtom(atomList);
                    choiceList.add(tempExp);
                }


                System.out.println("Predicate: " + predicate.toString());
            }
            else
            {
                //if the answerExp has only base predicate, negated.
                Exp negatedExp = negateExp(answerExp);
                choiceList.add(negatedExp);
            }


        }

        return choiceList;
    }

    private Exp negateExp(Exp exp)
    {
        Exp negatedExp = null;
        //If the answerExp is not compound, set negated.
        if( exp.getConnective().equals(Connective.NOT) )
        {
            negatedExp = exp.getChildren().get(0);
        }
        else if( exp.getConnective().equals(Connective.ATOM) )
        {
            negatedExp = new Exp(Connective.NOT);
            negatedExp.addChild(exp);
        }

        return negatedExp;
    }

    private boolean isPredicateHasPairConstantValue(Exp exp)
    {
        boolean hasPairConstantValue = false;
        boolean isCompoundPredicate = InferenceEngineUtil.isCompoundPredicate(exp);
        if( isCompoundPredicate )
        {
            return false;
        }

        // the connective must be ATOM
        if( exp.getConnective().equals(Connective.ATOM) )
        {
            List<Symbol> atomList = exp.getAtom();
            if( atomList != null && atomList.size() == 2 )
            {
                //Check if the first one is predicate and the second one is constant
                Symbol firstSymbol = exp.getAtom().get(0);
                Symbol secondSymbol = exp.getAtom().get(1);

                if( firstSymbol.getKind().equals(Symbol.Kind.PREDICATE) &&
                        secondSymbol.getKind().equals(Symbol.Kind.CONSTANT) )
                {
                    hasPairConstantValue = true;
                }
            }
        }
        else if( exp.getConnective().equals(Connective.NOT) )
        {
            Exp tempExp = exp.getChildren().get(0);

            List<Symbol> atomList = tempExp.getAtom();
            if( atomList != null && atomList.size() == 2 )
            {
                //Check if the first one is predicate and the second one is constant
                Symbol firstSymbol = tempExp.getAtom().get(0);
                Symbol secondSymbol = tempExp.getAtom().get(1);

                if( firstSymbol.getKind().equals(Symbol.Kind.PREDICATE) &&
                        secondSymbol.getKind().equals(Symbol.Kind.CONSTANT) )
                {
                    hasPairConstantValue = true;
                }
            }
        }

        return hasPairConstantValue;
    }

    public List<Exp> generateProjectionChoices_noAnswer()
    {
        List<Exp> choiceList = new ArrayList<>();
        Exp choice = null;

        int totalChoices = InterventionConstants.MAXIMUM_CHOICES - 1;

        for( int i = 0; i < totalChoices; i++ )
        {
            //get choice that is not desired outcome
            TutorGraphNode tgNode = getProjectionNodeByDesiredOutcome(false, choiceList);

            if( !choiceList.contains(tgNode.getProjectionExp()) )
            {
                choiceList.add(tgNode.getProjectionExp());
            }
        }

        return choiceList;
    }

    //1. Get projection List
    //2. for each projection list, get its parent : action condition
    //3  for the action condition, get the perception
    //4 check if the projection list is desired outcome, then add to answer
    //else add to choice list.

    public Exp generatePerceptionChoicesFromActionCondition_onlyAnswer()
    {
        //getStateNode().getPerceptionWithComprehensionString()
        //List<Exp> choiceList = new ArrayList<>();
        Exp answer_choice = null;
        int totalChoices = InterventionConstants.MAXIMUM_CHOICES - 1;
        List<TutorGraphNode> acNodeList = this.getActionConditionList();

        //1. Get AnswerNode
        for( TutorGraphNode acNode : acNodeList )
        {

            /*
            TEmp Comment
            if(acNode.getActionCondition().getConnective().equals(Connective.AND ) )
            {
                //TODO: we do not have mechanism to check the nested group of comprehension nodes.
                //In fact, it should extract the comprehension node from actionCondition Node.
                //The logic should be
                //Flat the list of comprehension exp
                //for each exp, return only exp that is comprehension node.
                //for each extracted node, return the pnodes.
                continue;
            }*/

            //for each acNode, extract perception
            boolean isComprehension = InferenceEngineUtil.isComprehensionExp(acNode.getActionCondition());
            boolean isUserAction = InferenceEngineUtil.isUserPredicateExp(acNode.getActionCondition());

            if( isComprehension )
            {
                //Get Perception
                Exp pExp = InferenceEngineUtil.getPerceptionFromComprehensionExp(acNode.getActionCondition());

                if( isProjectionSatisfyDO(acNode) )
                {
                    answer_choice = pExp;
                    break;
                }

            }
            else if( isUserAction )
            {
                continue;
            }
            else
            {
                //This is perception,
                //Check if it is in the hashMap
                Exp pExp = acNode.getActionCondition();
                if( isProjectionSatisfyDO(acNode) )
                {
                    answer_choice = pExp;
                    break;
                }
            }
        }

        return answer_choice;
    }

    public static Exp findExpByBasePredicate(List<Exp> tutorExpList, Exp keyExp)
    {
        String basePredicate_key = InterventionStringUtil.getTheBasePredicateFromExp(keyExp);
        String basePredicate_item = null;

        Exp foundExp = null;

        if( basePredicate_key == null )
        {
            return null;
        }

        for( Exp e : tutorExpList )
        {
            basePredicate_item = InterventionStringUtil.getTheBasePredicateFromExp(e);
            if( basePredicate_item != null )
            {
                if( basePredicate_item.trim().toLowerCase().equals(basePredicate_key.trim().toLowerCase()) )
                {
                    foundExp = e;
                    break;
                }
            }

        }

        return foundExp;
    }

    //TODO: YOU NEED TO COMBINE THE METHOD
    //generate_AC_Choices_forNonCompoundExp_noAnswer2
    //generate_AC_Choices_forCompoundExp_noAnswer2

    public List<Exp> generate_AC_Choices_forNonCompoundExp_noAnswer2(List<TutorGraphNode> tutorAcNodeList, Exp answerExp)
    {
        if( tutorAcNodeList == null )
        {
            tutorAcNodeList = this.getActionConditionList();
        }

        List<Exp> choiceList = new ArrayList<>();

        //add acNode to list
        int totalChoices = InterventionConstants.MAXIMUM_CHOICES - 1;

        //Get the First Predicate of the AnswerExp
        String basePredicate_answerExp = InterventionStringUtil.getTheBasePredicateFromExp(answerExp);

        for( TutorGraphNode acNode : tutorAcNodeList )
        {
            //Get ActionCondition that its pj does not satisfy DO and it is not answer exp.
            if( acNode.getExpByNodeType().equals(answerExp) )
            {
                //Skip answer exp
                continue;
            }
            //else if( TutorGraphUtil.isAllPjSatisfyDO( acNode.getChildNodes() ) )
            else if( isProjectionSatisfyDO(acNode) )
            {
                //Skip act that pj satisfy DO
                continue;
            }
            else
            {
                //add acNode to list
//                if( !choiceList.contains( acNode.getExpByNodeType() ) && choiceList.size() < totalChoices );
//                choiceList.add( acNode.getExpByNodeType() );

                Exp tutorExp = acNode.getExpByNodeType();

                if( InferenceEngineUtil.isCompoundPredicate(tutorExp) )
                {
                    continue;
                }
                else
                {
                    if( tutorExp != null )
                    {
                        String baseTutorPredicate = InterventionStringUtil.getTheBasePredicateFromExp(tutorExp);
                        //If predicate matched!!!
                        if( basePredicate_answerExp.trim().toLowerCase().equals(baseTutorPredicate.trim().toLowerCase()) )
                        {
                            if( !tutorExp.equals(answerExp) )
                            {
                                if( !choiceList.contains(tutorExp) && choiceList.size() < totalChoices )
                                {
                                    choiceList.add(tutorExp);
                                }
                            }
                        }
                        else
                        {
                            continue;
                        }
                    }
                    else
                    {
                        continue;
                    }
                }
            }
        }

        return choiceList;
    }
    /*
    public List<Exp> generateChoicesByActionCondition_noAnswer2( TutorGraphNode tgAcGraphNode_answer )
    {
        if( tgAcGraphNode_answer.getActionCondition() == null )
        {
            return null;
        }

        List<Exp> acList = null;
        for( TutorGraphNode acNode : this.getActionConditionList() )
        {
            //Get ActionCondition that its pj does not satisfy DO and it is not answer exp.
            if( acNode.getExpByNodeType().equals( tgAcGraphNode_answer.getExpByNodeType() ) )
            {
                //similar exp as answer exp, skip
                continue;
            }
            //else if( TutorGraphUtil.isAllPjSatisfyDO( acNode.getChildNodes() ) )
            else if( isProjectionSatisfyDO( acNode ) )
            {
                continue;
            }
            else
            {
                //add acNode to list
                int totalChoices = InterventionConstants.MAXIMUM_CHOICES -1 ;
                if( !choiceList.contains( acNode.getExpByNodeType() ) && choiceList.size() < totalChoices );
                choiceList.add( acNode.getExpByNodeType() );
            }
        }

        return choiceList;
    }
    * */

    //Idea: get the exp that has the predicate like the expin TutorAcNode.
    //This is for the compound sentence only.
    public List<Exp> generate_AC_Choices_forCompoundExp_noAnswer2(List<TutorGraphNode> tutorAcNodeList, TutorGraphNode tutorAcNode, Exp answerExp)
    {
        List<Exp> choiceList = new ArrayList<>();

        //add acNode to list
        int totalChoices = InterventionConstants.MAXIMUM_CHOICES - 1;

        //Get the First Predicate of the AnswerExp
        String basePredicate_answerExp = InterventionStringUtil.getTheBasePredicateFromExp(answerExp);

        //for each node from all tutorAcNodeList
        for( TutorGraphNode tgNode : tutorAcNodeList )
        {
            //get exp of tgNode
            Exp tgNodeExp = tgNode.getExpByNodeType();

            //Get only compound sentences
            if( InferenceEngineUtil.isCompoundPredicate(tgNodeExp) )
            {
                List<Exp> tutorExpList = InterventionStringUtil.flattenExpList(tgNodeExp, null);

                //find the exp that has the same based Predicate
                Exp tutorExp = findExpByBasePredicate(tutorExpList, answerExp);

                if( tutorExp != null )
                {
                    if( !tutorExp.equals(answerExp) )
                    {
                        if( !choiceList.contains(tutorExp) && choiceList.size() < totalChoices )
                        {
                            choiceList.add(tutorExp);
                        }
                    }
                }
            }
            else
            {
                continue;
                //Add for test
                /*
                if( tgNodeExp != null )
                {
                    String baseTutorPredicate = InterventionStringUtil.getTheBasePredicateFromExp( tgNodeExp );

                    if( ! tgNodeExp.equals( answerExp ))
                    {
                        if( !choiceList.contains( tgNodeExp ) && choiceList.size() < totalChoices )
                        {
                            choiceList.add( tgNodeExp );
                        }
                    }
                }
                */
            }
        }

        return choiceList;

    }

    public List<Exp> generateChoicesByActionCondition_noAnswer2(TutorGraphNode tgAcGraphNode_answer)
    {
        if( tgAcGraphNode_answer.getActionCondition() == null )
        {
            return null;
        }

        boolean isComprehension = InferenceEngineUtil.isComprehensionExp(tgAcGraphNode_answer.getExpByNodeType());
        boolean isUserAction = InferenceEngineUtil.isUserPredicateExp(tgAcGraphNode_answer.getExpByNodeType());

        List<Exp> choiceList = new ArrayList<>();

        //String basePredicateTutorNode = InterventionStringUtil.getTheBasePredicateFromExp( tgAcGraphNode_answer.getExpByNodeType() );

        //Get choices which is ActionCondition of the particular predicate

        List<Exp> acList = null;
        for( TutorGraphNode acNode : this.getActionConditionList() )
        {
            //Get ActionCondition that its pj does not satisfy DO and it is not answer exp.
            if( acNode.getExpByNodeType().equals(tgAcGraphNode_answer.getExpByNodeType()) )
            {
                //similar exp as answer exp, skip
                continue;
            }
            //else if( TutorGraphUtil.isAllPjSatisfyDO( acNode.getChildNodes() ) )
            else if( isProjectionSatisfyDO(acNode) )
            {
                continue;
            }
            else
            {
                //add acNode to list
                int totalChoices = InterventionConstants.MAXIMUM_CHOICES - 1;
                if( !choiceList.contains(acNode.getExpByNodeType()) && choiceList.size() < totalChoices )
                {
                    ;
                }
                choiceList.add(acNode.getExpByNodeType());
            }
        }

        return choiceList;
    }

    public List<Exp> generatePerceptionChoicesFromActionCondition_noAnswer2()
    {
        int totalChoices = InterventionConstants.MAXIMUM_CHOICES - 1;

        List<TutorGraphNode> acNodeList = this.getActionConditionList();

        List<Exp> acList = null;
        List<Exp> choiceList = new ArrayList<>();

        for( TutorGraphNode acNode : acNodeList )
        {
            acList = new ArrayList<>();
            acList = InterventionStringUtil.flattenExpList(acNode.getActionCondition(), acList);

            for( Exp acExp : acList )
            {
                boolean isComprehension = InferenceEngineUtil.isComprehensionExp(acExp);
                boolean isUserAction = InferenceEngineUtil.isUserPredicateExp(acExp);

                if( isComprehension )
                {
                    //Get Perception
                    Exp pExp = InferenceEngineUtil.getPerceptionFromComprehensionExp(acNode.getActionCondition());

                    if( !isProjectionSatisfyDO(acNode) )
                    {
                        if( !choiceList.contains(pExp) && choiceList.size() < totalChoices )
                        {
                            choiceList.add(pExp);
                        }
                    }
                }
                else if( isUserAction )
                {
                    continue;
                }
                else
                {
                    //This is perception,
                    //Check if it is in the hashMap
                    Exp pExp = acNode.getActionCondition();
                    if( !isProjectionSatisfyDO(acNode) )
                    {
                        if( !choiceList.contains(pExp) && choiceList.size() < totalChoices )
                        {
                            choiceList.add(pExp);
                        }
                    }
                }
            }
        }
        return choiceList;
    }


    public List<Exp> generatePerceptionChoicesFromActionCondition_noAnswer()
    {
        //getStateNode().getPerceptionWithComprehensionString()
        List<Exp> choiceList = new ArrayList<>();
        Exp choice = null;
        int totalChoices = InterventionConstants.MAXIMUM_CHOICES - 1;
        List<TutorGraphNode> acNodeList = this.getActionConditionList();

        //1. Get nonAnswerNode


        for( TutorGraphNode acNode : acNodeList )
        {
            if( acNode.getActionCondition().getConnective().equals(Connective.AND) )
            {
                //TODO: we do not have mechanism to check the nested group of comprehension nodes.
                //In fact, it should extract the comprehension node from actionCondition Node.
                //The logic should be
                //Flat the list of comprehension exp
                //for each exp, return only exp that is comprehension node.
                //for each extracted node, return the pnodes.
                continue;
            }

            //for each acNode, extract perception
            boolean isComprehension = InferenceEngineUtil.isComprehensionExp(acNode.getActionCondition());
            boolean isUserAction = InferenceEngineUtil.isUserPredicateExp(acNode.getActionCondition());

            if( isComprehension )
            {
                //Get Perception
                Exp pExp = InferenceEngineUtil.getPerceptionFromComprehensionExp(acNode.getActionCondition());

                if( !isProjectionSatisfyDO(acNode) )
                {
                    if( !choiceList.contains(pExp) )
                    {
                        choiceList.add(pExp);
                    }
                }

            }
            else if( isUserAction )
            {
                continue;
            }
            else
            {
                //This is perception,
                //Check if it is in the hashMap
                Exp pExp = acNode.getActionCondition();
                if( !isProjectionSatisfyDO(acNode) )
                {
                    if( !choiceList.contains(pExp) )
                    {
                        choiceList.add(pExp);
                    }
                }
            }
        }


        return choiceList;
    }

    private static boolean isProjectionSatisfyDO(TutorGraphNode tgNode)
    {
        List<TutorGraphNode> pList = null;

        boolean isPjNodeSatisfyDO = false;

        if( tgNode.getActionCondition() != null )  //this ac node
        {
            //get children
            List<TutorGraphNode> pjList = tgNode.getChildNodes();

            for( TutorGraphNode pjNode : pjList )
            {
                if( !pjNode.isDesiredOutCome() )
                {
                    isPjNodeSatisfyDO = false;      //if there is on pj does not satisfy do, pj does not satisfy
                    break;
                }
                else
                {
                    isPjNodeSatisfyDO = true;       //every pj must satisfy DO
                }
            }
        }

        return isPjNodeSatisfyDO;
    }


    public static TutorGraphNode getCorrectAcNodeByStudentIncorrectAcNode(StudentGraphNode studentAcGraphNode)
    {
        if( studentAcGraphNode == null )
        {
            return null;
        }

        TutorGraphNode tutorAcGraphNode = null;

        //1. Get student the first predicate


        return tutorAcGraphNode;
    }

}
