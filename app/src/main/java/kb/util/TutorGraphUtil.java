package kb.util;

import com.surgical.decision3.common.controller.ApplicationController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Symbol;
import kb.common.constants.GraphNodeConstants;
import kb.common.controller.ParserController;
import kb.common.datastructure.graph.TutorActionConditionDetails;
import kb.common.datastructure.graph.TutorGraph;
import kb.common.datastructure.graph.TutorGraphNode;
import kb.common.datastructure.oneofprojection.OneOfGroup;
import kb.common.datastructure.oneofprojection.OneOfNode;
import kb.common.datastructure.solutionpath.PlanAction;

import static com.surgical.decision3.common.controller.ApplicationController.action_predicate_map;
import static com.surgical.decision3.common.controller.ApplicationController.tutorGraphList;
import static kb.common.controller.PedagogicalController.getGoalExpList;
import static kb.util.GraphUtil.getPerceptionAndComprehensionByComparingAC;
import static kb.util.StringUtil.subStringExp;

/**
 * Created by Nattapon on 7/7/2560.
 */

public class TutorGraphUtil {

    public static List<Exp> predicate_list = new ArrayList<>();

    public static void initialValue()
    {
        for (PlanAction eachPlanAction : ParserController.plan.getAllPlanActions()) {

            List<Exp> actionConditionList = OpUtil.getActionConditionFromGround(eachPlanAction.getActionName());
            List<Exp> projectionEffectList = OpUtil.getProjectionEffectFromGround(eachPlanAction.getActionName());

            if (actionConditionList.size() != 0 && projectionEffectList.size() != 0) {

                TutorGraph tutorGraphObject = new TutorGraph();

                predicate_list.addAll(getExpValueFromExpList(actionConditionList));
                predicate_list.addAll(getExpValueFromExpList(projectionEffectList));

                addAllPerceptionAndComprehensionMap(predicate_list, tutorGraphObject);

                addPerceptionAndComprehensionNode(tutorGraphObject, predicate_list);

                addActionNode(eachPlanAction, tutorGraphObject);

                addActionConditionAndProjectionNode(eachPlanAction.getActionName(), tutorGraphObject);

                updateDOInProjectionNode(eachPlanAction, tutorGraphObject);

                removeEffectInPerceptionNode(tutorGraphObject);

                tutorGraphList.add(tutorGraphObject);

                predicate_list.clear();
            }
        }

    }

