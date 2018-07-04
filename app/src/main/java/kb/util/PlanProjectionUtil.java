package kb.util;

import com.surgical.decision3.common.controller.ApplicationController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Op;
import fr.uga.pddl4j.parser.Symbol;
import kb.common.controller.ParserController;
import kb.common.datastructure.oneofprojection.OneOfNode;
import kb.common.datastructure.useraction.UserAction;
import kb.common.datastructure.useraction.UserActionParameter;

import static com.surgical.decision3.functions.TransformStudentAction.studentGraphObject;
import static com.surgical.decision3.functions.TransformStudentAction.studentProjectionEffect;
import static kb.common.controller.PedagogicalController.getEffectExpList;
import static kb.util.Utils.getDoubleValueFromExp;
import static kb.util.Utils.getExpName;
import static kb.util.Utils.projectionExpList;

/**
 * Created by Nattapon on 9/6/2560.
 */

public class PlanProjectionUtil {

//    public static void combineUserActionToFactList( UserAction uAction )
//    {
//        //1. get current facts combind to actionAndFactList
//        //List<Exp> currentFacts = FactUtil.getCurrentFacts();
//        List<Exp> currentFacts = WorldState.getCurrentStatesList();
//
//        List<Exp> afList = new ArrayList<>();
//        afList.addAll(currentFacts);
//
//        //Add in the case that there is no math comparison e.g. drill_size with pc_with
//        if( uAction.getDrillAction() != null )
//        {
//            if( uAction.getDrillAction().getDrillSizeExp() != null )
//            {
//                //Create exp for comparision of size.
//                Exp compareSizeExp = getCompareDrillSizeExp( uAction );
//
//                if( compareSizeExp!= null )
//                {
//                    afList.add( compareSizeExp );
//                }
//            }
//
//            //afList.add( uAction.getDrillAction().getDrill_shape_exp() );		//Shape
//            if( uAction.getDrillAction().getDrill_shape_exp() != null )
//            {
//                afList.add( uAction.getDrillAction().getDrill_shape_exp() );
//            }
//
//            if( uAction.getDrillAction().getUser_action_predicate_list() != null
//                    && uAction.getDrillAction().getUser_action_predicate_list().size() > 0 )
//            {
//                afList.addAll( uAction.getDrillAction().getUser_action_predicate_list() );
//            }
//        }
//
//
//        Fact f = new Fact( FactUtil.actionAndFactList.size() );		//if size = 0, there is no fact. this is the first one.
//        f.setPredicateInstantList( currentFacts);
//        f.setActionWithPredicatesList(afList);						// ActionWithPredicatesList are overlapped with predicateInstantList
//
//        FactUtil.actionAndFactList.add( f );
//    }

    public static void combineProjectionEffectToCurrentFactList(Exp action_effect)
    {
        List<Exp> effectExpList = new ArrayList<>();
        effectExpList = getEffectExpList(action_effect, effectExpList);
        studentProjectionEffect.clear();
        studentProjectionEffect.addAll(effectExpList);

        //In order to apply the new state there are 3 steps
        //1. Copy P's from previous State
        //2. Apply action effects
        //3. Run domain rules to get Comprehension
        List<Exp> factAndEffect = new ArrayList<>();
        factAndEffect.addAll(WorldStateUtil.getCurrentFactPredicate());//1.
        factAndEffect.addAll(effectExpList);//2.
        processIfPredicatesAreConflict(factAndEffect, effectExpList);//check if there is any predicate that negated
        InferenceEngineUtil.processInference(WorldStateUtil.getCurrentFactAndEffect());//3. run domain rules
    }

    private static void processIfPredicatesAreConflict(List<Exp> factAndEffect ,List<Exp> effectExpList)
    {
        //Firstly, compare fact and effect with projection effects
        //if found any negation then remove the old one and add the new one
        List<Exp> factAndEffectList = addProjectionEffectsThatNegated(WorldStateUtil.getCurrentFactPredicate(), factAndEffect);
        //add the rest of projection effects that are not negated
        factAndEffectList = addProjectionEffectsThatNotNegated(factAndEffectList, effectExpList);
        //check predicate that has variable /** added 1/4/2018 by Toey **/
        factAndEffectList = checkPredicateVariable(factAndEffectList, effectExpList);

        WorldStateUtil.setCurrentFactAndEffect(factAndEffectList);
    }


    private static List<Exp> checkPredicateVariable(List<Exp> factAndEffectList, List<Exp> effectExpList)
    {
        List<Exp> addList = new ArrayList<>();
        addList.addAll(factAndEffectList);

        for (Exp inEffectExpList : effectExpList) {
            String effectName = getExpName(inEffectExpList);
            for(Exp eachFactAndEffectList : factAndEffectList) {
                String faceName = getExpName(eachFactAndEffectList);

                if(effectName.equals(faceName) && !inEffectExpList.equals(eachFactAndEffectList)
                        /*&& !inEffectExpList.getConnective().equals(eachFactAndEffectList.getConnective())*/)
                {
                    addList.remove(eachFactAndEffectList);
                    //addList.add(inEffectExpList);
                    break;
                }

            }
        }
        return addList;
    }

    /***
     * this method is used to add the projection effects that are not negated in facts
     * @param factAndEffectList
     * @param effectExpList
     * @return
     */
    private static List<Exp> addProjectionEffectsThatNotNegated(List<Exp> factAndEffectList, List<Exp> effectExpList) {
        List<Exp> addList = new ArrayList<>();
        addList.addAll(factAndEffectList);

        for (Exp inEffectExpList : effectExpList) {
            for(Exp eachFactAndEffectList : factAndEffectList)
            {
                if (!inEffectExpList.equals(eachFactAndEffectList) && !addList.contains(inEffectExpList)) {
                    addList.add(inEffectExpList);
//                    System.err.println("add " + inEffectExpList);
                    break;
                }
            }
        }

        return addList;
    }

    /***
     * this method is used for removing negated facts if the same
     * predicate was found
     */
    private static List<Exp> addProjectionEffectsThatNegated(List<Exp> currentPredicate, List<Exp> factAndEffect)
    {
        List<Exp> return_list = new ArrayList<>();

        for(Exp valueCurrentPredicate : currentPredicate) {
            boolean negation = false;

            Exp expFromBased = getChildrenNodeFromExp(valueCurrentPredicate);
            for(Exp valueFactAndEffect : factAndEffect) {
                Exp expFromCompare = getChildrenNodeFromExp(valueFactAndEffect);
                if(expFromCompare.equals(expFromBased) && !valueFactAndEffect.getConnective().equals(valueCurrentPredicate.getConnective())
                        && !return_list.contains(valueFactAndEffect))
                {
                    return_list.add(valueFactAndEffect);
                    negation = true;
                }
            }
            if(!negation){return_list.add(valueCurrentPredicate);}
        }
        return return_list;
    }

    public static Exp getChildrenNodeFromExp(Exp node)
    {
        Exp exp;
        if(node.getConnective().equals(Connective.ATOM)){exp = node;}
        else{exp = node.getChildren().get(0);}
        return exp;

    }

    public static Exp processActionToGetEffect(UserAction uAction)
    {
        //TODO: create function to get around action that matched with parameter.
        Op groundAction = OpUtil.getGroundAction(uAction.getName());
//        Op groundAction = OpUtil.groundActionList.get(0);

        //2. Get effect of action.
        // if the connective is not when,
        // add effects to effect list
        // add effects to graph.

        // if the connective is when
        // get condition (each item whether is it available in factList or actionList
        // Add node to graph

        if(groundAction != null)
        {
//            System.err.println(uAction.getName() + " " + groundAction.getName());
            if (uAction.getName().equals(groundAction.getName())) {

                /**
                 * Transform user action.
                 * */
                //Transform user action and add to the data structure.
                //Shape = premolar
                combineUserActionToFactList(uAction);

//                StudentGraphUtil.updatePerceptionAndComprehension();

                //add action node to student graph
                StudentGraphUtil.addActionNode(ApplicationController.currentAction);

                StudentGraphUtil.updateStudentStateNode(studentGraphObject);


                //			System.out.println( "Action is valid !!!!" );
                //get effect
                projectionExpList.clear();
                Exp effectSource = groundAction.getEffects();
                Exp effectOutput = PlanProjectionUtil.getEffectOfAction(effectSource);

                //create Graph for action output.
                System.out.println();
                System.out.println("===== EFFECT (THIS IS EFFECT FROM PLAN PROJECTION) =====");
                System.out.println(effectOutput);
                System.out.println();

                return effectOutput;
            } else {
                System.out.println("Action is not valid !!!!");
            }
        }
        else
        {
            System.err.println("Error: not found this action in groundAction!");
        }
        return null;
    }