    private static void addPerceptionAndComprehensionNode(TutorGraph tutorGraphObject, List<Exp> predicateList)
    {
        HashMap<String,String> perceptionAndComprehensionStringMap
                = tutorGraphObject.getPerceptionAndComprehensionNode().getPerceptionWithComprehensionHashMap();

        for (Map.Entry<String, String> each_perception_comprehension : perceptionAndComprehensionStringMap.entrySet()) {

            Exp perceptionExp = getPredicateExpFromHashMap(each_perception_comprehension.getKey(), tutorGraphObject);

            if (perceptionExp != null) {

                String perceptionString = getPerceptionSubStringExp(perceptionExp);

                if(!isThisPredicateUserAction(perceptionString)) {

                    Exp comprehensionExp = null;
                    if(each_perception_comprehension.getValue() != null)
                    {
                        comprehensionExp = getPredicateExpFromHashMap(each_perception_comprehension.getValue(), tutorGraphObject);

                        TutorGraphNode perceptionNode = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PERCEPTION);
                        perceptionNode.setPerceptionExp(perceptionExp);
                        perceptionNode.addParentNode(tutorGraphObject.getPerceptionAndComprehensionNode());

                        tutorGraphObject.getPerceptionAndComprehensionNode().addChildNode(perceptionNode);

                        TutorGraphNode comprehensionNode = null;
                        if (comprehensionExp != null) {
                            comprehensionNode = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_COMPREHENSION);
                            comprehensionNode.setComprehensionExp(comprehensionExp);
                            comprehensionNode.addParentNode(perceptionNode);

                            perceptionNode.addChildNode(comprehensionNode);
                        }

                        tutorGraphObject.getPerceptionList().add(perceptionNode);
                        if (comprehensionNode != null) tutorGraphObject.getComprehensionList().add(comprehensionNode);
                    }
                }
            }
        }

        //in case of AND Connective Axiom
        Exp perception_and_exp = InferenceEngineUtil.getANDPerceptionFromAxiom(predicateList);
        if (perception_and_exp != null) {

            Exp comprehension_and_exp = InferenceEngineUtil.getComprehensionANDFromAxiom(perception_and_exp);

            TutorGraphNode perceptionNode = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PERCEPTION);
            perceptionNode.setPerceptionExp(perception_and_exp);
            perceptionNode.addParentNode(tutorGraphObject.getPerceptionAndComprehensionNode());

            tutorGraphObject.getPerceptionAndComprehensionNode().addChildNode(perceptionNode);

            TutorGraphNode comprehensionNode = null;
            if (comprehension_and_exp != null) {
                comprehensionNode = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_COMPREHENSION);
                comprehensionNode.setComprehensionExp(comprehension_and_exp);
                comprehensionNode.addParentNode(perceptionNode);

                perceptionNode.addChildNode(comprehensionNode);
            }

            tutorGraphObject.getPerceptionList().add(perceptionNode);
            if (comprehensionNode != null) tutorGraphObject.getComprehensionList().add(comprehensionNode);

        }
    }

    public static boolean isThisPredicateUserAction(String perceptionString)
    {
        for (Map.Entry<String, String> action_pred_map : action_predicate_map.entrySet())
        {
            if(perceptionString.equals(action_pred_map.getValue().toLowerCase())) return true;
        }

        return false;
    }

    public static String getPerceptionSubStringExp(Exp expValue)
    {
        String string = "";
        if(expValue.getConnective().equals(Connective.NOT)) {

            //if this predicate has constant value
            if(expValue.getChildren().get(0).getAtom().size() < 2) {
//                System.out.println("toey not " + exp.getChildren().get(0).getAtom().get(0));

                string = expValue.getChildren().get(0).getAtom().get(0).toString();
//                string = string + "_not";
            }
            else {
                string = expValue.toString().substring(6, expValue.toString().length() - 2);
//                string = string + "_not";
            }
        }
        else
        {
            //if this predicate has constant value
            if(expValue.getChildren().size() != 0 && expValue.getChildren().get(0).getAtom() != null) {
                string = expValue.getChildren().get(0).getAtom().get(0).toString();
            }
            else string = expValue.toString().substring(1, expValue.toString().length()-1);
        }

        return string;
    }

    private static Exp getPredicateExpFromHashMap(String predicateString, TutorGraph tutorGraphObject)
    {
        HashMap<String,Exp> PredicateExpMap
                = tutorGraphObject.getPerceptionAndComprehensionNode().getPredicateExpDataHashMap();

        for (Map.Entry<String, Exp> each_predicate_exp : PredicateExpMap.entrySet()) {
            if(each_predicate_exp.getKey().equals(predicateString))
            {
                return each_predicate_exp.getValue();
            }
        }

        return null;
    }

    private static void addAllPerceptionAndComprehensionMap(List<Exp> predicateList, TutorGraph tutorGraphObject)
    {
        HashMap<String, String> perceptionToComprehensionMap = new HashMap<>();
        HashMap<String, Exp> predicateExpData = new HashMap<>();
        TutorGraphNode percep_comp_node = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PERCEPANDCOMP);

        for (Exp each_predicate : predicateList) {

            List<Exp> perception_exp_list = InferenceEngineUtil.getTutorPerceptionListFromAxiom(each_predicate);

            if (perception_exp_list.size() != 0) {

                for(Exp each_perception_exp : perception_exp_list)
                {
                    Exp comprehension_exp = InferenceEngineUtil.getComprehensionFromAxiom(each_perception_exp);
//                System.out.println("toey perception "+ perception_exp +" comprehension "+comprehension_exp);

                    String comprehension_string = subStringExp(comprehension_exp);

                    ArrayList<String> perception_string_list = StringUtil.getSubStringFromExpList(each_perception_exp);

                    for(String each_predicate_string : perception_string_list) {
                        perceptionToComprehensionMap.put(each_predicate_string, comprehension_string);

                        if (each_predicate.getConnective().equals(Connective.ATOM) && each_predicate.getAtom().size() == 2) {
                            Exp node = new Exp(Connective.ATOM);
                            Symbol symbol = each_predicate.getAtom().get(0);
                            List<Symbol> symbol_list = new ArrayList<Symbol>();
                            symbol_list.add(symbol);
                            node.setAtom(symbol_list);

                            predicateExpData.put(each_predicate_string, node);
                        }
                        predicateExpData.put(each_predicate_string, each_perception_exp);

                    }
                    predicateExpData.put(comprehension_string, comprehension_exp);
                }
            }
            else
            {
                percep_comp_node = putAllPredicateExpData(percep_comp_node, each_predicate);
            }
        }

        percep_comp_node.getPerceptionWithComprehensionHashMap().putAll(perceptionToComprehensionMap);
        percep_comp_node.getPredicateExpDataHashMap().putAll(predicateExpData);

        tutorGraphObject.setPerceptionAndComprehensionNode(percep_comp_node);
    }

    private static void addActionNode(PlanAction Action, TutorGraph tutorGraphObject)
    {

        TutorGraphNode action_node = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_ACTION);
        action_node.setActionNode(Action);

        for (TutorGraphNode each_perception : tutorGraphObject.getPerceptionList()) {
            if(!doesThisNodeContainComprehensionNodeInChild(each_perception))
            {
                action_node.addParentNode(each_perception);
                each_perception.addChildNode(action_node);
            }
        }
        for (TutorGraphNode each_comprehension : tutorGraphObject.getComprehensionList()) {
            action_node.addParentNode(each_comprehension);
            each_comprehension.addChildNode(action_node);
        }

        tutorGraphObject.setActionNode(action_node);

        addUserActionDetailsToActionNode(tutorGraphObject);
    }

    private static boolean doesThisNodeContainComprehensionNodeInChild(TutorGraphNode perceptionNode)
    {
        boolean contain = false;

        for(TutorGraphNode each_child_node : perceptionNode.getChildNodes())
        {
            if(each_child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_COMPREHENSION))
            {
                contain = true;
                break;
            }
        }

        return contain;
    }

    private static void addUserActionDetailsToActionNode(TutorGraph tutorGraphObject)
    {
        for (Map.Entry<String, Exp> predicateExpData : tutorGraphObject.getPerceptionAndComprehensionNode()
                .getPredicateExpDataHashMap().entrySet())
        {
            for (Map.Entry<String, String> action_pred_map : action_predicate_map.entrySet()) {

                String[] action = action_pred_map.getKey().split("-");

                if(action_pred_map.getValue().toLowerCase()
                        .equals(predicateExpData.getKey())
                        && action[0].equals(tutorGraphObject.getActionNode().getActionNode().getActionName()))
                {

                    TutorActionConditionDetails acDetail = new TutorActionConditionDetails();
                    acDetail.setExp(predicateExpData.getValue());
                    acDetail.setType("UserActionDetails");
                    acDetail.setRefGraphNode(tutorGraphObject.getActionNode());

                    tutorGraphObject.getActionNode().getAcDetailsList().add(acDetail);

                    break;
                }
            }
        }
    }

    private static void addActionConditionAndProjectionNode(String actionName, TutorGraph tutorGraphObject)
    {

        Exp effectSource = OpUtil.getGroundAction(actionName);

        if(effectSource.getChildren() != null && effectSource.getChildren().size() > 0 ) {
            for (Exp ex : effectSource.getChildren())
            {
                if (ex.getConnective().getImage().equals("when"))
                {

                    Exp conditionExp = ex.getChildren().get(0);
                    Exp effectExp = ex.getChildren().get(1);

                    TutorGraphNode actionNode = tutorGraphObject.getActionNode();

                    //------------------action condition-----------------//
                    TutorGraphNode graph_action_condition_node
                            = addActionConditionNode(conditionExp, actionNode, actionName);


                    //-----------------Add Perception & Comprehension to HashMap -----------------//
                    HashMap<String, String> perceptionWithComprehensionMap = new HashMap<>();

                    perceptionWithComprehensionMap.putAll(
                            getPerceptionWithComprehensionMap(conditionExp,
                                    tutorGraphObject));

                    graph_action_condition_node.setPerceptionWithComprehensionHashMap(perceptionWithComprehensionMap);

                    graph_action_condition_node.setPerceptionWithComprehensionList(
                            getPerceptionWithComprehensionListFromMap(perceptionWithComprehensionMap, tutorGraphObject));

                    //-------------------add Action Condition Details------------------------------//
                    graph_action_condition_node = addActionConditionDetailInACNode(tutorGraphObject, graph_action_condition_node);

                    tutorGraphObject.getActionConditionList().add(graph_action_condition_node);
                    //------------------projection-----------------//

                    List<TutorGraphNode> graph_proection_nodes_list = new ArrayList<>();

                    graph_proection_nodes_list.addAll(addProjectionNode(effectExp,graph_action_condition_node,conditionExp));

//                    graph_proection_nodes_list.addAll(addProjectionNode(effectExp
//                            , graph_action_condition_node
//                            , conditionExp));


                    tutorGraphObject.getProjectionList().addAll(graph_proection_nodes_list);

                    //action node add action condition node as a child
                    actionNode.addChildNode(graph_action_condition_node);

                }
            }
        }
    }

    private static List<TutorGraphNode> addProjectionNode(Exp effectExp, TutorGraphNode graph_action_condition_node
            , Exp conditionExp)
    {

        List<TutorGraphNode> graph_projection_node_list = new ArrayList<>();
        // Mes - add IF to handle ONEOF
        if(effectExp.getConnective().equals(Connective.ONEOF)){
            OneOfGroup oneOfGroup = new OneOfGroup();

            for(Exp each_oneof_probAtom_exp : effectExp.getChildren()){
                TutorGraphNode graph_projection_node = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PROJECTION);
                Exp effect = each_oneof_probAtom_exp.getChildren().get(1);
                double probValue = each_oneof_probAtom_exp.getChildren().get(0).getValue();

                OneOfNode oneOfNode = new OneOfNode();
                oneOfNode.setExp(effect);
                oneOfNode.setProbabilityValue(probValue);
                oneOfGroup.addOneOfNode(oneOfNode);

                graph_projection_node.setProjectionExp(effect);
                graph_projection_node.addParentNode(graph_action_condition_node);
                graph_projection_node.setOneOf(true);
                graph_projection_node.setOneOfProbability(probValue);
                graph_projection_node.setOneOfGroup(oneOfGroup);

                oneOfGroup.addOneOfTutorGraphNode(graph_projection_node);

                //action condition node add projection node as a child
                graph_action_condition_node.addChildNode(graph_projection_node);

                graph_projection_node_list.add(graph_projection_node);
            }
        } else {
            TutorGraphNode graph_projection_node = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PROJECTION);
            graph_projection_node.setProjectionExp(effectExp);
            graph_projection_node.addParentNode(graph_action_condition_node);

            //action condition node add projection node as a child
            graph_action_condition_node.addChildNode(graph_projection_node);

            graph_projection_node_list.add(graph_projection_node);
        }

        return graph_projection_node_list;
    }