    public static void combineUserActionToFactList( UserAction uAction )
    {
        List<Exp> currentFacts = WorldStateUtil.getCurrentFactPredicate();

        List<Exp> afList = new ArrayList<>();
        afList.addAll(currentFacts);

        //Add in the case that there is no math comparison e.g. drill_size with pc_with
        if( !uAction.userActionWithValue.isEmpty() )
        {
            for(UserActionParameter userAP : uAction.userActionWithValue) {
                if (userAP.getValueForCompare() != null) {
                    //Create exp for comparision of size.
                    Exp compareSizeExp = getCompareDrillSizeExp(userAP);

                    if (compareSizeExp != null) {
                        afList.add(compareSizeExp);
                    }
                }
                else if (userAP.getValueExp() != null) {
                    afList.add(userAP.getValueExp());
                }
            }
        }
        if( uAction.getUser_action_predicate_list() != null
                    && uAction.getUser_action_predicate_list().size() > 0 )
        {
            afList.addAll( uAction.getUser_action_predicate_list() );
        }

        WorldStateUtil.updateState(afList);
//        WorldStateUtil.generateNewState(currentFacts, afList);
    }

    private static List<Exp> removeEffectAlreadyExist(List<Exp> effectExpList , Exp newEffect)
    {
        List<Exp> clone_effectExpList = new ArrayList<>();
        clone_effectExpList.addAll(effectExpList);

        Exp projectEffect;

        Exp compareEffect;

//        System.err.println("effectExp "+ effectExp + " Connective "+ effectExp.getConnective());
        for(Exp newEffectList : newEffect.getChildren())//list of all new effects that going to be projected
        {
            if (newEffectList.getConnective().equals(Connective.ATOM)) {
                compareEffect = newEffectList;
            } else {
                compareEffect = newEffectList.getChildren().get(0);
            }

            for(Exp each_effectExpList : effectExpList)
            {
                if(each_effectExpList.getConnective().equals(Connective.ATOM)) {
                    projectEffect = each_effectExpList;
                }
                else if (each_effectExpList.getAtom() != null)
                {
                    projectEffect = each_effectExpList;
                }
                else {
                    projectEffect = each_effectExpList.getChildren().get(0);
                }

                //if the new projection effect already exist in the projection effects list
                //then remove the old effect and add the new one
                if (projectEffect.equals(compareEffect) && each_effectExpList.getConnective().equals(newEffectList.getConnective())) {
                    System.err.println("found!! " + "projectEffect "+projectEffect +" "+each_effectExpList.getConnective()
                            + " compareEffect "+ compareEffect + newEffectList.getConnective());
                    clone_effectExpList.remove(each_effectExpList);
                }
            }
        }

        return clone_effectExpList;
    }

    public static Exp getEffectOfAction( Exp gAction)
    {
        Exp newEffect = new Exp(gAction.getConnective());
        if( !gAction.getConnective().equals(Connective.AND) )
        {
            if(gAction.getConnective().equals(Connective.ONEOF)){
                Exp randomedExp = processOneOfEffect(gAction);
                newEffect = randomedExp;
            } else {
                newEffect.setAtom( gAction.getAtom() );
            }
        }
        else
        {
            HashMap<Exp, Double> functionWithValueHashMap = new HashMap<>();
            //No: there is no nested when in this version.
            //For each predicate in when, it must be available in FactList.
            if(gAction.getChildren() != null && gAction.getChildren().size() > 0 )
            {
                for( Exp ex : gAction.getChildren() )
                {
                    if( ex.getConnective().getImage().equals( "when" ) )
                    {
                        //First children is condition
                        Exp conditionExp = ex.getChildren().get(0);

                        System.out.println("-----------------------------------------------------");
                        System.out.println( "--> conditionExp : " + conditionExp.toString() );

                        projectionExpList = getEffectExpList(newEffect, projectionExpList);

                        boolean isWhenConditionMet = isWhenConditionMetFact( conditionExp, functionWithValueHashMap );

                        System.out.println( "--> isWhenConditionMet : " + isWhenConditionMet );

                        //CANNOT USE RECURSIVE FUNCTION WITH THIS STRUCTURE>
                        //boolean isWhenConditionMet = isWhenConditionMetFact_recursive( conditionExp, currentFactList, false );

                        if( isWhenConditionMet )
                        {
                            //add action condition node to student graph
                            StudentGraphUtil.addActionConditionNode(conditionExp);

                            Exp effectExp = ex.getChildren().get(1);

                            if(effectExp.getConnective().equals(Connective.ASSIGN))
                            {
                                effectExp = assignConnectiveCalculation(effectExp, functionWithValueHashMap);

                                functionWithValueHashMap = assignValueToFunctionHashMap(effectExp, functionWithValueHashMap);

                            }
                            //in case that there is assign in AND condition connective
                            else if(effectExp.getConnective().equals(Connective.AND))
                            {
                                //firstly, clone the effect exp
                                Exp newEffectExp = new Exp(Connective.AND);

                                for(Exp each_effect_exp : effectExp.getChildren())
                                {
                                    if(each_effect_exp.getConnective().equals(Connective.ASSIGN))
                                    {
//                                        newEffectExp.addChild(assignConnectiveCalculation(each_effect_exp, functionWithValueHashMap));
                                        Exp assignExp = assignConnectiveCalculation(each_effect_exp, functionWithValueHashMap);
                                        newEffectExp = removeExistAssignValue(newEffectExp, assignExp);
                                        System.out.println(newEffectExp);

                                        functionWithValueHashMap = assignValueToFunctionHashMap(newEffectExp, functionWithValueHashMap);
                                        effectExp = newEffectExp;
                                    }
                                    else{ newEffectExp.addChild(each_effect_exp); }
                                }
                            }

                            List<Exp> effectExpList = removeConnectiveAndFromProjectionEffect(effectExp);
                            //check that if the effect is negated then remove the old effect and add the new one
                            newEffect = removeNegationEffect(effectExpList, newEffect);
                            effectExpList = removeEffectAlreadyExist(effectExpList, newEffect);

                            //add this function to handle adding multiple predicate value (ex. (COLOR RED) (COLOR BLUE))
                            for(Exp each_effectExp : effectExpList)
                            {
                                if(each_effectExp.getAtom() != null)
                                {
                                    if(each_effectExp.getAtom().size() == 2)
                                    {
                                        String effectString = InferenceEngineUtil.getAtomStringFromPredicate(each_effectExp);

                                        for(Exp new_effect : newEffect.getChildren())
                                        {
                                            if(new_effect.getAtom() != null) {
                                                if (new_effect.getAtom().size() == 2) {

                                                    String newEffectString = InferenceEngineUtil.getAtomStringFromPredicate(new_effect);

                                                    if(newEffectString.equals(effectString))
                                                    {
                                                        new_effect.getAtom().set(1, each_effectExp.getAtom().get(1));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //--------------------------------//

                            for(Exp each_effectExpList : effectExpList)
                            {
                                if(!newEffect.getChildren().contains(each_effectExpList)) newEffect.addChild(each_effectExpList);//if the effect is not in the effect list then add
                            }

                            StudentGraphUtil.addProjectionNode(conditionExp, effectExp);

                            //Mes - add ONEOF projection node to student graph
                            if(oneOfNodeList.size() > 0) {
                                StudentGraphUtil.addOneOfProjectionNode(conditionExp, oneOfNodeList);
                                oneOfNodeList.clear();
                            }
                        }
                        else
                        {
                            //do nothing, read next.
                        }
                    }
                    else
                    {
                        if(ex.getConnective().equals(Connective.ONEOF)){
                            Exp randomedExp = processOneOfEffect(ex);
                            newEffect.addChild(randomedExp);
                        }
                        else if(ex.getConnective().equals(Connective.ASSIGN))
                        {
                            Exp assignExp = assignConnectiveCalculation(ex, functionWithValueHashMap);
                            functionWithValueHashMap = assignValueToFunctionHashMap(assignExp, functionWithValueHashMap);

                            newEffect.addChild(assignExp);
                        }
                        else {
                            newEffect.addChild( ex );
                        }
                    }
                }
            }
        }
        return newEffect;
    }

    private static Exp removeExistAssignValue(Exp newEffectExp, Exp assignExp)
    {
        Exp returnEffectExp = new Exp(Connective.AND);
        boolean added = false;

        if(newEffectExp.getChildren().size() == 0)
        {
            returnEffectExp.addChild(assignExp);
        }
        else
        {
            for(Exp each_new_effect_exp : newEffectExp.getChildren())
            {
                if(each_new_effect_exp.getChildren().size() != 0)
                {
                    //if assignExp does exist in newEffectExpList then add assignExp
                    if(each_new_effect_exp.getChildren().get(0).getChildren().get(0).getAtom().equals(assignExp.getChildren().get(0).getChildren().get(0).getAtom()))
                    {
                        returnEffectExp.addChild(assignExp);
                        added = true;
                    }
                    else
                    {
                        returnEffectExp.addChild(each_new_effect_exp);
                    }
                }
                else
                {
                    returnEffectExp.addChild(each_new_effect_exp);
                }
            }
        }

        if(!added) returnEffectExp.addChild(assignExp);

        return returnEffectExp;
    }

    private static HashMap<Exp, Double> assignValueToFunctionHashMap(Exp effectExp, HashMap<Exp, Double> functionWithValueHashMap)
    {
        //in case that there is assign in AND condition connective
        if(effectExp.getConnective().equals(Connective.AND))
        {
            for(Exp each_effect_exp : effectExp.getChildren())
            {
                if(each_effect_exp.getConnective().equals(Connective.EQUAL)) {
                    //if this assign effect does exist in hash map then remove the old one
                    if (functionWithValueHashMap.containsKey(each_effect_exp.getChildren().get(0).getChildren().get(0))) {
                        functionWithValueHashMap.remove(each_effect_exp.getChildren().get(0).getChildren().get(0));
                    }
                    functionWithValueHashMap.put(each_effect_exp.getChildren().get(0).getChildren().get(0)
                            , each_effect_exp.getChildren().get(0).getChildren().get(0).getValue());
                }
            }
        }
        else
        {
            //if this effect does exist in hash map then remove the old one
            if(functionWithValueHashMap.containsKey(effectExp.getChildren().get(0).getChildren().get(0)))
            {
                functionWithValueHashMap.remove(effectExp.getChildren().get(0).getChildren().get(0));
            }
            functionWithValueHashMap.put(effectExp.getChildren().get(0).getChildren().get(0)
                    ,effectExp.getChildren().get(0).getChildren().get(0).getValue());
        }

        return functionWithValueHashMap;
    }

    private static Exp assignConnectiveCalculation(Exp effectExp, HashMap<Exp, Double> functionWithValueHashMap)
    {
        Double firstValueAttribute = 0.0;
        Double secondValueAttribute = 0.0;

        //In case that A - number or number - A
        if(effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getConnective().equals(Connective.NUMBER)
                || effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(1).getChildren().get(0).getConnective().equals(Connective.NUMBER))
        {
            //Is first exp a number
            if(effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getConnective().equals(Connective.NUMBER))
            {
                firstValueAttribute = effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).getValue();
                secondValueAttribute = getDoubleValueFromExp(effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(1), functionWithValueHashMap);
            }
            //else then second exp is a number
            else
            {
                firstValueAttribute = getDoubleValueFromExp(effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(0), functionWithValueHashMap);
                secondValueAttribute = effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(1).getChildren().get(0).getValue();
            }
        }
        //In case that A - B
        else {
            firstValueAttribute = getDoubleValueFromExp(effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(0), functionWithValueHashMap);
            secondValueAttribute = getDoubleValueFromExp(effectExp.getChildren().get(1).getChildren().get(0).getChildren().get(1), functionWithValueHashMap);
        }

        Double assignValue = 0.0;

        if(effectExp.getChildren().get(1).getChildren().get(0).getConnective().equals(Connective.MINUS))
        {
            assignValue = firstValueAttribute-secondValueAttribute;
        }
        else if(effectExp.getChildren().get(1).getChildren().get(0).getConnective().equals(Connective.PLUS))
        {
            assignValue = firstValueAttribute+secondValueAttribute;
        }

        effectExp.getChildren().get(0).setValue(assignValue);
        Exp assignExp = new Exp(Connective.EQUAL);
        Exp functionExp = new Exp(Connective.F_EXP);
        Exp valueExp = new Exp(Connective.F_EXP);

        Exp numberExp = new Exp(Connective.NUMBER);
        numberExp.setValue(assignValue);

        functionExp.addChild(effectExp.getChildren().get(0));
        valueExp.addChild(numberExp);

        assignExp.addChild(functionExp);
        assignExp.addChild(valueExp);
//        effectExp = assignExp;

        return assignExp;
    }

    private static Boolean isEffectAlreadyExist(List<Exp> effectExpList , Exp newEffect)
    {
        boolean exist = false;

        Exp projectEffect;

        Exp compareEffect;

//        System.err.println("effectExp "+ effectExp + " Connective "+ effectExp.getConnective());
        for(Exp newEffectList : newEffect.getChildren())//list of all new effects that going to be projected
        {
            if (newEffectList.getConnective().equals(Connective.ATOM)) {
                compareEffect = newEffectList;
            } else {
                compareEffect = newEffectList.getChildren().get(0);
            }

            for(Exp each_effectExpList : effectExpList)
            {
                if(each_effectExpList.getConnective().equals(Connective.ATOM)) {
                    projectEffect = each_effectExpList;
                } else {
                    projectEffect = each_effectExpList.getChildren().get(0);
                }

                //if the new projection effect already exist in the projection effects list
                //then remove the old effect and add the new one
                if (projectEffect.equals(compareEffect) && each_effectExpList.getConnective().equals(newEffectList.getConnective())) {
                    //                System.err.println("found!! " + "projectEffect "+projectEffect +" "+effectExp.getConnective()
                    //                        + " compareEffect "+ compareEffect + newEffectList.getConnective());
                    exist = true;
                }
            }
        }

        return exist;
    }

    private static List<Exp> removeConnectiveAndFromProjectionEffect(Exp effectExp)
    {
        List<Exp> newEffectExp = new ArrayList<>();
        List<Exp> tempExp = new ArrayList<>();

        if(effectExp.getConnective().equals(Connective.AND))
        {
            for(Exp effectExpChild : effectExp.getChildren())
            {
                tempExp.add(effectExpChild);
            }
        }
        else
        {
            tempExp.add(effectExp);
        }

        //Mes - refactor
        for(Exp each_effectExp : tempExp)
        {
            if(each_effectExp.getConnective().equals(Connective.ONEOF)){
                Exp randomedExp = processOneOfEffect(each_effectExp);
                newEffectExp.add(randomedExp);
            } else {
                newEffectExp.add(each_effectExp);
            }
        }

        return newEffectExp;
    }

    // Mes - add to random ONEOF effect
    static ArrayList<OneOfNode> oneOfNodeList = new ArrayList<>();

    private static Exp processOneOfEffect(Exp oneOfExp) {
        Exp resultExp;

        //insert oneof domain
        for(Exp prob_exp : oneOfExp.getChildren()){
            OneOfNode oneOfNode = new OneOfNode();
            Exp prob_exp_effect = prob_exp.getChildren().get(1);
            double probabilityValue = prob_exp.getChildren().get(0).getValue();
            oneOfNode.setExp(prob_exp_effect);
            oneOfNode.setProbabilityValue(probabilityValue);
            oneOfNodeList.add(oneOfNode);
        }

        //get one of the ONEOF effect
        resultExp = randomOneOfEffect(oneOfNodeList);



        return resultExp;
    }


    private static Exp randomOneOfEffect(ArrayList<OneOfNode> oneOfNodeList) {
        Exp resultExp = null;
        Random rand = new Random();
        double index = rand.nextDouble();
        double relativeProb = 0;
        int i = 0;
        while(index > relativeProb){
            relativeProb = relativeProb + oneOfNodeList.get(i++).getProbabilityValue();
        }

        resultExp = oneOfNodeList.get(Math.max(0,i-1)).getExp();    // get max to avoid getting below 0
        oneOfNodeList.get(Math.max(0,i-1)).setOneOfOccur(true);

        System.err.println("------------ ONEOF Random Process Initialized ------------");
        System.out.println("Random Value: " + index);
        System.out.println("Selected Exp: " + resultExp);
        System.err.println("--------------- ONEOF Random Process Ended ---------------");

        return resultExp;
    }

    private static Exp removeNegationEffect(List<Exp> effectExpList , Exp newEffect)
    {
        Exp projectEffect;

        Exp compareEffect;

        Exp clone_newEffect = new Exp(newEffect.getConnective());
        for(Exp newEffectList : newEffect.getChildren())//list of all new effects that going to be projected
        {
            clone_newEffect.addChild(newEffectList);
        }

//        System.err.println("effectExp "+ effectExp + " Connective "+ effectExp.getConnective());
        for(Exp newEffectList : newEffect.getChildren())//list of all new effects that going to be projected
        {
            if (newEffectList.getConnective().equals(Connective.ATOM)) {
                compareEffect = newEffectList;
            } else {
                compareEffect = newEffectList.getChildren().get(0);
            }

            for(Exp each_effectExpList : effectExpList)
            {
                if(each_effectExpList.getConnective().equals(Connective.ATOM)){projectEffect = each_effectExpList;}
                else if (each_effectExpList.getAtom() != null)
                {
                    projectEffect = each_effectExpList;
                }
                else{projectEffect = each_effectExpList.getChildren().get(0);}

                //if the new projection effect already exist in the projection effects list
                //then remove the old effect and add the new one
                if (projectEffect.equals(compareEffect) && !each_effectExpList.getConnective().equals(newEffectList.getConnective())) {
                    System.err.println("conflict!! " + "projectEffect " + projectEffect + " " + each_effectExpList.getConnective()
                            + " compareEffect " + compareEffect + newEffectList.getConnective());
                    System.err.println("remove " + newEffectList + " out of list!");
                    clone_newEffect.remove(newEffectList);
                }
            }
        }

        return clone_newEffect;
    }

    private static boolean isWhenConditionMetFact(Exp conditionExp, HashMap<Exp, Double> functionWithValueHashMap)
    {
        //System.out.println( "--- isWhenConditionMetFact ---" );
        boolean isWhenConditionMet = false;

        //List<Exp> currentFactList = getCurrentFacts();
        List<Exp> currentActAndPredFactList = WorldStateUtil.getCurrentFactAndActionPredicate();

        boolean isExist = false;

        if( currentActAndPredFactList == null || currentActAndPredFactList .size() == 0 )
        {
            return false;
        }

        //----------------------------------------------
        // Step1: extract to "considerList"
        //----------------------------------------------
        //Fix bug. If conditionExp has connective AND, this is ok.
        //If conditionExp is NOT, it takes the children to compare, which is P, instead of -P
        //----------------------------------------------
        List<Exp> considerList = new ArrayList<>();

        if( conditionExp.getConnective().equals( Connective.LESS ) ||
                conditionExp.getConnective().equals( Connective.LESS_OR_EQUAL) ||
                conditionExp.getConnective().equals( Connective.GREATER) ||
                conditionExp.getConnective().equals( Connective.GREATER_OR_EQUAL ) ||
                conditionExp.getConnective().equals( Connective.EQUAL)
                )
        {
            considerList.add(conditionExp);
        }
        else if( conditionExp.getConnective().equals( Connective.NOT ) )
        {
            //if( the connection of the first child is numeric, it is like above. )
            // for the case (not(<(drill_size)(pc_widt)))
//			Exp firstChild = conditionExp.getChildren().get(0);
//			if( firstChild != null && isNumericConnective( firstChild ) )
//			{
//				considerList.add(conditionExp);
//			}

            considerList.add(conditionExp);
        }
        else if( conditionExp.getChildren().size() == 0 )
        {
            //if there is no children.
            considerList.add(conditionExp);
        }
        else
        {
            considerList = conditionExp.getChildren();
        }

        //for( Exp cExpChild : conditionExp.getChildren() )
        for( Exp cExpChild : considerList )
        {
            boolean isAvailable = false;

            if( cExpChild.getConnective().equals( Connective.LESS ) ||
                    cExpChild.getConnective().equals( Connective.LESS_OR_EQUAL) ||
                    cExpChild.getConnective().equals( Connective.GREATER) ||
                    cExpChild.getConnective().equals( Connective.GREATER_OR_EQUAL )
                    || cExpChild.getConnective().equals( Connective.EQUAL)
                    )
            {
                //Special compare.
                isAvailable = Utils.isAvailable_NumericComparison(currentActAndPredFactList, cExpChild, functionWithValueHashMap);
            }
            else
            {
                isAvailable = Utils.isAvailable(  currentActAndPredFactList , cExpChild );
                if(!isAvailable)
                {
                    isAvailable = Utils.isAvailableInProjectionEffect(cExpChild);

                    if(!isAvailable)//if still not available then check in case (ex. have (COLOR RED) -> WHEN (NOT(COLOR BLUE)) is the same)
                    {
                        isAvailable = Utils.isAvailableInWhenCase(  currentActAndPredFactList , cExpChild );
                    }
                }
            }

            if( isAvailable )
            {
                //Note: check if connective must be check or not.
                isExist = true;
            }
            else
            {
                isExist = false;
                break;
            }
        }


        if( isExist )
        {
            isWhenConditionMet = true;
        }

        //System.out.println( "--> Meet the condition: " + isWhenConditionMet );

        return isWhenConditionMet;
    }

    public static Exp getCompareDrillSizeExp( UserActionParameter userAP)
    {
        Exp user_selected_exp_value = userAP.getValueForCompare();

        //1. create compareExp from UserDrillAction.
        //get compare_predicate from map

        //pair_user_action_map
        Exp sizeExp = userAP.getValueForCompare();

        List<String> final_key_list = new ArrayList<>();
        final_key_list = StringUtil.getKeyListFromExpression(sizeExp, final_key_list, sizeExp.getConnective() );

        String key = null;
        if( final_key_list.size() > 0 )
        {
            key = final_key_list.get(0);
        }

//        String pair_key = key;
        String pair_key = ApplicationController.pair_user_action_map.get( key.toUpperCase() ); //toey
        if(pair_key != null) {
//            System.err.println("pair_key " + pair_key); //toey
            //        System.err.println("key "+ key); //toey

            Exp temp_compareExpFromFact = getComparePairExpFromFact(pair_key, Connective.FN_HEAD);

            //		Exp temp_compareExpFromFact = getComparePairExpFromFact( PddlFunctionConstants.PC_WIDTH_SIZE_FUNCTION,
            //																 Connective.FN_HEAD );

            Exp exp_value_from_fact = userAP.transformValueToExp_withoutVariable(temp_compareExpFromFact.getValue(),
                    pair_key,  //"pc_width_size",
                    ParserController.domain.getFunctions(),
                    InferenceEngineUtil.typeMap);

            //		Exp exp_value_from_fact = uAction.getDrillAction().transformDrillSizeToExp_withoutVariable(  temp_compareExpFromFact.getValue() ,
            //																								   PddlFunctionConstants.PC_WIDTH_SIZE_FUNCTION,  //"pc_width_size",
            //																								   ParserUtil.domain.getFunctions(),
            //																								   InferenceEngineUtil.typeMap );
            //---------------------------------------
            //2. compare value, and generate teh exp for adding to action
            //(< (size ?ds) (pc_width_size ?pcw) )
            //---------------------------------------
            //		Exp compareSizeExp = new Exp(Connective.LESS);
            //		compareSizeExp.addChild( user_selected_exp_value );
            //		compareSizeExp.addChild( exp_value_from_fact );

            Exp compareSizeExp = null;

            //user_selected_exp_value.getValue() == exp_value_from_fact.getValue() )
            //		System.out.println( "=== COMPARE DOUBLE ===" );
            //		System.out.println( "user_value(): " + user_selected_exp_value.getValue()  );
            //		System.out.println( "pc_width_size(): " + exp_value_from_fact.getValue()  );
            //		System.out.println( "equal?" + ( user_selected_exp_value.getValue().doubleValue() == exp_value_from_fact.getValue().doubleValue() ) );
            //		System.out.println( ">?" + ( user_selected_exp_value.getValue().doubleValue() > exp_value_from_fact.getValue().doubleValue() ) );
            //		System.out.println( ">?" + ( user_selected_exp_value.getValue() > exp_value_from_fact.getValue() ) );

            if (user_selected_exp_value.getValue().doubleValue() < exp_value_from_fact.getValue().doubleValue()) {
                System.out.println("==> size is too small");
                compareSizeExp = new Exp(Connective.LESS);
                compareSizeExp.addChild(user_selected_exp_value);
                compareSizeExp.addChild(exp_value_from_fact);
            } else if (user_selected_exp_value.getValue().doubleValue() > exp_value_from_fact.getValue().doubleValue()) {
                System.out.println("==> size is too large");
                compareSizeExp = new Exp(Connective.GREATER);
                compareSizeExp.addChild(user_selected_exp_value);
                compareSizeExp.addChild(exp_value_from_fact);

                //------------------------------------------------------------------------
                //NOTE: at this moment, we do not support negation for numeric comparison
                //------------------------------------------------------------------------
                /*System.out.println( "==> size is too large" );
                Exp neg_compare_exp = new Exp(Connective.NOT);
                neg_compare_exp.addChild(compareSizeExp);
                return neg_compare_exp;*/
            } else if (user_selected_exp_value.getValue().doubleValue() == exp_value_from_fact.getValue().doubleValue()) {
                System.out.println("==> size is equal");
                //compareSizeExp = new Exp(Connective.FN_ATOM);
                compareSizeExp = new Exp(Connective.EQUAL);
                compareSizeExp.addChild(user_selected_exp_value);
                compareSizeExp.addChild(exp_value_from_fact);

                //------------------------------------------------------------------------
                //NOTE: at this moment, we do not support negation for numeric comparison
                //------------------------------------------------------------------------
            }
            return compareSizeExp;
        }
        return null;
    }

    public static Exp getComparePairExpFromFact( String key, Connective connective )
    {
        //For drill size, comparePair is pc_width_size,and connective is "FN_ATOM"
        Exp compareExp = null;
        Double valueToCompare = null;
        List<Exp> currentFacts = WorldStateUtil.getCurrentFactPredicate();
        for( Exp e : currentFacts )
        {
            if( e.getConnective().getImage().equals( "=")
                    && e.getConnective().equals(Connective.FN_ATOM))
            {
                Exp exp1 = e.getChildren().get( 0 );
                Symbol s = exp1.getAtom().get(0);
//                System.err.println("found "+e.getConnective().getImage()+" "+e.getChildren().get(0)+" "+exp1.getAtom().get(0)); //toey
//                System.err.println(s.getImage()+" "+exp1.getConnective());
                //If key == "pc_width_size"
                //connective is "FN_HEAD"
                if( s.getImage().equals( key.toLowerCase() )
                        && exp1.getConnective().equals( connective ) )
                {
//                    System.err.println("e.getChildren().get( 1 ) "+e.getChildren().get( 1 ));//toey
                    Exp exp2 = e.getChildren().get( 1 );
                    if(exp2.getConnective().equals( Connective.NUMBER ) )
                    {
                        valueToCompare = exp2.getValue();

                        compareExp = new Exp(exp1);
                        compareExp.setValue( valueToCompare );
                        return compareExp;
                    }
                }
            }
        }
        return compareExp;
    }

}