//    private static List<TutorGraphNode> addProjectionNode(Exp effectExp, TutorGraphNode graph_action_condition_node
//            , Exp conditionExp)
//    {
//
//        List<TutorGraphNode> graph_projection_node_list = new ArrayList<>();
//        List<Exp> effectExpList = new ArrayList<>();
//
//        if(effectExp.getChildren().size() > 1) {
//            for (Exp each_projection_exp : effectExp.getChildren()) {
//                effectExpList.add(each_projection_exp);
//            }
//        }
//        else
//        {
//            effectExpList.add(effectExp);
//        }
//
//        for (Exp each_projection_exp : effectExpList) {
//
//            // Mes - add IF to handle ONEOF
//            if(each_projection_exp.getConnective().equals(Connective.ONEOF)){
//                OneOfGroup oneOfGroup = new OneOfGroup();
//
//                for(Exp each_oneof_probAtom_exp : each_projection_exp.getChildren()){
//                    TutorGraphNode graph_projection_node = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PROJECTION);
//                    Exp effect = each_oneof_probAtom_exp.getChildren().get(1);
//                    double probValue = each_oneof_probAtom_exp.getChildren().get(0).getValue();
//
//                    OneOfNode oneOfNode = new OneOfNode();
//                    oneOfNode.setExp(effect);
//                    oneOfNode.setProbabilityValue(probValue);
//                    oneOfGroup.addOneOfNode(oneOfNode);
//
//                    graph_projection_node.setProjectionExp(effect);
//                    graph_projection_node.addParentNode(graph_action_condition_node);
//                    graph_projection_node.setOneOf(true);
//                    graph_projection_node.setOneOfProbability(probValue);
//                    graph_projection_node.setOneOfGroup(oneOfGroup);
//
//                    oneOfGroup.addOneOfTutorGraphNode(graph_projection_node);
//
//                    //action condition node add projection node as a child
//                    graph_action_condition_node.addChildNode(graph_projection_node);
//
//                    graph_projection_node_list.add(graph_projection_node);
//                }
//            } else {
//                TutorGraphNode graph_projection_node = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PROJECTION);
//                graph_projection_node.setProjectionExp(each_projection_exp);
//                graph_projection_node.addParentNode(graph_action_condition_node);
//
//                //action condition node add projection node as a child
//                graph_action_condition_node.addChildNode(graph_projection_node);
//
//                graph_projection_node_list.add(graph_projection_node);
//            }
//        }
//
//        return graph_projection_node_list;
//    }

    private static TutorGraphNode addActionConditionNode(Exp conditionExp, TutorGraphNode eachNode, String actionName)
    {
        TutorGraphNode graph_action_condition_node
                = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION);

        graph_action_condition_node.setActionCondition(conditionExp);
        graph_action_condition_node.addParentNode(eachNode);

        return graph_action_condition_node;
    }

    private static void updateDOInProjectionNode(PlanAction planAction, TutorGraph tutorGraphObject)
    {
        List<TutorGraphNode> projectionNodeList = new ArrayList<>();
        projectionNodeList.addAll(tutorGraphObject.getProjectionList());

        Exp desired_outcome = PlanActionUtil.getDesiredOutcome(planAction);
        List<Exp> desired_outcome_ExpList = new ArrayList<>();
        desired_outcome_ExpList = getGoalExpList( desired_outcome, desired_outcome_ExpList );

        for (TutorGraphNode eachProjectionNode : projectionNodeList) {

            for(Exp each_desired_outcome : desired_outcome_ExpList)
            {
                if(eachProjectionNode.getProjectionExp().equals(each_desired_outcome))
                {
                    eachProjectionNode.setDesiredOutCome(true);
                    if(each_desired_outcome.isMain()){
                        eachProjectionNode.setMain(true);
                    }
                }
            }
        }
    }

    private static void removeEffectInPerceptionNode(TutorGraph tutorGraphObject)
    {
        List<Exp> effectExpList = new ArrayList<>();

        for(TutorGraphNode each_projection_node : tutorGraphObject.getProjectionList())
        {
            if(each_projection_node.getProjectionExp().getChildren().size() > 1) {
                for (Exp each_projection_exp : each_projection_node.getProjectionExp().getChildren()) {
                    effectExpList.add(each_projection_exp);
                }
            }
            else
            {
                effectExpList.add(each_projection_node.getProjectionExp());
            }
        }

        List<TutorGraphNode> clone_perception_list = new ArrayList<>();

        for(TutorGraphNode each_perception_node : tutorGraphObject.getPerceptionList())
        {
            boolean found = isFoundThisPerceptionInEffectList(each_perception_node.getPerceptionExp(), effectExpList);
            if(!found)
            {
                clone_perception_list.add(each_perception_node);
            }
        }

        tutorGraphObject.getPerceptionList().clear();
        tutorGraphObject.getPerceptionList().addAll(clone_perception_list);
    }

    private static boolean isFoundThisPerceptionInEffectList(Exp perceptionExp, List<Exp> effectExpList)
    {
        List<Exp> perception_exp_list = InferenceEngineUtil.getTutorPerceptionListFromAxiom(perceptionExp);

        if (perception_exp_list.size() != 0) {
            return false;
        }

        for (Exp each_effect_exp : effectExpList)
        {
            String each_effect_exp_string = getPredicateString(each_effect_exp);
            String perceptionExpString = getPredicateString(perceptionExp);

            if(each_effect_exp_string.contains(" "))
            {
                String[] split_text = each_effect_exp_string.split(" ");
                each_effect_exp_string = split_text[0];
            }

            if(perceptionExpString.contains(" "))
            {
                String[] split_text = perceptionExpString.split(" ");
                perceptionExpString = split_text[0];
            }

            if(each_effect_exp_string.equals(perceptionExpString)) return true;
        }
        return false;
    }

    private static String getPredicateString(Exp expValue)
    {
        String string = "";
        if(expValue.getConnective().equals(Connective.NOT)) {

            //if this predicate has constant value
            if(expValue.getChildren().get(0).getAtom().size() < 2) {
//                System.out.println("toey not " + exp.getChildren().get(0).getAtom().get(0));

                string = expValue.getChildren().get(0).getAtom().get(0).toString();
            }
            else {
                string = expValue.toString().substring(6, expValue.toString().length() - 2);
            }
        }
        else
        {
            //if this predicate has constant value
            if(expValue.getAtom() != null)
            {
                if(expValue.getAtom().size() < 2) {
                    string = expValue.getAtom().get(0).toString();
                }
                else string = expValue.toString().substring(1, expValue.toString().length()-1);
            }
        }

        return string;
    }

    public static HashMap<String, String> getPerceptionWithComprehensionMap(Exp conditionExp
            , TutorGraph tutorGraphObject)
    {
        HashMap<String, String> PerceptionWithComprehensionMap = new HashMap<>();
        HashMap<String, String> clone_PerceptionWithComprehensionMap = new HashMap<>();
        HashMap<String, Exp> clone_PredicateExpDataMap = new HashMap<>();

        clone_PredicateExpDataMap.putAll(getPredicateExpDataFromGraphNode(tutorGraphObject));
        clone_PerceptionWithComprehensionMap.putAll(getPercAndCompFromGraphNode(tutorGraphObject));

        if(conditionExp.getChildren().size() != 0 && !conditionExp.getConnective().equals(Connective.NOT))
        {
            for (Exp each_condition : conditionExp.getChildren()) {

                PerceptionWithComprehensionMap.putAll(getPerceptionAndComprehensionByComparingAC(each_condition
                        ,clone_PredicateExpDataMap, clone_PerceptionWithComprehensionMap));
            }
        }
        else
        {
            PerceptionWithComprehensionMap.putAll(getPerceptionAndComprehensionByComparingAC(conditionExp
                    ,clone_PredicateExpDataMap, clone_PerceptionWithComprehensionMap));
        }

        return PerceptionWithComprehensionMap;
    }

    public static HashMap<String, Exp> getPredicateExpDataFromGraphNode(TutorGraph tutorGraphObject)
    {
        HashMap<String, Exp> clone_PredicateExpDataMap = new HashMap<>();


        clone_PredicateExpDataMap.putAll(tutorGraphObject.getPerceptionAndComprehensionNode().getPredicateExpDataHashMap());


        return clone_PredicateExpDataMap;
    }

    public static HashMap<String, String> getPercAndCompFromGraphNode(TutorGraph tutorGraphObject)
    {
        HashMap<String, String> clone_PercAndCompMap = new HashMap<>();

        clone_PercAndCompMap.putAll(tutorGraphObject.getPerceptionAndComprehensionNode().getPerceptionWithComprehensionHashMap());


        return clone_PercAndCompMap;
    }

    private static List<Exp> getExpValueFromExpList(List<Exp> expList)
    {
        List<Exp> predicate_list = new ArrayList<>();

        for(Exp each_expList : expList)
        {
            //if this exp list has children
            if(each_expList.getChildren().size() != 0 && !each_expList.getConnective().equals(Connective.NOT))
            {
                for(Exp each_children_expList: each_expList.getChildren())
                {
                    //Mes - add switch case to support condition
                    switch(each_children_expList.getConnective()){
                        case ONEOF:
                            for(Exp each_oneof_children_expList : each_children_expList.getChildren()) {
                                    predicate_list.add(each_oneof_children_expList.getChildren().get(1));
                            }
                            break;
                        default:
                            if(!predicate_list.contains(each_children_expList)) predicate_list.add(each_children_expList);
                            break;
                    }
                }
            }
            else
            {
                if(!predicate_list.contains(each_expList)) predicate_list.add(each_expList);
            }
        }

        return predicate_list;
    }

    public static TutorGraphNode putAllPredicateExpData(TutorGraphNode graphNode, Exp each_predicate)
    {
        HashMap<String, String> perceptionToComprehensionMap = new HashMap<>();
        HashMap<String, Exp> predicateExpData = new HashMap<>();

        if(StringUtil.getSubStringFromExpList(each_predicate).size() != 0)
        {
            for(String each_predicate_string : StringUtil.getSubStringFromExpList(each_predicate)) {
                perceptionToComprehensionMap.put(each_predicate_string, null);

                if(each_predicate.getConnective().equals(Connective.EQUAL)
                        || each_predicate.getConnective().equals(Connective.FN_ATOM))
                {
//                    System.out.println("toey " + each_predicate.getChildren().get(0));
                    predicateExpData.put(each_predicate_string, each_predicate.getChildren().get(0));
                }
                else if(each_predicate.getConnective().equals(Connective.LESS)
                        || each_predicate.getConnective().equals(Connective.LESS_OR_EQUAL)
                        || each_predicate.getConnective().equals(Connective.GREATER)
                        || each_predicate.getConnective().equals(Connective.GREATER_OR_EQUAL))
                {
                    for(Exp child : each_predicate.getChildren())
                    {
                        String func_string = child.toString()
                                .substring(1, child.toString().length()-1);

                        if(each_predicate_string.equals(func_string)) predicateExpData.put(each_predicate_string, child);
                    }

                }
                else if(each_predicate.getConnective().equals(Connective.ATOM) && each_predicate.getAtom().size() == 2 )
                {
                    Exp node = new Exp(Connective.ATOM);
                    Symbol symbol = each_predicate.getAtom().get(0);
                    List<Symbol> symbol_list = new ArrayList<Symbol>();
                    symbol_list.add(symbol);
                    node.setAtom(symbol_list);

//                  System.out.println("toey "+ node);
                    predicateExpData.put(each_predicate_string, node);
                }
                else if(each_predicate.getConnective().equals(Connective.NOT))
                {
                    if(each_predicate.getChildren().size() != 0)
                    {
                        Exp atom_node = new Exp(Connective.ATOM);
                        Symbol symbol = each_predicate.getChildren().get(0).getAtom().get(0);
                        List<Symbol> symbol_list = new ArrayList<Symbol>();
                        symbol_list.add(symbol);
                        atom_node.setAtom(symbol_list);

                        Exp not_node = new Exp(Connective.NOT);
                        not_node.addChild(atom_node);

//                        System.out.println("toey "+ not_node);
                        predicateExpData.put(each_predicate_string, not_node);
                    }
                }
                else
                {
                    predicateExpData.put(each_predicate_string, each_predicate);
                }
            }
        }

        graphNode.getPerceptionWithComprehensionHashMap().putAll(perceptionToComprehensionMap);
        graphNode.getPredicateExpDataHashMap().putAll(predicateExpData);

        return graphNode;
    }

    private static TutorGraphNode addActionConditionDetailInACNode(TutorGraph tutorGraphObject, TutorGraphNode graph_action_condition_node)
    {
        List<TutorGraphNode[]> clonePerceptionWithComprehensionList = new ArrayList<>();
        clonePerceptionWithComprehensionList.addAll(graph_action_condition_node.
                getPerceptionWithComprehensionList());

        //firstly, add User Action predicate
        for (TutorGraphNode[] percepWithCompString : graph_action_condition_node.
                getPerceptionWithComprehensionList())
        {
            for (TutorActionConditionDetails acDetails : tutorGraphObject.getActionNode().getAcDetailsList())
            {
                if(percepWithCompString[0].getPerceptionExp().getConnective().equals(Connective.NOT))
                {
                    Symbol acDetailSymbol = GraphUtil.getExpSymbol(acDetails.getExp());
                    if(acDetailSymbol.equals(
                            percepWithCompString[0].getPerceptionExp().getChildren().get(0).getAtom().get(0)))
                    {
                        TutorActionConditionDetails tutorActionDetail = new TutorActionConditionDetails();
                        tutorActionDetail.setExp(acDetails.getExp());
                        tutorActionDetail.setType(acDetails.getType());
                        tutorActionDetail.setRefGraphNode(acDetails.getRefGraphNode());

                        clonePerceptionWithComprehensionList.remove(percepWithCompString);

                        graph_action_condition_node.getAcDetailsList().add(tutorActionDetail);
                    }
                }
                else if(acDetails.getExp().equals(percepWithCompString[0].getPerceptionExp()))
                {
                    TutorActionConditionDetails tutorActionDetail = new TutorActionConditionDetails();
                    tutorActionDetail.setExp(acDetails.getExp());
                    tutorActionDetail.setType(acDetails.getType());
                    tutorActionDetail.setRefGraphNode(acDetails.getRefGraphNode());

                    clonePerceptionWithComprehensionList.remove(percepWithCompString);

                    graph_action_condition_node.getAcDetailsList().add(tutorActionDetail);
                }
            }
        }

        //secondly, add perception and comprehension predicate
        for (TutorGraphNode[] percepWithCompString : clonePerceptionWithComprehensionList)
        {
            TutorActionConditionDetails tutorPerceptionDetail = new TutorActionConditionDetails();
            TutorGraphNode perceptionNode = getPerceptionNode(tutorGraphObject, percepWithCompString[0].getPerceptionExp());
            if(perceptionNode != null)
            {
                tutorPerceptionDetail.setExp(perceptionNode.getPerceptionExp());
                tutorPerceptionDetail.setType(perceptionNode.getNodeType());
                tutorPerceptionDetail.setRefGraphNode(perceptionNode);

                graph_action_condition_node.getAcDetailsList().add(tutorPerceptionDetail);
            }


            if(percepWithCompString[1].getComprehensionExp() != null)
            {
                TutorActionConditionDetails tutorComprehensionDetail = new TutorActionConditionDetails();
                TutorGraphNode compNode = getComprehensionNode(tutorGraphObject, percepWithCompString[1].getComprehensionExp());
                if(compNode != null)
                {
                    tutorComprehensionDetail.setExp(compNode.getComprehensionExp());
                    tutorComprehensionDetail.setType(compNode.getNodeType());
                    tutorComprehensionDetail.setRefGraphNode(compNode);

                    graph_action_condition_node.getAcDetailsList().add(tutorComprehensionDetail);
                }
            }
        }

        return graph_action_condition_node;
    }

    private static TutorGraphNode getPerceptionNode(TutorGraph tutorGraphObject,Exp perceptionExp)
    {
        for(TutorGraphNode each_perception_node : tutorGraphObject.getPerceptionList())
        {
            if(each_perception_node.getPerceptionExp().equals(perceptionExp))
            {
                return each_perception_node;
            }
        }

        return null;
    }

    private static TutorGraphNode getComprehensionNode(TutorGraph tutorGraphObject,Exp comprehensionExp)
    {
        for(TutorGraphNode each_comp_node : tutorGraphObject.getComprehensionList())
        {
            if(each_comp_node.getComprehensionExp().equals(comprehensionExp))
            {
                return each_comp_node;
            }
        }

        return null;
    }

    public static List<TutorGraphNode[]> getPerceptionWithComprehensionListFromMap(HashMap<String, String> perceptionWithComprehensionMap
            , TutorGraph tutorGraphObject)
    {
        List<TutorGraphNode[]> perceptionWithComprehensionList = new ArrayList<>();

        for (Map.Entry<String, Exp> predicateExp : tutorGraphObject.getPerceptionAndComprehensionNode().getPredicateExpDataHashMap().entrySet())
        {
            for (Map.Entry<String, String> percepWithCompString : perceptionWithComprehensionMap.entrySet())
            {
                TutorGraphNode[] tutorGraphNodesArray = new TutorGraphNode[2];

                TutorGraphNode perceptionNode = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_PERCEPTION);
                TutorGraphNode comprehensionNode = new TutorGraphNode(GraphNodeConstants.NODE_TYPE_COMPREHENSION);

                if(percepWithCompString.getKey().equals(predicateExp.getKey()))
                {
                    perceptionNode.setPerceptionExp(predicateExp.getValue());
                    if(percepWithCompString.getValue() != null) {
                        comprehensionNode.setComprehensionExp(getComprehensionExpFromHashMap(percepWithCompString.getValue(), tutorGraphObject));
                    }

                    tutorGraphNodesArray[0] = perceptionNode;
                    tutorGraphNodesArray[1] = comprehensionNode;

                    perceptionWithComprehensionList.add(tutorGraphNodesArray);
                }
            }
        }

        return perceptionWithComprehensionList;
    }

    private static Exp getComprehensionExpFromHashMap(String comprehensionString, TutorGraph tutorGraphObject)
    {
        Exp comprehensionExp = null;

        for (Map.Entry<String, Exp> predicateExp : tutorGraphObject.getPerceptionAndComprehensionNode().getPredicateExpDataHashMap().entrySet()) {

            if(comprehensionString.equals(predicateExp.getKey()))
            {
                comprehensionExp = predicateExp.getValue();
            }
        }

        return comprehensionExp;
    }

    public static void printAllTutorGraph()
    {
        System.err.println("----------------------------------- Tutor Graph -----------------------------------");
        for(TutorGraph each_tutor_graph : tutorGraphList) {

            System.err.println("----------------------------------- Graph -----------------------------------");

            System.out.println("Perception & Comprehension");
            for (Map.Entry<String, String> percepWithComp : each_tutor_graph.getPerceptionAndComprehensionNode().getPerceptionWithComprehensionHashMap().entrySet()) {
                System.out.println("   Perception: " + percepWithComp.getKey()
                                + " -> Comprehension " + percepWithComp.getValue());
            }
            System.err.println("   Predicate & Exp");
            for (Map.Entry<String, Exp> predicateExpData : each_tutor_graph.getPerceptionAndComprehensionNode().getPredicateExpDataHashMap().entrySet()) {
                System.out.println("   Predicate: " + predicateExpData.getKey()
                        + " -> Exp " + predicateExpData.getValue() + " " + predicateExpData.getValue().getConnective());
            }
            if (!each_tutor_graph.getPerceptionAndComprehensionNode().getChildNodes().isEmpty()) {
                printChildTutorGraph(each_tutor_graph.getPerceptionAndComprehensionNode());
            }
            System.err.println("---------------------------------------------------------------------------");

            for (TutorGraphNode each_projection : each_tutor_graph.getPerceptionList()) {
                System.out.println("Perception: " + each_projection.getPerceptionExp());
                if (!each_projection.getChildNodes().isEmpty()) {

                    printChildTutorGraph(each_projection);

                }
                if(!each_projection.getParentNodes().isEmpty())
                {
                    printParentTutorGraph(each_projection);
                }
                System.err.println("---");
            }
            System.err.println("---------------------------------------------------------------------------");

            for (TutorGraphNode each_projection : each_tutor_graph.getComprehensionList()) {
                System.out.println("Comprehension: " + each_projection.getComprehensionExp());
                if (!each_projection.getChildNodes().isEmpty()) {

                    printChildTutorGraph(each_projection);

                }
                if(!each_projection.getParentNodes().isEmpty())
                {
                    printParentTutorGraph(each_projection);
                }
                System.err.println("---");
            }
            System.err.println("---------------------------------------------------------------------------");

            System.out.println("Action name: " + each_tutor_graph.getActionNode().getActionNode().getActionName());
            for(TutorActionConditionDetails acDetailsNode : each_tutor_graph.getActionNode().getAcDetailsList())
            {
                System.out.println("   Action Detail: Exp " + acDetailsNode.getExp());
                System.out.println("                  Type " + acDetailsNode.getType());
                System.out.println("                  RefGraphNode " + acDetailsNode.getRefGraphNode().getNodeType());
            }
            if (!each_tutor_graph.getActionNode().getChildNodes().isEmpty()) {

                printChildTutorGraph(each_tutor_graph.getActionNode());

            }
            if(!each_tutor_graph.getActionNode().getParentNodes().isEmpty())
            {
                printParentTutorGraph(each_tutor_graph.getActionNode());
            }
            System.err.println("---------------------------------------------------------------------------");

            for (TutorGraphNode each_action_condition : each_tutor_graph.getActionConditionList()) {
                System.out.println("Action Condition: " + each_action_condition.getActionCondition());

                for(TutorActionConditionDetails acDetailsNode : each_action_condition.getAcDetailsList())
                {
                    System.out.println("Action Detail: Exp " + acDetailsNode.getExp());
                    System.out.println("               Type " + acDetailsNode.getType());
//                    System.out.println("               RefGraphNode " + acDetailsNode.getRefGraphNode().getNodeType());
                }

                System.out.println("Perception & Comprehension");
                for (Map.Entry<String, String> percepWithComp : each_action_condition.getPerceptionWithComprehensionHashMap().entrySet()) {
                    System.out.println("   Perception: " + percepWithComp.getKey()
                            + " -> Comprehension " + percepWithComp.getValue());
                }
                System.out.println("Perception & Comprehension ArrayList");
                for(TutorGraphNode[] percepWithCompList : each_action_condition.getPerceptionWithComprehensionList())
                {
                    System.out.println("   Perception: " + percepWithCompList[0].getPerceptionExp()
                            + " -> Comprehension " + percepWithCompList[1].getComprehensionExp());
                }

                if (!each_action_condition.getChildNodes().isEmpty()) {

                    printChildTutorGraph(each_action_condition);

                }
                if(!each_action_condition.getParentNodes().isEmpty())
                {
                    printParentTutorGraph(each_action_condition);
                }
                System.err.println("---");
            }
            System.err.println("---------------------------------------------------------------------------");

            for (TutorGraphNode each_projection : each_tutor_graph.getProjectionList()) {
                System.out.println("Projection: " + each_projection.getProjectionExp());
                System.out.println("   is_desired_outcome: " + each_projection.isDesiredOutCome());
                System.out.println("   is_allow_explore: " + each_projection.isAllowExplore());
                if (!each_projection.getChildNodes().isEmpty()) {

                    printChildTutorGraph(each_projection);

                }
                if(!each_projection.getParentNodes().isEmpty())
                {
                    printParentTutorGraph(each_projection);
                }
                System.err.println("---");
            }
            System.err.println("------------------------------------ End ---------------------------------------");
        }
    }

    public static void printChildTutorGraph(TutorGraphNode child_details) {
        for (TutorGraphNode child_node : child_details.getChildNodes()) {
            System.out.println("--------> child " + child_node.getNodeType());

            if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_PERCEPANDCOMP)) {
                System.out.println("Perception & Comprehension");
            } else if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION)) {
                System.out.println("                Action name: " + child_node.getActionNode().getActionName());
            } else if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION)) {
                System.out.println("                Action Condition: " + child_node.getActionCondition());
            } else if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_PROJECTION)) {
                System.out.println("                Projection: " + child_node.getProjectionExp());
            }
        }
    }

    public static void printParentTutorGraph(TutorGraphNode parent_details)
    {
        if (!parent_details.getParentNodes().isEmpty()) {
            for (TutorGraphNode parent_node : parent_details.getParentNodes()) {
                System.out.println("--------> parent " + parent_node.getNodeType());

                if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_PERCEPANDCOMP)) {
                    System.out.println("Perception & Comprehension");
                } else if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION)) {
                    System.out.println("                Action name: " + parent_node.getActionNode().getActionName());
                } else if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION)) {
                    System.out.println("                Action Condition: " + parent_node.getActionCondition());
                } else if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_PROJECTION)) {
                    System.out.println("                Projection: " + parent_node.getProjectionExp());
                }
            }
        }
    }

    public static TutorGraph getTutorGraphByAction( PlanAction action )
    {
        PlanAction planAction = (action == null) ? ApplicationController.currentAction : action;

        TutorGraph resultTutorGraph = null;

        for ( TutorGraph tGraph :  tutorGraphList )
        {
            PlanAction tAction = tGraph.getActionNode().getActionNode();

            if( tAction.getActionName().equals( planAction.getActionName() ) )
            {
                resultTutorGraph = tGraph;
                break;
            }
        }

        return resultTutorGraph;
    }

    public static List<TutorGraphNode> getAcNodeListByAction( PlanAction action )
    {
        if( action == null )
        {
            //Get current action
            action = ApplicationController.currentAction;
        }

        List<TutorGraph> tutorGraphList = ApplicationController.tutorGraphList;
        TutorGraph tutorGraph = TutorGraphUtil.getTutorGraphByAction( action );
        List<TutorGraphNode> tutorAcNodeList = tutorGraph.getActionConditionList();

        return tutorAcNodeList;

    }

    public static List<TutorGraphNode> getPjNodeListByAction( PlanAction action )
    {
        if( action == null )
        {
            //Get current action
            action = ApplicationController.currentAction;
        }

        List<TutorGraph> tutorGraphList = ApplicationController.tutorGraphList;
        TutorGraph tutorGraph = TutorGraphUtil.getTutorGraphByAction( action );
        List<TutorGraphNode> tutorAcNodeList = tutorGraph.getProjectionList();

        return tutorAcNodeList;

    }
    public static boolean isAllPjSatisfyDO( List<TutorGraphNode> pjNodeList  )
    {
        boolean isPjSatisfyDO = true;

        for( TutorGraphNode item : pjNodeList )
        {
            if( item.isDesiredOutCome() )
            {
                isPjSatisfyDO = true;
                continue;
            }
            else
            {
                isPjSatisfyDO = false;
                break;
            }
        }

        return isPjSatisfyDO;
    }

}

